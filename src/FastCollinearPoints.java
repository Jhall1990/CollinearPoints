import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private final LineSegment[] lines;
    private final ArrayList<Point[]> segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array must not be null");
        } else if (points[0] == null) {
            throw new IllegalArgumentException("No point in points array can be null");
        }

        // finds all line segments containing 4 or more points
        segments = new ArrayList<>();

        for (int i = 0; i < points.length - 1; i++) {
            sort(points[i], points);
            findCollinear(points);
        }

        lines = new LineSegment[segments.size()];

        for (int i = 0; i < segments.size(); i++) {
            lines[i] = new LineSegment(segments.get(i)[0], segments.get(i)[1]);
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return lines.length;
    }

    public LineSegment[] segments() {
        // the line segments
        return Arrays.copyOf(lines, lines.length);
    }

    private void sort(Point origin, Point[] a) {
        // This function sorts an array of slope objects based on their slopes.

        // Create an aux array that is a copy of <a>.
        Point[] aux = new Point[a.length];
        sort(origin, a, aux, 0, a.length - 1);
    }

    private void sort(Point origin, Point[] a, Point[] aux, int lo, int hi) {
        // Overloaded sort function. Uses recursive merge sort to sort an array
        // of SlopeObjects based on their slope.
        if (hi <= lo) {
            return;
        }

        int mid = lo + (hi - lo) / 2;

        sort(origin, a, aux, lo, mid);
        sort(origin, a, aux, mid + 1, hi);
        merge(origin, a, aux, lo, mid, hi);

    }

    private void merge(Point origin, Point[] a, Point[] aux, int lo, int mid, int hi) {
        // Merges an array <a> and auxiliary array <aux>.
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(origin, aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    private boolean less(Point origin, Point p1, Point p2) {
        // Get the slope from the origin point to p1 and p2. Then
        // compare them. Return True if p1's slope is less than p2's.
        if (origin == null || p1 == null || p2 == null)
            throw new IllegalArgumentException("No point in points array can be null");

        double p1Slope = origin.slopeTo(p1);
        double p2Slope = origin.slopeTo(p2);
        return p1Slope < p2Slope;
    }

    private void findCollinear(Point[] points) {
        // This function iterates over each point in the point array and checks
        // It's slope against the current slope. It builds segments and checks if
        // they needed to be added by calling addSegment().
        Point origin = points[0];

        if (origin == null)
            throw new IllegalArgumentException("No point in points array can be null");

        Point min = origin;
        Point max = origin;
        ArrayList<Point> curSegment = new ArrayList<>();
        Comparator<Point> slopeCompare = origin.slopeOrder();

        for (int i = 1; i < points.length; i++) {
            Point p = points[i];
            double s = origin.slopeTo(p);

            // Check the points slope against the current slope. If it's the same
            // add the point to the current segment.
            if (origin.compareTo(p) == 0)
                throw new IllegalArgumentException("Points array cannot have duplicate entries.");
            else if (curSegment.size() > 0 && slopeCompare.compare(curSegment.get(0), p) == 0) {
                curSegment.add(p);

                if (min.compareTo(p) > 0)
                    min = p;
                else if (max.compareTo(p) < 0)
                    max = p;

                // If the slope is different and the current segment has more than 3
                // entries add it to the segment array (if needed). Then create a new
                // current segment array and add the current point. Then update the
                // current slope.
            } else {
                if (curSegment.size() >= 3)
                    if (origin.compareTo(min) < 0 || origin.compareTo(max) > 0)
                        segments.add(new Point[]{min, max});

                curSegment = new ArrayList<>();
                curSegment.add(p);
                min = p;
                max = p;
            }
        }

        // If the last point matches the current slope it won't be added
        // to the segments array. This is a final check to see if the current
        // segment is long enough to be added.
        if (curSegment.size() >= 3)
            if (origin.compareTo(min) < 0 || origin.compareTo(max) > 0)
                segments.add(new Point[]{min, max});
    }
}
