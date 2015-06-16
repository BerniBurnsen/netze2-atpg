public class Main
{
    public static void main(String[] args)
    {
        new Switch(Config.Switch_A, Config.ports.get(Config.Switch_A), Config.rules.get(Config.Switch_A), "Link_AB", "Link_AC").start();
        new Switch(Config.Switch_B, Config.ports.get(Config.Switch_B), Config.rules.get(Config.Switch_B), "Link_AB", "Link_BD").start();
        new Switch(Config.Switch_C, Config.ports.get(Config.Switch_C), Config.rules.get(Config.Switch_C), "Link_AC", "Link_CD").start();
        new Switch(Config.Switch_D, Config.ports.get(Config.Switch_D), Config.rules.get(Config.Switch_D), "Link_CD", "Link_BD").start();
        System.out.println("Switches started!");

        TestTerminal tt = new TestTerminal(Config.Terminal_A_port, Config.Terminal_B_port, Config.Terminal_C_port, Config.Terminal_D_port);
        tt.start();
        System.out.println("TestTerminal started!");

        while(true)
        {
            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            tt.setSend(true);
            System.out.println("Allowed to send Packets");
        }
    }
}