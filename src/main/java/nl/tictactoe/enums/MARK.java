/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tictactoe.enums;

/**
 *
 * @author John
 */
public enum MARK {
    CROSS(" X "),
    NOUGHT(" O "),
    EMPTY("   ");
        
    private final String symbol;
    
    private MARK(String symbol){
        this.symbol = symbol;
    }
    
    @Override
    public String toString(){
        return symbol;
    }
}
