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
                packetImageView_SwitchC
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
        System.out.println(tmpiv);

        final ImageView iv = tmpiv;
        Task task = new Task<Void>()
        {
            @Override
            public Void call() throws Exception
            {
                Platform.runLater(() ->
                {
                    Image oldImage = iv.getImage();
                    Platform.runLater(() -> iv.setOpacity(0.0));
                    Platform.runLater(() -> iv.setImage(new Image("/netze2gui/resources/paket_wrong.png")));
                    Platform.runLater(() -> iv.setOpacity(1.0));
                    try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> iv.setOpacity(0.0));
                    Platform.runLater(() -> iv.setImage(oldImage));

                });
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