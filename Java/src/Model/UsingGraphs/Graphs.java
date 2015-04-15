package Model.UsingGraphs;

import Model.Model;

import java.util.Iterator;
import java.util.PriorityQueue;

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

    @Override
    public int getValueAtPosition(int row, int col) { return positions[row][col]; }
}
