package frazier.c482_pa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** This class 'InventoryManagement' is the entry point for the entire application.
 * */
public class InventoryManagement extends Application {
    /**
     The start method is called when the application is launched.  It loads the main menu FXML file and displays the application in a 1087 x 520 window.

     @param stage the primary stage for the application
     @throws IOException if the FXML file cannot be loaded
       */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryManagement.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1087, 520);
        stage.setTitle("Inventory Management Application");
        stage.setScene(scene);
        stage.show();
    }

    /** The main method is the entry point for the application.
     * FUTURE ENHANCEMENT : Although I believe that my application works very well for its intended purposes, there are a couple things that I would look
     * into changing to create a better user experience.  One thing that I would change is a more optimized search algorithm for the search boxes, as they are
     * not very scalable.  They work very well for the limited number of test data used but if the number of parts expanded to thousands / hundreds of thousands,
     * there would be slowdown.  Speaking on that, there's currently a built-in ID generator for both parts and products, the parts start at ID 1 and the products
     * start at ID 1000; so once again there is a scale issue because technically you could only have 999 parts before the IDs start becoming non-unique.  So looking
     * at a better ID generation scheme would be something to look into in the future.
     *
     * JAVADOC LOCATION:  The Javadoc file is a seperate file along with the Java source code in the zip file*/
    public static void main(String[] args) {
        launch();
    }
}