import java.io.Serializable;

/**
 * Created by Joncn on 15.06.2015.
 */
public class Rule implements Serializable
{
    private final String destination;
    private final String link;
    private final String name;
    private boolean working;

    public Rule(String name, String destination, String link, boolean working)
    {
        this.destination = destination;
        this.link = link;
        this.name = name;
        this.setWorking(working);
    }

    public String getDestination()
    {
        return destination;
    }

    public String getLink()
    {
        return link;
    }

    @Override
    public boolean equals(Object rule2)
    {
        if(rule2 == null)
        {
            return false;
        }
        if(rule2 instanceof Rule)
        {
            Rule rule = (Rule) rule2;
            if (this.destination.equals(rule.destination) && this.link.equals(rule.getLink()))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isWorking()
    {
        return working;
    }

    public void setWorking(boolean working)
    {
        this.working = working;
    }

    @Override
    public String toString()
    {
        return "Rule \"" + name + "\"";
    }
}
