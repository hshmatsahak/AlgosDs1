/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;

public class Board {
    private int size;
    private int[][] tiles;

    public Board (int [][] tiles){
        size = tiles.length;
        this.tiles = new int[size][size];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int dimension (){
        return size;
    }

    public int hamming(){
        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++){
                if (tiles[i][j] != i*size+j+1 && tiles[i][j] != 0) count++;
            }
        }
        //if(tiles[tiles.length-1][tiles.length-1] != 0) count--;
        return count;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                int num = tiles[i][j];
                if (tiles[i][j] != 0) {
                    int row = (tiles[i][j] - 1) / size;
                    int col = tiles[i][j] - row * size - 1;
                    int a = row-i; int b = col - j;
                    if (a>0) manhattan+=a;
                    else manhattan-=a;
                    if (b>0) manhattan+=b;
                    else manhattan-=b;
                }
            }
        }
        return manhattan;
    }

    public boolean equals (Object y){
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.size != that.size) return false;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (that.tiles[i][j] != this.tiles[i][j]) return false;
            }
        }
        return true;
    }

    public boolean isGoal(){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++){
                if (i == tiles.length-1 && j == i){
                    if (tiles[i][j] != 0) return false;
                }
                else if (tiles[i][j] != i*size+j+1) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors(){
        Stack<Board> boardstack = new Stack<>();
        int row = 0; int col = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) {row = i; col = j;}
            }
        }
        if (row > 0 && row < size-1 && col>0 && col < size-1){
            boardstack.push(neighbour(row, col, row+1, col));
            boardstack.push(neighbour(row, col, row, col+1));
            boardstack.push(neighbour(row, col, row-1, col));
            boardstack.push(neighbour(row, col, row, col-1));
        }
        else if (row == 0 && col == 0){
            boardstack.push(neighbour(row, col, row+1, col));
            boardstack.push(neighbour(row, col, row, col+1));
        }
        else if(row == 0 && col == size-1){
            boardstack.push(neighbour(row, col, row+1, col));
            boardstack.push(neighbour(row, col, row, col-1));
        }
        else if(row == size-1 && col == 0){
            boardstack.push(neighbour(row, col, row-1, col));
            boardstack.push(neighbour(row, col, row, col+1));
        }
        else if(row == size-1 && col == size-1){
            boardstack.push(neighbour(row, col, row-1, col));
            boardstack.push(neighbour(row, col, row, col-1));
        }
        else if(row == 0){
            boardstack.push(neighbour(row, col, row, col+1));
            boardstack.push(neighbour(row, col, row, col-1));
            boardstack.push(neighbour(row, col, row+1, col));
        }
        else if (row == size-1){
            boardstack.push(neighbour(row, col, row, col+1));
            boardstack.push(neighbour(row, col, row, col-1));
            boardstack.push(neighbour(row, col, row-1, col));
        }
        else if (col == 0){
            boardstack.push(neighbour(row, col, row+1, col));
            boardstack.push(neighbour(row, col, row-1, col));
            boardstack.push(neighbour(row, col, row, col+1));
        }
        else{
            boardstack.push(neighbour(row, col, row+1, col));
            boardstack.push(neighbour(row, col, row-1, col));
            boardstack.push(neighbour(row, col, row, col-1));
        }
        return boardstack;
    }

    private Board neighbour (int zrb, int zcb, int zra, int zca){
        int [][] neighbortiles = new int[tiles.length][tiles.length];
        for (int i= 0; i < tiles.length; i++){
            for(int j =0; j<tiles.length;j++){
                neighbortiles[i][j] = tiles[i][j];
            }
        }
        neighbortiles[zrb][zcb] = tiles[zra][zca];
        neighbortiles[zra][zca] = 0;
        return new Board(neighbortiles);
    }

    public Board twin(){
        int [][] twintiles = new int[tiles.length][tiles.length];
        for (int i= 0; i < tiles.length; i++){
            for(int j =0; j<tiles.length;j++){
                twintiles[i][j] = tiles[i][j];
            }
        }
        if (tiles[0][0] != 0 && tiles[0][1]!=0){
            twintiles[0][0] = tiles[0][1]; twintiles[0][1] = tiles[0][0];
        }
        else if (tiles[0][1] != 0 && tiles[1][0]!=0){
            twintiles[0][1] = tiles[1][0]; twintiles[1][0] = tiles[0][1];
        }
        else{
            twintiles[1][0] = tiles[0][0]; twintiles[0][0] = tiles[1][0];
        }
        return new Board(twintiles);
    }

    public static void main(String[] args) {
        /*System.out.println("hi\n\nkjkkkjk");
        int [][] test = {{1,2,3},{4,5,6},{7,8,1}};
        Board b = new Board(test);
        if (b.isGoal())
            System.out.println("hi");
        System.out.println (b.isGoal());*/
    }
}