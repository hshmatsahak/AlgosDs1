import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] linesegments;
    public FastCollinearPoints(Point[] points){
        if (points == null) throw new IllegalArgumentException();
        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = i + 1; j < n; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException();
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }
        Point p[] = points.clone();
        ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

        for (int i =0; i < points.length; i++){
            Point[] ps = p.clone();
            Arrays.sort(ps, ps[i].slopeOrder());
            int k = 1;
            while(k < points.length-2){
                int j = k;
                double slope1 = ps[0].slopeTo(ps[k++]);
                double slope2;
                do{
                    if (k == ps.length) {
                        k++;
                        break;
                    }
                    slope2 = ps[0].slopeTo(ps[k++]);
                }while (slope2 == slope1);
                if (k - j < 4){
                    k--;
                    continue;
                }
                int len = k-- - j;
                Point [] line = new Point[len];
                line[0] = ps[0];
                for (int l = 1; l < len; l++) {
                    line[l] = ps[j + l - 1];
                }
                Arrays.sort(line);
                if (line[0].equals(ps[0]))
                    segs.add(new LineSegment(line[0], line[len-1]));
            }
        }
        linesegments = segs.toArray(new LineSegment[segs.size()]);
    }

    public int numberOfSegments(){
        return linesegments.length;
    }

    public LineSegment[] segments(){
        return linesegments.clone();
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