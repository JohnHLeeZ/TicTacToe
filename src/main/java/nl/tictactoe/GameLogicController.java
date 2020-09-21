package nl.tictactoe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameLogicController {

    @Autowired
    private BoardService boardService;


    @GetMapping("/reset")
    public void reset(){
         boardService.reset();
    }
    
    @PostMapping("/getBoard")
    public String createBoard(@RequestParam String name) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            boardService.reset();
            boardService.setPlayerName(name);
            return mapper.writeValueAsString(boardService.getBoardResponse());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(GameLogicController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    @PostMapping("/executeTurn")
    public String move(@RequestParam boolean turn, @RequestParam int column, @RequestParam int row) {
        BoardResponse board = this.boardService.turn(row, column);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(board);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(GameLogicController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
