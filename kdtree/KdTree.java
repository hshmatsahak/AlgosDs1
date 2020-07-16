/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size;

    private class Node {
        private RectHV rect;
        private Point2D pt;
        private Node lb;
        private Node rt;

        public Node(RectHV rect, Point2D pt){
            this.rect = rect;
            this.pt = pt;
            lb = null;
            rt = null;
        }
    }

    public KdTree(){
        root = null;
        size = 0;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public int size(){
        return size;
    }

    public void insert(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, p, true, new RectHV(0,0,1,1));
    }

    private Node insert(Node x, Point2D p, boolean RED, RectHV r){
        if (x == null){
            size += 1;
            return new Node(r, p);
        }
        if (x.pt.equals(p)) return x;
        RectHV temp;
        double xrectxmin = x.rect.xmin();
        double xrectxmax = x.rect.xmax();
        double xrectymin = x.rect.ymin();
        double xrectymax = x.rect.ymax();
        if (RED) {
            double xptx = x.pt.x();
            if (p.x() < xptx){
                if (x.lb == null) {
                    temp = new RectHV(xrectxmin, xrectymin, xptx, xrectymax);
                    x.lb = insert(x.lb, p, false, temp);
                }else x.lb = insert(x.lb, p, false, x.lb.rect);
            }
            else{
                if (x.rt == null) {
                    temp = new RectHV(xptx, xrectymin, xrectxmax, xrectymax);
                    x.rt = insert(x.rt, p, false, temp);
                }else x.rt = insert(x.rt, p, false, x.rt.rect);
            }
        }
        else {
            double xpty = x.pt.y();
            if (p.y() < xpty){
                if (x.lb == null) {
                    temp = new RectHV(xrectxmin, xrectymin, xrectxmax, xpty);
                    x.lb = insert(x.lb, p, true, temp);
                }else x.lb = insert(x.lb, p, true, x.lb.rect);
            }
            else{
                if(x.rt == null) {
                    temp = new RectHV(xrectxmin, xpty, xrectxmax, xrectymax);
                    x.rt = insert(x.rt, p, true, temp);
                }
                else x.rt = insert(x.rt, p, true, x.rt.rect);
            }
        }
        return x;
    }

    public boolean contains (Point2D p){
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p, true);
    }

    private boolean contains (Node x, Point2D p, boolean RED){
        if (x == null) return false;
        if (x.pt.equals(p)) return true;
        if (RED){
            if (p.x() < x.pt.x()) return contains(x.lb, p, false);
            else return contains(x.rt, p, false);
        }
        else{
            if (p.y() < x.pt.y()) return contains(x.lb, p, true);
            else return contains(x.rt, p, true);
        }
    }

    public void draw(){
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        draw(root, true);
    }

    private void draw(Node x, boolean RED){
        if (x != null){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.pt.draw();
            StdDraw.setPenRadius();
            if (RED){
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.pt.x(), x.rect.ymin(), x.pt.x(), x.rect.ymax());
            }
            else{
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rect.xmin(), x.pt.y(), x.rect.xmax(), x.pt.y());
            }
            draw(x.lb, !RED);
            draw(x.rt, !RED);
        }
    }

    public Iterable<Point2D> range (RectHV rect){
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> points = new Stack<>();
        return range(root, rect, points);
    }

    private Iterable<Point2D> range (Node x, RectHV query, Stack<Point2D> points){
        if (x!=null && x.rect.intersects(query)){
            if (query.contains(x.pt)) points.push(x.pt);
            range(x.lb, query, points);
            range(x.rt, query, points);
        }
        return points;
    }

    public Point2D nearest (Point2D p){
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        return nearest(root, p, root.pt, true);
    }

    private Point2D nearest (Node x, Point2D p, Point2D currmin, boolean RED){
        if (x == null) return currmin;
        if (x.pt.distanceSquaredTo(p) < currmin.distanceSquaredTo(p))
            currmin = x.pt;
        if (RED){
            if (x.pt.x() > p.x()){
                if (x.lb!=null && x.lb.rect.distanceSquaredTo(p)<currmin.distanceSquaredTo(p))
                    currmin = nearest(x.lb, p, currmin, false);
                if (x.rt!=null && x.rt.rect.distanceSquaredTo(p)<currmin.distanceSquaredTo(p))
                    currmin = nearest(x.rt, p, currmin, false);
            }
            else{
                if (x.rt!=null && x.rt.rect.distanceSquaredTo(p)<currmin.distanceSquaredTo(p))
                    currmin = nearest(x.rt, p, currmin, false);
                if (x.lb!=null && x.lb.rect.distanceSquaredTo(p)<currmin.distanceSquaredTo(p))
                    currmin = nearest(x.lb, p, currmin, false);
            }
        }
        else{
            if (x.pt.y() > p.y()){
                if (x.lb!=null && x.lb.rect.distanceSquaredTo(p)<currmin.distanceSquaredTo(p))
                    currmin = nearest(x.lb, p, currmin, true);
                if (x.rt!=null && x.rt.rect.distanceSquaredTo(p)<currmin.distanceSquaredTo(p))
                    currmin = nearest(x.rt, p, currmin, true);
            }
            else{
                if (x.rt!=null && x.rt.rect.distanceSquaredTo(p)<currmin.distanceSquaredTo(p))
                    currmin = nearest(x.rt, p, currmin, true);
                if (x.lb!=null && x.lb.rect.distanceSquaredTo(p)<currmin.distanceSquaredTo(p))
                    currmin = nearest(x.lb, p, currmin, true);
            }
        }
        return currmin;
    }

    public static void main(String[] args) {
        KdTree t = new KdTree();
        t.insert(new Point2D(0.5,0.6));
        t.insert(new Point2D(0.3,0.7));
        t.insert(new Point2D(0.8,0.2));
        t.insert(new Point2D(0.35,0.6));
        t.insert(new Point2D(0.95,0.26));
        t.insert(new Point2D(0.36,0.86));
        t.insert(new Point2D(0.39,0.57));
        t.draw();
        Stack<Point2D> s = (Stack<Point2D>) t.range(new RectHV(0, 0, 0.4, 0.63));
        while (!s.isEmpty()){
            System.out.println(s.pop().x() + " " + s.pop().y());
        }
        Point2D n = t.nearest(new Point2D(0.8, 0.16));
        System.out.println(n.x() + " " + n.y());
        System.out.println(n.x() + " " + n.y());
    }
}
