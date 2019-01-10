import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lines;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        lines = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            Point[] subArray = Arrays.copyOfRange(points, i + 1, points.length);
            SlopeObject[] slopes = createSlopeObjects(points[i], subArray);
            sort(slopes);
            findCollinear(slopes);
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return 0;
    }

    public LineSegment[] segments() {
        // the line segments
        return new LineSegment[0];
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
        System.out.println("DONE");
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
        // todo Need to find min and max before adding line.
        // todo Need to make sure the line hasn't already been added before adding.
        // todo probably a good idea to make a addLine function that takes in the current segment.

        double curSlope = slopes[0].slope;
        ArrayList<SlopeObject> curSegment = new ArrayList<>();

        for (int i = 1; i < slopes.length; i++) {
            if (Double.compare(curSlope, slopes[i].slope) == 0) {
                curSegment.add(slopes[i]);
            } else if (curSegment.size() >= 3) {
                LineSegment line = new LineSegment(curSegment.get(0).origin, curSegment.get(0).p);
                lines.add(line);
                curSegment = new ArrayList<>();
            } else {
                curSegment = new ArrayList<>();
                curSegment.add(slopes[i]);
                curSlope = slopes[i].slope;
            }
        }

        if (curSegment.size() >= 3) {
            LineSegment line = new LineSegment(curSegment.get(0).origin, curSegment.get(0).p);
            lines.add(line);
        }
    }

    class SlopeObject {
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
    }

    public static void main(String[] args) {
        Point[] p = new Point[10];
        p[0] = new Point(0, 10);
        p[1] = new Point(10, 20);
        p[2] = new Point(20, 30);
        p[3] = new Point(30, 40);
        p[4] = new Point(10, 0);
        p[5] = new Point(20, 0);
        p[6] = new Point(30, 0);
        p[7] = new Point(40, 0);
        p[8] = new Point(15, 15);
        p[9] = new Point(25, 25);

        FastCollinearPoints f = new FastCollinearPoints(p);
    }
}
