package proyecto2.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ViewGameController implements Initializable {

    ImageView Respaldo = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
    ImageView Respaldo2 = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));

    private String[][] MatrizNumber = new String[10][10];
    private String[][] MatrizRespaldo = new String[10][10];
    String[] numeros;
    private int PJ_Columna;
    private int PJ_Fila;
    private int numFila;
    private int numColumna;
    private int BanderaGanar;
    @FXML
    private GridPane Fisic;
    @FXML
    private BorderPane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarNivel();
        Platform.runLater(() -> {
            Scene scene = root.getScene();
            scene.setOnKeyPressed((KeyEvent event) -> {
                MovimientoPersonaje(event);
            });
        });
    }

    public void MovimientoPersonaje(KeyEvent event) {
        ImageView PersonajeMove = new ImageView(new Image("/proyecto2/Assets/Personaje.png"));
        switch (event.getCode()) {
            case UP:
                moverPersonaje(-1, 0, PersonajeMove);
                break;
            case DOWN:
                moverPersonaje(1, 0, PersonajeMove);
                break;
            case LEFT:
                PersonajeMove.setImage(new Image("/proyecto2/Assets/PersonajeIzquierda.png"));
                moverPersonaje(0, -1, PersonajeMove);
                break;
            case RIGHT:
                moverPersonaje(0, 1, PersonajeMove);
                break;
            default:
                break;
        }
    }

    private boolean verificarBorde(int fila, int columna, int desplazamientoFila, int desplazamientoColumna) {
        return MatrizNumber[fila + desplazamientoFila][columna + desplazamientoColumna].equals("1");
    }

    private boolean verificarCaja(int fila, int columna, int desplazamientoFila, int desplazamientoColumna) {
        return MatrizNumber[fila + desplazamientoFila][columna + desplazamientoColumna].equals("2");
    }

    public void moverPersonaje(int desplazamientoFila, int desplazamientoColumna, ImageView PersonajeMove) {
        ImageView caja = new ImageView(new Image("/proyecto2/Assets/BloqueCaja.png"));
        ImageView tierra = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
        ImageView BloqueDestino = new ImageView(new Image("/proyecto2/Assets/BloqueDestino.png"));
        boolean cajaLibre = true;

        if (!verificarBorde(PJ_Fila, PJ_Columna, desplazamientoFila, desplazamientoColumna)) { // Verifica si el campo donde quiere ir es un "1", osea, un borde
            if (!verificarBorde(PJ_Fila - desplazamientoFila, PJ_Columna - desplazamientoColumna, desplazamientoFila, desplazamientoColumna)) {
                // Verifica si el campo anterior es un borde para hacer la copia de seguridad de la imagen
                // Al ser ImageView, si yo camino, la imagen del bicho se repetirá en varias celdas
                Respaldo2 = SiguienteRespaldo(PJ_Fila + desplazamientoFila, PJ_Columna + desplazamientoColumna);
            } else {
                Respaldo2 = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                Respaldo = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
            }
            if (verificarCaja(PJ_Fila, PJ_Columna, desplazamientoFila, desplazamientoColumna)) {
                if (!MatrizNumber[PJ_Fila + ((desplazamientoFila + desplazamientoFila) * Math.abs(desplazamientoFila))][PJ_Columna + ((desplazamientoColumna + desplazamientoColumna) * Math.abs(desplazamientoColumna))].equals("1")) {
                    Fisic.add(caja, (PJ_Columna + ((desplazamientoColumna + desplazamientoColumna) * Math.abs(desplazamientoColumna))), (PJ_Fila + ((desplazamientoFila + desplazamientoFila) * Math.abs(desplazamientoFila))));

                    if (MatrizRespaldo[PJ_Fila + desplazamientoFila][PJ_Columna + desplazamientoColumna].equals("3")) {
                        MatrizNumber[PJ_Fila + desplazamientoFila][PJ_Columna + desplazamientoColumna] = "3";
                        Fisic.add(BloqueDestino, PJ_Columna + desplazamientoColumna, PJ_Fila + desplazamientoFila);
                    } else {
                        MatrizNumber[PJ_Fila + desplazamientoFila][PJ_Columna + desplazamientoColumna] = "0"; //problema aqui de perder el 3
                        Fisic.add(tierra, PJ_Columna + desplazamientoColumna, PJ_Fila + desplazamientoFila);
                    }
                    MatrizNumber[PJ_Fila + ((desplazamientoFila + desplazamientoFila) * Math.abs(desplazamientoFila))][PJ_Columna + ((desplazamientoColumna + desplazamientoColumna) * Math.abs(desplazamientoColumna))] = "2";

                    if (MatrizRespaldo[PJ_Fila][PJ_Columna].equals("3")) {
                        Fisic.add(BloqueDestino, PJ_Columna, PJ_Fila);
                    } else {
                        Fisic.add(Respaldo, PJ_Columna, PJ_Fila);
                    }
                    cajaLibre = true;
                } else {
                    cajaLibre = false;
                }
            } else {
                if (MatrizRespaldo[PJ_Fila][PJ_Columna].equals("3")) {
                    Fisic.add(BloqueDestino, PJ_Columna, PJ_Fila);
                } else {
                    Fisic.add(Respaldo, PJ_Columna, PJ_Fila);
                }
            }
            if (cajaLibre) {
                Fisic.add(PersonajeMove, PJ_Columna + desplazamientoColumna, PJ_Fila + desplazamientoFila);
                Respaldo = Respaldo2;
                PJ_Columna += desplazamientoColumna;
                PJ_Fila += desplazamientoFila;
            }
        }
        if (PosibleVictoria()) {
            System.out.println("gano la partida");
        }
    }

    public boolean PosibleVictoria() {
        for (int i = 0; i < MatrizNumber.length; i++) {
            for (int j = 0; j < MatrizNumber.length; j++) {
                if (MatrizNumber[i][j].equals("3")) {
                    return false;
                }
            }
        }
        return true;
    }

    public ImageView SiguienteRespaldo(int fila, int columna) {
        switch (MatrizNumber[fila][columna]) {
            case "0":
                return new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
            case "2":
                return new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
            case "3":
                return new ImageView(new Image("/proyecto2/Assets/BloqueDestino.png"));
            case "4":
                return new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
        }
        return null;
    }

    public void CargarMatriz(String[] numeros) {
        int index = 0;
        for (int i = 0; i < MatrizNumber.length; i++) {
            for (int j = 0; j < MatrizNumber.length; j++) {
                MatrizNumber[i][j] = numeros[index];
                index++;
            }
        }
        for (int i = 0; i < MatrizNumber.length; i++) {
            for (int j = 0; j < MatrizNumber.length; j++) {
                MatrizRespaldo[i][j] = MatrizNumber[i][j];
            }
        }
    }

    public void CargarNivel() {
        int i = 0;
        ImageView imageView;
        StringBuilder builder = new StringBuilder();
        try {
            File file = new File("src/main/resources/proyecto2/Levels/1.txt");
            InputStream in = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line + "\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String file = builder.toString();
        numeros = file.split("\\s+");
        CargarMatriz(numeros);

        numFila = Fisic.getRowCount();
        numColumna = Fisic.getColumnCount();
        for (int Fila = 0; Fila < numFila; Fila++) {
            for (int columna = 0; columna < numColumna; columna++) {
                switch (MatrizNumber[Fila][columna]) {
                    case "0":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                        Fisic.add(imageView, columna, Fila);
                        break;
                    case "1":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BloqueBloqueo.png"));
                        Fisic.add(imageView, columna, Fila);
                        break;
                    case "2":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BloqueCaja.png"));
                        Fisic.add(imageView, columna, Fila);
                        break;
                    case "3":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BloqueDestino.png"));
                        Fisic.add(imageView, columna, Fila);
                        break;
                    case "4":
                        imageView = new ImageView(new Image("/proyecto2/Assets/Personaje2.png"));
                        Fisic.add(imageView, columna, Fila);
                        PJ_Columna = columna;
                        PJ_Fila = Fila;
                        break;
                }
                i++;
            }
        }
    }
}
