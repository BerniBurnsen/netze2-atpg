import java.io.Serializable;

/**
 * Created by Joncn on 15.06.2015.
 */
public class TestPacket implements Serializable
{
    private String[] ruleHistory;
    private String name;
    private String destination;
    private String source;
    private String lastHop = null;

    public TestPacket()
    {

    }

    public TestPacket(String name, String source, String destination, String... ruleHistory)
    {
        this.name = name;
        this.setSource(source);
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

    public String getSource()
    {
        return source;
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

    public void setSource(String source)
    {
        this.source = source;
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