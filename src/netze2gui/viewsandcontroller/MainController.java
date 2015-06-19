package netze2gui.viewsandcontroller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import netze2gui.TestTerminal;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by B3rni on 17.06.2015.
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
    Button startAnimationButton;
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
    public void packetLinkAB_Error_Visible() {        animateImageVisible(packetImageView_LINKAB_ERROR);}
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

    public void changePictureToWrong()
    {
        ImageView tmpiv = null;
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
        th.start();
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

        /*System.out.println("starting animation test");

        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                animateLinkABForward();
                try
                {
                    Thread.sleep(MILLIS);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                animateLinkABBackward();
                try
                {
                    Thread.sleep(MILLIS);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                animateLinkACForward();
                try
                {
                    Thread.sleep(MILLIS);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                animateLinkACBackward();
                try
                {
                    Thread.sleep(MILLIS);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                animateLinkBCForward();
                try
                {
                    Thread.sleep(MILLIS);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                animateLinkBCBackward();
                try
                {
                    Thread.sleep(MILLIS);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();


*/
    }

    public void setTestTerminal(TestTerminal tt)
    {
        this.tt = tt;
    }


}