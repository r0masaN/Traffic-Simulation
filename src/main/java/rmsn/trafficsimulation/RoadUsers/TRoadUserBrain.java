package rmsn.trafficsimulation.RoadUsers;

import rmsn.trafficsimulation.Geometry.Point2D;
import rmsn.trafficsimulation.Geometry.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TRoadUserBrain {
    private final List<Point2D> path;
    private int currGoalIdx;
    private boolean hasReachedGoal;

    public TRoadUserBrain() {
        this.path = new ArrayList<>();
        this.currGoalIdx = -1;
        this.hasReachedGoal = false;
    }

    public TRoadUserBrain(final List<Point2D> path, final int currGoalIdx) {
        this.path = new ArrayList<>(path.size());
        path.forEach((final Point2D p) ->
                this.path.add(new Point2D(p)));

        this.currGoalIdx = currGoalIdx;
        this.hasReachedGoal = false;
    }

    public TRoadUserBrain(final TRoadUserBrain other) {
        this.path = new ArrayList<>(other.path.size());
        other.path.forEach((final Point2D p) ->
                this.path.add(new Point2D(p)));

        this.currGoalIdx = other.currGoalIdx;
        this.hasReachedGoal = other.hasReachedGoal;
    }

    public boolean addPathPoint(final Point2D p) {
        return this.path.add(new Point2D(Objects.requireNonNull(p)));
    }

    public boolean addAllPathPoints(final List<Point2D> path) {
        boolean[] status = new boolean[]{true};
        path.forEach((final Point2D pathPoint) ->
                status[0] &= this.path.add(new Point2D(pathPoint)));

        return status[0];
    }

    public void update(final TRoadUser roadUser) {
        final Point2D diff = roadUser.getPos().subtract(this.getCurrGoal());
        final double distance = new Vector2D(diff).magnitude();

        // если расстояние между объектом и целевой точкой очень маленькое, то:
        // 1. позиция объекта = целевая точка (во избежание погрешностей)
        // 2. целевая точка = следующая точка в пути
        // 2.1. если следующей точки в пути нет, то целевая точка = null и флаг hasReachedGoal = true
        // 3. обновление direction у объекта (новая целевая точка минус текущая позиция объекта)
        if (Double.compare(distance, 0.01 * roadUser.getSpeed()) <= 0) {
            roadUser.getPos().setPoint(this.getCurrGoal()); // 1

            if (this.currGoalIdx + 1 >= this.path.size()) { // 2.1
                this.currGoalIdx = -1;
                this.hasReachedGoal = true;
                return;
            }

            // 2
            ++this.currGoalIdx;

            // 3
            this.updateDirectionBasedOnGoal(roadUser);
        }
    }

    public void updateDirectionBasedOnGoal(final TRoadUser roadUser) {
        final Point2D diff = roadUser.brain.getCurrGoal().subtract(roadUser.pos);
        roadUser.direction.setPoint(diff);
        roadUser.direction.normalizeV();
    }

    public List<Point2D> getPath() {
        return Collections.unmodifiableList(this.path);
    }

    public int getCurrGoalIdx() {
        return this.currGoalIdx;
    }

    public Point2D getCurrGoal() {
        return (this.currGoalIdx >= 0) ?
                this.path.get(this.currGoalIdx) :
                null;
    }

    public boolean hasReachedGoal() {
        return this.hasReachedGoal;
    }

    public void setPath(final List<Point2D> newPath) {
        this.path.clear();

        if (newPath.isEmpty())
            return;

        newPath.forEach((final Point2D pathPoint) ->
                this.path.add(new Point2D(pathPoint)));

        this.currGoalIdx = (this.path.size() >= 2) ? 1 : -1;
        // в точке this.path.get(0) в принципе бот спавнится => нет смысла её целью делать
    }

    public void setCurrGoalIdx(final int newGoalIdx) {
        if (newGoalIdx >= this.path.size())
            throw new IllegalArgumentException("Index of new goal is out of range for the path list");

        this.currGoalIdx = newGoalIdx;
    }

    @Override
    public String toString() {
        return String.format("TRoadUserBrain[path=%s, currGoal=%s]", this.path, this.currGoalIdx);
    }
}
