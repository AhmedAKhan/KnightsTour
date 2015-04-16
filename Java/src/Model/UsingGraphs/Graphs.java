package Model.UsingGraphs;

import Model.Model;

public class Graphs implements Model{

    private int[][] positions;
    private boolean finished;

    public Graphs(int row, int col){
        positions = new int[row][col];
        finished = false;
        findPath();
    }

    private void findPath(){
        //make a graph

        //use bellmond fords algorithm with these modifications
            // when you go through an edge it removes all edges going to the old node
            //
    }

    @Override public String getValueAtPosition(int row, int col) { return String.valueOf(positions[row][col]); }
}
