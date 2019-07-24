import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: Point.java
 *
 *  Brute Colliner Point finder implementation.
 *
 ******************************************************************************/
public class BruteCollinearPoints {

    /**
     * internal list of line segments
     */
    private final List<LineSegment> segments;

    /**
     * finds all line segments containing 4 points
     * @param argPoints input data
     */
    public BruteCollinearPoints(Point[] argPoints) {

        Point[] points = Arrays.copyOf(argPoints, argPoints.length);

        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point point: points) {
            if (point == null)
                throw new IllegalArgumentException();
        }

        /**
         * internal list of points
         */
        segments = new LinkedList<>();

        if (points.length < 4) {
            return;
        }

        Arrays.sort(points);

        for (int i = 0; i < points.length-3; i++) {
            // Validate corner cases

            for (int j = i + 1; j < points.length-2; j++) {
                // Validate corner cases
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }

                double slope1 = points[i].slopeTo(points[j]);

                for (int k = j + 1; k < points.length-1; k++) {

                    double slope2 = points[i].slopeTo(points[k]);
                    if (Double.compare(slope1, slope2) == 0) {
                        for (int m = k + 1; m < points.length; m++) {

                            double slope3 = points[i].slopeTo(points[m]);
                            if (Double.compare(slope1, slope3) == 0) {
                                addSegment(new LineSegment(points[i], points[m]));
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * Private method to add new line segments resizing the vector if needed
     * @param segment segment to be added
     */
    private void addSegment(LineSegment segment) {
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
     * Testing the solution
     * @param args not used
     */
    public static void main(String... args) {
        Point[] points  = {
                new Point(2, 3), new Point(3, 5), new Point(4, 7),
                new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4),
                new Point(4, 6), new Point(4, 5)};

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        System.out.println(bcp.numberOfSegments());
        LineSegment[] segments = bcp.segments();
        for (int i = 0; i < bcp.numberOfSegments(); i++) {
            System.out.println(segments[i]);
        }

    }

}
