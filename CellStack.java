/**
 * CellStack.java
 * Narit Trikasemsak
 * CS231A
 * February 22, 2022
 * Project 3
 */


public class CellStack{
    public static final int CAPACITY = 1024; 
    private Cell[] data; 
    private int t = -1; 

    public CellStack(){
        //constructor method for cellstack with default capacity
        data = new Cell[CAPACITY];
    }

    public CellStack(int max) {
        //construct method for cellstack with max capacity
        data = new Cell[max]; 
    }

    public int size(){
        //accessor method to return size of staack
        int result = t + 1; 
        return result; 
    }

    public boolean empty(){
        //accesor method to check with stack is empty
        if (size() == 0){
            return true; 
        }
        else{
            return false; 
        }
    }

    public void push (Cell c) {
        //mutator method to add object Cell onto Stack
        if (size() == data.length){
            Cell[] copy = new Cell[data.length * 2];
            for (int i = 0; i < data.length; i++){
                copy[i] = data[i]; 
            }
            data = copy; 
            t++;
            copy[t] = c; 
        }
        else {
            t++; 
            data[t] = c; 
        }
    }

    public Cell pop(){
        //mutator method to remove Cell from stack
        if (empty()){
            return null; 
        }
        else{
            Cell r = data[t]; 
            data[t] = null; 
            t--;
            return r; 
        }
    }

    




}
