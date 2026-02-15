package rmsn.trafficsimulation.Graphics;

import rmsn.trafficsimulation.Geometry.Point2D;
import rmsn.trafficsimulation.Geometry.Vector2D;
import rmsn.trafficsimulation.RoadEquipment.PedestrianTrafficLight;
import rmsn.trafficsimulation.RoadEquipment.RoadTrafficLight;
import rmsn.trafficsimulation.RoadEquipment.StopLine;
import rmsn.trafficsimulation.RoadEquipment.TrafficLightState;
import rmsn.trafficsimulation.RoadUsers.*;
import rmsn.trafficsimulation.Simulation.TrafficSimulationContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;

public final class TrafficSimulationGraphicsImpl extends TrafficSimulationGraphics {
    private static final boolean DRAW_ROAD_USERS_PATHS = false;
    private static final boolean DRAW_ROAD_USERS_DIRECTIONS = false;
    private static final boolean LOG_VELOCITY_INFO = false;
    private static final boolean LOG_MAX_VELOCITY_INFO = false;
    private static final boolean LOG_POSITION_INFO = false;

    public TrafficSimulationGraphicsImpl(final int width, final int height, final Canvas canvas) {
        super(width, height, canvas);
    }

    public TrafficSimulationGraphicsImpl(final int width, final int height, final Canvas canvas, final TrafficSimulationContext ctx) {
        super(width, height, canvas, ctx);
    }

    @Override
    protected void renderBase() {
        this.gc.clearRect(0, 0, WIDTH, HEIGHT);

        // дорога
        this.gc.setFill(Color.GRAY);
        this.gc.fillRect(0, 0, WIDTH, HEIGHT);

        // тротуары
        this.gc.setFill(Color.LIGHTGRAY);
        this.gc.fillRect(0, 0, 324, 324);
        this.gc.fillRect(476, 0, 324, 324);
        this.gc.fillRect(0, 476, 324, 324);
        this.gc.fillRect(476, 476, 324, 324);

        // дорожная разметка
        this.gc.setFill(Color.WHITE);
        for (int i = 0; i * 41 < WIDTH; ++i)
            this.gc.fillRect(i * 41, 396, 33, 8);
        for (int j = 0; j * 41 < HEIGHT; ++j)
            this.gc.fillRect(396, j * 41, 8, 33);

        // пешеходные переходы
        this.gc.setFill(Color.LIGHTGOLDENRODYELLOW);
        for (int a = 0; a * 20 < 152; ++a)
            this.gc.fillRect(324 + a * 20, 484, 10, 50);
        for (int b = 0; b * 20 < 152; ++b)
            this.gc.fillRect(324 + b * 20, 264, 10, 50);
        for (int c = 0; c * 20 < 152; ++c)
            this.gc.fillRect(484, 324 + c * 20, 50, 10);
        for (int d = 0; d * 20 < 152; ++d)
            this.gc.fillRect(264, 324 + d * 20, 50, 10);
    }

    @Override
    protected void renderSimulationFrame() {
        this.ctx.stopLines().forEach(this::renderStopLine);

        this.ctx.roadUsers().forEach((final TRoadUser roadUser) -> {
            if (DRAW_ROAD_USERS_PATHS)
                renderRoadUserPath(roadUser);

            switch (roadUser) {
                case TCar car -> renderCar(car);
                case TTruck truck -> renderTruck(truck);
                case TPedestrian pedestrian -> renderPedestrian(pedestrian);
                default -> {
                    // ничего
                }
            }

            if (DRAW_ROAD_USERS_DIRECTIONS)
                renderRoadUserDirection(roadUser);

            if (LOG_MAX_VELOCITY_INFO)
                renderMaxVelocityInfo(roadUser);

            if (LOG_VELOCITY_INFO)
                renderVelocityInfo(roadUser);

            if (LOG_POSITION_INFO)
                renderPositionInfo(roadUser);
        });
    }

