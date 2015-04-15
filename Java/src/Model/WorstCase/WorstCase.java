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
    private void findPath(int row, int col, int totalTurns){

        for(int turnsInSpot = 0; turnsInSpot < 8; turnsInSpot++){
            //get next position
            Position currentPosition = getPositionAt(row, col, turnsInSpot);

            //check if position is out of bounds
            if(!isInBounds(currentPosition)) continue;
            //check if is empty
            if(positions[currentPosition.row][currentPosition.col] != 0) continue;

            positions[currentPosition.row][currentPosition.col] = totalTurns+1;

            //base case
            if(totalTurns >= positions.length*positions[0].length) return;
        }
    }
    private boolean isInBounds(Position position){
        if(position.row < 0 || position.col < 0) return false;
        if(position.row > positions.length || position.col > positions[position.row].length) return false;
        return true;
    }

    class Position{
        private int row;
        private int col;
        public Position(int r, int c){row = r; col = c;}
    }
    private Position getPositionAt(int row, int col, int c){
        switch (c){
            case 0: return new Position(row+2, col+1);
            case 1: return new Position(row+2, col-1);
            case 2: return new Position(row-2, col+1);
            case 3: return new Position(row-2, col-1);
            case 4: return new Position(row+1, col+2);
            case 5: return new Position(row-1, col+2);
            case 6: return new Position(row+1, col-2);
            case 7: return new Position(row-1, col-2);
        }
        System.out.println("c is out of bounds in the getPositionAtTile function returning a c of 7");
        return new Position(row, col);
    }

    @Override
    public int getValueAtPosition(int row, int col) {
        return positions[row][col];
    }
}//end class
