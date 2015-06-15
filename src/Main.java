public class Main
{
    public static void main(String[] args)
    {
        new Switch(Config.switch1Rules, Config.switch1Ports).start();
        new Switch(Config.switch2Rules, Config.switch2Ports).start();
        new Switch(Config.switch3Rules, Config.switch3Ports).start();

        System.out.println("Switches started!");

        TestTerminal tt = new TestTerminal(Config.testTerminalPorts);
        tt.start();
        System.out.println("TestTerminal started!");

        while(true)
        {
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            tt.setSend(true);
            System.out.println("Allowed to send Packets");
        }
    }
}