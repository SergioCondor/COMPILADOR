

package sample;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Splash extends Preloader {
    private Label lblProgress;
    private ProgressBar barra;
    private Stage stage;
    private Scene scene;

    public Splash() {
    }

    public void handleStateChangeNotification(StateChangeNotification info) {
        Type type = info.getType();
        switch(type) {
            case BEFORE_START:
                this.stage.hide();
            default:
        }
    }

    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
           this.lblProgress.setText(((ProgressNotification)info).getProgress() + "%");
           this.barra.setProgress(((ProgressNotification)info).getProgress() / 100.0D);
        }

    }

    public void init() throws Exception {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    Parent root2 = (Parent)FXMLLoader.load(this.getClass().getResource("Splash.fxml"));
                    Splash.this.scene = new Scene(root2, 580.0D, 350.0D);
                } catch (IOException var3) {
                    var3.printStackTrace();
                }

            }
        });
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.setScene(this.scene);
        this.stage.show();
     this.lblProgress = (Label)this.scene.lookup("#lblP");
     this.barra = (ProgressBar)this.scene.lookup("#prog");
    }
}
