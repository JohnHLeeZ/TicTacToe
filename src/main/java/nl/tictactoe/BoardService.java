package nl.tictactoe;

import nl.tictactoe.enums.MARK;
import nl.tictactoe.enums.GAMESTATE;
import nl.tictactoe.model.Cell;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import nl.tictactoe.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author John
 */
@Component
public class BoardService {

    private Cell[][] board;
    private String playerName;
    private List<Score> scoreBoard = new ArrayList<>();
    private MARK playerSymbol = MARK.CROSS;
    private int BOARDLENGTH = 3;
    private GAMESTATE currentState = GAMESTATE.PLAYING;
    private int turn = 0;
    private int score;
    private StopWatch stopWatch;

    @Autowired
    public BoardService() {
        createBoard();
    }

    public void createBoard() {
        this.board = new Cell[BOARDLENGTH][BOARDLENGTH];
        for (int row = 0; row < BOARDLENGTH; ++row) {
            for (int col = 0; col < BOARDLENGTH; ++col) {
                board[row][col] = new Cell(row, col);
            }
        }
    
    }

    public GAMESTATE checkGameState(int row, int column) {

        this.board[row][column].setMark(playerSymbol);

        if (this.hasWon(playerSymbol, row, column)) {
            return GAMESTATE.PLAYER_WON;
        }

        if (this.currentState == GAMESTATE.PLAYING) {
            MARK aiMark = (this.playerSymbol == MARK.CROSS) ? MARK.NOUGHT : MARK.CROSS;
            Cell cell = dumbMoveAI(aiMark);
            if (this.hasWon(aiMark, cell.getRow(), cell.getCol())) {
                return GAMESTATE.AI_WON;
            }
        }

        if (this.isDraw()) {
            return GAMESTATE.DRAW;
        }
        return GAMESTATE.PLAYING;
    }

    public void calculateScore() {
        double score = 0;
        double maxTurnScore = 60;
        double maxTimeScore = 40;

        if (turn > 3) {
            double points = maxTurnScore / turn;
            score += points;
        } else {
            score += maxTurnScore;
        }

        if (this.stopWatch.getTotalTimeSeconds() < 300) {
            
            double milisec = this.stopWatch.getTotalTimeSeconds();
            double points = 1 - (milisec / maxTimeScore);
            double finalScore = points * maxTimeScore;
            score += finalScore;
        }
        this.score = (int) (score * 100);
        scoreBoard.add(new Score(playerName, this.score));
    
    }

    private boolean hasWon(MARK mark, int row, int column) {
        for (int c = 0; c < BOARDLENGTH; c++) { // check 3 in a row
            if (board[row][c].getMark() != mark) {
                break;
            }
            if (c == BOARDLENGTH - 1) {
                return true;
            }
        }
        for (int r = 0; r < BOARDLENGTH; r++) { // check 3 in a column
            if (board[r][column].getMark() != mark) {
                break;
            }
            if (r == BOARDLENGTH - 1) {
                return true;
            }
        }

        if (row == column) { // check 3 in a diagonal
            for (int i = 0; i < BOARDLENGTH; i++) {
                if (board[i][i].getMark() != mark) {
                    break;
                }
                if (i == BOARDLENGTH - 1) {
                    return true;
                }
            }
        }

        if (row + column == BOARDLENGTH - 1) { // check 3 in a reverse diagonal
            for (int i = 0; i < BOARDLENGTH; i++) {
                if (board[i][(BOARDLENGTH - 1) - i].getMark() != mark) {
                    break;
                }
                if (i == BOARDLENGTH - 1) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isDraw() {
        for (int row = 0; row < BOARDLENGTH; ++row) {
            for (int col = 0; col < BOARDLENGTH; ++col) {
                if (board[row][col].getMark() == MARK.EMPTY) {
                    return false; // an empty cell found, not draw, exit
                }
            }
        }
        return true;  // no more empty cell, it's a draw
    }

    public BoardResponse turn(int row, int column) {
        if (row > BOARDLENGTH || column > BOARDLENGTH) {
            return null;
        }
        if (turn == 0) {
            this.stopWatch.start();
        }

        this.currentState = this.checkGameState(row, column);

        if (this.currentState != GAMESTATE.PLAYING) {
            this.stopWatch.stop();
            if(this.currentState != GAMESTATE.AI_WON){
                   this.calculateScore();
            }
           
        }
        System.out.println(this.stopWatch.getTotalTimeSeconds());
        System.out.println("state is " + this.currentState);
        draw();
        turn++;

        return new BoardResponse(this.playerName, board, score, currentState, scoreBoard);
    }
    
    public BoardResponse getBoardResponse(){
        return new BoardResponse(this.playerName, board, score, currentState, scoreBoard);
    }

    public Cell dumbMoveAI(MARK mark) {

        List<Cell> emptyCells = new ArrayList<>();
        for (int row = 0; row < BOARDLENGTH; ++row) {
            for (int col = 0; col < BOARDLENGTH; ++col) {
                if (board[row][col].getMark() == MARK.EMPTY) {
                    emptyCells.add(board[row][col]);
                }
            }
        }
        SecureRandom rand = new SecureRandom();

        Cell onCell = emptyCells.get(rand.nextInt(emptyCells.size()));
        onCell.setMark(mark);
        return onCell;

    }

    public void reset() {
        createBoard();
        this.stopWatch = new StopWatch();
        this.score = 0;
        this.currentState = GAMESTATE.PLAYING;
        this.turn = 0 ;

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void draw() {
        for (int row = 0; row < BOARDLENGTH; ++row) {
            for (int col = 0; col < BOARDLENGTH; ++col) {
                board[row][col].draw();// each cell paints itself
                if (col < BOARDLENGTH - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (row < BOARDLENGTH - 1) {
                System.out.println("-----------");
            }
        }
    }

}
