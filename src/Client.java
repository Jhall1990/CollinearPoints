import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Client {
    public static void main(String[] args) {

         // read the n points from a file
//         In in = new In(args[0]);
//         int n = in.readInt();
//         Point[] points = new Point[n];
//         for (int i = 0; i < n; i++) {
//             int x = in.readInt();
//             int y = in.readInt();
//             points[i] = new Point(x, y);
//         }

        Point[] points = new Point[9];
        points[0] = new Point(9000, 9000);
        points[1] = new Point(8000, 8000);
        points[2] = new Point(7000, 7000);
        points[3] = new Point(6000, 6000);
        points[4] = new Point(5000, 5000);
        points[5] = new Point(4000, 4000);
        points[6] = new Point(3000, 3000);
        points[7] = new Point(2000, 2000);
        points[8] = new Point(1000, 1000);

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints fast = new FastCollinearPoints(points);
//        BruteCollinearPoints brute = new BruteCollinearPoints(points);

        System.out.println("Fast:");
        for (LineSegment segment : fast.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }
}
