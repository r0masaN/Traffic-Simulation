package rmsn.trafficsimulation.Geometry;

import java.util.Objects;

public final class Vector2D {
    protected Point2D to;

    public Vector2D() {
        this.to = new Point2D();
    }

    public Vector2D(final double toX, final double toY) {
        this.to = new Point2D(toX, toY);
    }

    public Vector2D(final Vector2D other) {
        this.to = new Point2D(Objects.requireNonNull(other.to));
    }

    public Vector2D(final Point2D p) {
        this.to = new Point2D(Objects.requireNonNull(p));
    }

    public Vector2D add(final Vector2D other)  {
        Objects.requireNonNull(other);
        return new Vector2D(this.to.add(other.to));
    }

    public Vector2D add(final Point2D p) {
        Objects.requireNonNull(p);
        return new Vector2D(this.to.add(p));
    }

    public Vector2D add(final double scalar) {
        return new Vector2D(this.to.add(scalar));
    }

    public void addV(final Vector2D other)  {
        Objects.requireNonNull(other);
        this.to.addV(other.to);
    }

    public void addV(final Point2D p) {
        Objects.requireNonNull(p);
        this.to.addV(p);
    }

    public void addV(final double scalar) {
        this.to.addV(scalar);
    }

    public void addVx(final double vx) {
        this.to.addVx(vx);
    }

    public void addVy(final double vy) {
        this.to.addVy(vy);
    }

    public Vector2D subtract(final Vector2D other)  {
        Objects.requireNonNull(other);
        return new Vector2D(this.to.subtract(other.to));
    }

    public Vector2D subtract(final Point2D p) {
        Objects.requireNonNull(p);
        return new Vector2D(this.to.subtract(p));
    }

    public Vector2D subtract(final double scalar) {
        return new Vector2D(this.to.subtract(scalar));
    }

    public void subtractV(final Vector2D other)  {
        Objects.requireNonNull(other);
        this.to.subtractV(other.to);
    }

    public void subtractV(final Point2D p) {
        Objects.requireNonNull(p);
        this.to.subtractV(p);
    }

    public void subtractV(final double scalar) {
        this.to.subtractV(scalar);
    }

    public void subtractVx(final double vx) {
        this.to.subtractVx(vx);
    }

    public void subtractVy(final double vy) {
        this.to.subtractVy(vy);
    }

    public Vector2D multiply(final double scalar) {
        return new Vector2D(this.to.multiply(scalar));
    }

    public double multiply(final Vector2D vec) {
        return this.to.getX() * vec.to.getX() + this.to.getY() * vec.to.getY();
    }

    public void multiplyV(final double scalar) {
        this.to.multiplyV(scalar);
    }

    public Vector2D divide(final double scalar) {
        if (Double.compare(scalar, 0.0) == 0)
            throw new ArithmeticException("Division be zero");

        return new Vector2D(this.to.divide(scalar));
    }

    public void divideV(final double scalar) {
        this.to.divideV(scalar);
    }

    public double magnitude() {
        return Math.hypot(this.to.getX(), this.to.getY());
    }

    public Vector2D normalize() {
        final double length = this.magnitude();
        return new Vector2D(this.to.getX() / length, this.to.getY() / length);
    }

    public void normalizeV() {
        final double length = this.magnitude();
        this.to = new Point2D(this.to.getX() / length, this.to.getY() / length);
    }

    public boolean isZero() {
        return this.to.isZero();
    }

    public boolean isNormalized() {
        return rmsn.trafficsimulation.Math.Math.compareDouble(this.magnitude(), 1.0) == 0;
    }

    public double getX() {
        return this.to.getX();
    }

    public double getY() {
        return this.to.getX();
    }

    public Point2D getPoint() {
        return this.to;
    }

    public void setX(final double x) {
        this.to.setX(x);
    }

    public void setY(final double y) {
        this.to.setY(y);
    }

    public void setPoint(final Point2D p) {
        this.to.setPoint(p);
    }

    public void setVec(final Vector2D vec) {
        this.to.setPoint(vec.to);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Vector2D other))
            return false;

        return Double.compare(this.to.getX(), other.to.getX()) == 0 && Double.compare(this.to.getY(), other.to.getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.to.getX(), this.to.getY());
    }

    @Override
    public String toString() {
        return String.format("Vector2D[to=%s]", this.to);
    }
}
