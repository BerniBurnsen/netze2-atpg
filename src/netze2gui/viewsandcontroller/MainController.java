package netze2gui.viewsandcontroller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import netze2gui.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by B3rni on 17.06.2015.
 * This is the controller of the ATPG-GUI
 */
public class MainController implements Initializable
{
    public static final int MILLIS = 4000;
    ImageView[] packetImageViews;
    ImageView[] LINK_AB_FORWARD;
    ImageView[] LINK_AB_BACKWARD;
    ImageView[] LINK_AC_FORWARD;
    ImageView[] LINK_AC_BACKWARD;
    ImageView[] LINK_BC_FORWARD;
    ImageView[] LINK_BC_BACKWARD;

    @FXML
    ImageView SwitchAImageView;
    @FXML
    ImageView SwitchBImageView;
    @FXML
    ImageView SwitchCImageView;

    @FXML
    ImageView packetImageView_SwitchA;
    @FXML
    ImageView packetImageView_SwitchB;
    @FXML
    ImageView packetImageView_SwitchC;

    @FXML
    ImageView packetImageView_LINKAB_1;
    @FXML
    ImageView packetImageView_LINKAB_2;
    @FXML
    ImageView packetImageView_LINKAB_3;

    @FXML
    ImageView packetImageView_LINKAC_1;
    @FXML
    ImageView packetImageView_LINKAC_2;
    @FXML
    ImageView packetImageView_LINKAC_3;

    @FXML
    ImageView packetImageView_LINKBC_1;
    @FXML
    ImageView packetImageView_LINKBC_2;
    @FXML
    ImageView packetImageView_LINKBC_3;

    @FXML
    ImageView packetImageView_SWITCH_A_ERROR;
    @FXML
    ImageView packetImageView_SWITCH_B_ERROR;
    @FXML
    ImageView packetImageView_SWITCH_C_ERROR;

    @FXML
    ImageView packetImageView_LINKAB_ERROR;
    @FXML
    ImageView packetImageView_LINKAC_ERROR;
    @FXML
    ImageView packetImageView_LINKBC_ERROR;

    @FXML
    ImageView packetImageView_TERMINAL_A_1;
    @FXML
    ImageView packetImageView_TERMINAL_A_2;
    @FXML
    ImageView packetImageView_TERMINAL_B_1;
    @FXML
    ImageView packetImageView_TERMINAL_B_2;
    @FXML
    ImageView packetImageView_TERMINAL_C_1;
    @FXML
    ImageView packetImageView_TERMINAL_C_2;

    @FXML
    ImageView packetImageView_TERMINAL_A_1_ERROR;
    @FXML
    ImageView packetImageView_TERMINAL_A_2_ERROR;
    @FXML
    ImageView packetImageView_TERMINAL_B_1_ERROR;
    @FXML
    ImageView packetImageView_TERMINAL_B_2_ERROR;
    @FXML
    ImageView packetImageView_TERMINAL_C_1_ERROR;
    @FXML
    ImageView packetImageView_TERMINAL_C_2_ERROR;

    @FXML
    ListView allRules;
    @FXML
    ListView switch_A_RULES;
    @FXML
    ListView switch_B_RULES;
    @FXML
    ListView switch_C_RULES;

    @FXML
    TableColumn<TestPacket,String> fromTableColumn;
    @FXML
    TableColumn<TestPacket,String> toTableColumn;
    @FXML
    TableColumn<TestPacket,String> historyTableColumn;

    @FXML
    TableView<TestPacket> testPacketsTableView;

    @FXML
    Button startAnimationButton;

    private final List<Object> allRulesLinks = new ArrayList<>();
    private TestTerminal tt;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        packetImageViews = new ImageView[]{
                packetImageView_LINKAB_1,
                packetImageView_LINKAB_2,
                packetImageView_LINKAB_3,
                packetImageView_LINKAC_1,
                packetImageView_LINKAC_2,
                packetImageView_LINKAC_3,
                packetImageView_LINKBC_1,
                packetImageView_LINKBC_2,
                packetImageView_LINKBC_3,
                packetImageView_SwitchA,
                packetImageView_SwitchB,
                packetImageView_SwitchC,
                packetImageView_LINKAB_ERROR,
                packetImageView_LINKAC_ERROR,
                packetImageView_LINKBC_ERROR,
                packetImageView_SWITCH_A_ERROR,
                packetImageView_SWITCH_B_ERROR,
                packetImageView_SWITCH_C_ERROR,
                packetImageView_TERMINAL_A_1,
                packetImageView_TERMINAL_A_2,
                packetImageView_TERMINAL_B_1,
                packetImageView_TERMINAL_B_2,
                packetImageView_TERMINAL_C_1,
                packetImageView_TERMINAL_C_2,
                packetImageView_TERMINAL_A_1_ERROR,
                packetImageView_TERMINAL_A_2_ERROR,
                packetImageView_TERMINAL_B_1_ERROR,
                packetImageView_TERMINAL_B_2_ERROR,
                packetImageView_TERMINAL_C_1_ERROR,
                packetImageView_TERMINAL_C_2_ERROR

        };

