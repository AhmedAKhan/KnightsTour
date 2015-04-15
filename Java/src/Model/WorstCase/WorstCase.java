package Model.WorstCase;

import Model.Model;

public class WorstCase implements Model{

    int[][] positions;

    public WorstCase(int row, int col){
        positions = new int[row][col];
        findPath();
    }

    private void findPath(){

        

    }


    @Override
    public int getValueAtPosition(int row, int col) {
        return positions[row][col];
    }
}//end class
