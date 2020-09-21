/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tictactoe;

import java.util.List;
import nl.tictactoe.enums.GAMESTATE;
import nl.tictactoe.model.Cell;
import nl.tictactoe.model.Score;

/**
 *
 * @author John
 */
public class BoardResponse {

    private String name;
    private Cell[][] board;
    private int highScore = 0;
    private GAMESTATE gameState = GAMESTATE.PLAYING;
    private List<Score> scoreBoard;

    public BoardResponse(String name) {
        this.name = name;
        this.board = new Cell[3][3];
    }

    public BoardResponse(String name, Cell[][] board, int highScore, GAMESTATE gameState, List<Score> scoreBoard) {
        this.name = name;
        this.board = board;
        this.highScore = highScore;
        this.gameState = gameState;
        this.scoreBoard = scoreBoard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public GAMESTATE getGameState() {
        return gameState;
    }

    public void setGameState(GAMESTATE gameState) {
        this.gameState = gameState;
    }

    public List<Score> getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(List<Score> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
}
