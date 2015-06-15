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
public class Switch extends Thread
{
    private final Rule[] rules;
    private final int[] ports;

    public Switch(Rule[] rules, int... ports)
    {
        this.rules = rules;
        this.ports = ports;
    }

    @Override
    public void run()
    {
        for(int i = 0; i < ports.length; i++)
        {
            final int portNumber = i;
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try (ServerSocket serverSocket = new ServerSocket(ports[portNumber]))
                    {
                        do
                        {
                            try(Socket clientSocket = serverSocket.accept();
                                ObjectInputStream ois = new ObjectInputStream(
                                    new BufferedInputStream(
                                            clientSocket.getInputStream()))
                            )
                            {
                                //Receive TestPacket
                                TestPacket tp = (TestPacket) ois.readObject();
                                int dest = tp.getDestination();
                                int portToSend = 0;
                                for (Rule rule : rules)
                                {
                                    if (dest == rule.getDestination())
                                    {
                                        portToSend = rule.getPort();
                                    }
                                }

                                final int tmpPort = portToSend;
                                new Thread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        //Send TestPacket to next Switch
                                        try (Socket socket = new Socket("localhost", tmpPort);
                                             ObjectOutputStream oos = new ObjectOutputStream(
                                                     new BufferedOutputStream(socket.getOutputStream()))
                                        )
                                        {
                                            oos.writeObject(tp);
                                            oos.flush();
                                        } catch (Exception e)
                                        {
                                            System.out.println("ERROR by writing " + tp);
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        }
                        while(true);
                    } catch (Exception e)
                    {
                        System.out.println("ERROR");
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
