package rmsn.trafficsimulation.Geometry;

public final class Line2D {
    private Point2D p1, p2;

    public Line2D() {
        this.p1 = this.p2 = new Point2D();
    }

    public Line2D(final Point2D p1, final Point2D p2) {
        this.p1 = new Point2D(p1);
        this.p2 = new Point2D(p2);
    }

    public Line2D(final Line2D other) {
        this.p1 = new Point2D(other.p1);
        this.p2 = new Point2D(other.p2);
    }

    public Point2D getP1() {
        return this.p1;
    }

    public Point2D getP2() {
        return this.p2;
    }

    public void setP1(final Point2D p1) {
        this.p1 = p1;
    }

    public void setP2(final Point2D p2) {
        this.p2 = p2;
    }

    @Override
    public String toString() {
        return String.format("Line2D[p1=%s, p2=%s]", this.p1, this.p2);
    }
}
