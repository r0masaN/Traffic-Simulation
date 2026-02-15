package rmsn.trafficsimulation.RoadEquipment;

public final class RoadTrafficLight extends TrafficLight {
    private TrafficLightState prevState;

    public RoadTrafficLight() {
        super();
    }

    public RoadTrafficLight(final double greenDuration, final double redDuration, final TrafficLightState currState) {
        super(greenDuration, redDuration, currState);
    }

    public RoadTrafficLight(final double greenDuration, final double redDuration, final TrafficLightState prevState, final TrafficLightState currState) {
        super(greenDuration, redDuration, currState);
        this.prevState = prevState;
    }

    public RoadTrafficLight(final double greenDuration, final double redDuration, final double currStateDuration, final TrafficLightState prevState, final TrafficLightState currState) {
        super(greenDuration, redDuration, currStateDuration, currState);
        this.prevState = prevState;
    }

    public RoadTrafficLight(final RoadTrafficLight other) {
        super(other.GREEN_DURATION, other.RED_DURATION, other.currStateDuration, other.currState);
        this.prevState = other.prevState;
    }

    @Override
    public void updateState(final double dt) {
        this.currStateDuration += dt;

        if (this.currState == TrafficLightState.RED && Double.compare(this.currStateDuration, this.RED_DURATION) >= 0) {
            this.prevState = this.currState;
            this.currState = TrafficLightState.YELLOW;
            this.currStateDuration = 0.0;

        } else if (this.currState == TrafficLightState.YELLOW && Double.compare(this.currStateDuration, YELLOW_DURATION) >= 0) {
            final TrafficLightState oldPrevState = this.prevState;
            this.prevState = this.currState;
            this.currStateDuration = 0.0;

            if (oldPrevState == TrafficLightState.RED)
                this.currState = TrafficLightState.GREEN;
            else // если был GREEN или другой (null, например)
                this.currState = TrafficLightState.RED;

        } else if (this.currState == TrafficLightState.GREEN && Double.compare(this.currStateDuration, this.GREEN_DURATION) >= 0) {
            this.prevState = this.currState;
            this.currState = TrafficLightState.YELLOW;
            this.currStateDuration = 0.0;
        }
    }

    public TrafficLightState getPrevState() {
        return this.prevState;
    }

    public void setPrevState(final TrafficLightState prevState) {
        this.prevState = prevState;
    }

    public static final class Builder {
        private double greenDuration = 0.0, redDuration = 0.0;
        private double currStateDuration = 0.0;
        private TrafficLightState prevState = null;
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

        public Builder prevState(final TrafficLightState prevState) {
            this.prevState = prevState;
            return this;
        }

        public Builder currState(final TrafficLightState currState) {
            this.currState = currState;
            return this;
        }

        public RoadTrafficLight build() {
            return new RoadTrafficLight(this.greenDuration, this.redDuration, this.currStateDuration, this.prevState, this.currState);
        }
    }
}
