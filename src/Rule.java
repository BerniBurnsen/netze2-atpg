import java.io.Serializable;

/**
 * Created by Joncn on 15.06.2015.
 */
public class Rule implements Serializable
{
    private final String destination;
    private final String link;
    private final String name;

    public Rule(String name, String destination, String link)
    {
        this.destination = destination;
        this.link = link;
        this.name = name;
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

    @Override
    public String toString()
    {
        return "Rule \"" + name + "\"";
    }
}
