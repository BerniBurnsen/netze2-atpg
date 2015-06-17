package netze2gui;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joncn on 15.06.2015.
 */
public class Config
{
/*    public static String Switch_A = "netze2gui.Switch A";
    public static String Switch_B = "netze2gui.Switch B";
    public static String Switch_C = "netze2gui.Switch C";
    public static String Switch_D = "netze2gui.Switch D";

    public static String Terminal_A = "Terminal A";
    public static String Terminal_B = "Terminal B";
    public static String Terminal_C = "Terminal C";
    public static String Terminal_D = "Terminal D";

    public static String Link_AB = "netze2gui.Link AB";
    public static String Link_AC = "netze2gui.Link AC";
    public static String Link_BD = "netze2gui.Link BD";
    public static String Link_CD = "netze2gui.Link CD";

    public static int Terminal_A_port = 10001;
    public static int Terminal_B_port = 10002;
    public static int Terminal_C_port = 10003;
    public static int Terminal_D_port = 10004;

    public static int Switch_A_port = 10005;
    public static int Switch_B_port = 10006;
    public static int Switch_C_port = 10007;
    public static int Switch_D_port = 10008;

    public static int Link_AB_port = 10009;
    public static int Link_AC_port = 10010;
    public static int Link_BD_port = 10011;
    public static int Link_CD_port = 10012;

    public static Map<String, Integer> ports = new HashMap<>();
    static
    {
        ports.put(Terminal_A, Terminal_A_port);
        ports.put(Terminal_B, Terminal_B_port);
        ports.put(Terminal_C, Terminal_C_port);
        ports.put(Terminal_D, Terminal_D_port);
        ports.put(Switch_A, Switch_A_port);
        ports.put(Switch_B, Switch_B_port);
        ports.put(Switch_C, Switch_C_port);
        ports.put(Switch_D, Switch_D_port);
        ports.put(Link_AB, Link_AB_port);
        ports.put(Link_AC, Link_AC_port);
        ports.put(Link_BD, Link_BD_port);
        ports.put(Link_CD, Link_CD_port);
    }

    public static netze2gui.Rule[] Switch_A_rules = {
            new netze2gui.Rule("A:r1", Terminal_A, Terminal_A, true),
            new netze2gui.Rule("A:r2", Terminal_B, Link_AB, true),
            new netze2gui.Rule("A:r3", Terminal_C, Link_AC, true),
            new netze2gui.Rule("A:r4", Terminal_D, Link_AB, true)
    };
    public static netze2gui.Rule[] Switch_B_rules = {
            new netze2gui.Rule("B:r1", Terminal_A, Link_AB, true),
            new netze2gui.Rule("B:r2", Terminal_B, Terminal_B, false),
            new netze2gui.Rule("B:r3", Terminal_C, Link_AB, true),
            new netze2gui.Rule("B:r4", Terminal_D, Link_BD, true)
    };
    public static netze2gui.Rule[] Switch_C_rules = {
        new netze2gui.Rule("C:r1", Terminal_A, Link_AC, true),
        new netze2gui.Rule("C:r2", Terminal_B, Link_AC, true),
        new netze2gui.Rule("C:r3", Terminal_C, Terminal_C, true),
        new netze2gui.Rule("C:r4", Terminal_D, Link_CD, true)
    };
    public static netze2gui.Rule[] Switch_D_rules = {
            new netze2gui.Rule("D:r1", Terminal_A, Link_CD, true),
            new netze2gui.Rule("D:r2", Terminal_B, Link_BD, true),
            new netze2gui.Rule("D:r3", Terminal_C, Link_CD, true),
            new netze2gui.Rule("D:r4", Terminal_D, Terminal_D, true)
    };

    public static Map<String, netze2gui.Rule[]> rules = new HashMap<>();
    static
    {
        rules.put(Switch_A, Switch_A_rules);
        rules.put(Switch_B, Switch_B_rules);
        rules.put(Switch_C, Switch_C_rules);
        rules.put(Switch_D, Switch_D_rules);
    }

    public static netze2gui.TestPacket[] neededPackets = {
            new netze2gui.TestPacket("p1", Switch_A, Terminal_B, "A:r2", "Link_AB", "B:r2"),
            new netze2gui.TestPacket("p2", Switch_A, Terminal_C, "A:r3", "Link_AC", "C:r3"),
            new netze2gui.TestPacket("p3", Switch_A, Terminal_D, "A:r4", "Link_AB", "B:r4", "Link_BD", "D:r4"),
            new netze2gui.TestPacket("p4", Switch_B, Terminal_A, "B:r1", "Link_AB", "A:r1"),
            new netze2gui.TestPacket("p5", Switch_B, Terminal_C, "B:r3", "Link_AB", "A:r3", "Link_AC", "C:r3"),
            new netze2gui.TestPacket("p7", Switch_C, Terminal_A, "C:r1", "Link_AC", "A:r1"),
            new netze2gui.TestPacket("p8", Switch_C, Terminal_B, "C:r2", "Link_AC", "A:r2", "Link_AB", "B:r2"),
            new netze2gui.TestPacket("p9", Switch_C, Terminal_D, "C:r4", "Link_CD", "D:r4"),
            new netze2gui.TestPacket("p10", Switch_D, Terminal_A, "D:r1", "Link_CD", "C:r1", "Link_AC", "A:r1"),
            new netze2gui.TestPacket("p11", Switch_D, Terminal_B, "D:r2", "Link_BD", "B:r2"),
            new netze2gui.TestPacket("p12", Switch_D, Terminal_C, "D:r3", "Link_CD", "C:r3")
    };

    public static netze2gui.TestPacket[] reservedPackets = {
            new netze2gui.TestPacket("p6", Switch_B, Terminal_D, "B:r4", "Link_BD", "D:r4"),
    };

    public static String[] allRules = {
            "A:r1", "A:r2", "A:r3", "A:r4",
            "B:r1", "B:r2", "B:r3", "B:r4",
            "C:r1", "C:r2", "C:r3", "C:r4",
            "D:r1", "D:r2", "D:r3", "D:r4",
            Link_AB, Link_AC, Link_BD, Link_CD
    };*/

