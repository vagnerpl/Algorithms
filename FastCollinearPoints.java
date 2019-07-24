import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: Point.java
 *
 *  Fast Collinear Point finder implementation.
 *
 ******************************************************************************/
public class FastCollinearPoints {

    /**
     * Internal list of line segments
     */
    private final List<LineSegment> segments;

    /**
     * finds all line segments containing 4 or more points
     * @param argPoints input data
     */
    public FastCollinearPoints(Point[] argPoints) {

        Point[] points = Arrays.copyOf(argPoints, argPoints.length);



        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point point: points) {
            if (point == null)
                throw new IllegalArgumentException();
        }

        segments = new LinkedList<>();

        if (points.length < 4) {
            return;
        }

        for (int i = 0; i < points.length; i++) {
            Point[] workingCopy = Arrays.copyOfRange(points, i+1, points.length);
            Arrays.sort(workingCopy, points[i].slopeOrder());

            double currentSlope = Double.NaN;
            Point min = null;
            Point max = null;
            int countColinear = -1;

            for (int j = 0; j < workingCopy.length; j++) {

                if (points[i] == workingCopy[j]) {
                    throw new IllegalArgumentException();
                }

                if (Double.compare(Double.NaN, currentSlope) == 0) {
                    currentSlope = points[i].slopeTo(workingCopy[j]);

                    min = workingCopy[j];
                    max = workingCopy[j];

                    if (points[i].compareTo(workingCopy[j]) < 0) {
                        min = points[i];
                    } else {
                        max = points[i];
                    }

                    countColinear = 2;
                } else if (Double.compare(points[i].slopeTo(workingCopy[j]), currentSlope) == 0) {
                    min = min(min, workingCopy[j]);
                    max = max(max, workingCopy[j]);
                    countColinear++;
                    if (j == workingCopy.length-1 && countColinear > 3) {
                        addSegment(new LineSegment(min, max));
                    }
                } else {
                    if (countColinear > 3) {
                        addSegment(new LineSegment(min, max));
                    }
                    currentSlope = points[i].slopeTo(workingCopy[j]);

                    min = workingCopy[j];
                    max = workingCopy[j];

                    if (points[i].compareTo(workingCopy[j]) < 0) {
                        min = points[i];
                    } else {
                        max = points[i];
                    }

                    countColinear = 2;
                }
            }
        }
    }

    private Point min(Point min, Point p) {
        if (p.compareTo(min) < 0)
            return p;
        return min;

    }

    private Point max(Point max, Point p) {
        if (p.compareTo(max) > 0)
            return p;
        return max;
    }

    /**
     * Add segments checking for duplication
     * @param segment segment to be added
     */
    private void addSegment(LineSegment segment) {
        for (LineSegment currentSegment: segments) {
            if (segment.toString().equals(currentSegment.toString())) {
                return;
            }
        }

        segments.add(segment);
    }

    /**
     *
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     *
     * @return the line segments
     */
    public LineSegment[] segments() {
        LineSegment[] segmentArray = new LineSegment[segments.size()];
        int i = 0;
        for (LineSegment lineSegment: segments) {
            segmentArray[i++] = lineSegment;
        }

        return segmentArray;
    }

    /**
     * Testing the implementations
     * @param args not used
     */
    public static void main(String... args) {
        Point[] points  = {
                new Point(2, 3), new Point(3, 5), new Point(4, 7),
                new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4),
                new Point(4, 6), new Point(4, 5)};



        FastCollinearPoints fcp = new FastCollinearPoints(points);

        System.out.println(fcp.numberOfSegments());
        LineSegment[] segments = fcp.segments();
        for (int i = 0; i < fcp.numberOfSegments(); i++) {
            System.out.println(segments[i]);
        }

    }
}
