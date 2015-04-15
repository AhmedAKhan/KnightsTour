package Model.WanrersRule;

import Model.Model;

import java.util.Iterator;
import java.util.PriorityQueue;

public class WarnsdorffRule implements Model{

    private int[][] positions;
    private boolean finished;


    public WarnsdorffRule(int row, int col){
        positions = new int[row][col];
        finished = false;
        findPath();
    }

    private void findPath(){
        //all the corners have the least possible moves at the begining so its going to chose that
        for(int startRow = 0; startRow < positions.length; startRow++){
            for(int startCol = 0; startCol < positions[startRow].length; startCol++){
                positions[startRow][startCol] = 1;
                findPath(startRow, startCol, 2);
                if(finished) return;
                positions[startRow][startCol] = 0;
            }
        }
    }
    private void findPath(int row, int col, int totalTurns){

        for(int turnsInSpot = 0; turnsInSpot < 8; turnsInSpot++){
            //get next position
            Position currentPosition = getTheIthBestPosition(row, col, turnsInSpot);

            if(currentPosition == null) continue;
//            if(!isInBounds(currentPosition)) continue; //check if position is out of bounds
//            if(positions[currentPosition.row][currentPosition.col] != 0) continue; //check if is empty

            positions[currentPosition.row][currentPosition.col] = totalTurns;

            //base case
            if(totalTurns >= positions.length*positions[0].length){
                finished = true;
                return;
            }
            findPath(currentPosition.row, currentPosition.col, totalTurns+1);

            if(finished) return;

            positions[currentPosition.row][currentPosition.col] = 0;
        }
    }
    private boolean isInBounds(Position position){
        if(position.row < 0 || position.col < 0) return false;
        if(position.row >= positions.length || position.col >= positions[position.row].length) return false;
        return true;
    }

    private Position getTheIthBestPosition(int row, int col, int ithPosition){
//        Position[] allPossiblePositions = new Position[8];
        PriorityQueue<Position> allPossiblePositions = new PriorityQueue<Position>();
        for(int i = 0; i < 8; i++){
            Position currentPosition = getPositionAt(row, col, i);
            if(!isInBounds(currentPosition)) continue; //check if position is out of bounds
            if(positions[currentPosition.row][currentPosition.col] != 0) continue; //check if is empty
            allPossiblePositions.add(currentPosition);
        }

        Iterator<Position> iterator = allPossiblePositions.iterator();
        Position result = null;
        int currentTurnNumber = 0;
        while(iterator.hasNext()){
            if(currentTurnNumber != ithPosition) iterator.next();
//                System.out.println("printing: " + iterator.next());
            else
            { result = iterator.next(); break; }
        }

        return result;
    }

    class Position implements Comparable<Position> {
        private int row;
        private int col;
        public Position(int r, int c){row = r; col = c;}
        public int numberOfPossibleMoves(){
            int result = 0;
            for(int i =0; i < 8; i++){
                Position currentPosition = getPositionAt(row, col, i);
                if(!isInBounds(currentPosition)) continue; //check if position is out of bounds
                if(positions[currentPosition.row][currentPosition.col] != 0) continue; //check if is empty
                result++;
            }
            return result;
        }
        @Override
        public int compareTo(Position o) {
            int possibleMoves = numberOfPossibleMoves();
            int otherPossibleMoves = o.numberOfPossibleMoves();
            if(possibleMoves > otherPossibleMoves) return 1;
            if(possibleMoves < otherPossibleMoves) return -1;
            return 0;
        }
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

}