    public static final String Switch_A = "netze2gui.Switch A";
    public static final String Switch_B = "netze2gui.Switch B";
    public static final String Switch_C = "netze2gui.Switch C";

    public static final String Terminal_A = "Terminal A";
    public static final String Terminal_B = "Terminal B";
    public static final String Terminal_C = "Terminal C";

    public static final String Link_AB = "netze2gui.Link AB";
    public static final String Link_AC = "netze2gui.Link AC";
    public static final String Link_BC = "netze2gui.Link BC";

    public static final int Terminal_A_port = 10001;
    public static final int Terminal_B_port = 10002;
    public static final int Terminal_C_port = 10003;

    public static final int Switch_A_port = 10005;
    public static final int Switch_B_port = 10006;
    public static final int Switch_C_port = 10008;

    public static final int Link_AB_port = 10009;
    public static final int Link_AC_port = 10010;
    public static final int Link_BC_port = 10011;

    public static final Map<String, Integer> ports = new HashMap<>();

    static
    {
        ports.put(Terminal_A, Terminal_A_port);
        ports.put(Terminal_B, Terminal_B_port);
        ports.put(Terminal_C, Terminal_C_port);
        ports.put(Switch_A, Switch_A_port);
        ports.put(Switch_B, Switch_B_port);
        ports.put(Switch_C, Switch_C_port);
        ports.put(Link_AB, Link_AB_port);
        ports.put(Link_AC, Link_AC_port);
        ports.put(Link_BC, Link_BC_port);
    }

    public static final Rule[] Switch_A_rules = {
            new Rule("A:r1", true, Terminal_A, Terminal_A),
            new Rule("A:r2", true, Link_AB, Terminal_B),
            new Rule("A:r3", true, Link_AC, Terminal_C)
    };
    public static final Rule[] Switch_B_rules = {
            new Rule("B:r1", true, Link_AB, Terminal_A),
            new Rule("B:r2", false, Terminal_B, Terminal_B),
            new Rule("B:r3", true, Link_BC, Terminal_C)
    };
    public static final Rule[] Switch_C_rules = {
            new Rule("C:r1", true, Link_AC, Terminal_A, Terminal_B),
            new Rule("C:r2", true, Terminal_C, Terminal_C)
    };

    public static final Map<String, Rule[]> rules = new HashMap<>();

    static
    {
        rules.put(Switch_A, Switch_A_rules);
        rules.put(Switch_B, Switch_B_rules);
        rules.put(Switch_C, Switch_C_rules);
    }

    public static final TestPacket[] neededPackets = {
            new TestPacket("p1", Switch_A, Terminal_B, "A:r2", Link_AB, "B:r2"),
            new TestPacket("p2", Switch_A, Terminal_C, "A:r3", Link_AC, "C:r2"),
            new TestPacket("p3", Switch_B, Terminal_A, "B:r1", Link_AB, "A:r1"),
            new TestPacket("p4", Switch_B, Terminal_C, "B:r3", Link_BC, "C:r2"),
            new TestPacket("p5", Switch_C, Terminal_A, "C:r1", Link_AC, "A:r1"),
            new TestPacket("p6", Switch_C, Terminal_B, "C:r1", Link_BC, "B:r2"),
    };

    public static final TestPacket[] reservedPackets = {

    };

    public static final String[] allRules = {
            "A:r1", "A:r2", "A:r3",
            "B:r1", "B:r2", "B:r3",
            "C:r1", "C:r2",
            Link_AB, Link_AC, Link_BC
    };
}