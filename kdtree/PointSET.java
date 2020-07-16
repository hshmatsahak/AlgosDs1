/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> tree;

    public PointSET(){
        tree = new SET<>();
    }

    public boolean isEmpty(){
        return tree.isEmpty();
    }

    public int size(){
        return tree.size();
    }

    public void insert(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        tree.add(p);
    }

    public boolean contains (Point2D p){
        if (p == null) throw new IllegalArgumentException();
        return tree.contains(p);
    }

    public void draw(){
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : tree){
            p.draw();
        }
    }

    public Iterable<Point2D> range (RectHV rect){
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> points = new Stack<>();
        for (Point2D p : tree){
            if (rect.contains(p)) points.push(p);
        }
        return points;
    }

    public Point2D nearest (Point2D p){
        if (p == null) throw new IllegalArgumentException();
        Point2D temppt = null;
        double distance = 1.5;
        double temp = 0;
        for (Point2D e : tree){
            temp = p.distanceSquaredTo(e);
            if (temp < distance){
                distance = temp;
                temppt = e;
            }
        }
        return temppt;
    }

    public static void main(String[] args) {

    }
}
