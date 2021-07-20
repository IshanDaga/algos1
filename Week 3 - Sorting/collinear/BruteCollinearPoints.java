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

import java.util.Arrays;
public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int numberOfSegments;
    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException(); // argument cannot be null
        Point[] copyOfPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(copyOfPoints); // sort the points
        for (int i = 0; i < copyOfPoints.length - 1; i++) {
            if (copyOfPoints[i] == null) throw new IllegalArgumentException("Points cannot be null"); // no point can be null
            if (copyOfPoints[i].compareTo(copyOfPoints[i+1]) == 0) throw new IllegalArgumentException("Cannot have the same points"); // if any two points are the same
        }
        numberOfSegments = 0;
        segments = new LineSegment[points.length];
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
                                addSegement(i, j, k, h);
                            }
                        }
                    }
                }
            }
        }
    }
    public int numberOfSegments() { // the number of line segments
        return numberOfSegments;
    }
    public LineSegment[] segments() {
        return segments.clone();
    }               // the line segments

    private void addSegement(Point p, Point q, Point r, Point s) {
        Point[] collinear = {p, q, r, s};
        Arrays.sort(collinear);
        segments[numberOfSegments++] = new LineSegment(collinear[0], collinear[3]);
    }

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
