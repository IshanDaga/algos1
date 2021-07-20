/* *****************************************************************************
 *  Name:        Bruteforce Collinear Points
 *  Date:        17/7/21
 *  @author : Ishan Daga
 *  Description: examines 4 points at a time and checks whether they all lie on the same
 *               line segment, returning all such line segments. To check whether the 4
 *               points p, q, r, and s are collinear, check whether the three slopes
 *               between p and q, between p and r, and between p and s are all equal.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;
    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (points == null) throw new NullPointerException("Array cannot be null"); // argument cannot be null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Points cannot be null"); // no point can be null
        }
        Point[] copyOfPoints = points.clone();
        Arrays.sort(copyOfPoints); // sort the points
        for (int i = 0; i < copyOfPoints.length - 1; i++) {
            if (copyOfPoints[i].compareTo(copyOfPoints[i+1]) == 0) throw new IllegalArgumentException("Cannot have the same points"); // if any two points are the same
        }

        ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();
        for (int p = 0; p < copyOfPoints.length - 3; p++) {
            Point i = copyOfPoints[p];
            for (int q = p + 1; q < copyOfPoints.length - 2; q++) {
                Point j = copyOfPoints[q];
                for (int r = q + 1; r < copyOfPoints.length - 1; r++) {
                    Point k = copyOfPoints[r];
                    if (Double.compare(i.slopeTo(j), i.slopeTo(k)) == 0) {
                        for (int s = r + 1; s < copyOfPoints.length; s++) {
                            Point h = copyOfPoints[s];
                            if (Double.compare(i.slopeTo(h), i.slopeTo(k)) == 0) {
                                Point[] collinear = {i, j, k, h};
                                Arrays.sort(collinear);
                                segmentList.add(new LineSegment(collinear[0], collinear[3]));
                            }
                        }
                    }
                }
            }
        }
        segments = segmentList.toArray(new LineSegment[0]);
    }
    public int numberOfSegments() { // the number of line segments
        return segments.length;
    }
    public LineSegment[] segments() {
        return segments.clone();
    }               // the line segments

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
