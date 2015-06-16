import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Joncn on 16.06.2015.
 */
public class Link extends Thread
{
    private final int port;
    private final Switch left;
    private final Switch right;
    private final String name;

    public Link(String name, int port, Switch left, Switch right)
    {
        this.name = name;
        this.port = port;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try (ServerSocket serverSocket = new ServerSocket(port))
                {
                    do
                    {
                        try (Socket clientSocket = serverSocket.accept();
                             ObjectInputStream ois = new ObjectInputStream(
                                     new BufferedInputStream(
                                             clientSocket.getInputStream()))
                        )
                        {
                            //Receive TestPacket
                            TestPacket tp = (TestPacket) ois.readObject();

                            int portToSend = 0;
                            if (tp.getLastHop().equals(left.getName()))
                            {
                                portToSend = Config.ports.get(right.getName());
                            } else
                            {
                                portToSend = Config.ports.get(left.getName());
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
                    while (true);
                } catch (Exception e)
                {
                    System.out.println("ERROR");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String getNameOfLink()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "Link \"" + name + "\"";
    }
}
