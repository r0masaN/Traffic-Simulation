package rmsn.trafficsimulation.RoadUsers;

import rmsn.trafficsimulation.Geometry.Point2D;
import rmsn.trafficsimulation.Geometry.Vector2D;
import rmsn.trafficsimulation.RoadEquipment.RoadTrafficLight;
import rmsn.trafficsimulation.RoadEquipment.StopLine;
import rmsn.trafficsimulation.RoadEquipment.TrafficLight;
import rmsn.trafficsimulation.RoadEquipment.TrafficLightState;
import rmsn.trafficsimulation.Simulation.TrafficSimulationContext;
import rmsn.trafficsimulation.Math.Math;

import java.util.List;
import java.util.Random;

public final class TCar extends TRoadUser {
    private static final double START_ACCELERATION_COEFFICIENT = 0.5;
    private static final double STOP_ACCELERATION_COEFFICIENT = 0.4;

    public TCar() {
        super();
    }

    public TCar(final double maxSpeed, final double acceleration, final double speed, final double minFreeSpaceAround,
                final Vector2D direction, final Point2D pos, final TRoadUserBrain brain) {
        super(maxSpeed, acceleration, speed, minFreeSpaceAround, direction, pos, brain);
    }

    @Override
    public void update(final double dt, final TrafficSimulationContext ctx) {
        // 1. найти ближайшую стоп-линию перед собой (минимальный угол между векторами направления и вектором к стоп-линии)
        // 2. если расстояние до неё меньше минимального свободного и светофор запрещает ехать, то остановиться
        // 3. найти ближайшего бота перед собой, аналогичным образом
        // 4. если расстояние до него меньше минимального свободного (либо своего), то остановиться
        // 5. если в обоих случаях не должны остановиться, но скорость 0, значит, мы остановились ранее
        // но сейчас-то уже ничего не мешает ехать, так что ставим скорость в максимальную
        // 6. обновить координату (если скорость не 0, очевидно)
        // 7. обновить мозг (если скорость не 0, очевидно)

        // 1
        boolean shouldStop = false;

        // 2
        StopLine closestStopLine = null;
        double minDistanceToStopLine = Double.POSITIVE_INFINITY;

        for (final StopLine stopLine : ctx.stopLines()) {
            if (!(stopLine.getLinkedTrafficLight() instanceof RoadTrafficLight))
                continue;

            final Vector2D vectorFromPosToStopLine = Math.vectorFromPointToLine(this.pos, stopLine.getLine());
            final double distance = vectorFromPosToStopLine.magnitude();

            if (distance < minDistanceToStopLine) {
                final double angleInDegrees = Math.angleInDegreesBetweenTwoVectors(this.direction, vectorFromPosToStopLine);
                if (java.lang.Math.abs(angleInDegrees) <= 5.0) {
                    closestStopLine = stopLine;
                    minDistanceToStopLine = distance;
                }
            }
        }

        if (closestStopLine != null) {
            final RoadTrafficLight roadTrafficLight = (RoadTrafficLight) closestStopLine.getLinkedTrafficLight();

            if (!(roadTrafficLight.getCurrState() == TrafficLightState.GREEN ||
                    roadTrafficLight.getCurrState() == TrafficLightState.YELLOW &&
                            roadTrafficLight.getPrevState() == TrafficLightState.GREEN)) {

                if (Math.compareDouble(minDistanceToStopLine, TrafficLight.MIN_FREE_SPACE_AROUND) <= 0) {
                    this.stoppingReason = StoppingReason.STOP_LINE;
                    shouldStop = true;

                } else if (Math.compareDouble(minDistanceToStopLine, TrafficLight.MIN_FREE_SPACE_AROUND * 4.0 * this.MAX_SPEED / 200) <= 0) {
                    this.stoppingReason = StoppingReason.STOP_LINE;
                    this.acceleration = -1.2 * STOP_ACCELERATION_COEFFICIENT * this.MAX_SPEED;
                }

            } else if (Math.compareDouble(minDistanceToStopLine, TrafficLight.MIN_FREE_SPACE_AROUND * 4.0 * this.MAX_SPEED / 200) <= 0 &&
                    Math.compareDouble(minDistanceToStopLine, TrafficLight.MIN_FREE_SPACE_AROUND) > 0 &&
                    this.acceleration < 0.0 && this.stoppingReason == StoppingReason.STOP_LINE) {
                this.stoppingReason = null;
                this.acceleration = START_ACCELERATION_COEFFICIENT * this.MAX_SPEED;
            }

        } else if (this.acceleration < 0.0 && this.stoppingReason == StoppingReason.STOP_LINE) {
            this.stoppingReason = null;
            this.acceleration = START_ACCELERATION_COEFFICIENT * this.MAX_SPEED;
        }

        // 3
        if (!shouldStop) {
            TRoadUser closestRoadUser = null;
            double minDistanceToRoadUser = Double.POSITIVE_INFINITY;

            for (final TRoadUser roadUser : ctx.roadUsers()) {
                if (roadUser == this)
                    continue;

                final Vector2D vectorFromPosToRoadUser = new Vector2D(roadUser.pos.subtract(this.pos));
                final double distance = vectorFromPosToRoadUser.magnitude();

                if (distance < minDistanceToRoadUser) {
                    final double angleInDegrees = Math.angleInDegreesBetweenTwoVectors(this.direction, vectorFromPosToRoadUser);
                    if (java.lang.Math.abs(angleInDegrees) <= 35.0) {
                        closestRoadUser = roadUser;
                        minDistanceToRoadUser = distance;
                    }
                }
            }

            if (closestRoadUser != null) {
                if (Math.compareDouble(minDistanceToRoadUser, this.MIN_FREE_SPACE_AROUND) <= 0) {
                    this.stoppingReason = StoppingReason.ROAD_USER;
                    shouldStop = true;

                } else if (Math.compareDouble(minDistanceToRoadUser, this.MIN_FREE_SPACE_AROUND * 1.5 * this.MAX_SPEED / 200) <= 0) {
                    this.stoppingReason = StoppingReason.ROAD_USER;
                    this.acceleration = -2.5 * STOP_ACCELERATION_COEFFICIENT * this.MAX_SPEED;
                }

            } else if (this.acceleration < 0.0 && this.stoppingReason == StoppingReason.ROAD_USER) {
                this.stoppingReason = null;
                this.acceleration = START_ACCELERATION_COEFFICIENT * this.MAX_SPEED;
            }
        }

        // 4
        if (shouldStop && Math.compareDouble(this.speed, 0.0) != 0) // должны остановиться и ещё не остановились
            this.acceleration = this.speed = 0.0;
        else if (!shouldStop && Math.compareDouble(this.speed, 0.0) == 0) // не должны останавливаться, но почему-то стоим
            this.acceleration = START_ACCELERATION_COEFFICIENT * this.MAX_SPEED;

        this.speed += this.acceleration * dt;
        if (Math.compareDouble(this.speed, this.MAX_SPEED) >= 0) {
            this.acceleration = 0.0;
            this.speed = this.MAX_SPEED;
        } else if (this.speed <= 0.0) {
            this.acceleration = 0.0;
            this.speed = 0.0;
        }

        if (Math.compareDouble(this.speed, 0.0) > 0) {
            //6
            this.pos.addV(this.direction.multiply(this.speed * dt));

            // 7
            this.brain.update(this);
        }
    }

