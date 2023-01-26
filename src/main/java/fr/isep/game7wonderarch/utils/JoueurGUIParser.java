package fr.isep.game7wonderarch.utils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * classe qui définit le GUI d'un joueur, elle permet de lancer l'application
 */
public class JoueurGUIParser extends Application {

    /**
     * constructeur de la classe, ne doit pas être utilisé, c'est pour eviter le message javadoc
     */
    public JoueurGUIParser(){

    }

    // le log de log4j
    private static final Logger log = LogManager.getLogger( JoueurGUIParser.class );

    /**
     * démarre l'application
     * @param primaryStage l'écran d'affichage
     * @throws IOException si l'application ne peut etre lancée
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("joueurGUI.fxml"));
        primaryStage.setTitle("7Wonders");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * fonction prinicpale du programme
     * @param args aucun argument pris en compte
     */
    public static void main(String[] args) {

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);

        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.DEBUG);
        ctx.updateLoggers();

        launch();
    }
}
