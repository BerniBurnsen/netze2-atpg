public class Main
{
    public static void main(String[] args)
    {
       /* new Link(Config.Link_AB, Config.ports.get(Config.Link_AB),Config.Switch_A, Config.Switch_B, true).start();
        new Link(Config.Link_AC, Config.ports.get(Config.Link_AC),Config.Switch_A, Config.Switch_C, true).start();
        new Link(Config.Link_BD, Config.ports.get(Config.Link_BD),Config.Switch_B, Config.Switch_D, true).start();
        new Link(Config.Link_CD, Config.ports.get(Config.Link_CD),Config.Switch_C, Config.Switch_D, true).start();

        new Switch(Config.Switch_A, Config.ports.get(Config.Switch_A), Config.rules.get(Config.Switch_A), Config.Link_AB, Config.Link_AC).start();
        new Switch(Config.Switch_B, Config.ports.get(Config.Switch_B), Config.rules.get(Config.Switch_B), Config.Link_AB, Config.Link_BD).start();
        new Switch(Config.Switch_C, Config.ports.get(Config.Switch_C), Config.rules.get(Config.Switch_C), Config.Link_AC, Config.Link_CD).start();
        new Switch(Config.Switch_D, Config.ports.get(Config.Switch_D), Config.rules.get(Config.Switch_D), Config.Link_CD, Config.Link_BD).start();
        System.out.println("Switches started!");

        TestTerminal tt = new TestTerminal(Config.Terminal_A_port, Config.Terminal_B_port, Config.Terminal_C_port, Config.Terminal_D_port);
        tt.start();
        System.out.println("TestTerminal started!");*/

        new Link(Config.Link_AB, Config.ports.get(Config.Link_AB),Config.Switch_A, Config.Switch_B, true).start();
        new Link(Config.Link_AC, Config.ports.get(Config.Link_AC),Config.Switch_A, Config.Switch_C, true).start();
        new Link(Config.Link_BC, Config.ports.get(Config.Link_BC),Config.Switch_B, Config.Switch_C, true).start();

        new Switch(Config.Switch_A, Config.ports.get(Config.Switch_A), Config.rules.get(Config.Switch_A), Config.Link_AB, Config.Link_AC).start();
        new Switch(Config.Switch_B, Config.ports.get(Config.Switch_B), Config.rules.get(Config.Switch_B), Config.Link_AB, Config.Link_BC).start();
        new Switch(Config.Switch_C, Config.ports.get(Config.Switch_C), Config.rules.get(Config.Switch_C), Config.Link_AC, Config.Link_BC).start();
        System.out.println("Switches started!");

        TestTerminal tt = new TestTerminal(Config.Terminal_A_port, Config.Terminal_B_port, Config.Terminal_C_port);
        tt.start();
        System.out.println("TestTerminal started!");


      /*  while(true)
        {*/
            tt.setSend(true);
/*        }*/
    }
}