package com.intuit.playerservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="players")
public class Player {
    @Id
    @Column(name = "playerID", nullable = false)
    private String playerId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    //maybe enum
    private String birthCountry;
    private String birthState;
    private String birthCity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deathDate;

    //maybe enum
    private String deathCountry;
    private String deathState;
    private String deathCity;

    private String firstName;
    private String lastName;
    private String givenName;

    private int weight;
    private int height;
    private String bats;

    @Column(name = "throws")
    private String throwsInd;
    private LocalDate debut;
    private LocalDate finalGame;
    private String retroId;
    private String bbrefID;
}
