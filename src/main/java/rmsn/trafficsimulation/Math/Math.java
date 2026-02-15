package rmsn.trafficsimulation.Math;

import rmsn.trafficsimulation.Geometry.Line2D;
import rmsn.trafficsimulation.Geometry.Point2D;
import rmsn.trafficsimulation.Geometry.Vector2D;

import java.util.Objects;

public final class Math {
    private static final double DELTA = 1e-7;
    private static final double EPSILON = 1e-3;

    public static int compareDouble(final double d1, final double d2) {
        final double diff = d1 - d2;
        if (java.lang.Math.abs(diff) < DELTA) {
            return 0;
        } else {
            return (int) java.lang.Math.signum(diff);
        }
    }

    public static boolean isPointInEpsilonNeighbourhoodOfOtherPoint(final Point2D point1, final Point2D point2) {
        Objects.requireNonNull(point1);
        Objects.requireNonNull(point2);

        final double dx = point1.getX() - point2.getX(),
                dy = point1.getY() - point2.getY();

        final double distance = java.lang.Math.sqrt(dx * dx + dy * dy);
        return distance <= EPSILON;
    }

    public static boolean isPointInEpsilonNeighbourhoodOfLine(final Point2D point, final Line2D line) {
        Objects.requireNonNull(point);
        Objects.requireNonNull(line);

        final double x0 = point.getX(),
                y0 = point.getY();

        final double x1 = line.getP1().getX(), y1 = line.getP1().getY();
        final double x2 = line.getP2().getX(), y2 = line.getP2().getY();

        final double A = y2 - y1,
                B = x1 - x2,
                C = x2 * y1 - x1 * y2;

        final double distance = java.lang.Math.abs(A * x0 + B * y0 + C) / java.lang.Math.sqrt(A * A + B * B);

        return Double.compare(distance, EPSILON) < 0;
    }

    public static Vector2D vectorFromPointToLine(final Point2D point, final Line2D line) {
        Objects.requireNonNull(point);
        Objects.requireNonNull(line);

        final double x0 = point.getX(),
                y0 = point.getY();
        final double x1 = line.getP1().getX(),
                y1 = line.getP1().getY();
        final double x2 = line.getP2().getX(),
                y2 = line.getP2().getY();

        final double dx = x2 - x1,
                dy = y2 - y1;
        final double lenSq = dx * dx + dy * dy;

        if (Double.compare(lenSq, 0.0) == 0)
            return new Vector2D(x1 - x0, y1 - y0);

        final double t = ((x0 - x1) * dx + (y0 - y1) * dy) / (dx * dx + dy * dy);
        final double xh = x1 + t * dx,
                yh = y1 + t * dy;

        if (Double.compare(t, 0.0) < 0)
            return new Vector2D(x1 - x0, y1 - y0);
        else if (Double.compare(t, 1.0) > 0)
            return new Vector2D(x2 - x0, y2 - y0);
        else
            return new Vector2D(xh - x0, yh - y0);
    }

    public static double angleInDegreesBetweenTwoVectors(final Vector2D vector1, final Vector2D vector2) {
        final double multiplication = vector1.multiply(vector2);
        final double magnitude1 = vector1.magnitude(),
                magnitude2 = vector2.magnitude();

        final double cosOfAngle = multiplication / (magnitude1 * magnitude2);
        final double angleInRadians = java.lang.Math.acos(cosOfAngle);

        return java.lang.Math.toDegrees(angleInRadians);
    }

    public static boolean doLinesIntersects(final Line2D line1, final Line2D line2) {
        final Point2D a = line1.getP1(), b = line1.getP2(),
                c = line2.getP1(), d = line2.getP2();

        double o1 = orient(a, b, c);
        double o2 = orient(a, b, d);
        double o3 = orient(c, d, a);
        double o4 = orient(c, d, b);

        // общий случай
        if (o1 * o2 < 0 && o3 * o4 < 0)
            return true;

        // граничные случаи (касание)
        if (o1 == 0 && onSegment(a, b, c))
            return true;
        if (o2 == 0 && onSegment(a, b, d))
            return true;
        if (o3 == 0 && onSegment(c, d, a))
            return true;
        if (o4 == 0 && onSegment(c, d, b))
            return true;

        return false;
    }

    private static double orient(final Point2D a, final Point2D b, final Point2D p) {
        return (b.getX() - a.getX()) * (p.getY() - a.getY()) - (b.getY() - a.getY()) * (p.getX() - a.getX());
    }

    private static boolean onSegment(final Point2D a, final Point2D b, final Point2D p) {
        return p.getX() >= java.lang.Math.min(a.getX(), b.getX())
                && p.getX() <= java.lang.Math.max(a.getX(), b.getX())
                && p.getY() >= java.lang.Math.min(a.getY(), b.getY())
                && p.getY() <= java.lang.Math.max(a.getY(), b.getY());
    }
}
