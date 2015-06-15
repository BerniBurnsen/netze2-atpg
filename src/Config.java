import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joncn on 15.06.2015.
 */
public class Config
{
    public static int[] testTerminalPorts = {10001, 10002, 10003};

    public static int[] switch1Ports = {11001, 11002, 11003};
    public static int[] switch2Ports = {12001, 12002, 12003};
    public static int[] switch3Ports = {13001, 13002, 13003};

    public static Rule[] switch1Rules = {
            new Rule(10001, 10001),
            new Rule(10002, 12001),
            new Rule(10003, 13001)
    };

    public static Rule[] switch2Rules = {
            new Rule(10001, 11002),
            new Rule(10002, 10002),
            new Rule(10003, 13002)
    };

    public static Rule[] switch3Rules = {
            new Rule(10001, 11003),
            new Rule(10002, 12003),
            new Rule(10003, 10003)
    };

    private static Rule[] packet1History = {switch1Rules[1], switch2Rules[1]};
    private static Rule[] packet2History = {switch1Rules[2], switch3Rules[2]};
    private static Rule[] packet3History = {switch2Rules[0], switch1Rules[0]};
    private static Rule[] packet4History = {switch2Rules[2], switch3Rules[2]};
    private static Rule[] packet5History = {switch3Rules[0], switch1Rules[0]};
    private static Rule[] packet6History = {switch3Rules[1], switch2Rules[1]};

    public static TestPacket[] neededPackets = {
            new TestPacket(11001, 10002, packet1History),
            new TestPacket(11001, 10003, packet2History),
            new TestPacket(12002, 10001, packet3History),
            new TestPacket(12002, 10003, packet4History),
            new TestPacket(13003, 10001, packet5History),
    };

    public static TestPacket[] resercedPackets = {
            new TestPacket(13003, 10002, packet6History)
    };
}