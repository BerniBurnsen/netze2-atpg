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
    private boolean send = false;

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
                                        new BufferedInputStream(
                                                socket.getInputStream()))
                            )
                            {
                                TestPacket tp = (TestPacket) ois.readObject();
                                receivedPackets.add(tp);
                                System.out.println(tp.getDestination() + " gets Packet: " + tp);
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
        System.out.println("TESTTERMINAL: Sockets opened");

        try
        {
            Thread.sleep(3000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        while(true)
        {
            if(send)
            {
                receivedPackets.clear();
                missingPackets.clear();
                System.out.println("TESTTERMINAL: sending...");
                for(int i = 0; i < Config.neededPackets.length; i++)
                {
                    try (Socket so = new Socket("localhost", Config.ports.get(Config.neededPackets[i].getSource()));
                            ObjectOutputStream oos = new ObjectOutputStream(
                            new BufferedOutputStream(
                                    so.getOutputStream()))
                    )
                    {
                        System.out.println("Sending Packet: " + Config.neededPackets[i]);
                        oos.writeObject(Config.neededPackets[i]);
                    }
                    catch ( Exception e)
                    {
                        e.printStackTrace();
                    }
                    try
                    {
                        Thread.sleep(3000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                send = false;
            }

            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            //Check received Packets
            if(receivedPackets.size() != Config.neededPackets.length)
            {
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

                if(missingRules.size() > 1)
                {
                    //Find failure
                    for(int i = 0; i < Config.reservedPackets.length; i++)
                    {
                        try (Socket so = new Socket("localhost", Config.ports.get(Config.reservedPackets[i].getSource()));
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
                try
                {
                    Thread.sleep(3000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setSend(boolean toSend)
    {
        send = toSend;
    }
}
