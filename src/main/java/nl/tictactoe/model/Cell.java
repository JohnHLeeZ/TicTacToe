package nl.tictactoe.model;


import nl.tictactoe.enums.MARK;
import nl.tictactoe.enums.MARK;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author John
 */
public class Cell {
    private int row,col;
    private MARK mark;
    
   public Cell(int row, int col) {
      this.row = row;
      this.col = col;
      clear();  // clear content
   }
   
     public void clear() {
      mark = MARK.EMPTY;
   }

    public MARK getMark() {
        return mark;
    }

    public void setMark(MARK mark) {
        this.mark = mark;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    
     
 
   public void draw() {
       System.out.print(mark.toString());
   }
   
   @Override
   public String toString(){
       return this.mark.toString();
   }
}
