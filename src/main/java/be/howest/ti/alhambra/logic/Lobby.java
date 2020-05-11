package be.howest.ti.alhambra.logic;


import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Lobby {

    private final int maxNumberOfPlayers;
    @JsonIgnore
    private static final int MIN_PLAYER_COUNT = 2;
    private final String id;
    private final String customNameLobby;
    private List<PlayerInLobby> playersReady;
    private int playerCount;
    private int readyCount;

    public Lobby(String gameId, String customNameLobby, int maxNumberOfPlayers) {
        this(gameId, new ArrayList<>(), customNameLobby, maxNumberOfPlayers);
    }

    @JsonCreator
    public Lobby(@JsonProperty("id") String id, @JsonProperty("players") List<PlayerInLobby> playersReady, @JsonProperty("customNameLobby") String customNameLobby, @JsonProperty("maxNumberOfPlayers") int maxNumberOfPlayers) {
        this.id = id;
        this.playersReady = playersReady;
        this.customNameLobby = customNameLobby;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        updatePlayerCount();
        updateReadyCount();
    }

    public Lobby(String gameId, String customNameLobby) {
        this(gameId, new ArrayList<>(), customNameLobby, 6);
    }


    private void updatePlayerCount() {
        playerCount = countPlayer();
    }

    private void updateReadyCount() {
        readyCount = countReady();
    }

    public int countPlayer() {
        return playersReady.size();
    }

    public int countReady() {
        int i = 0;
        for (PlayerInLobby p : playersReady){
            if (p.isStatus()){
                i++;
            }
        }
        return i;
    }

    public String getId() {
        return id;
    }

    public String getCustomNameLobby() { return customNameLobby; }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getReadyCount() {
        return readyCount;
    }

    public int getMaxNumberOfPlayers()
    {
        return maxNumberOfPlayers;
    }

    @JsonGetter("players")
    public List<PlayerInLobby> getPlayersReady() {
        return playersReady;
    }

    public void addPlayer(String name) {
        if (countPlayer() < maxNumberOfPlayers) {
            if (checkInLobby(name))
                throw new AlhambraGameRuleException("Name already used");
            else {
                playersReady.add(new PlayerInLobby(name));
            }
        }
        else {
            throw new AlhambraGameRuleException("The lobby is full");
        }
        updatePlayerCount();
    }

    private PlayerInLobby getPlayerClass(String name) {
        for (PlayerInLobby p : playersReady) {
            if (name.equals(p.getName())) {
                return p;
            }
        }
        throw new IllegalArgumentException("player not in lobby");
    }


    private boolean checkInLobby(String name) {
        for (PlayerInLobby p : playersReady) {
            if (name.equals(p.getName())) {
                return true;
            }
        }
        return false;
    }

    public void removePlayer(String name) {
        PlayerInLobby player = getPlayerClass(name);
        playersReady.remove(player);
        updatePlayerCount();
    }

    public boolean readyUpPlayer(String name) {
        getPlayerClass(name).setStatus(true);
        updateReadyCount();
        return true;
    }

    public boolean unreadyPlayer(String name) {
        getPlayerClass(name).setStatus(false);
        updateReadyCount();
        return true;
    }

    public Game startGame() {
        if (countPlayer() >= MIN_PLAYER_COUNT) {
            if (readyCount == playerCount) {
               return new Game(playersReady);
            } else {
                throw new AlhambraGameRuleException("All players need to be ready to start the game");
            }
        } else {
            throw new AlhambraGameRuleException("You must be with 2 players to start a game");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lobby lobby = (Lobby) o;
        return maxNumberOfPlayers == lobby.maxNumberOfPlayers &&
                playerCount == lobby.playerCount &&
                readyCount == lobby.readyCount &&
                Objects.equals(id, lobby.id) &&
                Objects.equals(customNameLobby, lobby.customNameLobby) &&
                Objects.equals(playersReady, lobby.playersReady);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxNumberOfPlayers, id, customNameLobby, playersReady, playerCount, readyCount);
    }
}
