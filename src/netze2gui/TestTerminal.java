package netze2gui;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by Joncn on 15.06.2015.
 * This class represents ATPG-testterminals in the network
 */
public class TestTerminal extends Thread
{
    private static final int PACKET_TIMEOUT = 7000;
    private static final int TIME_BETWEEN_PACKETS = 1000;

    private final int[] ports;

    private Mutex mutex = new Mutex();
    private Mutex sendMutex = new Mutex();
    private Mutex readMutex = new Mutex();

    private List<TestPacket> receivedPackets = new ArrayList<>();
    private List<TestPacket> missingPackets = new ArrayList<>();

    public TestTerminal(int... ports)
    {
        this.ports = ports;
    }

    public void run()
    {
        /*
        starting several threads where each thread has a serversocket
        and is waiting for Testterminals to receive a Testpacket
         */
        for (int i = 0; i < ports.length; i++)
        {
            final int portIndex = i;
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try (ServerSocket ss = new ServerSocket(ports[portIndex]))
                    {
                        do
                        {
                            try (Socket socket = ss.accept();
                                 ObjectInputStream ois = new ObjectInputStream(
                                         socket.getInputStream())
                            )
                            {
                                TestPacket tp = (TestPacket) ois.readObject();
                                receivedPackets.add(tp);
                                System.out.println(tp.getDestination().replaceAll("Switch", "Terminal") + " received " + tp);
                                System.out.println("----- " + tp + " -----");
                                sleep(TIME_BETWEEN_PACKETS);
                                mutex.release();
                            } catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        while (true);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        } //End Testterminal Server Thread

        /*
            This loops sends the testpackets to the switches.
         */
        while (true)
        {
            try
            {   //wait for the permission to start sending
                sendMutex.acquire();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            //clear up
            receivedPackets.clear();
            missingPackets.clear();
            boolean timeout = false;
            for (int i = 0; i < Config.neededPackets.length; i++)
            {
                try (Socket so = new Socket("localhost", Config.ports.get(Config.neededPackets[i].getFirstHop()));
                     ObjectOutputStream oos = new ObjectOutputStream(
                             so.getOutputStream())
                )
                {
                    TestPacket tp = Config.neededPackets[i]; //current testpacket
                    mutex.attempt(PACKET_TIMEOUT);
//                    if (mutex.attempt(PACKET_TIMEOUT))
//                    {
//
//                        timeout = false;
//                    }
//                    else
//                    {
//                        timeout = true;
//                    }
//                    if (timeout)
//                    {
//                        //when a timeout occurred the a switch or link are faulty
//                        setPacketWrong(); //in case a switch rule is faulty, this method will display it.
//                        sleep(1500);
//                    }
                    System.out.println("");
                    System.out.println("----- " + tp + " -----");
                    System.out.println(tp.getFirstHop().replaceAll("Switch", "Terminal") + " sending " + tp);
                    oos.writeObject(tp);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }//sending all testpackets done.
            try
            {
                mutex.attempt(PACKET_TIMEOUT);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } finally
            {
                mutex.release();
            }

            try
            {
                mutex.acquire();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            /*
                check if all received packets are the needed Packets as well.
                If not, at least one Packet got lost.
                Error!
             */
            if (receivedPackets.size() != Config.neededPackets.length)
            {
                findMissingPackets();
                Set<String> missingRules = null;
                if (missingPackets.size() > 0)
                {
                    missingRules = findMissingRules();
                }

                /*
                if there is more than one rule in the list of possible faulty rules start the "ATPG-Algorithm"
                to pinpoint the faulty rule
                 */
                if (missingRules.size() > 1)
                {
                    findFailure(missingRules);
                    mutex.release();
                }
                else
                {
                    System.err.println("");
                    System.err.println("Failure found: " + missingRules);
                    mutex.release();
                }
            }
            //readMutex.release();
        }
    }

    private void findFailure(Set<String> missingRules)
    {
        receivedPackets.clear();
        mutex.release();
        /*
        if there are any reserve packets for failure detection/pinpointing, send them.
         */
        for (int i = 0; i < Config.reservedPackets.length; i++)
        {
            try (Socket so = new Socket("localhost", Config.ports.get(Config.reservedPackets[i].getFirstHop()));
                 ObjectOutputStream oos = new ObjectOutputStream(
                         new BufferedOutputStream(
                                 so.getOutputStream()))
            )
            {
                mutex.attempt(PACKET_TIMEOUT);
                TestPacket tp = Config.reservedPackets[i];
                System.err.println("Possible Failures: " + missingRules);
                System.err.println("");
                System.err.println("----- " + tp + " to find failure -----");
                System.out.println(tp.getFirstHop().replaceAll("Switch", "Terminal") + " sending " + tp + " to");
                oos.writeObject(tp);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            mutex.attempt(PACKET_TIMEOUT);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        /*
            after all reserved packets have been sent, ATPG is trying to pointing the faulty rule.
         */
        missingRules = removeRules(missingRules);
        if (missingRules.size() > 1)
        {
            //multiple faulty rules.
            //ATPG wasn't able to pinpoint the failure count down to 1
            System.err.println("");
            System.err.println("Possible Failures: " + missingRules);
        } else
        {
            //ATPG found the exact fault. No other possibilities
            System.err.println("");
            System.err.println("Failure found: " + missingRules);
        }
        try
        {
            sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        readMutex.release();
    }

    private void findMissingPackets()
    {
        for (TestPacket tp : Config.neededPackets)
        {
            if (!receivedPackets.contains(tp))
            {
                missingPackets.add(tp);
            }
        }
    }


    private Set<String> findMissingRules()
    {
        System.err.println("");
        System.err.println("----- FAILURE IN NETWORK -----");
        Set<String> missingRules = new HashSet<>();
        for (TestPacket p : missingPackets)
        {
            Collections.addAll(missingRules, p.getRuleHistory());
        }
        System.err.println("all possible missing rules are" + missingRules);
        /*
        clear up
         */

        for (TestPacket p : receivedPackets)
        {
//            System.err.println("received successfully: " + p);
            for (String s : p.getRuleHistory())
            {
                if (missingRules.contains(s))
                {
                    System.err.println("found rule, removing from faulty rule set" + s);
                    missingRules.remove(s);
                }
            }
        }
        System.out.println("Missing Rules: " + missingRules);
        return missingRules;
    }

    private Set<String> removeRules(Set<String> missingRules)
    {
        for (TestPacket tp : receivedPackets)
        {
            for (String s : tp.getRuleHistory())
            {
                if (missingRules.contains(s))
                {
                    missingRules.remove(s);
                }
            }
        }
        return missingRules;
    }

    private void setPacketWrong()
    {
        System.err.println("setpacketwrong");
        Main.mainWindow.changePictureToWrong();
    }

    public void releaseSendMutex()
    {
        sendMutex.release();
    }

    public void acquireSendMutex() throws InterruptedException
    {
        sendMutex.acquire();
    }


    public void releaseReadMutex()
    {
        readMutex.release();
    }

    public void acquireReadMutex() throws InterruptedException
    {
        readMutex.acquire();
    }
}
