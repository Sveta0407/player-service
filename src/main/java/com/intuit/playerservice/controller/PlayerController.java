package com.intuit.playerservice.controller;

import com.intuit.playerservice.dto.APIResponse;
import com.intuit.playerservice.exception.ValidationPlayerException;
import com.intuit.playerservice.model.Player;
import com.intuit.playerservice.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/players")
public class PlayerController {
    @Autowired
    PlayerService playerService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPlayers() {
        try {
            return ResponseEntity.ok(playerService.uploadPlayers());
        } catch (ValidationPlayerException e) {
            return ResponseEntity.badRequest().body(new APIResponse("BAD REQUEST", HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }


    @GetMapping("/{playerId}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("playerId") String playerId) {
        return playerService.getPlayerById(playerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
