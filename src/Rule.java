import java.io.Serializable;

/**
 * Created by Joncn on 15.06.2015.
 */
public class Rule implements Serializable
{
    private final int destination, port;

    public Rule(int destination, int port)
    {
        this.destination = destination;
        this.port = port;
    }

    public int getDestination()
    {
        return destination;
    }

    public int getPort()
    {
        return port;
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
            if (this.destination == rule.destination && this.port == rule.getPort())
            {
                return true;
            }
        }
        return false;
    }
}
