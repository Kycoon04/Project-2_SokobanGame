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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ViewGameController implements Initializable {

    private String[] MatrizNumber;
    private int PJ_X;
    private int PJ_Y;

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
                if (event.getCode() == KeyCode.UP) {
                    System.out.println("Se presionó la tecla hacia arriba");
                }
                if (event.getCode() == KeyCode.DOWN) {
                    move(event);
                }
            });
        });
    }

    public void move(KeyEvent event) {
        ImageView imageView = new ImageView(new Image("/proyecto2/Assets/Personaje.png"));
        ImageView imageView2 = new ImageView(new Image("/proyecto2/Assets/BaseTierra.png"));
        if (event.getCode() == KeyCode.DOWN) {
            Fisic.add(imageView2,PJ_X, PJ_Y);
            Fisic.add(imageView, PJ_X, PJ_Y+1);
            PJ_Y+=1;
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
        MatrizNumber = file.split("\\s+");

        int numRows = Fisic.getRowCount();
        int numCols = Fisic.getColumnCount();
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                switch (MatrizNumber[i]) {
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
                        imageView = new ImageView(new Image("/proyecto2/Assets/Personaje.png"));
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
