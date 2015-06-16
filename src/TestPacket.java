import java.io.Serializable;

/**
 * Created by Joncn on 15.06.2015.
 */
public class TestPacket implements Serializable
{
    private String[] ruleHistory;
    private String name;
    private String destination;
    private String firstHop;
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

    public String getName() { return name;}

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
        if(o != null)
        {
            if(o instanceof TestPacket)
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
}