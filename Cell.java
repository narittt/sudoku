/**
 * Cell.java
 * Narit Trikasemsak
 * CS231A
 * February 22, 2022
 * Project 3
 */

import java.awt.Color;
import java.awt.Graphics;

 public class Cell {
     private int row;
     private int col;
     private int value;
     private boolean locked; 

    public Cell(){
        row = 0;
        col = 0;
        value = 0;
        locked = false; 
    }

    public Cell(int row, int col, int value){
        this.row = row;
        this.col = col; 
        this.value =value; 
        this.locked = false; 
    }

    public Cell (int row, int col, int value, boolean locked){
        this.row = row; 
        this.col = col;
        this.value = value;
        this.locked = locked; 
    }

    public int getRow(){
        return this.row; 
    }

    public int getCol(){
        return this.col; 
    }

    public int getValue(){
        return this.value; 
    }

    public void setValue(int value){
        this.value = value; 
    }

    public boolean isLocked(){
        return this.locked; 
    }

    public void setLocked(boolean locked){
        this.locked = locked; 
    }
    
    public Cell clone(){
        Cell result = new Cell(this.row, this.col, this.value, this.locked);
        return result; 

    }

    public String toString(){
        return(Integer.toString(this.value));
    }

    public void draw (Graphics g, int x0, int y0, int scale){
        //method to draw the cell 

        //change color of cell
        // if(isLocked()){
        //     g.setColor(Color.BLUE);
        // }
        // else{
        //     g.setColor(Color.BLACK);
        // }

        char[] arr = new char[2];
        arr[0] = (char)('0' + this.value); 
        // System.out.println(arr[0]);
        // arr[1] = (char)('0'); 
        g.drawChars(arr, 0, 1, x0*scale + (scale), y0*scale + (2*scale));
    }


 }