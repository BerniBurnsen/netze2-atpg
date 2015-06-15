import java.io.Serializable;

/**
 * Created by Joncn on 15.06.2015.
 */
public class TestPacket implements Serializable
{
    private Rule[] ruleHistory;
    private int destination;
    private int source;

    public TestPacket()
    {

    }

    public TestPacket(int source, int destination, Rule... ruleHistory)
    {
        this.setSource(source);
        this.setDestination(destination);
        this.setRuleHistory(ruleHistory);
    }

    public Rule[] getRuleHistory()
    {
        return ruleHistory;
    }

    public int getDestination()
    {
        return destination;
    }

    public int getSource()
    {
        return source;
    }

    public void setRuleHistory(Rule[] ruleHistory)
    {
        this.ruleHistory = ruleHistory;
    }

    public void setDestination(int destination)
    {
        this.destination = destination;
    }

    public void setSource(int source)
    {
        this.source = source;
    }

    @Override
    public String toString()
    {
        return "TestPacket: source: " + getSource() + " destination: " + getDestination();
    }
}