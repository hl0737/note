import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("tab.fxml"));
        primaryStage.setTitle("平面最近点对Demo");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }
}
