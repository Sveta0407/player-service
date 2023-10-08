package com.intuit.playerservice.helper;

import com.intuit.playerservice.exception.ValidationPlayerException;
import com.intuit.playerservice.model.Player;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVHelper {

    public static List<Player> csvToPlayers(InputStream is) throws ValidationPlayerException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())){

            List<Player> players = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Player player = Player.builder()
                        .playerId(csvRecord.get("playerID"))
                        .birthDate(getDate(csvRecord,"birthYear","birthMonth","birthDay"))
                        .birthCountry(csvRecord.get("birthCountry"))
                        .birthState(csvRecord.get("birthState"))
                        .birthCity(csvRecord.get("birthCity"))
                        .deathDate(getDate(csvRecord,"deathYear","deathMonth","deathDay"))
                        .deathCountry(csvRecord.get("deathCountry"))
                        .deathState(csvRecord.get("deathState"))
                        .deathCity(csvRecord.get("deathCity"))
                        .firstName(csvRecord.get("nameFirst"))
                        .lastName(csvRecord.get("nameLast"))
                        .givenName(csvRecord.get("nameGiven"))
                        .weight(getInt(csvRecord.get("weight")))
                        .height(getInt(csvRecord.get("height")))
                        .bats(csvRecord.get("bats"))
                        .throwsInd(csvRecord.get("throws"))
                        .debut(getDate(csvRecord.get("debut")))
                        .finalGame(getDate(csvRecord.get("finalGame")))
                        .retroId(csvRecord.get("retroID"))
                        .bbrefID(csvRecord.get("bbrefID"))
                        .build();
                players.add(player);
            }
            return players;
        } catch (IOException e) {
            throw new ValidationPlayerException("fail to parse CSV file: " + e.getMessage(),e);
        } catch (DateTimeParseException e){
            throw new ValidationPlayerException("one of data fields cannot be parsed " + e.getMessage(),e);
        } catch ( NumberFormatException e){
            throw new ValidationPlayerException("one of integer fields cannot be parsed " + e.getMessage(),e);
        }
    }


        private static LocalDate getDate(CSVRecord csvRecord,String yearFldName, String monthFldName, String dayFldName){
        String year = csvRecord.get(yearFldName);
        String month = csvRecord.get(monthFldName);
        String day = csvRecord.get(dayFldName);
            if(!StringUtils.hasLength(year) || !StringUtils.hasLength(month) || !StringUtils.hasLength(day) ){
                return null;
            }
            return  LocalDate.of(getInt(year),getInt(month),getInt(day));
        }

    private static Integer getInt(String s) {
        return !StringUtils.hasLength(s) ? 0 :Integer.parseInt(s);
    }

    private static LocalDate getDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return !StringUtils.hasLength(dateString) ? null : LocalDate.parse(dateString, formatter);
    }
}
