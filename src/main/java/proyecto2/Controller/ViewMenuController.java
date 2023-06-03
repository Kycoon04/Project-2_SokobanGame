package proyecto2.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import proyecto2.util.FlowController;

public class ViewMenuController implements Initializable {
    Button pressedButton;
    @FXML
    private Button Nivel1;
    @FXML
    private Button Nivel2;
    @FXML
    private Button Nivel3;
    @FXML
    private Button Nivel4;
    @FXML
    private Button Nivel5;
    @FXML
    private Button Nivel6;
    @FXML
    private BorderPane ViewMain;
    @FXML
    private BorderPane ViewNiveles;
    @FXML
    private BorderPane ViewDerrota;
    @FXML
    private BorderPane ViewVictoria;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ViewMain.toFront();
        if(FlowController.getInstance().isPerdio()){
        ViewDerrota.toFront();
        }else{
        if(FlowController.getInstance().isGano()){
        ViewVictoria.toFront();
        }
        } 
        NivelEnSeleccion(FlowController.getNivel());
    }

    @FXML
    private void Met_Jugar(ActionEvent event) {
        FlowController.getInstance().setPerdio(false);
        FlowController.getInstance().setGano(false);
        FlowController.getInstance().goMain("ViewGame");
    }

    @FXML
    private void Met_Niveles(ActionEvent event) {
        Nivel1.getStyleClass().remove("nivel-seleccionado");
        Nivel2.getStyleClass().remove("nivel-seleccionado");
        Nivel3.getStyleClass().remove("nivel-seleccionado");
        Nivel4.getStyleClass().remove("nivel-seleccionado");
        Nivel5.getStyleClass().remove("nivel-seleccionado");
        Nivel6.getStyleClass().remove("nivel-seleccionado");
        pressedButton.getStyleClass().add("nivel-seleccionado");
        Nivel2.setDisable(!FlowController.isNivel2());
        Nivel3.setDisable(!FlowController.isNivel3());
        Nivel4.setDisable(!FlowController.isNivel4());
        Nivel5.setDisable(!FlowController.isNivel5());
        Nivel6.setDisable(!FlowController.isNivel6());
        ViewNiveles.toFront();
    }

    @FXML
    private void Met_Guardar(ActionEvent event) {
    }

    @FXML
    private void Volver(ActionEvent event) {
        ViewMain.toFront();
    }

    @FXML
    private void Selecionado(ActionEvent event) {
        pressedButton = (Button) event.getSource();
        int nivel = 0;
        Nivel1.getStyleClass().remove("nivel-seleccionado");
        Nivel2.getStyleClass().remove("nivel-seleccionado");
        Nivel3.getStyleClass().remove("nivel-seleccionado");
        Nivel4.getStyleClass().remove("nivel-seleccionado");
        Nivel5.getStyleClass().remove("nivel-seleccionado");
        Nivel6.getStyleClass().remove("nivel-seleccionado");
        switch (pressedButton.getId()) {
            case "Nivel1":
                nivel = 1;
                pressedButton.getStyleClass().add("nivel-seleccionado");
                break;
            case "Nivel2":
                nivel = 2;
                pressedButton.getStyleClass().add("nivel-seleccionado");
                break;
            case "Nivel3":
                nivel = 3;
                pressedButton.getStyleClass().add("nivel-seleccionado");
                break;
            case "Nivel4":
                nivel = 4;
                pressedButton.getStyleClass().add("nivel-seleccionado");
                break;
            case "Nivel5":
                nivel = 5;
                pressedButton.getStyleClass().add("nivel-seleccionado");
                break;
            case "Nivel6":
                nivel = 6;
                pressedButton.getStyleClass().add("nivel-seleccionado");
                break;
            default:
                break;
        }
        FlowController.setNivel(nivel);
    }
    public void NivelEnSeleccion(int Nivel){ 
        switch (Nivel) {
            case 1:
                pressedButton = Nivel1;
                break;
            case 2:
                pressedButton = Nivel2;
                break;
            case 3:
                pressedButton = Nivel3;
                break;
            case 4:
                pressedButton = Nivel4;
                break;
            case 5:
                pressedButton = Nivel5;
                break;
            case 6:
                pressedButton = Nivel6;
                break;
            default:
                break;
        }
    }
}