    @Override
    public boolean setupAndAddIntoContext(final TrafficSimulationContext ctx) {
        this.MAX_SPEED = new Random().nextInt(150, 300);
        this.acceleration = 0.0;
        this.speed = this.MAX_SPEED;
        this.MIN_FREE_SPACE_AROUND = 40.0;

        final List<List<Point2D>> potentialPathsForCar = ctx.potentialPathsForRoadUsers().get(TCar.class);
        if (potentialPathsForCar == null || potentialPathsForCar.isEmpty())
            return false;

        boolean canTCarSpawnCorrectly = false;
        final Random rnd = new Random();
        int randomPathIndex;
        List<Point2D> randomPath = null;

        // 5 попыток для выбора пути, ибо, если в начальной точке пути уже находится другой участник движения
        // мы по минимальному свободному расстоянию вокруг никак не сможем находиться с ним рядом
        // и придётся выбирать другой путь
        for (int i = 0; i < 5; ++i) {
            randomPathIndex = rnd.nextInt(potentialPathsForCar.size());
            randomPath = potentialPathsForCar.get(randomPathIndex);

            if (randomPath == null || randomPath.isEmpty())
                continue;

            final Point2D potentialStartPos = randomPath.getFirst();

            final boolean canSpawnCarHere = ctx.roadUsers().stream()
                    .noneMatch((final TRoadUser roadUser) -> {
                        final Point2D diff = roadUser.getPos().subtract(potentialStartPos);
                        final double distanceBetween = new Vector2D(diff).magnitude();

                        final double minAllowedDistance = java.lang.Math.max(this.MIN_FREE_SPACE_AROUND,
                                roadUser.MIN_FREE_SPACE_AROUND);

                        return distanceBetween < minAllowedDistance;
                    });

            if (canSpawnCarHere) {
                canTCarSpawnCorrectly = true;
                break;
            }
        }

        if (!canTCarSpawnCorrectly)
            return false;


        // если удалось всё же выбрать удачный путь для спавна, то продолжаем настройку
        this.pos.setPoint(randomPath.getFirst());

        this.brain.setPath(randomPath);

        // когда установили максимальную скорость, текущий маршрут и текущую позицию
        // мы имеем текущую цель (точку), в которую будем двигаться из текущей
        // таким образом вычислим нормализованный вектор (текущая точка -> целевая точка)
        // и затем умножим его на максимальную скорость, т. е. двигаемся изначально с ней

        this.brain.updateDirectionBasedOnGoal(this);

        // добавляем машину в список машин сцены
        ctx.addRoadUser(this);

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("TCar[super=%s]", super.toString());
    }

    public static final class Builder {
        private double MAX_SPEED = Double.POSITIVE_INFINITY;
        private double acceleration = 0.0, speed = 0.0;
        private double MIN_FREE_SPACE_AROUND = 0.0;
        private Vector2D direction = new Vector2D();
        private Point2D pos = new Point2D();
        private TRoadUserBrain brain = new TRoadUserBrain();

        public Builder() {

        }

        public Builder maxSpeed(final double maxSpeed) {
            this.MAX_SPEED = maxSpeed;
            return this;
        }

        public Builder acceleration(final double acceleration) {
            this.acceleration = acceleration;
            return this;
        }

        public Builder speed(final double speed) {
            this.speed = speed;
            return this;
        }

        public Builder minFreeDistanceAround(final double minFreeDistanceAround) {
            this.MIN_FREE_SPACE_AROUND = minFreeDistanceAround;
            return this;
        }

        public Builder direction(final Vector2D direction) {
            this.direction = direction;
            return this;
        }

        public Builder pos(final Point2D pos) {
            this.pos = pos;
            return this;
        }

        public Builder brain(final TRoadUserBrain brain) {
            this.brain = new TRoadUserBrain(brain);
            return this;
        }

        public TCar build() {
            return new TCar(this.MAX_SPEED, this.acceleration, this.speed, this.MIN_FREE_SPACE_AROUND, this.direction, this.pos, this.brain);
        }
    }
}
