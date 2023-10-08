package com.intuit.playerservice.service;

import com.intuit.playerservice.exception.ValidationPlayerException;
import com.intuit.playerservice.helper.CSVHelper;
import com.intuit.playerservice.model.Player;
import com.intuit.playerservice.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Value("${csvFileName}")
    private String csvFileName;

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> uploadPlayers() throws ValidationPlayerException {
        List<Player> players;
        try (InputStream in = getClass().getResourceAsStream("/" + csvFileName)) {
            players = CSVHelper.csvToPlayers(in);
            playerRepository.saveAll(players);
        } catch (IOException e) {
            throw new ValidationPlayerException(e.getMessage(),e);
        }
        return players;
    }

    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(String playerId) {
        return playerRepository.findPlayerByPlayerId(playerId);
    }
}

