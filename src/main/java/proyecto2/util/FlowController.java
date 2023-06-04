/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2.util;

import proyecto2.Main.App;
import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proyecto2.Controller.Controller;
import proyecto2.Model.Jugador;

public class FlowController {
    private static String NivelImportado;
    private static Jugador JugadorEnSesion = null;
    private static FlowController INSTANCE = null;
    private static Stage mainStage;
    private static ResourceBundle idioma;
    private static HashMap<String, FXMLLoader> loaders = new HashMap<>();
    static int Nivel=1;
    static boolean Nivel1=true;
    static boolean Nivel2=false;
    static boolean Nivel3=false;
    static boolean Nivel4=false;
    static boolean Nivel5=false;
    static boolean Nivel6=false;
    static boolean Perdio=false;
    static boolean Gano=false;
    static boolean Importar=false;
    private FlowController() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (FlowController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FlowController();
                }
            }
        }
    }

    public static String getNivelImportado() {
        return NivelImportado;
    }

    public static void setNivelImportado(String NivelImportado) {
        FlowController.NivelImportado = NivelImportado;
    }

    public static boolean isImportar() {
        return Importar;
    }

    public static void setImportar(boolean Importar) {
        FlowController.Importar = Importar;
    }

    public static Jugador getJugadorEnSesion() {
        return JugadorEnSesion;
    }

    public static void setJugadorEnSesion(Jugador JugadorEnSesion) {
        FlowController.JugadorEnSesion = JugadorEnSesion;
    }

    public static boolean isPerdio() {
        return Perdio;
    }

    public static void setPerdio(boolean Perdio) {
        FlowController.Perdio = Perdio;
    }

    public static boolean isGano() {
        return Gano;
    }

    public static void setGano(boolean Gano) {
        FlowController.Gano = Gano;
    }

    public static boolean isNivel1() {
        return Nivel1;
    }

    public static void setNivel1(boolean Nivel1) {
        FlowController.Nivel1 = Nivel1;
    }

    public static boolean isNivel2() {
        return Nivel2;
    }

    public static void setNivel2(boolean Nivel2) {
        FlowController.Nivel2 = Nivel2;
    }

    public static boolean isNivel3() {
        return Nivel3;
    }

    public static void setNivel3(boolean Nivel3) {
        FlowController.Nivel3 = Nivel3;
    }

    public static boolean isNivel4() {
        return Nivel4;
    }

    public static void setNivel4(boolean Nivel4) {
        FlowController.Nivel4 = Nivel4;
    }

    public static boolean isNivel5() {
        return Nivel5;
    }

    public static void setNivel5(boolean Nivel5) {
        FlowController.Nivel5 = Nivel5;
    }

    public static boolean isNivel6() {
        return Nivel6;
    }

    public static void setNivel6(boolean Nivel6) {
        FlowController.Nivel6 = Nivel6;
    }

    public static FlowController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public static int getNivel() {
        return Nivel;
    }

    public static void setNivel(int nivel) {
        FlowController.Nivel = nivel;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public void InitializeFlow(Stage stage, ResourceBundle idioma) {
        getInstance();
        this.mainStage = stage;
        this.idioma = idioma;
    }

    private FXMLLoader getLoader(String name) {
        FXMLLoader loader = loaders.get(name);
        if (loader == null) {
            synchronized (FlowController.class) {
                if (loader == null) {
                    try {
                        loader = new FXMLLoader(App.class.getResource(name + ".fxml"), this.idioma);
                        loader.load();
                        loaders.put(name, loader);
                    } catch (Exception ex) {
                        loader = null;
                    }
                }
            }
        }
        return loader;
    }

    public void goMain(String vista) {
        try {
            this.mainStage.setScene(new Scene(FXMLLoader.load(App.class.getResource(vista + ".fxml"), this.idioma)));
            this.mainStage.show();
        } catch (IOException ex) {
            //java.util.logging.Logger.getLogger(FlowController.class.getName()).log(Level.SEVERE, "Error inicializando la vista base.", ex);
        }
    }

    public void goView(String viewName) {
        goView(viewName, "Center", null);
    }

    public void goView(String viewName, String accion) {
        goView(viewName, "Center", accion);
    }

    public void goView(String viewName, String location, String accion) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setAccion(accion);
        controller.initialize();
        Stage stage = controller.getStage();
        if (stage == null) {
            stage = this.mainStage;
            controller.setStage(stage);
        }
        switch (location) {
            case "Center":
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().clear();
                ((VBox) ((BorderPane) stage.getScene().getRoot()).getCenter()).getChildren().add(loader.getRoot());
                break;
            case "Top":
                break;
            case "Bottom":
                break;
            case "Right":
                break;
            case "Left":
                break;
            default:
                break;
        }
    }

    public void goViewInStage(String viewName, Stage stage) {
        FXMLLoader loader = getLoader(viewName);
        Controller controller = loader.getController();
        controller.setStage(stage);
        stage.getScene().setRoot(loader.getRoot());
    }

    public Controller getController(String viewName) {
        return getLoader(viewName).getController();
    }

    public void initialize() {
        this.loaders.clear();
    }

    public void salir() {
        this.mainStage.close();
    }

}
