package netze2gui;

import java.io.Serializable;

/**
 * Created by Joncn on 15.06.2015.
 * This is class represents a ATPG-testpacket
 */
public class TestPacket implements Serializable
{
    private String[] ruleHistory;
    private String ruleHistoryString;
    private String name;
    private String destination;
    private String firstHop;
    private String from;
    private String lastHop = null;

    public TestPacket()
    {

    }

    public TestPacket(String name, String firstHop, String destination, String... ruleHistory)
    {
        this.name = name;
        this.setFirstHop(firstHop);
        this.setDestination(destination);
        this.setRuleHistory(ruleHistory);
        StringBuilder sb = new StringBuilder();
        for(String s : ruleHistory)
        {
            sb.append(s+", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        setRuleHistoryString(sb.toString());
        switch(firstHop)
        {
            case Config.Switch_A:
                setFrom(Config.Terminal_A);
                break;
            case Config.Switch_B:
                setFrom(Config.Terminal_B);
                break;
            case Config.Switch_C:
                setFrom(Config.Terminal_C);
                break;
        }
    }

    public String[] getRuleHistory()
    {
        return ruleHistory;
    }

    public String getDestination()
    {
        return destination;
    }

    public String getFirstHop()
    {
        return firstHop;
    }

    public String getLastHop()
    {
        return lastHop;
    }

    public String getName()
    {
        return name;
    }

    public void setRuleHistory(String[] ruleHistory)
    {
        this.ruleHistory = ruleHistory;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public void setFirstHop(String firstHop)
    {
        this.firstHop = firstHop;
    }

    public void setLastHop(String lastHop)
    {
        this.lastHop = lastHop;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof TestPacket)
            {
                TestPacket tp = (TestPacket) o;
                return tp.getName().equals(this.name);
            }
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "Packet \"" + name + "\"";
    }

    public String getRuleHistoryString()
    {
        return ruleHistoryString;
    }

    public void setRuleHistoryString(String ruleHistroyString)
    {
        this.ruleHistoryString = ruleHistroyString;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }
}