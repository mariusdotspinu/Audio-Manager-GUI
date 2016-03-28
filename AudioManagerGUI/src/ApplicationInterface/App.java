package ApplicationInterface;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

/**
 * Created by marius on 3/21/16.
 */

/**
 * Main class of the application ,here we are starting the program
 */
public class App {

    public static void main (String[] args){

        SwingUtilities.invokeLater(() -> {

            JFrame mainWindow = new MainWindow("Audio Manager");

            mainWindow.setVisible(true);
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to allow terminating the application
            mainWindow.setSize(600,400);
            mainWindow.setMinimumSize(new DimensionUIResource(600,400));

        });

    }
}
