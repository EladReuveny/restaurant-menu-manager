/*
 * The Main class is the starting point of the application. It loads the FXML file and sets up the primary stage.
 * @author Elad Reuveny
 */
package q2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    /**
     * The start method sets up the primary stage by loading the FXML file and setting the scene.
     *
     * @param stage the primary stage of the application
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("restaurant.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome to my Restaurant!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method is the entry point of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
