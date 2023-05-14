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
    String[] numeros;
    private int PJ_X;
    private int PJ_Y;
    private int numRows;
    private int numCols;

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

    private boolean verificarBorde(int y, int x, int PJ_DY, int PJ_DX) {
        return MatrizNumber[y + PJ_DY][x + PJ_DX].equals("1");
    }

    private boolean verificarCaja(int y, int x, int PJ_DY, int PJ_DX) {
        return MatrizNumber[y + PJ_DY][x + PJ_DX].equals("2");
    }

    private void moverPersonaje(int PJ_DY, int PJ_DX, ImageView PersonajeMove) {
        ImageView Caja = new ImageView(new Image("/proyecto2/Assets/BloqueCaja.png"));
        ImageView Tierra = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
        if (!verificarBorde(PJ_Y, PJ_X, PJ_DY, PJ_DX)) { //Verifica si el campo donde quiere ir es un "1" osea, un borde

            if (!verificarBorde(PJ_Y - PJ_DY, PJ_X - PJ_DX, PJ_DY, PJ_DX)) { //Verifica si el campo anterior es un borde para hacer la copia de seguridad de la imagen
                Respaldo2 = SiguienteRespaldo(PJ_Y + PJ_DY, PJ_X + PJ_DX);  //Al ser ImageView, si yo camino, la imagen del bicho se repiterá en varias celdas
            } else {
                Respaldo2 = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                Respaldo = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
            }

            if (verificarCaja(PJ_Y, PJ_X, PJ_DY, PJ_DX)) {
                Fisic.add(Caja, (PJ_X + ((PJ_DX + PJ_DX) *  Math.abs(PJ_DX))), (PJ_Y + ((PJ_DY + PJ_DY) * Math.abs(PJ_DY))));
                MatrizNumber[PJ_Y + PJ_DY][ PJ_X + PJ_DX] = "0";  
                MatrizNumber[PJ_Y + ((PJ_DY + PJ_DY) * Math.abs(PJ_DY))][PJ_X + ((PJ_DX + PJ_DX) * Math.abs(PJ_DX))] = "2";

                Fisic.add(Tierra, PJ_X + PJ_DX, PJ_Y + PJ_DY);
                Fisic.add(Respaldo, PJ_X, PJ_Y);
            } else {
                Fisic.add(Respaldo, PJ_X, PJ_Y);
            }
            
            Fisic.add(PersonajeMove, PJ_X + PJ_DX, PJ_Y + PJ_DY);
            Respaldo = Respaldo2;
            PJ_X += PJ_DX;
            PJ_Y += PJ_DY;

        }
    }

    public ImageView SiguienteRespaldo(int row, int col) {
        switch (MatrizNumber[row][col]) {
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
                System.out.print(MatrizNumber[i][j] + " ");
            }
            System.out.println();
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

        numRows = Fisic.getRowCount();
        numCols = Fisic.getColumnCount();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                switch (MatrizNumber[row][col]) {
                    case "0":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
                        Fisic.add(imageView, col, row);
                        break;
                    case "1":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BloqueBloqueo.png"));
                        Fisic.add(imageView, col, row);
                        break;
                    case "2":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BloqueCaja.png"));
                        Fisic.add(imageView, col, row);
                        break;
                    case "3":
                        imageView = new ImageView(new Image("/proyecto2/Assets/BloqueDestino.png"));
                        Fisic.add(imageView, col, row);
                        break;
                    case "4":
                        imageView = new ImageView(new Image("/proyecto2/Assets/Personaje2.png"));
                        Fisic.add(imageView, col, row);
                        PJ_X = col;
                        PJ_Y = row;
                        break;
                }
                i++;
            }
        }
    }
}
