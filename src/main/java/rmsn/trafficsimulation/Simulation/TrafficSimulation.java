package rmsn.trafficsimulation.Simulation;

import rmsn.trafficsimulation.Config.TrafficSimulationConfiguration;
import rmsn.trafficsimulation.Geometry.Point2D;
import rmsn.trafficsimulation.RoadEquipment.StopLine;
import rmsn.trafficsimulation.RoadEquipment.RoadTrafficLight;
import rmsn.trafficsimulation.RoadEquipment.TrafficLight;
import rmsn.trafficsimulation.RoadUsers.TRoadUser;
import rmsn.trafficsimulation.RoadUsers.TRoadUserFactory;

import java.util.*;

public final class TrafficSimulation {
    private TrafficSimulationConfiguration cfg = null;

    private int maxRoadUsersCount;
    private int preferredRoadUsersCount;
    private final Map<Class<? extends TRoadUser>, List<List<Point2D>>> potentialPathsForRoadUsers = new HashMap<>();

    private final List<TrafficLight> roadTrafficLights = new ArrayList<>();
    private final List<StopLine> stopLines = new ArrayList<>();
    private final List<TRoadUser> roadUsers = new ArrayList<>();
    private final TrafficSimulationContext ctx = new TrafficSimulationContext(this.stopLines, this.roadUsers, this.potentialPathsForRoadUsers);

    public TrafficSimulation() {
        this.preferredRoadUsersCount = 16;
        this.maxRoadUsersCount = 32;
    }

    public TrafficSimulation(final int maxRoadUsersCount, final int preferredRoadUsersCount) {
        this.maxRoadUsersCount = maxRoadUsersCount;
        this.preferredRoadUsersCount = preferredRoadUsersCount;
    }

    public boolean addPotentialPathForRoadUser(final Class<? extends TRoadUser> clazz, final List<Point2D> potentialPath) {
        return this.potentialPathsForRoadUsers
                .computeIfAbsent(clazz, (final Class<? extends TRoadUser> key) ->
                        new ArrayList<>())
                .add(deepCopyPath(potentialPath));
    }

    public boolean addAllPotentialPathForRoadUser(final Class<? extends TRoadUser> clazz, final List<List<Point2D>> potentialPaths) {
        final List<List<Point2D>> copy = new ArrayList<>(potentialPaths.size());
        for (final List<Point2D> potentialPath : potentialPaths)
            copy.add(deepCopyPath(potentialPath));

        return this.potentialPathsForRoadUsers
                .computeIfAbsent(clazz, (final Class<? extends TRoadUser> key) ->
                        new ArrayList<>())
                .addAll(copy);
    }

    private List<Point2D> deepCopyPath(final List<Point2D> path) {
        final List<Point2D> copy = new ArrayList<>(path.size());
        for (final Point2D pathPoint : path)
            copy.add(new Point2D(pathPoint));

        return copy;
    }

    public boolean addTrafficLight(final TrafficLight roadTrafficLight) {
        return this.roadTrafficLights.add(Objects.requireNonNull(roadTrafficLight));
    }

    public boolean addAllTrafficLights(final List<RoadTrafficLight> roadTrafficLights) {
        return this.roadTrafficLights.addAll(Objects.requireNonNull(roadTrafficLights));
    }

    public boolean addStopLine(final StopLine stopLine) {
        return this.stopLines.add(Objects.requireNonNull(stopLine));
    }

    public boolean addAllStopLines(final List<StopLine> stopLines) {
        return this.stopLines.addAll(Objects.requireNonNull(stopLines));
    }

    public boolean addRoadUser(final TRoadUser roadUser) {
        return this.roadUsers.add(Objects.requireNonNull(roadUser));
    }

    public boolean addAllRoadUsers(final List<TRoadUser> roadUsers) {
        return this.roadUsers.addAll(Objects.requireNonNull(roadUsers));
    }

    public void update(final double dt) {
        synchronized (this.roadTrafficLights) {
            this.roadTrafficLights.forEach((final TrafficLight roadTrafficLight) ->
                    roadTrafficLight.updateState(dt));
        }

        synchronized (this.roadUsers) {
            this.roadUsers.forEach((final TRoadUser roadUser) ->
                    roadUser.update(dt, this.ctx));
        }

        synchronized (this.roadUsers) {
            this.roadUsers.removeIf(TRoadUser::hasReachedGoal);
        }

        if (this.canRandomlyGenerateNewRoadUser())
            this.generateRandomTrafficUser(TRoadUser.INHERITED_CLASSES, false);
    }

    public void generateRandomTrafficUser(List<Class<? extends TRoadUser>> clazzes, final boolean isUserGeneration) {
        final int randomIndex = new Random().nextInt(clazzes.size());
        final Class<? extends TRoadUser> randomRoadUserClazz = clazzes.get(randomIndex);
        TRoadUserFactory.generateRandomRoadUserIntoTransportSimulationContext(randomRoadUserClazz, this, isUserGeneration);
    }

    public boolean canAddNewRoadUser() {
        return this.roadUsers.size() < this.maxRoadUsersCount;
    }

    public boolean canRandomlyGenerateNewRoadUser() {
        return this.roadUsers.size() < this.preferredRoadUsersCount;
    }

    public TrafficSimulationContext getContext() {
        return this.ctx;
    }

    public void restart() {
        if (!this.isCfgBound())
            throw new IllegalStateException("Don't able to restart simulation without bounded configurator (value is null)");

        this.potentialPathsForRoadUsers.clear();
        this.roadTrafficLights.clear();
        this.stopLines.clear();
        this.roadUsers.clear();

        this.cfg.configure(this);
    }

    public boolean isCfgBound() {
        return (this.cfg != null);
    }

    public void bindCfg(final TrafficSimulationConfiguration cfg) {
        this.cfg = cfg;
    }

    public TrafficSimulationConfiguration getCfg() {
        return this.cfg;
    }

    public void setMaxRoadUsersCount(int maxRoadUsersCount) {
        this.maxRoadUsersCount = maxRoadUsersCount;
    }

    public void setPreferredRoadUsersCount(int preferredRoadUsersCount) {
        this.preferredRoadUsersCount = preferredRoadUsersCount;
    }
}
