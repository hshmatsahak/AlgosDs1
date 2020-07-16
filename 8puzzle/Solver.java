/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public final class Solver {
    private boolean solvable;
    private int moves_start_to_goal;
    private Node goal;

    private class Node {
        private int moves;
        private Board board;
        private Node prev;
        private int manhattan;
        public Node(Board board){
            moves = 0;
            prev = null;
            this.board = board;
            manhattan = board.manhattan();
        }
    }

    private class manhattan_order implements Comparator<Node>{
        public int compare(Node a, Node b){
            return a.moves+a.manhattan-b.moves-b.manhattan;
        }
    }

    public Solver(Board initial){
        if (initial == null) throw new IllegalArgumentException();
        manhattan_order pqorder = new manhattan_order();
        MinPQ<Node> pq = new MinPQ<>(pqorder);
        MinPQ<Node> pqtwin = new MinPQ<>(pqorder);
        Node init_node = new Node(initial);
        Node init_node_twin = new Node(init_node.board.twin());
        pq.insert(init_node);
        pqtwin.insert(init_node_twin);
        Node removed = pq.delMin();
        Node removed_twin = pqtwin.delMin();
        while(!(removed.board.isGoal()) && !(removed_twin.board.isGoal())){
            //System.out.println("hey\n");
            Iterable<Board> neighbours = removed.board.neighbors();
            Iterable<Board> neighbours_twin = removed_twin.board.neighbors();
            for (Board n : neighbours){
                //System.out.println("tut\n");
                Node temp = new Node(n);
                temp.moves = removed.moves+1;
                temp.prev = removed;
                if (removed.prev == null || (removed.prev!=null && !temp.board.equals(removed.prev.board)))
                    pq.insert(temp);
            }
            for (Board nt : neighbours_twin){
                //System.out.println("put\n");
                Node temp_twin = new Node(nt);
                temp_twin.moves = removed_twin.moves+1;
                temp_twin.prev = removed_twin;
                if (removed_twin.prev==null || (removed_twin.prev != null && !temp_twin.board.equals(removed_twin.prev.board)))
                    pqtwin.insert(temp_twin);
            }
            removed = pq.delMin();
            removed_twin = pqtwin.delMin();
        }
        if (removed.board.isGoal()){
            solvable = true;
            moves_start_to_goal = removed.moves;
            goal = removed;
        }
        else{
            solvable = false;
            moves_start_to_goal = -1;
            goal = null;
        }
    }

    public boolean isSolvable(){
        return solvable;
    }

    public int moves(){
        return moves_start_to_goal;
    }

    public Iterable<Board> solution() {
        if (!solvable) return null;
        Stack<Board> sequence = new Stack<>();
        Node temp = goal;
        while (temp != null){
            sequence.push(temp.board);
            temp = temp.prev;
        }
        return sequence;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}