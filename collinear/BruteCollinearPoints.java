import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private LineSegment[] linesegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException();
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
        Point[] ps = points.clone();
        Arrays.sort(ps);
        ArrayList<LineSegment> ls = new ArrayList<>();
        for (int i = 0; i < ps.length - 3; i++) {
            Comparator<Point> c = ps[i].slopeOrder();
            for (int j = i + 1; j < ps.length - 2; j++) {
                for (int k = j + 1; k < ps.length - 1; k++) {
                    for (int l = k + 1; l < ps.length; l++){
                        if (c.compare(ps[j], ps[k]) == 0
                                && c.compare(ps[j], ps[l]) == 0)
                            ls.add(new LineSegment(ps[i], ps[l]));
                    }
                }
            }
        }
        linesegments = new LineSegment[ls.size()];
        linesegments = ls.toArray(linesegments);
    }

    public int numberOfSegments() {
        return linesegments.length;
    }

    public LineSegment[] segments() {
        return linesegments.clone();
    }

    public static void main (String [] args){
        Point p1 = new Point(20,4);
        Point[] arr = {p1, null};
        BruteCollinearPoints pts = new BruteCollinearPoints(arr);
    }
}