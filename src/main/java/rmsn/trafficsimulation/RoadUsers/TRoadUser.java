package rmsn.trafficsimulation.RoadUsers;

import rmsn.trafficsimulation.Geometry.Point2D;
import rmsn.trafficsimulation.Geometry.Vector2D;
import rmsn.trafficsimulation.Simulation.TrafficSimulationContext;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public sealed abstract class TRoadUser permits TCar, TTruck, TPedestrian {
    public static final List<Class<? extends TRoadUser>> INHERITED_CLASSES =
            List.of(TCar.class, TTruck.class, TPedestrian.class);

    private final long ID = new Random().nextInt();

    protected double MAX_SPEED;
    protected double acceleration, speed;
    protected double MIN_FREE_SPACE_AROUND;
    protected Vector2D direction;
    protected Point2D pos;
    protected StoppingReason stoppingReason = null;
    protected TRoadUserBrain brain;

    protected TRoadUser() {
        this.MAX_SPEED = Double.POSITIVE_INFINITY;
        this.acceleration = this.speed = this.MIN_FREE_SPACE_AROUND = 0.0;
        this.direction = new Vector2D();
        this.pos = new Point2D();
        this.brain = new TRoadUserBrain();
    }

    protected TRoadUser(final double maxSpeed, final double acceleration, final double speed, final double minFreeSpaceAround,
                        final Vector2D direction, final Point2D pos, final TRoadUserBrain brain) {
        this.MAX_SPEED = maxSpeed;
        this.acceleration = acceleration;
        this.speed = speed;
        this.MIN_FREE_SPACE_AROUND = minFreeSpaceAround;
        this.direction = new Vector2D(direction);
        this.pos = pos;
        this.brain = new TRoadUserBrain(brain);
    }

    public abstract void update(final double dt, final TrafficSimulationContext ctx);

    public abstract boolean setupAndAddIntoContext(final TrafficSimulationContext ctx);

    public boolean hasReachedGoal() {
        return this.brain.hasReachedGoal();
    }

    public double getMaxSpeed() {
        return this.MAX_SPEED;
    }

    public double getAcceleration() {
        return this.acceleration;
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getMinFreeSpaceAround() {
        return this.MIN_FREE_SPACE_AROUND;
    }

    public Vector2D getDirection() {
        return this.direction;
    }

    public Point2D getPos() {
        return this.pos;
    }

    public TRoadUserBrain getBrain() {
        return this.brain;
    }

    public void setAcceleration(final double acceleration) {
        this.acceleration = acceleration;
    }

    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    public void setDirection(final Vector2D direction) {
        this.direction = new Vector2D(direction);
    }

    public void setPos(final Point2D pos) {
        this.pos = new Point2D(pos);
    }

    public void setBrain(final TRoadUserBrain brain) {
        this.brain = new TRoadUserBrain(brain);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.ID);
        // return Objects.hash(this.MAX_SPEED, this.acceleration, this.speed, this.MIN_FREE_SPACE_AROUND,
        // this.direction, this.pos);
    }

    @Override
    public String toString() {
        return String.format("TRoadUser[MAX_SPEED=%f, acceleration=%s, speed=%s, pos=%s, brain=%s]",
                this.MAX_SPEED, this.acceleration, this.speed, this.pos, this.brain);
    }
}
