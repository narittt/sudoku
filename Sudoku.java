/**
 * Sudoku.java
 * Narit Trikasemsak
 * CS231A
 * February 22, 2022
 * Project 3
 */

import java.util.Random;

public class Sudoku {
    Board board; 
    LandscapeDisplay display; 

    public Sudoku(){
        //constructor method to create sudoku object
        board = new Board(); 
        display = new LandscapeDisplay(board, 30);
    }


    public Sudoku(int N){
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
        //algorithm to find the first cell with a 0 value
        //loop through each cell
        for(int i = 0; i < 9; i++){
            for(int j = 0; j<9; j++){
                Cell current = board.get(i,j); 
                //check if cell has valid value, return if yes, else return null 
                if (current.getValue() == 0){
                    for (int k = 1; k<10; k++){
                        if(board.validValue(i, j, k)){
                            return current; 
                        }
                        
                    }
                    return null; 
                }
            }
        }
        return null; 
    }

    public boolean solve(int delay){
        //algorithm to use findBestCell and fill out sudoku board
        int locked = board.numLocked(); 
        int unspecified = Board.size * Board.size - locked; 
        CellStack stack = new CellStack(unspecified); 

        //while stack size less than unspecified
        while (stack.size() < unspecified){
            Cell next = findBestCell(); 
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
                //no more backtracking possible, return false
                if (stack.size() == 0){
                    return false; 
                }

            }
            //timer for delay
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
        //has a solution, return true
        return true; 
    }

    public String toString(){
        //method to return the board in the sudoku class as a string 
        return board.toString(); 
    }

    public static void main(String[] args){
        //main method to test methods in this class
        Sudoku test = new Sudoku(25);
        System.out.println(test.toString());
        boolean result1 = test.solve(30);
        System.out.println(test.toString());
        System.out.println(result1);

        // test.board.read("board1.txt"); 

        // boolean result = test.board.read("board1.txt");
        // System.out.println(test.toString());
        // if (!result) {
        //     System.out.println("Failure to load test2.txt");
        // } else {
        //     System.out.println("Trying!");
        //     boolean results = test.solve(100); 
        //     System.out.println("Solved!");
        //     System.out.println(test.toString());
        //     System.out.println(results);

        // }

    }
}
