import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

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

//        Point[] points = new Point[15];
//        points[0] = new Point(10000, 0);
//        points[1] = new Point(8000, 2000);
//        points[2] = new Point(2000, 8000);
//        points[3] = new Point(0, 10000);
//        points[4] = new Point(20000, 0);
//        points[5] = new Point(18000, 2000);
//        points[6] = new Point(2000, 18000);
//        points[7] = new Point(10000, 20000);
//        points[8] = new Point(30000, 0);
//        points[9] = new Point(0, 30000);
//        points[10] = new Point(20000, 10000);
//        points[11] = new Point(13000, 0);
//        points[12] = new Point(11000, 3000);
//        points[13] = new Point(5000, 12000);
//        points[14] = new Point(9000, 6000);

        Point[] points = new Point[10];
        points[0] = null;
        points[1] = null;
        points[2] = null;
        points[3] = null;
        points[4] = null;
        points[5] = null;
        points[6] = null;
        points[7] = null;
        points[8] = null;
        points[9] = null;

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
        BruteCollinearPoints brute = new BruteCollinearPoints(points);
        for (LineSegment segment : brute.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
