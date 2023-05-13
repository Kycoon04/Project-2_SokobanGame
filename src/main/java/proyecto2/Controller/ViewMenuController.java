package proyecto2.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import proyecto2.util.FlowController;

public class ViewMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }    

    @FXML
    private void Met_Jugar(ActionEvent event) {
        FlowController.getInstance().goMain("ViewGame");

    }

    @FXML
    private void Met_Niveles(ActionEvent event) {
    }

    @FXML
    private void Met_Guardar(ActionEvent event) {
    }
    
}
