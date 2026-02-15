package rmsn.trafficsimulation.RoadEquipment;

public abstract class TrafficLight {
    public static final double MIN_FREE_SPACE_AROUND = 30.0;
    protected static final double YELLOW_DURATION = 1.0;

    protected final double GREEN_DURATION, RED_DURATION;
    protected double currStateDuration;
    protected TrafficLightState currState;

    protected TrafficLight() {
        this.GREEN_DURATION = this.RED_DURATION = this.currStateDuration = 0.0;
        this.currState = null;
    }

    protected TrafficLight(final double greenDuration, final double redDuration, final TrafficLightState currState) {
        this.GREEN_DURATION = greenDuration;
        this.RED_DURATION = redDuration;
        this.currStateDuration = 0.0;
        this.currState = currState;
    }

    protected TrafficLight(final double greenDuration, final double redDuration, final double currStateDuration, final TrafficLightState currState) {
        this.GREEN_DURATION = greenDuration;
        this.RED_DURATION = redDuration;
        this.currStateDuration = currStateDuration;
        this.currState = currState;
    }

    public abstract void updateState(final double dt);

    public final double getGreenDuration() {
        return this.GREEN_DURATION;
    }

    public final double getRedDuration() {
        return this.RED_DURATION;
    }

    public final double getCurrStateDuration() {
        return this.currStateDuration;
    }

    public final TrafficLightState getCurrState() {
        return this.currState;
    }

    public final void setCurrStateDuration(final double currStateDuration) {
        this.currStateDuration = currStateDuration;
    }

    public final void setCurrState(final TrafficLightState currState) {
        this.currState = currState;
    }
}
