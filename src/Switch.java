import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * Created by Joncn on 15.06.2015.
 *
 * Receives packets and sends them to the next switch/terminal independed from the rules
 */
public class Switch extends Thread
{
    private final String name;
    private final String[] links;
    private final Rule[] rules;
    private final int port;

    public Switch(String name, int port, Rule[] rules, String... links)
    {
        this.name = name;
        this.links = links;
        this.port = port;
        this.rules = rules;
    }

    @Override
    public void run()
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            do
            {
                final Socket clientSocket = serverSocket.accept();
                new Thread(new Runnable()
                {
                    Socket sock = clientSocket;
                    @Override
                    public void run()
                    {
                        try(ObjectInputStream ois = new ObjectInputStream(
                                clientSocket.getInputStream())
                        )
                        {
                            String dest;
                            int portToSend = 0;

                            //Receive TestPacket
                            TestPacket tp = (TestPacket) ois.readObject();
                            System.out.println(name + " (" + port + ") received " + tp);
                            dest = tp.getDestination();

                            ruleFinding:
                            for (Rule r : rules)
                            {
                                for (String s : r.getDestinations())
                                {
                                    if (s.equals(dest))
                                    {
                                        portToSend = Config.ports.get(r.getLink());
                                        if (!r.isWorking())
                                        {
                                            portToSend = -1;
                                        } else
                                        {
                                            System.out.println(name + " sending " + tp + " to " + r.getLink() + " (" + portToSend + ")");
                                        }
                                        break ruleFinding;
                                    }
                                }
                            }

                            if (portToSend != -1)
                            {
                                //Send TestPacket to next Switch
                                try (Socket socket = new Socket("localhost", portToSend);
                                     ObjectOutputStream oos = new ObjectOutputStream(
                                             socket.getOutputStream())
                                )
                                {
                                    tp.setLastHop(name);
                                    sleep(1000);
                                    oos.writeObject(tp);
                                    oos.flush();
                                    //System.out.println(name + " sending " + tp);
                                } catch (Exception e)
                                {
                                    System.out.println("ERROR by writing " + tp);
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            while(true);
        } catch (Exception e)
        {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return "Switch \"" + name + "\"";
    }
}
