package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private final String name;
    @JsonProperty("coins")
    private final Coins coins;
    private final Reserve reserve;
    private final City city;
    private final List<Building> buildingsInHand;
    private PlayerTitle title;

    private int score;
    private int virtualScore;

    @JsonIgnore //stats for playerTitle
    private int redesigns;
    @JsonIgnore
    private int viewTown;

    @JsonIgnore
    private PlayerToken token;

    public Player(String name) {
        this(name, new ArrayList<>(), new ArrayList<>(), City.getDefaultCity(), new ArrayList<>(), 0, 0, null);
    }

    @JsonCreator
    public Player(@JsonProperty("name") String name, @JsonProperty("coins") List<Coin> coins, @JsonProperty("reserve") List<Building> reserve, @JsonProperty("city") Building[][] city, @JsonProperty("buildings-in-hand") List<Building> buildingsInHand, @JsonProperty("score") int score, @JsonProperty("virtual-score") int virtualScore, @JsonProperty("title") PlayerTitle title) {
        this.name = name;
        this.coins = new Coins(coins);
        this.reserve = new Reserve(reserve);
        this.city = new City(city);
        this.buildingsInHand = buildingsInHand;
        this.score = score;
        this.virtualScore = virtualScore;
        this.title = title;
        redesigns = 0;
        viewTown = 0;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("coins") //this method is solely used for JSON conversion
    public List<Coin> coinConvert() {
        return coins.getCoinsBag();
    }

    @JsonProperty("reserve") //this method is solely used for JSON conversion
    public List<Building> reserveConvert() {
        return reserve.getBuildings();
    }

    @JsonProperty("city") //this method is solely used for JSON conversion
    public Building[][] cityConvert() {
        return city.getBuildings();
    }

    public Coins getCoins() {
        return coins;
    }

    public Reserve getReserve() {
        return reserve;
    }

    public City getCity() {
        return city;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRedesigns() {
        return redesigns;
    }

    public int getViewTown() {
        return viewTown;
    }

    public void incrRedesign() {
        redesigns++;
    }

    public void incrViewTown() {
        viewTown++;
    }

    @JsonGetter("virtual-score")
    public int getVirtualScore() {
        return virtualScore;
    }

    public void setVirtualScore(int virtualScore) {
        this.virtualScore = virtualScore;
    }

    @JsonGetter("buildings-in-hand")
    public List<Building> getBuildingsInHand() {
        return buildingsInHand;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    public PlayerToken getToken() {
        return token;
    }

    public Player setToken(PlayerToken token) {
        this.token = token;
        return this;
    }

    public PlayerTitle getTitle() {
        return title;
    }

    public void setTitle(PlayerTitle title) {
        this.title = title;
    }
}
