package netze2gui;

import javafx.scene.control.ListCell;

/**
 * Created by B3rni on 22.06.2015.
 */
public class RuleListViewCellFactory extends ListCell<Rule>
{
    @Override protected void updateItem(Rule item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty)
        {
            StringBuilder sb = new StringBuilder();
            for(String s : item.getDestinations())
            {
                sb.append(s + ", ");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.deleteCharAt(sb.length()-1);
            setText(item.getName() + " Dest:  " + sb.toString() +" -> " + item.getLink());
        }
    }

}
