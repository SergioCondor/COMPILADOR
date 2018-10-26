package sample;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader.ProgressNotification;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static int duracion = 1000;
    public static int steps = 1;

    public Main() {
    }

    public void init() throws Exception {
        for(int i = 0; i < duracion; ++i) {
            double progress = (double)(100 * i / duracion);
            LauncherImpl.notifyPreloader(this, new ProgressNotification(progress));
        }

    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("principal.fxml"));
        primaryStage.setTitle("Music Tracker");
        primaryStage.setScene(new Scene(root, 800.0D, 600.0D));
        primaryStage.show();
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(Main.class, Splash.class, args);
    }
}
