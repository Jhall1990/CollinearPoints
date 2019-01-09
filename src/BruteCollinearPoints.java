import java.util.ArrayList;

public class BruteCollinearPoints {
    private final ArrayList<Point[]> segments;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("points array must not be null");
        }

        segments = new ArrayList<>();

        for (int p = 0; p < points.length-3; p++) {
            for (int q = 1; q < points.length-2; q++) {
                for (int r = 2; r < points.length-1; r++) {
                    for (int s = 3; s < points.length; s++) {
                        if (points[p] == null || points[q] == null || points[r] == null || points[s] == null) {
                            throw new IllegalArgumentException("No point in points array should be null.");
                        }

                        double slopeQ = points[p].slopeTo(points[q]);
                        double slopeR = points[p].slopeTo(points[r]);
                        double slopeS = points[p].slopeTo(points[s]);

                        if (Double.compare(slopeQ, slopeR) == 0 && Double.compare(slopeQ, slopeS) == 0) {
                            if (addSegment(points[p], points[q])) {
                                Point[] pointArr = {points[p], points[q]};
                                segments.add(pointArr);
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() {
        // the line segments
        LineSegment[] lines = new LineSegment[segments.size()];

        for (int i = 0; i < lines.length; i++) {
            lines[i] = new LineSegment(segments.get(i)[0], segments.get(i)[1]);
        }

        return lines;
    }

    private boolean addSegment(Point p1, Point p2) {
        for (Point[] p : segments) {
            if (Double.compare(p1.slopeTo(p2), p[0].slopeTo(p[1])) == 0) {
                return false;
            }
        }

        return true;
    }
}