        LINK_AB_FORWARD = new ImageView[]{
                packetImageView_LINKAB_1,
                packetImageView_LINKAB_2,
                packetImageView_LINKAB_3
        };

        LINK_AB_BACKWARD = new ImageView[]{
                packetImageView_LINKAB_3,
                packetImageView_LINKAB_2,
                packetImageView_LINKAB_1
        };

        LINK_AC_FORWARD = new ImageView[]{
                packetImageView_LINKAC_1,
                packetImageView_LINKAC_2,
                packetImageView_LINKAC_3
        };

        LINK_AC_BACKWARD = new ImageView[]{
                packetImageView_LINKAC_3,
                packetImageView_LINKAC_2,
                packetImageView_LINKAC_1
        };

        LINK_BC_FORWARD = new ImageView[]{
                packetImageView_LINKBC_1,
                packetImageView_LINKBC_2,
                packetImageView_LINKBC_3

        };

        LINK_BC_BACKWARD = new ImageView[]{
                packetImageView_LINKBC_3,
                packetImageView_LINKBC_2,
                packetImageView_LINKBC_1

        };

        for (ImageView iv : packetImageViews)
        {
            iv.setOpacity(0.0);
        }
        System.out.println("initialize finished");
    }

    public void flagTestPacket(TestPacket tp)
    {
        Platform.runLater(() -> testPacketsTableView.getSelectionModel().select(tp));
    }

    public void init()
    {
        fillTestPacketTableView();
        fillSwitchARuleListView();
        fillSwitchBRuleListView();
        fillSwitchCRuleListView();
        fillAllRulesListView();
    }

    private void fillAllRulesListView()
    {
        allRulesLinks.clear();
        final ObservableList<Object> orl = FXCollections.observableArrayList();
        for(Rule r : Config.Switch_A_rules)
        {
            orl.add(r);
        }
        for(Rule r : Config.Switch_B_rules)
        {
            orl.add(r);
        }
        for(Rule r : Config.Switch_C_rules)
        {
            orl.add(r);
        }
        for(Link l : Main.allLinks)
        {
            orl.add(l);
        }
        allRulesLinks.addAll(orl);
        allRules.setItems(orl);
        allRules.setCellFactory(new Callback<ListView<Object>, ListCell<Object>>()
        {
            @Override
            public ListCell<Object> call(ListView<Object> list)
            {
                return new RuleCell();
            }
        });
    }

    public void flagRuleCorrect(String name)
    {
        Object tmpObject = null;
        for(Object obj : allRulesLinks)
        {
            if(obj.toString().contains(name))
            {
                tmpObject = obj;
                break;
            }
        }
        //System.err.println("found rule: " + tmpObject);
        if(tmpObject instanceof Rule)
        {
            ((Rule) tmpObject).setWorked(true);
        }
        else if (tmpObject instanceof Link)
        {
            ((Link)tmpObject).setWorked(true);
        }
        fillAllRulesListView();
    }

    private void fillSwitchARuleListView()
    {
        final ObservableList<Rule> orl = FXCollections.observableArrayList();
        for(Rule r : Config.Switch_A_rules)
        {
            orl.add(r);
        }
        switch_A_RULES.setItems(orl);
        switch_A_RULES.setCellFactory(new Callback<ListView<Rule>, ListCell<Rule>>()
        {
            @Override
            public ListCell<Rule> call(ListView<Rule> list)
            {
                return new RuleListViewCellFactory();
            }
        });
    }
    private void fillSwitchBRuleListView()
    {
        final ObservableList<Rule> orl = FXCollections.observableArrayList();
        for(Rule r : Config.Switch_B_rules)
        {
            orl.add(r);
        }
        switch_B_RULES.setItems(orl);
        switch_B_RULES.setCellFactory(new Callback<ListView<Rule>, ListCell<Rule>>()
        {
            @Override
            public ListCell<Rule> call(ListView<Rule> list)
            {
                return new RuleListViewCellFactory();
            }
        });

    }
    private void fillSwitchCRuleListView()
    {
        final ObservableList<Rule> orl = FXCollections.observableArrayList();
        for(Rule r : Config.Switch_C_rules)
        {
            orl.add(r);
        }
        switch_C_RULES.setItems(orl);
        switch_C_RULES.setCellFactory(new Callback<ListView<Rule>, ListCell<Rule>>()
        {
            @Override
            public ListCell<Rule> call(ListView<Rule> list)
            {
                return new RuleListViewCellFactory();
            }
        });
    }
    private void fillTestPacketTableView()
    {
        final ObservableList<TestPacket> otl = FXCollections.observableArrayList();
        otl.clear();
        otl.addAll(Config.allTestpackets);
        fromTableColumn.setCellValueFactory(new PropertyValueFactory<TestPacket, String>("from"));
        toTableColumn.setCellValueFactory(new PropertyValueFactory<TestPacket, String>("destination"));
        historyTableColumn.setCellValueFactory(new PropertyValueFactory<TestPacket, String>("ruleHistoryString"));
        testPacketsTableView.setItems(otl);
    }


    public void animateLinkABForward()
    {
        animateLink(LINK_AB_FORWARD);
    }

    public void animateLinkABBackward()
    {
        animateLink(LINK_AB_BACKWARD);
    }

    public void animateLinkACForward()
    {
        animateLink(LINK_AC_FORWARD);
    }

    public void animateLinkACBackward()
    {
        animateLink(LINK_AC_BACKWARD);
    }

    public void animateLinkBCForward()
    {
        animateLink(LINK_BC_FORWARD);
    }

    public void animateLinkBCBackward()
    {
        animateLink(LINK_BC_BACKWARD);
    }

    public void packetSwitch_A_Error_Visible() {animateImageVisible(packetImageView_SWITCH_A_ERROR);}
    public void packetSwitch_B_Error_Visible() {animateImageVisible(packetImageView_SWITCH_B_ERROR);}
    public void packetSwitch_C_Error_Visible() {animateImageVisible(packetImageView_SWITCH_C_ERROR);}
    public void packetLinkAB_Error_Visible() {animateImageVisible(packetImageView_LINKAB_ERROR);}
    public void packetLinkAC_Error_Visible() {animateImageVisible(packetImageView_LINKAC_ERROR);}
    public void packetLinkBC_ERROR_Visible() {animateImageVisible(packetImageView_LINKBC_ERROR);}

    public void packetSwitch_A_ERROR_Invisible() {animateImageInvisible(packetImageView_SWITCH_A_ERROR);}
    public void packetSwitch_B_ERROR_Invisible() {animateImageInvisible(packetImageView_SWITCH_B_ERROR);}
    public void packetSwitch_C_ERROR_Invisible() {animateImageInvisible(packetImageView_SWITCH_C_ERROR);}
    public void packetLinkAB_ERROR_Invisible() {animateImageInvisible(packetImageView_LINKAB_ERROR);}
    public void packetLinkAC_ERROR_Invisible() {animateImageInvisible(packetImageView_LINKAC_ERROR);}
    public void packetLinkBC_ERROR_Invisible() {animateImageInvisible(packetImageView_SWITCH_A_ERROR);}

    public void packetSwitch_A_Visible()
    {
        animateImageVisible(packetImageView_SwitchA);
    }
    public void packetSwitch_A_Inisible()
    {
        animateImageInvisible(packetImageView_SwitchA);
    }

    public void packetSwitch_B_Visible()
    {
        animateImageVisible(packetImageView_SwitchB);
    }

    public void packetSwitch_B_Inisible()
    {
        animateImageInvisible(packetImageView_SwitchB);
    }

    public void packetSwitch_C_Visible()
    {
        animateImageVisible(packetImageView_SwitchC);
    }

    public void packetSwitch_C_Inisible()
    {
        animateImageInvisible(packetImageView_SwitchC);
    }

    public void packetTerminal_A_ERROR()
    {
        if(packetImageView_TERMINAL_A_1_ERROR.getOpacity() == 0.0)
        {
           animateImageVisible(packetImageView_TERMINAL_A_1_ERROR);
        }
        else if(packetImageView_TERMINAL_A_2_ERROR.getOpacity() == 0.0)
        {
            animateImageVisible(packetImageView_TERMINAL_A_2_ERROR);
        }
    }
    public void packetTerminal_B_ERROR()
    {
        if(packetImageView_TERMINAL_B_1_ERROR.getOpacity() == 0.0)
        {
            animateImageVisible(packetImageView_TERMINAL_B_1_ERROR);
        }
        else if(packetImageView_TERMINAL_B_2_ERROR.getOpacity() == 0.0)
        {
            animateImageVisible(packetImageView_TERMINAL_B_2_ERROR);
        }
    }
    public void packetTerminal_C_ERROR()
    {
        if(packetImageView_TERMINAL_C_1_ERROR.getOpacity() == 0.0)
        {
            animateImageVisible(packetImageView_TERMINAL_C_1_ERROR);
        }
        else if(packetImageView_TERMINAL_C_2_ERROR.getOpacity() == 0.0)
        {
            animateImageVisible(packetImageView_TERMINAL_C_2_ERROR);
        }
    }

    public void packetTerminal_A_Success()
    {
        if(packetImageView_TERMINAL_A_1.getOpacity() == 0.0)
        {
            animateImageVisible(packetImageView_TERMINAL_A_1);
        }
        else if (packetImageView_TERMINAL_A_2.getOpacity() == 0.0)
        {
            animateImageVisible(packetImageView_TERMINAL_A_2);
        }
    }
    public void packetTerminal_B_Success()
    {
        if(packetImageView_TERMINAL_B_1.getOpacity() == 0.0)
        {
            animateImageVisible(packetImageView_TERMINAL_B_1);
        }
        else if (packetImageView_TERMINAL_B_2.getOpacity() == 0.0)
        {
            animateImageVisible(packetImageView_TERMINAL_B_2);
        }
    }
    public void packetTerminal_C_Success()
    {
        if(packetImageView_TERMINAL_C_1.getOpacity() == 0.0)
        {
            animateImageVisible(packetImageView_TERMINAL_C_1);
        }
        else if (packetImageView_TERMINAL_C_2.getOpacity() == 0.0)
        {
            animateImageVisible(packetImageView_TERMINAL_C_2);
        }
    }

    public void changePictureToWrong(TestPacket tp)
    {
        /*ImageView tmpiv = null;
        for(ImageView imageView : packetImageViews)
        {
            if(imageView.getOpacity() == 1.0)
            {
                tmpiv = imageView;
            }
        }

        final ImageView iv = tmpiv;
        Task task = new Task<Void>()
        {
            @Override
            public Void call() throws Exception
            {
                Image oldImage = iv.getImage();
                Platform.runLater(() -> iv.setOpacity(0.0));
                String imageViewID = iv.toString().substring(iv.toString().indexOf('=')+1,iv.toString().indexOf(']'));

                switch(imageViewID)
                {
                    case "packetImageView_SwitchA":
                        packetSwitch_A_Error_Visible();

                        break;
                    case "packetImageView_SwitchB":
                        packetSwitch_B_Error_Visible();
                        System.out.println(imageViewID);
                        break;
                    case "packetImageView_SwitchC":
                        packetSwitch_C_Error_Visible();
                        break;
                }
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                switch(imageViewID)
                {
                    case "packetImageView_SwitchA":
                        packetSwitch_A_ERROR_Invisible();
                        break;
                    case "packetImageView_SwitchB":
                        packetSwitch_B_ERROR_Invisible();
                        break;
                    case "packetImageView_SwitchC":
                        packetSwitch_C_ERROR_Invisible();
                        break;
                }
                Platform.runLater(() -> iv.setOpacity(0.0));
                Platform.runLater(() -> iv.setImage(oldImage));
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();*/
    }



    private void animateImageVisible(ImageView iv)
    {
        Platform.runLater(() -> iv.setOpacity(1.0));
    }

    private void animateImageInvisible(ImageView iv)
    {
        Platform.runLater(() -> iv.setOpacity(0.0));
    }

    private void animateLink(ImageView[] iva)
    {
        Task task = new Task<Void>()
        {
            @Override
            public Void call() throws Exception
            {
                for (ImageView iv : iva)
                {
//                    System.out.println("visible" + iv);
                    Platform.runLater(() -> iv.setOpacity(1.0));
                    try
                    {
                        Thread.sleep(300);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
//                    System.out.println("invisible" + iv);
                    Platform.runLater(() -> iv.setOpacity(0.0));
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }


    @FXML
    private void handleStartAnimation(ActionEvent event)
    {
        tt.releaseSendMutex();
    }

    public void setTestTerminal(TestTerminal tt)
    {
        this.tt = tt;
    }


}