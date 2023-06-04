package proyecto2.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import proyecto2.Model.Jugador;
import proyecto2.util.FlowController;
import proyecto2.util.Posicion;

public class ViewGameController implements Initializable {

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
    @FXML
    private BorderPane ViewVictoria;
    @FXML
    private BorderPane ViewDerrota;

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
            MoverCaja(desplazamientoFila, desplazamientoColumna, PersonajeMove);
        }
        PosibleVictoria();
        //Analiza si los espacios meta y las cajas estan en la misma posicion, esto cada vez que se mueve una caja (Muy optimizado Douglas lo confirmaría...)
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
                    Fisic.add(SiguienteRespaldo(PJ_Fila + desplazamientoFila, PJ_Columna + desplazamientoColumna), PJ_Columna + desplazamientoColumna, PJ_Fila + desplazamientoFila);
                    SiguienteRespaldo(PJ_Fila, PJ_Columna).setOnMouseClicked(event -> ObtenerPosicion(event));
                } else {
                    MatrizNumber[PJ_Fila + desplazamientoFila][PJ_Columna + desplazamientoColumna] = "0"; //problema aqui de perder el 3
                    Fisic.add(SiguienteRespaldo(PJ_Fila + desplazamientoFila, PJ_Columna + desplazamientoColumna), PJ_Columna + desplazamientoColumna, PJ_Fila + desplazamientoFila);
                    SiguienteRespaldo(PJ_Fila, PJ_Columna).setOnMouseClicked(event -> ObtenerPosicion(event));
                }

                MatrizNumber[OperacionX][OperacionY] = "2";

                Fisic.add(SiguienteRespaldo(PJ_Fila, PJ_Columna), PJ_Columna, PJ_Fila);
                SiguienteRespaldo(PJ_Fila, PJ_Columna).setOnMouseClicked(event -> ObtenerPosicion(event));
                cajaLibre = true;
                if (CajaEsquina(MatrizNumber, OperacionX, OperacionY)) {
                    ViewDerrota.toFront();
                    System.out.println("hola");
                }
            } else {
                cajaLibre = false;
            }

        } else {
            Fisic.add(SiguienteRespaldo(PJ_Fila, PJ_Columna), PJ_Columna, PJ_Fila);
            SiguienteRespaldo(PJ_Fila, PJ_Columna).setOnMouseClicked(event -> ObtenerPosicion(event));
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
            PJ_Columna += desplazamientoColumna;
            PJ_Fila += desplazamientoFila;
        }
    }

    public boolean CajaEsquina(String[][] matrix, int x, int y) {
        boolean Paralelo = false;
        if (matrix[x + 1][y].equals("1") && matrix[x][y - 1].equals("1") || matrix[x - 1][y].equals("1") && matrix[x][y + 1].equals("1")) {
            return true;
        }
        if (matrix[x][y - 1].equals("1") && matrix[x + 1][y].equals("1") || matrix[x][y + 1].equals("1") && matrix[x - 1][y].equals("1")) {
            return true;
        }
        if (matrix[x][y + 1].equals("1") && matrix[x + 1][y].equals("1") || matrix[x][y + 1].equals("1") && matrix[x + 1][y].equals("1")) {
            return true;
        }
        if (matrix[x][y - 1].equals("1") && matrix[x - 1][y].equals("1") || matrix[x][y - 1].equals("1") && matrix[x - 1][y].equals("1")) {
            return true;
        }
        return false;
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
                    ViewVictoria.toFront();
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
        ImageView imageView;
        switch (MatrizRespaldo[fila][columna]) {
            case "0":
                imageView = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                imageView.setOnMouseClicked(event -> ObtenerPosicion(event));
                return imageView;
            case "2":
                imageView = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                imageView.setOnMouseClicked(event -> ObtenerPosicion(event));
                return imageView;
            case "3":
                imageView = new ImageView(new Image("/proyecto2/Assets/BloqueDestino.png"));
                imageView.setOnMouseClicked(event -> ObtenerPosicion(event));
                return imageView;
            case "4":
                imageView = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                imageView.setOnMouseClicked(event -> ObtenerPosicion(event));
                return imageView;
            case "5":
                imageView = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                imageView.setOnMouseClicked(event -> ObtenerPosicion(event));
                return imageView;

        }
        return null;
    }

    public static String convertirMatrizAString(String[][] matriz) {
        StringBuilder sb = new StringBuilder();
        for (String[] fila : matriz) {
            for (String elemento : fila) {
                sb.append(elemento).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
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

    public List<Posicion> obtenerRutaMasCorta(String[][] matriz, Posicion inicio, Posicion objetivo) {
        int filas = matriz.length;
        int columnas = matriz[0].length;
        String ObstaculoBorde = "1";
        String ObstaculoCaja = "2";
        int[] FilasAlrededor = {-1, 0, 1, 0};
        int[] ColumbasAlrededor = {0, 1, 0, -1};
        boolean[][] PaseoMiedo = new boolean[filas][columnas];
        Posicion[][] padre = new Posicion[filas][columnas];
        Queue<Posicion> cola = new LinkedList<>();
        cola.offer(inicio);

        while (!cola.isEmpty()) {
            Posicion actual = cola.poll();
            if (actual.fila == objetivo.fila && actual.columna == objetivo.columna) {
                return construirRuta(padre, inicio, objetivo);
            }
            for (int j = 0; j < 4; j++) {
                int nuevaFila = actual.fila + FilasAlrededor[j];
                int nuevaColumna = actual.columna + ColumbasAlrededor[j];

                if (esPosicionValida(nuevaFila, nuevaColumna, filas, columnas) && !PaseoMiedo[nuevaFila][nuevaColumna] && !matriz[nuevaFila][nuevaColumna].equals(ObstaculoBorde) && !matriz[nuevaFila][nuevaColumna].equals(ObstaculoCaja)) {
                    cola.offer(new Posicion(nuevaFila, nuevaColumna));
                    PaseoMiedo[nuevaFila][nuevaColumna] = true;
                    padre[nuevaFila][nuevaColumna] = actual;
                }
            }
        }
        return new ArrayList<>();
    }

    private boolean esPosicionValida(int fila, int columna, int filas, int columnas) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    private List<Posicion> construirRuta(Posicion[][] PosicionRealizada, Posicion inicio, Posicion objetivo) {
        List<Posicion> ruta = new ArrayList<>();
        Posicion actual = objetivo;
        while (actual != null) {
            ruta.add(0, actual);
            if (actual.equals(inicio)) {
                break;
            }
            actual = PosicionRealizada[actual.fila][actual.columna];
        }
        return ruta;
    }

    private void MoverRuta(List<Posicion> ruta) {
        float counter = 0;

        for (int i = 1; i < ruta.size(); i++) {
            PauseTransition delayAppearance = new PauseTransition(Duration.seconds(counter));
            final int index = i;

            delayAppearance.setOnFinished(event -> {
                if (MatrizRespaldo[PJ_Fila][PJ_Columna].equals("3")) {
                    ImageView BloqueDestino = new ImageView(new Image("/proyecto2/Assets/BloqueDestino.png"));
                    Fisic.add(BloqueDestino, PJ_Columna, PJ_Fila);
                    BloqueDestino.setOnMouseClicked(mouseevent -> ObtenerPosicion(mouseevent));
                } else {
                    ImageView tierra = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                    Fisic.add(tierra, PJ_Columna, PJ_Fila);
                    tierra.setOnMouseClicked(mouseevent -> ObtenerPosicion(mouseevent));
                }
                ImageView PersonajeMove = new ImageView(new Image("/proyecto2/Assets/Personaje.png"));
                Fisic.add(PersonajeMove, ruta.get(index).columna, ruta.get(index).fila);
                MatrizNumber[PJ_Fila][PJ_Columna] = "0";
                MatrizNumber[ruta.get(index).fila][ruta.get(index).columna] = "4";
                if (MatrizRespaldo[PJ_Fila][PJ_Columna].equals("3")) {
                    MatrizNumber[PJ_Fila][PJ_Columna] = "3";
                } else {
                    MatrizNumber[PJ_Fila][PJ_Columna] = "0";
                }
                PJ_Columna = ruta.get(index).columna;
                PJ_Fila = ruta.get(index).fila;

            });
            delayAppearance.play();
            counter += 0.15;
        }
    }

    public void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    @FXML
    private void ObtenerPosicion(MouseEvent event) {
        Node clickedNode = (Node) event.getSource();
        Integer columnIndex = Fisic.getColumnIndex(clickedNode);
        Integer rowIndex = Fisic.getRowIndex(clickedNode);
        Posicion inicio = new Posicion(PJ_Fila, PJ_Columna);
        Posicion objetivo = new Posicion(rowIndex, columnIndex);
        List<Posicion> ruta = obtenerRutaMasCorta(MatrizNumber, inicio, objetivo);
        MoverRuta(ruta);
    }

    @FXML
    private void Exportar(ActionEvent event) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto2_Proyecto2_jar_1.0-SNAPSHOTPU");
            EntityManager em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            Query query = em.createQuery("SELECT p FROM Jugador p where p.jrNombre = :fname AND p.jrContrasena = :fcedula");
            query.setParameter("fname", FlowController.getJugadorEnSesion().getJrNombre());
            query.setParameter("fcedula", FlowController.getJugadorEnSesion().getJrContrasena());
            List<Jugador> registro = query.getResultList();
            tx.begin();
            registro.get(0).setJrNivelguardado(convertirMatrizAString(MatrizNumber));
            tx.commit();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("El jugador se ha insertado correctamente en la base de datos");
            alert.showAndWait();
            em.close();
            emf.close();
        
        
    }
}
