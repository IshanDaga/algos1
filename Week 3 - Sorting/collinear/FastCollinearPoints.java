/* *****************************************************************************
 *  Name        : Fast Collinear Points
 *  @author     : Ishan Daga
 *  Date        : 20/7/21
 *  Description : A faster, sorting-based solution. Given a point p, the
 *               following method determines whether p participates in a set of
 *               4 or more collinear points.
 * Think of p as the origin.
 * For each other point q, determine the slope it makes with p.
 * Sort the points according to the slopes they makes with p.
 * Check if any 3 (or more) adjacent points in the sorted order have equal slopes
 * with respect to p. If so, these points, together with p, are collinear.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] segments;
    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
        if (points == null) throw new NullPointerException("Array cannot be null"); // argument cannot be null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Points cannot be null"); // no point can be null
        }
        Point[] copyOfPoints = points.clone();
        Arrays.sort(copyOfPoints); // sort the points
        for (int i = 0; i < copyOfPoints.length - 1; i++) {
            if (copyOfPoints[i].compareTo(copyOfPoints[i+1]) == 0) throw new IllegalArgumentException("Cannot have the same points"); // if any two points are the same
        }

        List<LineSegment> maximalSegments = new LinkedList<LineSegment>();

        int len = copyOfPoints.length;
        for (int i = 0; i < len; i++) { // loop through all points
            Point p = copyOfPoints[i]; // current point
            Point[] bySlope = copyOfPoints.clone(); // order by slope around current point
            Arrays.sort(bySlope, p.slopeOrder()); // this is a different order compared to copyOfPoints

            int current = 1; // index of current point in slope-ordered array
            while (current < len) { // loop till end of array
                LinkedList<Point> possibility = new LinkedList<Point>(); // List that stores points that may be part of a line segment
                double slopeReference = p.slopeTo(bySlope[current]); // reference slope from P to first point
                do {
                    possibility.add(bySlope[current++]); // add current point to possible line segment
                } while (current < len && p.slopeTo(bySlope[current]) == slopeReference); // add all points with the same slope
                // at least 4 points must be on the line to make the cut.
                // so minimum 3 points must be part of the possibility list
                // as Point 'p' is never added and is implicit in the current loop due to slope order.
                // to add only maximal line segments, P must also be the smallest point (by co-ordinates)
                if (possibility.size() >= 3 && p.compareTo(possibility.peek()) < 0) { // .peek() takes head (first) element [smallest point]
                    Point max = possibility.removeLast(); // pop last element in list [largest element]
                    maximalSegments.add(new LineSegment(p, max)); // p is the min point [smallest element of segment]
                }
            }
        }
        segments = maximalSegments.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() { // the number of line segments
        return segments.length;
    }

    public LineSegment[] segments() { // the line segments
        return segments.clone();
    }

    public static void main(String[] args) { // provided in assignment specification
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
