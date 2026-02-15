package rmsn.trafficsimulation.RoadEquipment;

import rmsn.trafficsimulation.Geometry.Line2D;

public final class StopLine {
    private Line2D line;
    private TrafficLight linkedRoadTrafficLight;

    public StopLine() {
        this.line = new Line2D();
        this.linkedRoadTrafficLight = null;
    }

    public StopLine(final Line2D line, final TrafficLight linkedTrafficLight) {
        this.line = new Line2D(line);
        this.linkedRoadTrafficLight = linkedTrafficLight; // не копия! т. к. надо связать стоп-линию и светофор
    }

    public StopLine(final StopLine other) {
        this.line = new Line2D(other.line);
        this.linkedRoadTrafficLight = other.linkedRoadTrafficLight; // не копия! т. к. надо связать стоп-линию и светофор
    }

    public Line2D getLine() {
        return this.line;
    }

    public TrafficLight getLinkedTrafficLight() {
        return this.linkedRoadTrafficLight;
    }

    public void setLine(final Line2D line) {
        this.line = new Line2D(line);
    }

    public void setLinkedTrafficLight(final RoadTrafficLight linkedRoadTrafficLight) {
        this.linkedRoadTrafficLight = linkedRoadTrafficLight; // не копия! т. к. надо связать стоп-линию и светофор
    }
}
