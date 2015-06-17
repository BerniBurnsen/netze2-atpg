package netze2gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import netze2gui.viewsandcontroller.MainController;

import java.io.InputStream;
import java.util.Scanner;


public class Main extends Application
{
    Stage mainStage;
    public static MainController mainWindow;
    static TestTerminal tt;


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Platform.setImplicitExit(false);
        mainStage = primaryStage;

        gotoMainWindow();

        mainStage.show();

    }

    /**
     * switch view to Main Window
     */
    public void gotoMainWindow()
    {
        try
        {
            mainWindow = (MainController) replaceSceneContent("/netze2gui/viewsandcontroller/main.fxml");
            mainWindow.setTestTerminal(tt);
            mainStage.setTitle("Netze2");
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private Initializable replaceSceneContent(String fxml) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));

        AnchorPane page;
        try
        {
            page = (AnchorPane) loader.load(in);
        } finally
        {
            in.close();
        }
        Scene scene = new Scene(page, 800, 600);
        mainStage.setScene(scene);
        mainStage.sizeToScene();
        return (Initializable) loader.getController();
    }


    public static void main(String[] args)
    {
        new Link(Config.Link_AB, Config.ports.get(Config.Link_AB), Config.Switch_A, Config.Switch_B, true).start();
        new Link(Config.Link_AC, Config.ports.get(Config.Link_AC), Config.Switch_A, Config.Switch_C, true).start();
        new Link(Config.Link_BC, Config.ports.get(Config.Link_BC), Config.Switch_B, Config.Switch_C, true).start();

        new Switch(Config.Switch_A, Config.ports.get(Config.Switch_A), Config.rules.get(Config.Switch_A), Config.Link_AB, Config.Link_AC).start();
        new Switch(Config.Switch_B, Config.ports.get(Config.Switch_B), Config.rules.get(Config.Switch_B), Config.Link_AB, Config.Link_BC).start();
        new Switch(Config.Switch_C, Config.ports.get(Config.Switch_C), Config.rules.get(Config.Switch_C), Config.Link_BC, Config.Link_AC).start();
        System.out.println("Switches started!");

        tt = new TestTerminal(Config.Terminal_A_port, Config.Terminal_B_port, Config.Terminal_C_port);
        tt.start();
        System.out.println("netze2gui.TestTerminal started!");

        boolean run = true;
        try
        {
            tt.acquireSendMutex();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        launch(args);

        while (run)
        {
            try
            {
                tt.acquireReadMutex();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println("");
            System.out.println("Type start or end:");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input.toLowerCase())
            {
                case "start":
                    System.out.println("Starting");
                    tt.releaseSendMutex();
                    break;
                case "end":
                    System.out.println("Ending");
                    run = false;
            }
        }
        System.exit(0);


    }
}
