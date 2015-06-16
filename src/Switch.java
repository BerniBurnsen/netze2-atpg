import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public Switch(String name,int port,Rule[] rules, String... links)
    {
        this.name = name;
        this.links = links;
        this.port = port;
        this.rules = rules;
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
                        try(Socket clientSocket = serverSocket.accept();
                            ObjectInputStream ois = new ObjectInputStream(
                                new BufferedInputStream(
                                        clientSocket.getInputStream()))
                        )
                        {
                            //Receive TestPacket
                            TestPacket tp = (TestPacket) ois.readObject();
                            String dest = tp.getDestination();

                            int tmpPort = 0;
                            for(Rule r : rules)
                            {
                                if(r.getDestination().equals(dest))
                                {
                                    tmpPort = Config.ports.get(r.getLink());
                                    if(!r.isWorking())
                                    {
                                        tmpPort = -1;
                                    }
                                    break;
                                }
                            }

                            final int portToSend = tmpPort;
                            if(tmpPort != -1)
                            {
                                new Thread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        //Send TestPacket to next Switch
                                        try (Socket socket = new Socket("localhost", portToSend);
                                             ObjectOutputStream oos = new ObjectOutputStream(
                                                     new BufferedOutputStream(socket.getOutputStream()))
                                        )
                                        {
                                            tp.setLastHop(name);
                                            oos.writeObject(tp);
                                            oos.flush();
                                            //System.out.println(name + " sending " + tp);
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
                    while(true);
                } catch (Exception e)
                {
                    System.out.println("ERROR");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public String toString()
    {
        return "Switch \"" + name + "\"";
    }
}
