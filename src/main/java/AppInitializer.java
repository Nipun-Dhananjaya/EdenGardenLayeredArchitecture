import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class AppInitializer extends Application {

    public static void main(String[] args) throws MessagingException, GeneralSecurityException, IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //primaryStage.setFullScreen(true);
        //primaryStage.setMaximized(true);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Profile Selection");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/profileSelectionForm.fxml"))));
        primaryStage.show();
    }
}