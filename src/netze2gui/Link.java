package netze2gui;

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
        try (final ServerSocket serverSocket = new ServerSocket(port))
        {
            do
            {
                final Socket clientSocket = serverSocket.accept();
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try (ObjectInputStream ois = new ObjectInputStream(
                                clientSocket.getInputStream())
                        )
                        {
                            //Receive netze2gui.TestPacket
                            TestPacket tp = (TestPacket) ois.readObject();

                            System.out.println(name + " received " + tp);
                            int portToSend;
                            if (tp.getLastHop().equals(left))
                            {
                                portToSend = Config.ports.get(right);
                                System.out.println(name + " sending " + tp + " to " + right + " (" + portToSend + ")");
                                startAnimation(true);
                            } else
                            {
                                portToSend = Config.ports.get(left);
                                System.out.println(name + " sending " + tp + " to " + left + " (" + portToSend + ")");
                                startAnimation(false);
                            }

                            final int tmpPort = portToSend;
                            if (working)
                            {
                                new Thread(new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        //Send netze2gui.TestPacket to next netze2gui.Switch
                                        try (Socket socket = new Socket("localhost", tmpPort);
                                             ObjectOutputStream oos = new ObjectOutputStream(
                                                     socket.getOutputStream())
                                        )
                                        {
                                            sleep(1000);
                                            oos.writeObject(tp);
                                            oos.flush();
                                            System.out.println(name + " sended " + tp + " to " + tmpPort);
                                        } catch (Exception e)
                                        {
                                            System.out.println("ERROR by writing " + tp);
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            while (true);
        } catch (Exception e)
        {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    private void startAnimation(boolean forward)
    {
        switch (name)
        {
            case Config.Link_AB:
                if (forward)
                    Main.mainWindow.animateLinkABForward();
                else
                    Main.mainWindow.animateLinkABBackward();
                break;
            case Config.Link_AC:
                if (forward)
                    Main.mainWindow.animateLinkACForward();
                else
                    Main.mainWindow.animateLinkACBackward();
                break;
            case Config.Link_BC:
                if (forward)
                    Main.mainWindow.animateLinkBCForward();
                else
                    Main.mainWindow.animateLinkBCBackward();
                break;
        }
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
        return "netze2gui.Link \"" + name + "\"";
    }
}
