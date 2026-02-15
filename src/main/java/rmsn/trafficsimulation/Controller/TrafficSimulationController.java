package rmsn.trafficsimulation.Controller;

import rmsn.trafficsimulation.App.TrafficSimulationApplication;
import rmsn.trafficsimulation.Config.TrafficSimulationConfigurationImpl;
import rmsn.trafficsimulation.Graphics.TrafficSimulationGraphics;
import rmsn.trafficsimulation.Graphics.TrafficSimulationGraphicsImpl;
import rmsn.trafficsimulation.RoadUsers.TCar;
import rmsn.trafficsimulation.RoadUsers.TPedestrian;
import rmsn.trafficsimulation.RoadUsers.TTruck;
import rmsn.trafficsimulation.Simulation.TrafficSimulation;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

import java.util.List;

public class TrafficSimulationController {
    @FXML
    private Canvas canvas;

    private TrafficSimulation simulation;
    private TrafficSimulationGraphics graphics;
    private AnimationTimer timer;

    public TrafficSimulationController() {

    }

    public void initializeSimulationProcesses() {
        this.simulation = new TrafficSimulation();
        new TrafficSimulationConfigurationImpl().configure(this.simulation);

        this.graphics = new TrafficSimulationGraphicsImpl(
                TrafficSimulationApplication.WIDTH,
                TrafficSimulationApplication.HEIGHT,
                this.canvas
        );
        this.graphics.bindSimulationContext(this.simulation.getContext());

        this.startLoop();
    }

    private void startLoop() {
        this.timer = new AnimationTimer() {
            private long last = 0;
            private long tickCount = 0;

            @Override
            public void handle(final long now) {
                if (this.last == 0) {
                    this.last = now;
                    return;
                }

                final double dt = (now - this.last) / 1e9;
                if (dt < 0.005)
                    return; // меньше 5мс — пропускаем => 200 тиков в секунду

                ++this.tickCount; // счётчик тиков
                this.last = now;

                simulation.update(dt);
                graphics.render();
            }
        };

        this.timer.start();
    }

    @FXML
    private void restartSimulation() {
        this.simulation.restart();
    }

    @FXML
    private void spawnRandomCar() {
        this.simulation.generateRandomTrafficUser(List.of(TCar.class, TTruck.class), true);
    }

    @FXML
    private void spawnRandomPedestrian() {
        this.simulation.generateRandomTrafficUser(List.of(TPedestrian.class), true);
    }
}
