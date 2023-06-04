package proyecto2.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import proyecto2.Model.Jugador;
import proyecto2.util.FlowController;
import proyecto2.util.Formato;

public class ViewMenuController implements Initializable {

    Formato formato = new Formato();
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
    private BorderPane ViewIniciarCuenta;
    @FXML
    private BorderPane ViewRegistrarse;
    @FXML
    private TextField Campo_RegisUsuario;
    @FXML
    private TextField Campo_RegisContra;
    @FXML
    private TextField Campo_RegisContraConfi;
    @FXML
    private BorderPane ViewIniciarSesion;
    @FXML
    private Text NombreUsuarioImportar;
    @FXML
    private BorderPane ViewImportar;
    @FXML
    private TextField Campo_IniciarUsuario;
    @FXML
    private TextField Campo_IniciarContra;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ViewMain.toFront();

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

    public void ActualizarNivelesDispoDB() {
        FlowController.setNivel1(false);
        FlowController.setNivel2(false);
        FlowController.setNivel3(false);
        FlowController.setNivel4(false);
        FlowController.setNivel5(false);
        FlowController.setNivel6(false);
        for (int i = 1; i <= FlowController.getNivel(); i++) {
            switch (i) {
                case 1:
                    FlowController.setNivel1(true);
                    break;
                case 2:
                    FlowController.setNivel2(true);
                    break;
                case 3:
                    FlowController.setNivel3(true);
                    break;
                case 4:
                    FlowController.setNivel4(true);
                    break;
                case 5:
                    FlowController.setNivel5(true);
                    break;
                case 6:
                    FlowController.setNivel6(true);
                    break;
                default:
                    break;
            }
        }
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

    public void NivelEnSeleccion(int Nivel) {
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

    @FXML
    private void Met_ImportarPartida(ActionEvent event) {
        if (FlowController.getJugadorEnSesion() == null) {
            ViewIniciarCuenta.toFront();
        } else {
            ViewImportar.toFront();
        }
    }

    @FXML
    private void Registrarse(ActionEvent event) {
        ViewRegistrarse.toFront();
    }

    @FXML
    private void IniciarSesion(ActionEvent event) {
        ViewIniciarSesion.toFront();
    }

    @FXML
    private void AceptarRegistro(ActionEvent event) {
        if (Revisar()) {
            Jugador jugador = new Jugador(Campo_RegisUsuario.getText(),
                    Campo_RegisContra.getText(),
                    (short) FlowController.getNivel());
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto2_Proyecto2_jar_1.0-SNAPSHOTPU");
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(jugador);
            tx.commit();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("El jugador se ha insertado correctamente en la base de datos");
            alert.showAndWait();
            em.close();
            emf.close();
            FlowController.setJugadorEnSesion(jugador);

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El jugador no se pudo insertar en la base de datos");
            alert.showAndWait();
        }
    }

    public boolean Revisar() {
        boolean resultado = true;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto2_Proyecto2_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        List<Jugador> registros = null;
        Query query;
        query = em.createQuery("SELECT j FROM Jugador j WHERE j.jrNombre = :fname");
        query.setParameter("fname", Campo_RegisUsuario.getText());
        registros = query.getResultList();
        if (!registros.isEmpty()) {
            resultado = false;
        }
        resultado &= formato.validarCampoTexto(Campo_RegisUsuario.getText(), 30);
        resultado &= formato.validarCampoTexto(Campo_RegisContra.getText(), 30);
        if (!Campo_RegisContra.getText().equals(Campo_RegisContraConfi.getText())) {
            resultado = false;
        }
        return resultado;
    }

    @FXML
    private void AceptarInicioSesion(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto2_Proyecto2_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Query query = em.createQuery("SELECT p FROM Jugador p where p.jrNombre = :fname AND p.jrContrasena = :fcedula");
        query.setParameter("fname", Campo_IniciarUsuario.getText());
        query.setParameter("fcedula", Campo_IniciarContra.getText());
        List<Jugador> registro = query.getResultList();
        if (!registro.isEmpty()) {
            FlowController.setNivel(registro.get(0).getJrNivelesganados());
            ActualizarNivelesDispoDB();
            NivelEnSeleccion(FlowController.getNivel());
            FlowController.setJugadorEnSesion(registro.get(0));
            NombreUsuarioImportar.setText(FlowController.getJugadorEnSesion().getJrNombre());
            ViewImportar.toFront();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Usuario y/o contraseña incorrecta");
            alert.showAndWait();
        }
    }

    @FXML
    private void AceptarImportar(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto2_Proyecto2_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Query query = em.createQuery("SELECT p FROM Jugador p where p.jrNombre = :fname AND p.jrContrasena = :fcedula");
        query.setParameter("fname", FlowController.getJugadorEnSesion().getJrNombre());
        query.setParameter("fcedula", FlowController.getJugadorEnSesion().getJrContrasena());
        List<Jugador> registro = query.getResultList();
        if (registro.get(0).getJrNivelguardado() != null) {
            FlowController.setNivel(registro.get(0).getJrNivelesganados());
            FlowController.setNivelImportado(registro.get(0).getJrNivelguardado());
            FlowController.setImportar(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Se cargo la partida correctamente");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("No tiene una partida cargada");
            alert.showAndWait();
        }
    }

    @FXML
    private void CerrarSesion(ActionEvent event) {
        FlowController.setJugadorEnSesion(null);
        FlowController.setNivel(1);
        ActualizarNivelesDispoDB();
        NivelEnSeleccion(1);
        Nivel2.setDisable(true);
        Nivel3.setDisable(true);
        Nivel4.setDisable(true);
        Nivel5.setDisable(true);
        Nivel6.setDisable(true);
        ViewMain.toFront();
    }
}
