package proyecto2.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import proyecto2.util.FlowController;

public class ViewGameController implements Initializable {

    ImageView Respaldo = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
    ImageView Respaldo2 = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
    private FlowController flowController;
    private String[][] MatrizNumber = new String[10][10];
    private String[][] MatrizRespaldo = new String[10][10];
    private String[][] MatrizDevolver = new String[10][10];
    String[] numeros;
    int NumCajasTotal = 0;
    private int PJ_Columna;
    private int PJ_Fila;
    private int numFila;
    private int numColumna;
    @FXML
    private GridPane Fisic;
    @FXML
    private BorderPane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root.toFront();
        CargarNivel();
        Platform.runLater(() -> {
            Scene scene = root.getScene();
            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                MovimientoPersonaje(event);
            });
        });
    }

    private boolean verificarBorde(int fila, int columna, int desplazamientoFila, int desplazamientoColumna) {
        return MatrizNumber[fila + desplazamientoFila][columna + desplazamientoColumna].equals("1");
    }

    private boolean verificarCaja(int fila, int columna, int desplazamientoFila, int desplazamientoColumna) {
        return MatrizNumber[fila + desplazamientoFila][columna + desplazamientoColumna].equals("2");
    }

    public void CargarNivel() {
        flowController = FlowController.getInstance();
        int i = 0;
        ImageView imageView;
        StringBuilder builder = new StringBuilder();
        try {
            File file = new File("src/main/resources/proyecto2/Levels/" + flowController.getNivel() + ".txt");
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
        Pintar(MatrizNumber);
    }

    public void Pintar(String[][] matriz) {
        int i = 0;
        ImageView imageView;
        for (int Fila = 0; Fila < numFila; Fila++) {
            for (int columna = 0; columna < numColumna; columna++) {
                switch (matriz[Fila][columna]) {
                    case "0":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                        Fisic.setColumnIndex(imageView, columna);
                        Fisic.setRowIndex(imageView, Fila);
                        Fisic.add(imageView, columna, Fila);
                        imageView.setOnMouseClicked(event -> ObtenerPosicion(event));
                        break;
                    case "1":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BloqueBloqueo.png"));
                        Fisic.setColumnIndex(imageView, columna);
                        Fisic.setRowIndex(imageView, Fila);
                        Fisic.add(imageView, columna, Fila);
                        imageView.setOnMouseClicked(event -> ObtenerPosicion(event));
                        break;
                    case "2":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BloqueCaja.png"));
                        Fisic.setColumnIndex(imageView, columna);
                        Fisic.setRowIndex(imageView, Fila);
                        Fisic.add(imageView, columna, Fila);
                        imageView.setOnMouseClicked(event -> ObtenerPosicion(event));
                        break;
                    case "3":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BloqueDestino.png"));
                        Fisic.setColumnIndex(imageView, columna);
                        Fisic.setRowIndex(imageView, Fila);
                        Fisic.add(imageView, columna, Fila);
                        imageView.setOnMouseClicked(event -> ObtenerPosicion(event));
                        break;
                    case "4":
                        imageView = new ImageView(new Image("/proyecto2/Assets/Personaje2.png"));
                        Fisic.setColumnIndex(imageView, columna);
                        Fisic.setRowIndex(imageView, Fila);
                        Fisic.add(imageView, columna, Fila);
                        imageView.setOnMouseClicked(event -> ObtenerPosicion(event));
                        PJ_Columna = columna;
                        PJ_Fila = Fila;
                        break;
                    case "5":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                        Fisic.setColumnIndex(imageView, columna);
                        Fisic.setRowIndex(imageView, Fila);
                        Fisic.add(imageView, columna, Fila);
                        imageView.setOnMouseClicked(event -> ObtenerPosicion(event));
                        break;
                }
                i++;
            }
        }
    }

    public void CargarMatriz(String[] numeros) {
        int index = 0;
        for (int i = 0; i < MatrizNumber.length; i++) {
            for (int j = 0; j < MatrizNumber.length; j++) {
                MatrizNumber[i][j] = numeros[index];
                MatrizRespaldo[i][j] = MatrizNumber[i][j];
                index++;
            }
        }
        cargarDosAleatorios();
    }

    public void cargarDosAleatorios() {
        int cantidadTres = contarApariciones();
        Random random = new Random();

        for (int i = 0; i < cantidadTres; i++) {
            boolean cargado = false;

            while (!cargado) {
                int fila = random.nextInt(MatrizNumber.length);
                int columna = random.nextInt(MatrizNumber[0].length);

                if (MatrizNumber[fila][columna].equals("0")) {
                    MatrizNumber[fila][columna] = "2";
                    MatrizRespaldo[fila][columna] = "2";
                    NumCajasTotal++;
                    cargado = true;
                }
            }
        }
    }

    private int contarApariciones() {
        int contador = 0;
        for (int i = 0; i < MatrizNumber.length; i++) {
            for (int j = 0; j < MatrizNumber[0].length; j++) {
                if (MatrizNumber[i][j].equals("3")) {
                    contador++;
                }
            }
        }

        return contador;
    }

    public void MovimientoPersonaje(KeyEvent event) {
        ImageView PersonajeMove = new ImageView(new Image("/proyecto2/Assets/Personaje.png"));
        if (event.isControlDown() && event.getCode() == KeyCode.Z) {
            Pintar(MatrizDevolver);
            for (int i = 0; i < MatrizNumber.length; i++) {
                for (int j = 0; j < MatrizNumber.length; j++) {
                    MatrizNumber[i][j] = MatrizDevolver[i][j];
                }
            }
            return;
        }
        switch (event.getCode()) {
            case UP:
                for (int i = 0; i < MatrizNumber.length; i++) {
                    for (int j = 0; j < MatrizNumber.length; j++) {
                        MatrizDevolver[i][j] = MatrizNumber[i][j];
                    }
                }
                moverPersonaje(-1, 0, PersonajeMove);
                break;
            case DOWN:
                for (int i = 0; i < MatrizNumber.length; i++) {
                    for (int j = 0; j < MatrizNumber.length; j++) {
                        MatrizDevolver[i][j] = MatrizNumber[i][j];
                    }
                }
                moverPersonaje(1, 0, PersonajeMove);
                break;
            case LEFT:
                for (int i = 0; i < MatrizNumber.length; i++) {
                    for (int j = 0; j < MatrizNumber.length; j++) {
                        MatrizDevolver[i][j] = MatrizNumber[i][j];
                    }
                }
                PersonajeMove.setImage(new Image("/proyecto2/Assets/PersonajeIzquierda.png"));
                moverPersonaje(0, -1, PersonajeMove);
                break;
            case RIGHT:
                for (int i = 0; i < MatrizNumber.length; i++) {
                    for (int j = 0; j < MatrizNumber.length; j++) {
                        MatrizDevolver[i][j] = MatrizNumber[i][j];
                    }
                }
                moverPersonaje(0, 1, PersonajeMove);
                break;
            default:
                break;
        }
    }

    public void moverPersonaje(int desplazamientoFila, int desplazamientoColumna, ImageView PersonajeMove) {

        // Verifica si el campo donde quiere ir es un "1", osea, un borde
        if (!verificarBorde(PJ_Fila, PJ_Columna, desplazamientoFila, desplazamientoColumna)) {
            if (!verificarBorde(PJ_Fila - desplazamientoFila, PJ_Columna - desplazamientoColumna, desplazamientoFila, desplazamientoColumna)) {
                // Verifica si el campo anterior es un borde para hacer la copia de seguridad de la imagen
                // Al ser ImageView, si yo camino, la imagen del bicho se repetirá en varias celdas
                Respaldo2 = SiguienteRespaldo(PJ_Fila + desplazamientoFila, PJ_Columna + desplazamientoColumna);
            } else {
                Respaldo2 = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                Respaldo = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
            }
            MoverCaja(desplazamientoFila, desplazamientoColumna, PersonajeMove);
        }
        PosibleVictoria();  //Analiza si los espacios meta y las cajas estan en la misma posicion, esto cada vez que se mueve una caja (Muy optimizado Douglas lo confirmaría...)
    }

    public void MoverCaja(int desplazamientoFila, int desplazamientoColumna, ImageView PersonajeMove) {
        boolean cajaLibre = true;
        ImageView caja = new ImageView(new Image("/proyecto2/Assets/BloqueCaja.png"));
        ImageView tierra = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
        ImageView BloqueDestino = new ImageView(new Image("/proyecto2/Assets/BloqueDestino.png"));

        int OperacionX = PJ_Fila + ((desplazamientoFila + desplazamientoFila) * Math.abs(desplazamientoFila));
        int OperacionY = PJ_Columna + ((desplazamientoColumna + desplazamientoColumna) * Math.abs(desplazamientoColumna));

        if (verificarCaja(PJ_Fila, PJ_Columna, desplazamientoFila, desplazamientoColumna)) {
            if (!MatrizNumber[OperacionX][OperacionY].equals("1") && !MatrizNumber[OperacionX][OperacionY].equals("2")) {
                Fisic.add(caja, OperacionY, OperacionX);

                if (MatrizRespaldo[PJ_Fila + desplazamientoFila][PJ_Columna + desplazamientoColumna].equals("3")) {
                    MatrizNumber[PJ_Fila + desplazamientoFila][PJ_Columna + desplazamientoColumna] = "3";
                } else {
                    MatrizNumber[PJ_Fila + desplazamientoFila][PJ_Columna + desplazamientoColumna] = "0"; //problema aqui de perder el 3
                    Fisic.add(tierra, PJ_Columna + desplazamientoColumna, PJ_Fila + desplazamientoFila);
                }

                MatrizNumber[OperacionX][OperacionY] = "2";

                if (MatrizRespaldo[PJ_Fila][PJ_Columna].equals("3")) {
                    Fisic.add(BloqueDestino, PJ_Columna, PJ_Fila);
                } else {
                    Fisic.add(Respaldo, PJ_Columna, PJ_Fila);
                }
                cajaLibre = true;
            } else {
                cajaLibre = false;
            }
            if (CajaEsquina(MatrizNumber, OperacionX, OperacionY)) {
                flowController.setPerdio(true);
                FlowController.getInstance().goMain("ViewMenu");
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
            MatrizNumber[PJ_Fila + desplazamientoFila][PJ_Columna + desplazamientoColumna] = "4";
            MatrizNumber[PJ_Fila][PJ_Columna] = "0";

            if (MatrizRespaldo[PJ_Fila][PJ_Columna].equals("3")) {
                MatrizNumber[PJ_Fila][PJ_Columna] = "3";
            } else {
                MatrizNumber[PJ_Fila][PJ_Columna] = "0";
            }
            Respaldo = Respaldo2;
            PJ_Columna += desplazamientoColumna;
            PJ_Fila += desplazamientoFila;
        }
    }

    public boolean CajaEsquina(String[][] matrix, int x, int y) {
        int count = 0;
        boolean Paralelo = false;
        // Verificar los 4 espacios alredor del bloque
        if (matrix[x - 1][y].equals("1")) {
            count++;
            Paralelo = true;
        }
        if (matrix[x + 1][y].equals("1") && !Paralelo) {
            count++;
        }
        if (matrix[x][y - 1].equals("1")) {
            count++;
        }
        if (matrix[x][y + 1].equals("1") && !Paralelo) {
            count++;
        }
        return count >= 2;
    }

    public boolean PosibleVictoria() {
        int NumCajasTotalAux = NumCajasTotal;
        for (int i = 0; i < MatrizNumber.length; i++) {
            for (int j = 0; j < MatrizNumber.length; j++) {
                if (MatrizNumber[i][j].equals("2") && MatrizRespaldo[i][j].equals("3")) {
                    NumCajasTotalAux--;
                }
                if (NumCajasTotalAux == 0) {
                    ActualizarNivelesDispo();
                    flowController.setGano(true);
                    FlowController.getInstance().goMain("ViewMenu");
                    return true;
                }
            }
        }
        return false;
    }

    public void ActualizarNivelesDispo() {
        switch (flowController.getNivel()) {
            case 1:
                flowController.setNivel(2);
                flowController.setNivel2(true);
                break;
            case 2:
                flowController.setNivel(3);
                flowController.setNivel3(true);
                break;
            case 3:
                flowController.setNivel(4);
                flowController.setNivel4(true);
                break;
            case 4:
                flowController.setNivel(5);
                flowController.setNivel5(true);
                break;
            case 5:
                flowController.setNivel(6);
                flowController.setNivel6(true);
                break;
            default:
                break;
        }
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
            case "5":
                return new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
        }
        return null;
    }

    public static void guardarMatrizComoTexto(String[][] matriz, String nombreArchivo) {
        try {
            FileWriter writer = new FileWriter(nombreArchivo);

            for (String[] fila : matriz) {
                for (String elemento : fila) {
                    writer.write(elemento + " ");
                }
                writer.write(System.lineSeparator());
            }

            writer.close();
            System.out.println("La matriz se ha guardado en el archivo " + nombreArchivo + " correctamente.");
        } catch (IOException e) {
            System.out.println("Se produjo un error al guardar la matriz en el archivo.");
            e.printStackTrace();
        }
    }

    @FXML
    private void Volver(ActionEvent event) {
        FlowController.getInstance().goMain("ViewMenu");
    }

    @FXML
    private void Resetear(ActionEvent event) {
        NumCajasTotal = 0;
        CargarMatriz(numeros);
        Pintar(MatrizNumber);
    }

    @FXML
    private void ObtenerPosicion(MouseEvent event) {
        Node clickedNode = (Node) event.getSource();
        Integer columnIndex = Fisic.getColumnIndex(clickedNode);
        Integer rowIndex = Fisic.getRowIndex(clickedNode);
        System.out.println("Posición de la celda: x=" + columnIndex + ", y=" + rowIndex);
    }
}
