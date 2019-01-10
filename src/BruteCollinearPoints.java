import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<Point[]> segments;
    private final LineSegment[] lines;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("points array must not be null");
        }

        segments = new ArrayList<>();



        if (points.length < 4) {
            validateSmallPointArray(points);
        }

        for (int p = 0; p < points.length-3; p++) {
            for (int q = p + 1; q < points.length-2; q++) {
                for (int r = q + 1; r < points.length-1; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        checkNull(points[p]);
                        checkNull(points[q]);
                        checkNull(points[r]);
                        checkNull(points[s]);
                        checkDuplicate(points[p], points[q]);
                        checkDuplicate(points[p], points[r]);
                        checkDuplicate(points[q], points[r]);
                        checkDuplicate(points[p], points[s]);
                        checkDuplicate(points[q], points[s]);
                        checkDuplicate(points[r], points[s]);

                        double slopeQ = points[p].slopeTo(points[q]);
                        double slopeR = points[p].slopeTo(points[r]);
                        double slopeS = points[p].slopeTo(points[s]);

                        if (Double.compare(slopeQ, slopeR) == 0 && Double.compare(slopeQ, slopeS) == 0) {
                            Point[] minMaxPoints = getMinMax(points[p], points[q], points[r], points[s]);
                            if (addSegment(minMaxPoints)) {
                                segments.add(minMaxPoints);
                            }
                        }
                    }
                }
            }
        }

        lines = new LineSegment[segments.size()];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new LineSegment(segments.get(i)[0], segments.get(i)[1]);
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() {
        // the line segments
        return Arrays.copyOf(lines, lines.length);
    }

    private boolean addSegment(Point[] minMaxPoints) {
        for (Point[] p : segments) {
            if (p[0] == minMaxPoints[0] && p[1] == minMaxPoints[1]) {
                return false;
            }
        }

        return true;
    }

    private Point[] getMinMax(Point p, Point q, Point r, Point s) {
        Point min = p;
        Point max = p;

        if (min.compareTo(q) > 0)
            min = q;
        if (min.compareTo(r) > 0)
            min = r;
        if (min.compareTo(s) > 0)
            min = s;

        if (max.compareTo(q) < 0)
            max = q;
        if (max.compareTo(r) < 0)
            max = r;
        if (max.compareTo(s) < 0)
            max = s;

        return new Point[]{min, max};
    }

    private void checkNull(Point p) {
        if (p == null) {
            throw new IllegalArgumentException("No point in points array should be null.");
        }
    }

    private void checkDuplicate(Point p1, Point p2) {
        if (p1.compareTo(p2) == 0) {
            throw new IllegalArgumentException("No points should be the same.");
        }
    }

    private void validateSmallPointArray(Point[] points) {
        for (Point p : points) {
            checkNull(p);
        }

        if (points.length == 2) {
            checkDuplicate(points[0], points[1]);
        } else if (points.length == 3) {
            checkDuplicate(points[0], points[1]);
            checkDuplicate(points[0], points[2]);
            checkDuplicate(points[1], points[2]);
        }
    }
}
