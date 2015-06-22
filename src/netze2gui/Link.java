package netze2gui;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Joncn on 16.06.2015.
 * This Class is depicting a link, like a cable.
 * It listens on a port and decides based on where the packet came from, where it has to forward the testpacket
 * the link can be broken. in this case the packet is lost.
 */
public class Link extends Thread
{
    private final int port;
    private final String left;
    private final String right;
    private final String name;
    private boolean worked = false;
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
                /*
                sub thread to handle the incoming packet.
                this thread does the "dirty" work.
                 */
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
                            /*
                            decision where to forward the next packet.
                             */
                            if (tp.getLastHop().equals(left))
                            {
                                portToSend = Config.ports.get(right);
                                System.out.println(name + " sending " + tp + " to " + right + " (" + portToSend + ")");
                                startAnimation(true);
                            }
                            else
                            {
                                portToSend = Config.ports.get(left);
                                System.out.println(name + " sending " + tp + " to " + left + " (" + portToSend + ")");
                                startAnimation(false);
                            }


                            final int tmpPort = portToSend; //Port of the target switch
                            if (working)
                            {
                                /*
                                sub-sub Thread to forward the packet, in case the link is working.
                                 */
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
                                            System.err.println("ERROR by writing " + tp);
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                            else
                            {
                                switch(tp.getDestination())
                                {
                                    case Config.Terminal_A:
                                        Main.mainWindow.packetTerminal_A_ERROR();
                                        break;
                                    case Config.Terminal_B:
                                        Main.mainWindow.packetTerminal_B_ERROR();
                                        break;
                                    case Config.Terminal_C:
                                        Main.mainWindow.packetTerminal_C_ERROR();
                                        break;
                                }
                                System.err.println(name + " Link not working!");
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
            System.err.println("ERROR");
            e.printStackTrace();
        }
    }

    /*
        animates the send process of the packet
        in both cases, failure and success.
     */
    private void startAnimation(boolean forward)
    {
        if(working)
        {
            //normal animation
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
        else
        {
            //failure
            switch(name)
            {
                case Config.Link_AB:
                    Main.mainWindow.packetLinkAB_Error_Visible();
                    break;
                case Config.Link_AC:
                    Main.mainWindow.packetLinkAC_Error_Visible();
                    break;
                case Config.Link_BC:
                    Main.mainWindow.packetLinkBC_ERROR_Visible();
                    break;
            }

            try
            {
                sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            switch(name)
            {
                case Config.Link_AB:
                    Main.mainWindow.packetLinkAB_ERROR_Invisible();
                    break;
                case Config.Link_AC:
                    Main.mainWindow.packetLinkAC_ERROR_Invisible();
                    break;
                case Config.Link_BC:
                    Main.mainWindow.packetLinkBC_ERROR_Invisible();
                    break;
            }
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
        return "Link \"" + name + "\"";
    }

    public boolean isWorked()
    {
        return worked;
    }

    public void setWorked(boolean worked)
    {
        this.worked = worked;
    }
}
