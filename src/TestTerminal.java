import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by Joncn on 15.06.2015.
 */
public class TestTerminal extends Thread
{
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
        for(int i = 0; i < ports.length; i++)
        {
            final int portIndex = i;
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try(ServerSocket ss = new ServerSocket(ports[portIndex]))
                    {
                        do
                        {
                            try(Socket socket = ss.accept();
                                ObjectInputStream ois = new ObjectInputStream(
                                                socket.getInputStream())
                            )
                            {
                                TestPacket tp = (TestPacket) ois.readObject();
                                receivedPackets.add(tp);
                                System.out.println(tp.getDestination().replaceAll("Switch", "Terminal") + " received " + tp);
                                System.out.println("----- " + tp + " -----");
                                sleep(3000);
                                mutex.release();
                            }
                            catch( Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        while(true);
                    }
                    catch ( Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        while(true)
        {
            try
            {
                sendMutex.acquire();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            {
                receivedPackets.clear();
                missingPackets.clear();
                for (int i = 0; i < Config.neededPackets.length; i++)
                {
//                    for(int j = 0; j < 5; j++)
//                    {
                        try (Socket so = new Socket("localhost", Config.ports.get(Config.neededPackets[i].getFirstHop()));
                             ObjectOutputStream oos = new ObjectOutputStream(
                                             so.getOutputStream())
                        )
                        {
                            TestPacket tp = Config.neededPackets[i];
                            mutex.attempt(10000);
                            System.out.println("");
                            System.out.println("----- " + tp + " -----");
                            System.out.println(tp.getFirstHop().replaceAll("Switch", "Terminal") + " sending " + tp);
                            oos.writeObject(Config.neededPackets[i]);
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
//                    }
                }
                try
                {
                    mutex.attempt(10000);
                    readMutex.release();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    mutex.release();
                }
            }
/*
            //Check received Packets
            if(receivedPackets.size() != Config.neededPackets.length)
            {
                System.out.println("packets missing");
                System.out.println("receivedPackets.size() = " + receivedPackets.size());
                System.out.println("Config.neededPackets.length = " + Config.neededPackets.length);
                for(TestPacket tp : Config.neededPackets)
                {
                    if(!receivedPackets.contains(tp))
                    {
                        missingPackets.add(tp);
                    }
                }
            }

            if(missingPackets.size() > 0)
            {
                System.out.println("missingPackets.size() = " + missingPackets.size());
                Set<String> missingRules = new HashSet<>();
                for(TestPacket p : missingPackets)
                {
                    Collections.addAll(missingRules, p.getRuleHistory());
                }
                for(TestPacket p : receivedPackets)
                {
                    for(String s : p.getRuleHistory())
                    {
                        if(missingRules.contains(s))
                        {
                            missingRules.remove(s);
                        }
                    }
                }
                System.out.println("Missing Rules: " + missingRules);

                if(missingRules.size() > 1)
                {
                    //Find failure
                    for(int i = 0; i < Config.reservedPackets.length; i++)
                    {
                        try (Socket so = new Socket("localhost", Config.ports.get(Config.reservedPackets[i].getFirstHop()));
                             ObjectOutputStream oos = new ObjectOutputStream(
                                     new BufferedOutputStream(
                                             so.getOutputStream()))
                        )
                        {
                            System.out.println("Sending Packet: " + Config.reservedPackets[i]);
                            oos.writeObject(Config.reservedPackets[i]);
                        }
                        catch ( Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    System.out.println("Failure found: " + missingRules);
                }
            }
            else
            {
                System.out.println("----- Network OK! ------ ");
            }*/
        }
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
