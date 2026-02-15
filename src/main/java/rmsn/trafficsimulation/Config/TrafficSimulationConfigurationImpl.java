package rmsn.trafficsimulation.Config;

import rmsn.trafficsimulation.Geometry.Line2D;
import rmsn.trafficsimulation.Geometry.Point2D;
import rmsn.trafficsimulation.RoadEquipment.*;
import rmsn.trafficsimulation.RoadUsers.TCar;
import rmsn.trafficsimulation.RoadUsers.TPedestrian;
import rmsn.trafficsimulation.RoadUsers.TTruck;
import rmsn.trafficsimulation.Simulation.TrafficSimulation;

import java.util.List;

public class TrafficSimulationConfigurationImpl implements TrafficSimulationConfiguration {
    @Override
    public void configure(final TrafficSimulation simulation) {
        if (!simulation.isCfgBound() || simulation.getCfg() != this)
            simulation.bindCfg(this);

        this.initialize(simulation);
    }

    private void initialize(final TrafficSimulation simulation) {
        simulation.setPreferredRoadUsersCount(32);
        simulation.setMaxRoadUsersCount(48);

        this.addPedestrianTrafficLights(simulation);
        this.addRoadTrafficLights(simulation);
        this.addCarAndTruckPaths(simulation);
        this.addPedestrianPaths(simulation);
    }

