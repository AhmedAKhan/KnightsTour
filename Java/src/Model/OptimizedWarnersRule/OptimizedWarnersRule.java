package Model.OptimizedWarnersRule;

import Model.Model;
import javafx.geometry.Pos;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This is warners rule except it is optimized in a way where it expands from both sides.
 * This allows the program to be more efficient
 */

public class OptimizedWarnersRule implements Model{

    private Integer[][] positions;
    private boolean finished;

    public OptimizedWarnersRule(int row, int col){
        positions = new Integer[row][col];
        finished = false;
        findPath();
    }

    private void findPath(){
        positions[0][0] = 0;
        positions[2][1] = 1;
        positions[1][2] = -1;
        Position p = new Position(0,0, null);
        findPath(new Position(1,2,p), -2, new Position(2,1,p), 2);
    }
    private void findPath(Position pos1, int min, Position pos2, int max){
        Queue<Position> possibleMoves = new PriorityQueue<Position>();
        fillQueueWithPossiblePositions(possibleMoves, pos1);
        fillQueueWithPossiblePositions(possibleMoves, pos2);

        while(possibleMoves.size() != 0){
            Position currentPosition = possibleMoves.poll();
//            System.out.println("currentPosition: " + currentPosition);
            if(currentPosition.parent == pos1) {
                positions[currentPosition.row][currentPosition.col] = min;

                if(Math.abs(max) + Math.abs(min) >= positions.length*positions[0].length)
                {finished = true; return; }

                findPath(currentPosition, min-1, pos2, max);
            }else {
                positions[currentPosition.row][currentPosition.col] = max;
                findPath(pos1, min, currentPosition, max+1);
            }

            if(finished) return;

            positions[currentPosition.row][currentPosition.col] = null;

        }

    }

    private boolean isInBounds(Position position){
        if(position.row < 0 || position.col < 0) return false;
        if(position.row >= positions.length || position.col >= positions[position.row].length) return false;
        return true;
    }

    private Position getTheIthBestPosition(Position pos, int ithPosition){

        PriorityQueue<Position> allPossiblePositions = new PriorityQueue<Position>();
        for(int i = 0; i < 8; i++){
            Position currentPosition = getPositionAt(pos, i);
            if(!isInBounds(currentPosition)) continue; //check if position is out of bounds
            if(positions[currentPosition.row][currentPosition.col] != null) continue; //check if is empty
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
        private Position parent;

        public Position(int r, int c, Position parent){row = r; col = c; this.parent = parent;}
        public int numberOfPossibleMoves(){
            int result = 0;
            for(int i =0; i < 8; i++){
                Position currentPosition = getPositionAt(this, i);
                if(!isInBounds(currentPosition)) continue; //check if position is out of bounds
                if(positions[currentPosition.row][currentPosition.col] != null) continue; //check if is empty
                result++;
            }
            return result;
        }
        @Override public int compareTo(Position o) {
            int possibleMoves = numberOfPossibleMoves();
            int otherPossibleMoves = o.numberOfPossibleMoves();
            if(possibleMoves > otherPossibleMoves) return 1;
            if(possibleMoves < otherPossibleMoves) return -1;
            return 0;
        }
        @Override public String toString(){
            return "(row: " + row + " col: " + col+")";
        }
    }
    private Position getPositionAt(Position pos, int c){
        int row = pos.row;
        int col = pos.col;
        switch (c){
            case 0: return new Position(row+2, col+1, pos);
            case 1: return new Position(row+2, col-1, pos);
            case 2: return new Position(row-2, col+1, pos);
            case 3: return new Position(row-2, col-1, pos);
            case 4: return new Position(row+1, col+2, pos);
            case 5: return new Position(row-1, col+2, pos);
            case 6: return new Position(row+1, col-2, pos);
            case 7: return new Position(row-1, col-2, pos);
        }
        System.out.println("c is out of bounds in the getPositionAtTile function returning a c of 7");
        return new Position(row, col, pos);
    }
    private void fillQueueWithPossiblePositions(Queue<Position> possiblePositions, Position pos){
        for(int i = 0; i < 8; i++) {
            Position currentPos = getPositionAt(pos, i);
            if(!isInBounds(currentPos)) continue;
            if(positions[currentPos.row][currentPos.col] != null) continue;
            possiblePositions.add(currentPos);
        }
    }

    @Override
    public String getValueAtPosition(int row, int col) {
        return String.valueOf(positions[row][col]);
    }

}
