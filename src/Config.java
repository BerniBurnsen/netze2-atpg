import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joncn on 15.06.2015.
 */
public class Config
{
    public static String Switch_A = "Switch A";
    public static String Switch_B = "Switch B";
    public static String Switch_C = "Switch C";
    public static String Switch_D = "Switch D";

    public static String Terminal_A = "Terminal A";
    public static String Terminal_B = "Terminal B";
    public static String Terminal_C = "Terminal C";
    public static String Terminal_D = "Terminal D";

    public static String Link_AB = "Link AB";
    public static String Link_AC = "Link AC";
    public static String Link_BD = "Link BD";
    public static String Link_CD = "Link CD";

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

    public static Rule[] Switch_A_rules = {
            new Rule("A:r1", Terminal_A, Terminal_A, true),
            new Rule("A:r2", Terminal_B, Link_AB, true),
            new Rule("A:r3", Terminal_C, Link_AC, true),
            new Rule("A:r4", Terminal_D, Link_AB, true)
    };
    public static Rule[] Switch_B_rules = {
            new Rule("B:r1", Terminal_A, Link_AB, true),
            new Rule("B:r2", Terminal_B, Terminal_B, false),
            new Rule("B:r3", Terminal_C, Link_AB, true),
            new Rule("B:r4", Terminal_D, Link_BD, true)
    };
    public static Rule[] Switch_C_rules = {
        new Rule("C:r1", Terminal_A, Link_AC, true),
        new Rule("C:r2", Terminal_B, Link_AC, true),
        new Rule("C:r3", Terminal_C, Terminal_C, true),
        new Rule("C:r4", Terminal_D, Link_CD, true)
    };
    public static Rule[] Switch_D_rules = {
            new Rule("D:r1", Terminal_A, Link_CD, true),
            new Rule("D:r2", Terminal_B, Link_BD, true),
            new Rule("D:r3", Terminal_C, Link_CD, true),
            new Rule("D:r4", Terminal_D, Terminal_D, true)
    };

    public static Map<String, Rule[]> rules = new HashMap<>();
    static
    {
        rules.put(Switch_A, Switch_A_rules);
        rules.put(Switch_B, Switch_B_rules);
        rules.put(Switch_C, Switch_C_rules);
        rules.put(Switch_D, Switch_D_rules);
    }

    public static TestPacket[] neededPackets = {
            new TestPacket("p1", Switch_A, Terminal_B, "A:r2", "Link_AB", "B:r2"),
            new TestPacket("p2", Switch_A, Terminal_C, "A:r3", "Link_AC", "C:r3"),
            new TestPacket("p3", Switch_A, Terminal_D, "A:r4", "Link_AB", "B:r4", "Link_BD", "D:r4"),
            new TestPacket("p4", Switch_B, Terminal_A, "B:r1", "Link_AB", "A:r1"),
            new TestPacket("p5", Switch_B, Terminal_C, "B:r3", "Link_AB", "A:r3", "Link_AC", "C:r3"),
            new TestPacket("p7", Switch_C, Terminal_A, "C:r1", "Link_AC", "A:r1"),
            new TestPacket("p8", Switch_C, Terminal_B, "C:r2", "Link_AC", "A:r2", "Link_AB", "B:r2"),
            new TestPacket("p9", Switch_C, Terminal_D, "C:r4", "Link_CD", "D:r4"),
            new TestPacket("p10", Switch_D, Terminal_A, "D:r1", "Link_CD", "C:r1", "Link_AC", "A:r1"),
            new TestPacket("p11", Switch_D, Terminal_B, "D:r2", "Link_BD", "B:r2"),
            new TestPacket("p12", Switch_D, Terminal_C, "D:r3", "Link_CD", "C:r3")
    };

    public static TestPacket[] reservedPackets = {
            new TestPacket("p6", Switch_B, Terminal_D, "B:r4", "Link_BD", "D:r4"),
    };

    public static String[] allRules = {
            "A:r1", "A:r2", "A:r3", "A:r4",
            "B:r1", "B:r2", "B:r3", "B:r4",
            "C:r1", "C:r2", "C:r3", "C:r4",
            "D:r1", "D:r2", "D:r3", "D:r4",
            Link_AB, Link_AC, Link_BD, Link_CD
    };

}