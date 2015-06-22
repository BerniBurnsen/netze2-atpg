package netze2gui;

import netze2gui.viewsandcontroller.MainController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Joncn on 15.06.2015.
 *
 * Receives packets and sends them to the next switch/terminal depending on the rules
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
                        try (ObjectInputStream ois = new ObjectInputStream(
                                clientSocket.getInputStream())
                        )
                        {
                            String dest;
                            int portToSend = 0;

                            //Receive TestPacket
                            TestPacket tp = (TestPacket) ois.readObject();
                            setPacketVisible();
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
                                            System.err.println(name + " rule not working: " + r);
                                            Thread.sleep(1000);

                                            switch (name)
                                            {
                                                case Config.Switch_A:
                                                    Main.mainWindow.packetSwitch_A_Error_Visible();
                                                    Main.mainWindow.packetSwitch_A_Inisible();
                                                    break;
                                                case Config.Switch_B:
                                                    Main.mainWindow.packetSwitch_B_Error_Visible();
                                                    Main.mainWindow.packetSwitch_B_Inisible();
                                                    break;
                                                case Config.Switch_C:
                                                    Main.mainWindow.packetSwitch_C_Error_Visible();
                                                    Main.mainWindow.packetSwitch_C_Inisible();
                                                    break;
                                            }
                                            sleep(1000);
                                            switch (name)
                                            {
                                                case Config.Switch_A:
                                                    Main.mainWindow.packetSwitch_A_ERROR_Invisible();
                                                    break;
                                                case Config.Switch_B:
                                                    Main.mainWindow.packetSwitch_B_ERROR_Invisible();
                                                    break;
                                                case Config.Switch_C:
                                                    Main.mainWindow.packetSwitch_C_ERROR_Invisible();
                                                    break;
                                            }
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
                                            System.err.println(tp.getDestination());
                                            Thread.sleep(1000);
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
                                    setPacketInvisible();

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
            while (true);
        } catch (Exception e)
        {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    private void setPacketVisible()
    {
        switch (name)
        {
            case Config.Switch_A:
                Main.mainWindow.packetSwitch_A_Visible();
                break;
            case Config.Switch_B:
                Main.mainWindow.packetSwitch_B_Visible();
                break;
            case Config.Switch_C:
                Main.mainWindow.packetSwitch_C_Visible();
                break;
        }
    }

    private void setPacketInvisible()
    {
        switch (name)
        {
            case Config.Switch_A:
                Main.mainWindow.packetSwitch_A_Inisible();
                break;
            case Config.Switch_B:
                Main.mainWindow.packetSwitch_B_Inisible();
                break;
            case Config.Switch_C:
                Main.mainWindow.packetSwitch_C_Inisible();
                break;
        }
    }

    @Override
    public String toString()
    {
        return "Switch \"" + name + "\"";
    }
}
