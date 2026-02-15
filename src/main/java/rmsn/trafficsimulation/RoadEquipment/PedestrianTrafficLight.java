package rmsn.trafficsimulation.RoadEquipment;

public final class PedestrianTrafficLight extends TrafficLight {
    public PedestrianTrafficLight() {
        super();
    }

    public PedestrianTrafficLight(final double greenDuration, final double redDuration, final TrafficLightState currState) {
        super(greenDuration, redDuration, currState);
    }

    public PedestrianTrafficLight(final double greenDuration, final double redDuration, final double currStateDuration, final TrafficLightState currState) {
        super(greenDuration, redDuration, currStateDuration, currState);
    }

    public PedestrianTrafficLight(final RoadTrafficLight other) {
        super(other.GREEN_DURATION, other.RED_DURATION, other.currStateDuration, other.currState);
    }

    @Override
    public void updateState(final double dt) {
        this.currStateDuration += dt;

        if (this.currState == TrafficLightState.RED && Double.compare(this.currStateDuration, this.RED_DURATION) >= 0) {
            this.currState = TrafficLightState.GREEN;
            this.currStateDuration = 0.0;

        } else if (this.currState == TrafficLightState.GREEN && Double.compare(this.currStateDuration, this.GREEN_DURATION) >= 0) {
            this.currState = TrafficLightState.RED;
            this.currStateDuration = 0.0;
        }
    }

    public static final class Builder {
        private double greenDuration = 0.0, redDuration = 0.0;
        private double currStateDuration = 0.0;
        private TrafficLightState currState = null;

        public Builder() {

        }

        public Builder greenDuration(final double greenDuration) {
            this.greenDuration = greenDuration;
            return this;
        }

        public Builder redDuration(final double redDuration) {
            this.redDuration = redDuration;
            return this;
        }

        public Builder currStateDuration(final double currStateDuration) {
            this.currStateDuration = currStateDuration;
            return this;
        }

        public Builder currState(final TrafficLightState currState) {
            this.currState = currState;
            return this;
        }

        public PedestrianTrafficLight build() {
            return new PedestrianTrafficLight(this.greenDuration, this.redDuration, this.currStateDuration, this.currState);
        }
    }
}
