import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Joncn on 15.06.2015.
 */
public class Rule implements Serializable
{
    private String[] destinations;
    private final String link;
    private final String name;
    private boolean working;

    public Rule(String name, boolean working, String link, String destination, String... destinations)
    {
        if(destinations != null && destinations.length > 0)
        {
            LinkedList<String> tmp = new LinkedList<>();
            tmp.addFirst(destination);
            tmp.addAll(Arrays.asList(destinations));
            destinations = tmp.toArray(destinations);
        }
        else
        {
            destinations = new String[] {destination};
        }
        this.link = link;
        this.name = name;
        this.setWorking(working);
    }

    public String[] getDestinations()
    {
        return destinations;
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
            if (Arrays.equals(this.destinations, rule.destinations) && this.link.equals(rule.getLink()))
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
