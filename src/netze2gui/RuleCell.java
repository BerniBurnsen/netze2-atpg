package netze2gui;

import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;

/**
 * Created by B3rni on 22.06.2015.
 */
public class RuleCell extends ListCell<Object>
{

    public RuleCell() {    }

    @Override protected void updateItem(Object item, boolean empty) {
        super.updateItem(item,empty);
        //System.err.println("update item! " + item);
        if(item instanceof Rule)
        {
            Rule rule = (Rule)item;
            if(rule.isWorked())
            {
                setStyle("-fx-background-color: chartreuse; -fx-font-size: 15; -fx-font-weight: bold");
                setText(item.toString());
            }
            else
            {
                setStyle("-fx-background-color: #ffb2b2; -fx-font-size: 15; -fx-font-weight: bold");
                setText(item.toString());
            }
        }
        else if(item instanceof Link)
        {
            Link l = (Link)item;
            if(l.isWorked())
            {
                setStyle("-fx-background-color: chartreuse; -fx-font-size: 15; -fx-font-weight: bold");
                setText(item.toString());
            }
            else
            {
                setStyle("-fx-background-color: #ffb2b2; -fx-font-size: 15; -fx-font-weight: bold");
                setText(item.toString());
            }
        }
    }
}