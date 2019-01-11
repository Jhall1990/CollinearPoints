import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] lines;
    private final ArrayList<Point[]> segments;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        segments = new ArrayList<>();

        for (int i = 0; i < points.length - 1; i++) {
            Point[] subArray = Arrays.copyOfRange(points, i + 1, points.length);
            SlopeObject[] slopes = createSlopeObjects(points[i], subArray);
            sort(slopes);
            findCollinear(slopes);
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

    private SlopeObject[] createSlopeObjects(Point origin, Point[] otherPoints) {
        // This function creates an array of SlopeObjects that will later be sorted.
        SlopeObject[] slopes = new SlopeObject[otherPoints.length];

        for (int i = 0; i < otherPoints.length; i++) {
            slopes[i] = new SlopeObject(origin, otherPoints[i]);
        }

        return slopes;
    }

    private void sort(SlopeObject[] a) {
        // This function sorts an array of slope objects based on their slopes.
        SlopeObject[] aux = new SlopeObject[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private void sort(SlopeObject[] a, SlopeObject[] aux, int lo, int hi) {
        // Overloaded sort function. Uses recursive merge sort to sort an array
        // of SlopeObjects based on their slope.
        if (hi <= lo) {
            return;
        }

        int mid = lo + (hi - lo) / 2;

        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);

    }

    private void merge(SlopeObject[] a, SlopeObject[] aux, int lo, int mid, int hi) {
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
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    private boolean less(SlopeObject s1, SlopeObject s2) {
        return s1.slope < s2.slope;
    }

    private void findCollinear(SlopeObject[] slopes) {
        double curSlope = slopes[0].slope;
        ArrayList<SlopeObject> curSegment = new ArrayList<>();
        curSegment.add(slopes[0]);

        for (int i = 1; i < slopes.length; i++) {
            if (Double.compare(curSlope, slopes[i].slope) == 0) {
                curSegment.add(slopes[i]);
            } else if (curSegment.size() >= 3) {
                addSegment(curSegment);
                curSegment = new ArrayList<>();
                curSegment.add(slopes[i]);
                curSlope = slopes[i].slope;
            } else {
                curSegment = new ArrayList<>();
                curSegment.add(slopes[i]);
                curSlope = slopes[i].slope;
            }
        }

        if (curSegment.size() >= 3) {
            addSegment(curSegment);
        }
    }

    private void addSegment(ArrayList<SlopeObject> segment) {
        Point min = segment.get(0).origin;
        Point max = segment.get(0).origin;

        for (SlopeObject s : segment) {
            if (min.compareTo(s.p) > 0)
                min = s.p;
            if (max.compareTo(s.p) < 0)
                max = s.p;
        }

        boolean add = true;

        for (Point[] p : segments) {
            if (p[0].compareTo(min) == 0 || p[1].compareTo(max) == 0) {
                add = false;
                break;
            }
        }

        if (add)
            segments.add(new Point[]{min, max});
    }

    private class SlopeObject {
        /*
        Class that keeps track of two points. An origin point an some other point p.
        It also stores the slope between the two points.
         */
        private final Point origin;
        private final Point p;
        private final double slope;

        SlopeObject(Point origin, Point p) {
            this.origin = origin;
            this.p = p;

            slope = origin.slopeTo(p);
        }

        public String toString() {
            return "O: " + origin + " P: " + p + " Slope: " + slope;
        }
    }
}
