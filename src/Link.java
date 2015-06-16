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
    private final String left;
    private final String right;
    private final String name;
    private boolean working;

    public Link(String name, int port, String left, String right, boolean working)
    {
        this.name = name;
        this.port = port;
        this.left = left;
        this.right = right;
        this.setWorking(working);
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
                            if (tp.getLastHop().equals(left))
                            {
                                portToSend = Config.ports.get(right);
                            } else
                            {
                                portToSend = Config.ports.get(left);
                            }

                            final int tmpPort = portToSend;
                            if(working)
                            {
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
                                            //System.out.println(getNameOfLink() + " sending " + tp);
                                        } catch (Exception e)
                                        {
                                            System.out.println("ERROR by writing " + tp);
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
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

    public boolean isWorking()
    {
        return working;
    }

    public void setWorking(boolean working)
    {
        this.working = working;
    }

    @Override
    public String toString()
    {
        return "Link \"" + name + "\"";
    }
}
