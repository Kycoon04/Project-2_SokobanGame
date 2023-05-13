    package proyecto2.Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import proyecto2.util.FlowController;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage,null);
        stage.setTitle("Sokoban");
        FlowController.getInstance().goMain("ViewMenu");
    }
}