    private void addPedestrianTrafficLights(final TrafficSimulation simulation) {
        final TrafficLight pedestrianTrafficLight1 = new PedestrianTrafficLight.Builder()
                .redDuration(8.0)
                .greenDuration(6.0)
                .currState(TrafficLightState.RED)
                .build();
        simulation.addTrafficLight(pedestrianTrafficLight1);
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(324, 266),
                new Point2D(324, 316)
        ), pedestrianTrafficLight1));
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(476, 266),
                new Point2D(476, 316)
        ), pedestrianTrafficLight1));

        final TrafficLight pedestrianTrafficLight2 = new PedestrianTrafficLight.Builder()
                .redDuration(8.0)
                .greenDuration(6.0)
                .currState(TrafficLightState.RED)
                .build();
        simulation.addTrafficLight(pedestrianTrafficLight2);
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(324, 486),
                new Point2D(324, 536)
        ), pedestrianTrafficLight2));
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(476, 486),
                new Point2D(476, 536)
        ), pedestrianTrafficLight2));

        final TrafficLight pedestrianTrafficLight3 = new PedestrianTrafficLight.Builder()
                .redDuration(6.0)
                .greenDuration(8.0)
                .currState(TrafficLightState.GREEN)
                .build();
        simulation.addTrafficLight(pedestrianTrafficLight3);
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(484, 324),
                new Point2D(534, 324)
        ), pedestrianTrafficLight3));
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(484, 476),
                new Point2D(534, 476)
        ), pedestrianTrafficLight3));

        final TrafficLight pedestrianTrafficLight4 = new PedestrianTrafficLight.Builder()
                .redDuration(6.0)
                .greenDuration(8.0)
                .currState(TrafficLightState.GREEN)
                .build();
        simulation.addTrafficLight(pedestrianTrafficLight4);
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(264, 324),
                new Point2D(314, 324)
        ), pedestrianTrafficLight4));
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(264, 476),
                new Point2D(314, 476)
        ), pedestrianTrafficLight4));
    }

    private void addRoadTrafficLights(final TrafficSimulation simulation) {
        final TrafficLight roadTrafficLight1 = new RoadTrafficLight.Builder()
                .redDuration(5.0)
                .greenDuration(7.0)
                .currState(TrafficLightState.GREEN)
                .build();
        simulation.addTrafficLight(roadTrafficLight1);
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(400, 264),
                new Point2D(476, 264)
        ), roadTrafficLight1));
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(324, 536),
                new Point2D(400, 536)
        ), roadTrafficLight1));

        final TrafficLight roadTrafficLight2 = new RoadTrafficLight.Builder()
                .redDuration(7.0)
                .greenDuration(5.0)
                .currState(TrafficLightState.RED)
                .build();
        simulation.addTrafficLight(roadTrafficLight2);
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(536, 400),
                new Point2D(536, 476)
        ), roadTrafficLight2));
        simulation.addStopLine(new StopLine(new Line2D(
                new Point2D(264, 324),
                new Point2D(264, 400)
        ), roadTrafficLight2));
    }

    private void addCarAndTruckPaths(final TrafficSimulation simulation) {
        // север-юг
        final List<Point2D> carAndTruckPath1 = List.of(
                new Point2D(362.0, 840.0),
                new Point2D(362.0, -40.0)
        );

        // север-запад
        final List<Point2D> carAndTruckPath2 = List.of(
                new Point2D(362.0, 840.0),
                new Point2D(362.0, 438.0),
                new Point2D(-40.0, 438.0)
        );

        // юг-север
        final List<Point2D> carAndTruckPath3 = List.of(
                new Point2D(438.0, -40.0),
                new Point2D(438.0, 840.0)
        );

        // юг-запад
        final List<Point2D> carAndTruckPath4 = List.of(
                new Point2D(438.0, -40.0),
                new Point2D(438.0, 362.0),
                new Point2D(840.0, 362.0)
        );

        // запад-восток
        final List<Point2D> carAndTruckPath5 = List.of(
                new Point2D(-40.0, 362.0),
                new Point2D(840.0, 362.0)
        );

        // запад-юг
        final List<Point2D> carAndTruckPath6 = List.of(
                new Point2D(-40.0, 362.0),
                new Point2D(362.0, 362.0),
                new Point2D(362.0, -40.0)
        );

        // восток-запад
        final List<Point2D> carAndTruckPath7 = List.of(
                new Point2D(840.0, 438.0),
                new Point2D(-40.0, 438.0)
        );

        // восток-север
        final List<Point2D> carAndTruckPath8 = List.of(
                new Point2D(840.0, 438.0),
                new Point2D(438.0, 438.0),
                new Point2D(438.0, 840.0)
        );

        // юг-запад 1
        final List<Point2D> carAndTruckPath9 = List.of(
                new Point2D(438.0, -40.0),
                new Point2D(438.0, 760.0),
                new Point2D(362.0, 760.0),
                new Point2D(362.0, 438.0),
                new Point2D(-40.0, 438.0)
        );

        // юг-запад 2
        final List<Point2D> carAndTruckPath10 = List.of(
                new Point2D(438.0, -40.0),
                new Point2D(438.0, 438.0),
                new Point2D(-40.0, 438.0)
        );

        // север-восток 1
        final List<Point2D> carAndTruckPath11 = List.of(
                new Point2D(362.0, 840.0),
                new Point2D(362.0, 40.0),
                new Point2D(438.0, 40.0),
                new Point2D(438.0, 362.0),
                new Point2D(840.0, 362.0)
        );

        // север-восток 2
        final List<Point2D> carAndTruckPath12 = List.of(
                new Point2D(362.0, 840.0),
                new Point2D(362.0, 362.0),
                new Point2D(840.0, 362.0)
        );

        // запад-север 1
        final List<Point2D> carAndTruckPath13 = List.of(
                new Point2D(-40.0, 362.0),
                new Point2D(760.0, 362.0),
                new Point2D(760.0, 438.0),
                new Point2D(438.0, 438.0),
                new Point2D(438.0, 840.0)
        );

        // запад-север 2
        final List<Point2D> carAndTruckPath14 = List.of(
                new Point2D(-40.0, 362.0),
                new Point2D(438.0, 362.0),
                new Point2D(438.0, 840.0)
        );


        // восток-юг 1
        final List<Point2D> carAndTruckPath15 = List.of(
                new Point2D(840.0, 438.0),
                new Point2D(40.0, 438.0),
                new Point2D(40.0, 362.0),
                new Point2D(362.0, 362.0),
                new Point2D(362.0, -40.0)
        );

        // восток-юг 2
        final List<Point2D> carAndTruckPath16 = List.of(
                new Point2D(840.0, 438.0),
                new Point2D(362.0, 438.0),
                new Point2D(362.0, -40.0)
        );

        final List<List<Point2D>> carAndTruckPaths = List.of(
                carAndTruckPath1, carAndTruckPath2,
                carAndTruckPath3, carAndTruckPath4,
                carAndTruckPath5, carAndTruckPath6,
                carAndTruckPath7, carAndTruckPath8,
                carAndTruckPath9, carAndTruckPath10,
                carAndTruckPath11, carAndTruckPath12,
                carAndTruckPath13, carAndTruckPath14,
                carAndTruckPath15, carAndTruckPath16
        );

        simulation.addAllPotentialPathForRoadUser(TCar.class, carAndTruckPaths);
        simulation.addAllPotentialPathForRoadUser(TTruck.class, carAndTruckPaths);
    }

    private void addPedestrianPaths(final TrafficSimulation simulation) {
        // запад-восток 1
        final List<Point2D> pedestrianPath1 = List.of(
                new Point2D(-20.0, 291.0),
                new Point2D(820.0, 291.0)
        );

        // восток-запад 1
        final List<Point2D> pedestrianPath2 = List.of(
                new Point2D(820.0, 291.0),
                new Point2D(-20.0, 291.0)
        );

        // запад-восток 2
        final List<Point2D> pedestrianPath3 = List.of(
                new Point2D(-20.0, 509.0),
                new Point2D(820.0, 509.0)
        );

        // восток-запад 2
        final List<Point2D> pedestrianPath4 = List.of(
                new Point2D(820.0, 509.0),
                new Point2D(-20.0, 509.0)
        );

        // север-юг 1
        final List<Point2D> pedestrianPath5 = List.of(
                new Point2D(291.0, -20.0),
                new Point2D(291.0, 820.0)
        );

        // юг-север 1
        final List<Point2D> pedestrianPath6 = List.of(
                new Point2D(291.0, 820.0),
                new Point2D(291.0, -20.0)
        );

        // север-юг 2
        final List<Point2D> pedestrianPath7 = List.of(
                new Point2D(509.0, -20.0),
                new Point2D(509.0, 820.0)
        );

        // юг-север 2
        final List<Point2D> pedestrianPath8 = List.of(
                new Point2D(509.0, 820.0),
                new Point2D(509.0, -20.0)
        );

        // юг-восток 1
        final List<Point2D> pedestrianPath9 = List.of(
                new Point2D(291.0, -20.0),
                new Point2D(291.0, 291.0),
                new Point2D(820.0, 291.0)
        );

        // юг-запад 1
        final List<Point2D> pedestrianPath10 = List.of(
                new Point2D(509.0, -20.0),
                new Point2D(509.0, 291.0),
                new Point2D(-20.0, 291.0)
        );

        // восток-север 1
        final List<Point2D> pedestrianPath11 = List.of(
                new Point2D(820.0, 291.0),
                new Point2D(509.0, 291.0),
                new Point2D(509.0, 820.0)
        );

        // запад-север 1
        final List<Point2D> pedestrianPath12 = List.of(
                new Point2D(-20.0, 291.0),
                new Point2D(291.0, 291.0),
                new Point2D(291.0, 820.0)
        );

        // север-запад 1
        final List<Point2D> pedestrianPath13 = List.of(
                new Point2D(509.0, 820.0),
                new Point2D(509.0, 509.0),
                new Point2D(-20.0, 509.0)
        );

        // север-восток 1
        final List<Point2D> pedestrianPath14 = List.of(
                new Point2D(291.0, 820.0),
                new Point2D(291.0, 509.0),
                new Point2D(820.0, 509.0)
        );

        // запад-юг 1
        final List<Point2D> pedestrianPath15 = List.of(
                new Point2D(-20.0, 509.0),
                new Point2D(291.0, 509.0),
                new Point2D(291.0, -20.0)
        );

        // восток-юг 1
        final List<Point2D> pedestrianPath16 = List.of(
                new Point2D(820.0, 509.0),
                new Point2D(509.0, 509.0),
                new Point2D(509.0, -20.0)
        );

        final List<List<Point2D>> pedestrianPaths = List.of(
                pedestrianPath1, pedestrianPath2,
                pedestrianPath3, pedestrianPath4,
                pedestrianPath3, pedestrianPath4,
                pedestrianPath5, pedestrianPath6,
                pedestrianPath7, pedestrianPath8,
                pedestrianPath9, pedestrianPath10,
                pedestrianPath11, pedestrianPath12,
                pedestrianPath13, pedestrianPath14,
                pedestrianPath15, pedestrianPath16
        );

        simulation.addAllPotentialPathForRoadUser(TPedestrian.class, pedestrianPaths);
    }
}
