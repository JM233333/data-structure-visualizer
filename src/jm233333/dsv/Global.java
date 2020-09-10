package jm233333.dsv;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.stage.Stage;

/**
 * Class {@code Global} stores all the global constants statically.
 */
public class Global {

    /**
     * The flag indicating whether the application is running in the jar package.
     */
    public static final boolean isJar = Main.class.getResource("Main.class").toString().startsWith("jar");

    /**
     * Gets the (absolute) root path where the application runs.
     *
     * @return the (absolute) root path where the application runs
     */
    public static String getRootPath() {
        if (Global.isJar) {
            String pathJar = Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            return pathJar.substring(0, pathJar.lastIndexOf('/') + 1);
        }
        // debug
        return "F:/DataStructureVisualizer/";
    }

    /**
     * The primary stage of the application.
     */
    private static Stage primaryStage = null;
    /**
     * Sets the primary stage.
     *
     * @param primaryStage the new primary stage
     */
    public static void setPrimaryStage(Stage primaryStage) {
        Global.primaryStage = primaryStage;
    }
    /**
     * Gets the primary stage.
     *
     * @return the primary stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static double screenWidth = screenSize.getWidth();
    private static double screenHeight = screenSize.getHeight();

    /**
     * Gets the screen width of the machine.
     *
     * @return the screen width of the machine.
     */
    public static double getScreenWidth() {
        return screenWidth;
    }
    /**
     * Gets the screen height of the machine.
     *
     * @return the screen height of the machine.
     */
    public static double getScreenHeight() {
        return screenHeight;
    }

    private Global() {}

}