/**
 * SudokuExtension.java
 * Narit Trikasemsak
 * CS231A
 * February 22, 2022
 * Project 3
 */

import java.util.Random;

public class SudokuExtension {
    Board board; 
    LandscapeDisplay display; 

    public SudokuExtension(){
        //constructor method to create sudoku object
        board = new Board(); 
        display = new LandscapeDisplay(board, 30);
    }


    public SudokuExtension(int N){
        //constructor method to create object filled with N valid random values
        int n = 0;
        Random ran = new Random();
        board = new Board(); 
        while (n < N){
            int row = ran.nextInt(9);
            int col = ran.nextInt(9); 
            int value = ran.nextInt(8) + 1; 
            Cell current = board.get(row, col); 
            if (current.getValue() == 0){
                boolean valid = board.validValue(row, col, value);
                if (valid){
                    board.set(row, col, value, true); 
                    n++; 
                }
            }
        }
        display = new LandscapeDisplay(board, 30);
    }

    public Cell findBestCell(){
        //algorithm to find the cell with the least valid values
        int lowRow = 0;
        int lowCol = 0;
        int lowestValid = 10; 
        //loop through all cells
        for (int i = 0; i<9; i++){
            for (int j = 0; j < 9; j++){
                int currentValids = 0; 
                Cell current = board.get(i,j); 
                //check for least valid values
                if (current.getValue() == 0){
                    for (int k = 1; k<10; k++){
                        if (board.validValue(i,j,k)){
                            currentValids++; 
                        }
                    }
                    if (currentValids == 0){
                        return null; 
                    }
                    else if (currentValids < lowestValid){
                        lowRow = i;
                        lowCol = j;
                        lowestValid = currentValids; 
                    }
                }
                
            }
        }
        //return least valid values
        return (board.get(lowRow, lowCol));
    }

    public boolean solve(int delay){
        //algorithm to use findBestCell and fill out sudoku board
        int locked = board.numLocked(); 
        int unspecified = Board.size * Board.size - locked; 
        CellStack stack = new CellStack(unspecified); 

        //while stack size less than unspecified
        while (stack.size() < unspecified){
            Cell next = findBestCell(); 
            // System.out.println(next);
            // System.out.println(next.getRow() + ", " + next.getCol());
            //if there is a valid next cell
            if (next != null){
                //push the cell onto the stack
                stack.push(next);
                //update the board
                int row = next.getRow();
                int col = next.getCol(); 
                int value = next.getValue(); 
                for(int k = next.getValue()+1; k<10; k++){
                    if (board.validValue(row, col, k)){
                        value = k; 
                        board.set(row, col, value); 
                        break; 
                    }
                }
                
            }
            //else
            else{

                boolean stuck = true; 
                //while it is possible and necessary to backtrack
                while(stuck && stack.size() != 0){
                    //pop a cell off the stack
                    Cell popped = stack.pop();
                    int row = popped.getRow(); 
                    int col = popped.getCol(); 
                    boolean moreValues = false; 
                    int value = popped.getValue(); 
                    //check if there is another valid untested value for this cell
                    for (int i = popped.getValue() + 1; i < 10; i++){
                        if (board.validValue(row, col, i)){
                            value = i;  
                            moreValues = true;
                            break; 
                        }
                    }
                    //if there is
                    if (moreValues){
                        //push the cell with the new value onto the stack
                        popped.setValue(value);
                        stack.push(popped);
                        //update the board
                        board.set(row, col, value);
                        stuck = false; 
                        break; 
                    }
                    //else set the value to zero
                    else{
                        board.set(row, col, 0);
                    }
                    
                }
                //stack size is zero, backtracking not possible
                if (stack.size() == 0){
                    return false; 
                }

            }
            //add delay for visualization
            if( delay > 0 ) {
                try {
                    Thread.sleep(delay);
                }
                catch(InterruptedException ex) {
                    System.out.println("Interrupted");
                }
                display.repaint();
            }        
        }
        return true; 

    }

    public String toString(){
        return board.toString(); 
    }

    public static void main(String[] args){
        //main method to test methods in this class
        SudokuExtension test = new SudokuExtension(20);
        System.out.println(test.toString());
        boolean result1 = test.solve(100);
        System.out.println(test.toString());
        System.out.println(result1);
    }
}
