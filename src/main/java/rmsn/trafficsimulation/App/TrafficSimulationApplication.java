package rmsn.trafficsimulation.App;

import rmsn.trafficsimulation.Controller.TrafficSimulationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TrafficSimulationApplication extends Application {
    public static final int WIDTH = 800, HEIGHT = 800;

    @Override
    public void start(final Stage stage) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/rmsn/trafficsimulation/UI/window.fxml"));
        final Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);
        TrafficSimulationController.class.cast(loader.getController()).initializeSimulationProcesses();

        stage.setScene(scene);
        stage.setTitle("Traffic Simulation");
        stage.setResizable(false);
        stage.show();
    }
}
