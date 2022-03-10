/**
 * Board.java
 * Narit Trikasemsak
 * CS231A
 * February 22, 2022
 * Project 3
 */


import java.io.*;
import java.awt.Color;
import java.awt.Graphics; 

public class Board {
     
    private long start = System.nanoTime(); 
    private Cell[][] board; 
    public static final int size = 9; 

    public Board(){
        //constructor method to initialize the board
        this.board = new Cell[Board.size][Board.size];
        //loops through and initializes a cell with a value of zero at each point
        for (int i = 0; i<Board.size; i++){
            for (int j = 0; j<Board.size; j++){
                board[i][j] = new Cell(i, j, 0); 
            }
        }
    }

    public String toString(){
        //method to print the board as a string
        String result = ""; 
        //loop through board
        for (int i = 0; i<Board.size; i++){
            for (int j = 0; j<Board.size; j++){
                Cell print = board[i][j];
                result += print.toString(); 
                //space between third columns
                if ((j+1)%3 == 0){
                  result += "   ";
                }
            }
            //check if space between third rows
            result += "\n";
            if ((i+1)%3 == 0){
              result += "\n"; 
            }
        }
        return result; 
    }

    public int getCols(){
      //accessor method to reuturn number of columns
      return Board.size; 
    }

    public int getRows(){
      //accessor method to return number of rows
      return Board.size; 
    }

    public Cell get (int r, int c){
      //accessor method to return cell at row c and col c
      Cell result = board[r][c];
      return result; 
    }

    public boolean isLocked(int r, int c){
      //accessor method to return if cell at r,c is locked
      Cell result = board[r][c];
      return result.isLocked(); 
    }

    public int numLocked(){
      //method to return the number of locked cells on the board
      int locked = 0; 
      for (int i = 0; i<Board.size; i++){
        for (int j =0; j<Board.size; j++){
          Cell current = board[i][j];
          if(current.isLocked()){
            locked+=1; 
          }
        }
      }
      return locked; 
    }

    public int value(int r, int c){
      //accessor method to return the value of cell at point r c
      Cell result = board[r][c];
      return result.getValue(); 
      }

    public void set(int r, int c, int value){
      //mutator method to set value of cell at r,c to value
      board[r][c].setValue(value);
    } 

    public void set(int r, int c, int value, boolean locked){
      //mutator method to set value and locked stataus of cell at r,c to value and locked
      board[r][c].setValue(value);
      board[r][c].setLocked(locked); 
    }

    public boolean read(String filename) {
        //method to read the inputed file and add it to the board

        try {
          int i = 0; 
          // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
          FileReader reader = new FileReader(filename); 
          // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
          BufferedReader bReader = new BufferedReader(reader);
          // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
          String line = bReader.readLine(); 
          // start a while loop that loops while line isn't null
          while(line != null){
            String[] array = line.split("[ ]+"); 
            for (int j = 0; j<Board.size; j++){
              if (Integer.parseInt(array[j]) != 0){
                set(i, j, Integer.parseInt(array[j]), true);
            }
          }
            i++;
            // System.out.println(line);
            // System.out.println("Size: " + line.length()); 
            line = bReader.readLine(); 
          }
          bReader.close();
          return true; 
              // assign to an array of type String the result of calling split on the line with the argument "[ ]+"
              // print the String (line)
              // print the size of the String array (you can use .length)
              // assign to line the result of calling the readLine method of your BufferedReader object.
          // call the close method of the BufferedReader
          // return true
        }
        catch(FileNotFoundException ex) {
          System.out.println("Board.read():: unable to open file " + filename );
        }
        catch(IOException ex) {
          System.out.println("Board.read():: error reading file " + filename);
        }
    
        return false;
      }

    public boolean validValue(int row, int col, int value){
      //method to return whether the given value at row and col is valid in Sudoku 

      int startRow = row%3; 
      startRow = row - startRow; 
      // System.out.println("Start: " + startRow);

      int startCol = col%3; 
      startCol = col - startCol; 
      // System.out.println("Start Col: " + startCol);
      //check if value is valid sudoku value
      if (value<1 || value>9){
        System.out.println("not in range");
        return false;
      }
      //check against the column
      for (int i = 0; i<9; i++){
        Cell current = board[i][col];
        if (i != row){
          if(value == current.getValue()){
            // System.out.println(i +"," + j +"," + rn.getValue());
            return false; 
          }
        }
      }
      //check against the row
      for (int i = 0; i<9; i++){
        Cell current = board[row][i];
        if (i != col){
          if(value == current.getValue()){
            // System.out.println(i +"," + j +"," + rn.getValue());
            return false; 
          }
        }
      }
      for (int i = startRow; i<startRow+3; i++){
        for (int j = startCol; j<startCol+3; j++){
          Cell rn = board[i][j]; 
          // System.out.println(i +"," + j +"," + rn.getValue()); 
          if (i != row || j!= col){
            if(value == rn.getValue()){
              // System.out.println(i +"," + j +"," + rn.getValue());
              return false; 
            }
          }
        }
      }
      
    return true; 
    }

    public boolean validSolution(){
      //method to return true if the board is solved

      for (int i = 0; i<Board.size; i++){
        for (int j = 0; j<Board.size; j++){
          Cell current= board[i][j]; 
          if (current.getValue() == 0){
            // System.out.println(i + "," + j);
            return false; 
          }
          // System.out.println("current " + current.getValue());
          if(validValue(i,j,current.getValue()) == false){
            // System.out.println(i + "-" + j);
            return false;
          }
        }
      }
      return true; 
    }

    
    
    public void draw (Graphics g, int scale){
      //method to draw all cells on the board

      //draw title
      g.drawString("Sudoku!", 4*scale + 10, 20);
      
      //draw seperators
      g.drawLine(3*scale + 20, scale + 15 , 3*scale + 20, 10*scale + 10);
      g.drawLine(6*scale + 20, scale + 15 , 6*scale + 20, 10*scale + 10);
      g.drawLine(scale, 4*scale + 12 , 9*scale + 15, 4*scale + 12);
      g.drawLine(scale, 7*scale + 12 , 9*scale + 15, 7*scale + 12);
      
      //draw time
      long finish = System.nanoTime(); 
      long time = finish - start; 
      time = time/1000000; 
      String print = "Time Elapsed: " + Long.toString(time) + " ms"; 
      g.drawString(print, 3*scale, 11*scale + 10);
      
      //draw numbers
      for (int i = 0; i < Board.size; i++){
        for (int j = 0; j< Board.size; j++){
          Cell current = board[i][j];
          
          //change cell color dpeending on state of cell
          if(current.isLocked()){
            g.setColor(Color.BLUE);
            current.draw(g, i, j, scale);
          }
          else if(current.getValue() == 0){
            g.setColor(Color.RED);
            current.draw(g, i, j, scale);
          }
          else{
            g.setColor(Color.BLACK);
            current.draw(g, i, j, scale);
          }
           
        }
      }
    }

    public static void main(String[] args){
      //main method to test read function 
      Board test = new Board();
      System.out.println(test.toString());
      test.read(args[0]);
    } 
    
}
