import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by Joncn on 15.06.2015.
 */
public class TestTerminal extends Thread
{
    private final int[] ports;
    private boolean send = false;

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
                                System.out.println("Got Packet: " + tp);
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


        while(true)
        {
            if(send)
            {
                System.out.println("TESTTERMINAL: sending...");
                for(int i = 0; i < Config.neededPackets.length; i++)
                {
                    try (Socket so = new Socket("localhost", Config.neededPackets[i].getSource());
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
                }
                send = false;
            }
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setSend(boolean toSend)
    {
        send = toSend;
    }
}
