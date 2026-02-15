package rmsn.trafficsimulation.Geometry;

import java.util.Objects;

public final class Point2D {
    private double x, y;

    public Point2D() {
        this.x = this.y = 0.0;
    }

    public Point2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D(final Point2D other) {
        Objects.requireNonNull(other);
        this.x = other.x;
        this.y = other.y;
    }

    public Point2D add(final double x, final double y) {
        return new Point2D(this.x + x, this.y + y);
    }

    public Point2D add(final double scalar) {
        return new Point2D(this.x + scalar, this.y + scalar);
    }

    public Point2D add(final Point2D other) {
        Objects.requireNonNull(other);
        return new Point2D(this.x + other.x, this.y + other.y);
    }

    public Point2D addX(final double x) {
        return new Point2D(this.x + x, this.y);
    }

    public Point2D addY(final double y) {
        return new Point2D(this.x, this.y + y);
    }

    public void addV(final double x, final double y) {
        this.x += x;
        this.y += y;
    }

    public void addV(final double scalar) {
        this.x += scalar;
        this.y += scalar;
    }

    public void addV(final Point2D other) {
        Objects.requireNonNull(other);
        this.x += other.x;
        this.y += other.y;
    }

    public void addV(final Vector2D vec) {
        Objects.requireNonNull(vec);
        this.addV(vec.to);
    }

    public void addVx(final double vx) {
        this.x += vx;
    }

    public void addVy(final double vy) {
        this.y += vy;
    }

    public Point2D subtract(final double x, final double y) {
        return new Point2D(this.x - x, this.y - y);
    }

    public Point2D subtract(final double scalar) {
        return new Point2D(this.x - scalar, this.y - scalar);
    }

    public Point2D subtract(final Point2D other) {
        Objects.requireNonNull(other);
        return new Point2D(this.x - other.x, this.y - other.y);
    }

    public Point2D subtractX(final double x) {
        return new Point2D(this.x - x, this.y);
    }

    public Point2D subtractY(final double y) {
        return new Point2D(this.x, this.y - y);
    }

    public void subtractV(final double x, final double y) {
        this.x -= x;
        this.y -= y;
    }

    public void subtractV(final double scalar) {
        this.x -= scalar;
        this.y -= scalar;
    }

    public void subtractV(final Point2D other) {
        Objects.requireNonNull(other);
        this.x -= other.x;
        this.y -= other.y;
    }

    public void subtractV(final Vector2D vec) {
        Objects.requireNonNull(vec);
        this.subtractV(vec.to);
    }

    public void subtractVx(final double vx) {
        this.x -= vx;
    }

    public void subtractVy(final double vy) {
        this.x -= vy;
    }

    public Point2D multiply(final double scalar) {
        return new Point2D(this.x * scalar, this.y * scalar);
    }

    public void multiplyV(final double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public Point2D divide(final double scalar) {
        if (Double.compare(scalar, 0.0) == 0)
            throw new ArithmeticException("Division be zero");

        return new Point2D(this.x / scalar, this.y / scalar);
    }

    public void divideV(final double scalar) {
        if (Double.compare(scalar, 0.0) == 0)
            throw new ArithmeticException("Division be zero");

        this.x /= scalar;
        this.y /= scalar;
    }

    public void setPoint(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public void setPoint(final Point2D other) {
        Objects.requireNonNull(other);
        this.x = other.x;
        this.y = other.y;
    }

    public boolean isZero() {
        return (Double.compare(this.x, 0.0) == 0) && (Double.compare(this.y, 0.0) == 0);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Point2D other))
            return false;

        return Double.compare(this.x, other.x) == 0 && Double.compare(this.y, other.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return String.format("Point2D[x=%f, y=%f]", this.x, this.y);
    }
}