    private void renderStopLine(final StopLine stopLine) {
        switch (stopLine.getLinkedTrafficLight().getCurrState()) {
            case TrafficLightState.GREEN -> this.gc.setStroke(Color.GREEN);
            case TrafficLightState.YELLOW -> this.gc.setStroke(Color.YELLOW);
            case TrafficLightState.RED -> this.gc.setStroke(Color.RED);
            default -> gc.setStroke(Color.BLACK);
        }

        switch (stopLine.getLinkedTrafficLight()) {
            case RoadTrafficLight _ -> gc.setLineWidth(8);
            case PedestrianTrafficLight _ -> gc.setLineWidth(6);
            default -> gc.setLineWidth(5);
        }

        this.gc.strokeLine(
                stopLine.getLine().getP1().getX(),
                this.HEIGHT - stopLine.getLine().getP1().getY(),
                stopLine.getLine().getP2().getX(),
                this.HEIGHT - stopLine.getLine().getP2().getY()
        );
    }

    private void renderRoadUserPath(final TRoadUser roadUser) {
        final int hash = Math.abs(roadUser.hashCode());
        final Color color = Color.color(
                (hash % 507) / 511.0,
                (hash % 306) / 307.0,
                (hash % 911) / 917.0
        );

        this.gc.setStroke(color);
        this.gc.setLineWidth(2);

        Point2D currPoint = roadUser.getPos();
        final List<Point2D> path = roadUser.getBrain().getPath();
        int nextPointIdx = roadUser.getBrain().getCurrGoalIdx();

        while (nextPointIdx < path.size()) {
            this.gc.strokeLine(
                    currPoint.getX(),
                    this.HEIGHT - currPoint.getY(),
                    path.get(nextPointIdx).getX(),
                    this.HEIGHT - path.get(nextPointIdx).getY()
            );

            currPoint = path.get(nextPointIdx++);
        }
    }

    private void renderRoadUserDirection(final TRoadUser roadUser) {
        final int hash = Math.abs(roadUser.hashCode());
        final Color color = Color.color(
                (hash % 911) / 917.0,
                (hash % 507) / 511.0,
                (hash % 306) / 307.0
        );

        this.gc.setStroke(color);
        this.gc.setLineWidth(4);

        final Vector2D roadUserDirection = roadUser.getDirection();
        final Point2D roadUserPos = roadUser.getPos();
        final Point2D absoluteDirectionPoint = roadUserPos.add(roadUserDirection.getPoint().multiply(64.0));

        this.gc.strokeLine(roadUserPos.getX(), this.HEIGHT - roadUserPos.getY(),
                absoluteDirectionPoint.getX(), this.HEIGHT - absoluteDirectionPoint.getY());
    }

    private void renderMaxVelocityInfo(final TRoadUser roadUser) {
        this.gc.setFill(Color.BLACK);
        this.gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        this.gc.fillText(String.format("[V=%.1f]", roadUser.getMaxSpeed()), roadUser.getPos().getX() - 4.0,HEIGHT - roadUser.getPos().getY() - 8.0);
    }

    private void renderVelocityInfo(final TRoadUser roadUser) {
        this.gc.setFill(Color.BLACK);
        this.gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        this.gc.fillText(String.format("[v=%.2f]", roadUser.getSpeed()), roadUser.getPos().getX() - 4.0,HEIGHT - roadUser.getPos().getY() + 4.0);
    }

    private void renderPositionInfo(final TRoadUser roadUser) {
        this.gc.setFill(Color.BLACK);
        this.gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        this.gc.fillText(String.format("[%.2f;%.2f]", roadUser.getPos().getX(), roadUser.getPos().getY()), roadUser.getPos().getX() - 10.0,HEIGHT - roadUser.getPos().getY() + 16.0);
    }

    private void renderCar(final TCar car) {
        this.gc.setFill(Color.LIGHTSEAGREEN);
        this.gc.fillOval(
                car.getPos().getX() - 18,
                HEIGHT - car.getPos().getY() - 18,
                36,
                36
        );
    }

    private void renderTruck(final TTruck truck) {
        this.gc.setFill(Color.CORNFLOWERBLUE);
        this.gc.fillOval(
                truck.getPos().getX() - 24,
                HEIGHT - truck.getPos().getY() - 24,
                48,
                48
        );
    }

    private void renderPedestrian(final TPedestrian pedestrian) {
        this.gc.setFill(Color.HOTPINK);
        this.gc.fillOval(
                pedestrian.getPos().getX() - 12,
                HEIGHT - pedestrian.getPos().getY() - 12,
                24,
                24
        );
    }
}
