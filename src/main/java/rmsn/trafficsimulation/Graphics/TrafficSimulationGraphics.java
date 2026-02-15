package rmsn.trafficsimulation.Graphics;

import rmsn.trafficsimulation.Simulation.TrafficSimulationContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class TrafficSimulationGraphics {
    protected final GraphicsContext gc;
    protected TrafficSimulationContext ctx;
    protected final int WIDTH, HEIGHT;

    protected TrafficSimulationGraphics(final int width, final int height, final Canvas canvas) {
        this.gc = canvas.getGraphicsContext2D();
        this.ctx = null;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    protected TrafficSimulationGraphics(final int width, final int height, final Canvas canvas, final TrafficSimulationContext ctx) {
        this.gc = canvas.getGraphicsContext2D();
        this.ctx = ctx;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public final void bindSimulationContext(final TrafficSimulationContext ctx) {
        this.ctx = ctx;
    }

    public final void render() {
        this.renderBase();
        this.renderSimulationFrame();
    }

    protected abstract void renderBase();

    protected abstract void renderSimulationFrame();
}
