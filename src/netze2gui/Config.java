package netze2gui;

import java.util.*;

/**
 * Created by Joncn on 15.06.2015.
 * This class holds the configuration of rules and constants across the project.
 */
public class Config
{
    static boolean A2 = true;
    static boolean A3 = true;
    static
    {
        switch (Praesi.fall)
        {
            case 1:
                A2 = true;
                A3 = false;
                break;
            case 2:
                A2 = false;
                A3 = true;
                break;
            default:
                A2 = true;
                A3 = true;
                break;
        }
    }
    public static final String Switch_A = "Switch A";
    public static final String Switch_B = "Switch B";
    public static final String Switch_C = "Switch C";

    public static final String Terminal_A = "A";
    public static final String Terminal_B = "B";
    public static final String Terminal_C = "C";

    public static final String Link_AB = "Link AB";
    public static final String Link_AC = "Link AC";
    public static final String Link_BC = "Link BC";

    public static final int Terminal_A_port = 10001;
    public static final int Terminal_B_port = 10002;
    public static final int Terminal_C_port = 10003;

    public static final int Switch_A_port = 10005;
    public static final int Switch_B_port = 10006;
    public static final int Switch_C_port = 10008;

    public static final int Link_AB_port = 10009;
    public static final int Link_AC_port = 10010;
    public static final int Link_BC_port = 10011;

    public final static List<TestPacket> allTestpackets = new ArrayList<>();

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
            new Rule("A:r1", true, Terminal_A, Terminal_A), //false = A:r1, B:r1 mit Reserved
            new Rule("A:r2", A2, Link_AB, Terminal_B), //false = A:r2 mit Reserved //TODO: !!! MIT RESERVED !!!
            new Rule("A:r3", A3, Link_AC, Terminal_C) //false = A:r3 ohne Reserved
    };
    public static final Rule[] Switch_B_rules = {
            new Rule("B:r1", true, Link_AB, Terminal_A),   //false = B:r1  //TODO: !!! OHNE RESERVED !!!
            new Rule("B:r2", true, Terminal_B, Terminal_B), //false = A:r2, B:r2 mit Reserved
            new Rule("B:r3", true, Link_BC, Terminal_C)    //false = B:r3 //TODO: !!! MIT RESERVED !!!
    };
    static String[] ruleC = {Link_AC, Link_BC};
    public static final Rule[] Switch_C_rules = {
            new Rule("C:r1", true, ruleC, Terminal_A, Terminal_B), //false = C:r1 ohne Reserved
            new Rule("C:r2", true, Terminal_C, Terminal_C) //false = A:r3, B:r3, C:r2, Link BC //TODO: (Reserved wird noch geschickt aber unnötig) //OUT
    };

    public static final Map<String, Rule[]> rules = new HashMap<>();

    public static final TestPacket[] neededPackets = {
            new TestPacket("p1", Switch_A, Terminal_B, "A:r2", Link_AB, "B:r2"),
            new TestPacket("p2", Switch_A, Terminal_C, "A:r3", Link_AC, "C:r2"),
            new TestPacket("p3", Switch_B, Terminal_A, "B:r1", Link_AB, "A:r1"),
            new TestPacket("p4", Switch_B, Terminal_C, "B:r3", Link_BC, "C:r2"),
            new TestPacket("p5", Switch_C, Terminal_A, "C:r1", Link_AC, "A:r1"),
    };

    public static final TestPacket[] reservedPackets = {
            new TestPacket("p6", Switch_C, Terminal_B, "C:r1", Link_BC, "B:r2"),
    };

    public static final String[] allRules = {
            "A:r1", "A:r2", "A:r3",
            "B:r1", "B:r2", "B:r3",
            "C:r1", "C:r2",
            Link_AB, Link_AC, Link_BC
    };

    static
    {
        rules.put(Switch_A, Switch_A_rules);
        rules.put(Switch_B, Switch_B_rules);
        rules.put(Switch_C, Switch_C_rules);

        allTestpackets.addAll(Arrays.asList(neededPackets));
        allTestpackets.addAll(Arrays.asList(reservedPackets));
    }
}