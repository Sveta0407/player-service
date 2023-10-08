package com.intuit.playerservice.controller;

import com.intuit.playerservice.model.Player;
import com.intuit.playerservice.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PlayerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlayerService playerService;



    @Test
    void testUpload() throws Exception {
        List<Player> playersTest = new ArrayList<>();
        playersTest.add(createTestPlayer());
        Mockito.when(playerService.uploadPlayers()).thenReturn(playersTest);

        ResultActions response = mockMvc.perform(post("/api/players/upload"));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].playerId",is(playersTest.get(0).getPlayerId())))
                .andExpect(jsonPath("$.[0].birthCountry",is(playersTest.get(0).getBirthCountry())))
                .andExpect(jsonPath("$.[0].birthCity",is(playersTest.get(0).getBirthCity())));
    }

    private Player createTestPlayer() {
        return Player.builder()
                .playerId("testPlayer01")
                .birthDate(LocalDate.of(1985,5,1))
                .birthCity("Tel-Aviv")
                .birthCountry("Israel")
                .birthState("Center")
                .firstName("Test1")
                .lastName("testTest11")
                .givenName("Test Test")
                .height(190)
                .weight(80)
                .finalGame(LocalDate.of(1996,8,4))
                .build();
    }
    @Test
    void testGetAll() throws Exception {
        List<Player> playersTest = new ArrayList<>();
        playersTest.add(createTestPlayer());
        playersTest.add(createTestPlayer());
        Mockito.when(playerService.getAllPlayers()).thenReturn(playersTest);

        ResultActions response = mockMvc.perform(get("/api/players"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].playerId",is(playersTest.get(0).getPlayerId())))
                .andExpect(jsonPath("$.[1].playerId",is(playersTest.get(1).getPlayerId())));
    }

    @Test
    void testGetAll_emptyList() throws Exception {

        Mockito.when(playerService.getAllPlayers()).thenReturn(Collections.emptyList());

        ResultActions response = mockMvc.perform(get("/api/players"));
        response.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void testGetByPlayerId() throws Exception {
        Player playersTest = createTestPlayer();
        Mockito.when(playerService.getPlayerById("testPlayer01")).thenReturn(Optional.ofNullable(playersTest));

        ResultActions response = mockMvc.perform(get("/api/players/testPlayer01"));
        assert playersTest != null;
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.playerId",is(playersTest.getPlayerId())));

    }

    @Test
    void testGetByPlayerId_not_found() throws Exception {
        Player playersTest = createTestPlayer();
        Mockito.when(playerService.getPlayerById("testPlayer01")).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/api/players/testPlayer01"));
        assert playersTest != null;
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
}