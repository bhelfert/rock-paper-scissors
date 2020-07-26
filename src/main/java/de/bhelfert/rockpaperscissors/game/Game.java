package de.bhelfert.rockpaperscissors.game;

import de.bhelfert.rockpaperscissors.cli.GameInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static de.bhelfert.rockpaperscissors.game.GameResult.*;

public abstract class Game {

    private static final String SPACE = " ";

    private final String name;
    private final List<Enum<?>> items;

    private GameRules rules;

    protected Game(String name, List<Enum<?>> items) {
        this.name = name;
        this.items = items;
    }

    public RunOfPlay play(GameInput gameInput) {
        Enum<?> itemPlayer1 = getGameItemOfPlayerOne(gameInput);
        Enum<?> itemPlayer2 = getGameItemOfComputerPlayer();

        RunOfPlay runOfPlay = new RunOfPlay();
        runOfPlay.setItemPlayer1(itemPlayer1);
        runOfPlay.setItemPlayer2(itemPlayer2);
        runOfPlay.setGameResult(getGameResult(itemPlayer1, itemPlayer2));
        runOfPlay.setGameResultReason(getGameResultReason(runOfPlay));
        return runOfPlay;
    }

    private Enum<?> getGameItemOfPlayerOne(GameInput gameInput) {
        return shallComputerPlay(gameInput) ? getGameItemOfComputerPlayer() : gameInput.getGameItem();
    }

    private boolean shallComputerPlay(GameInput gameInput) {
        return gameInput.getGameItem().equals(getComputerGameItem());
    }

    private Enum<?> getComputerGameItem() {
        return getGameItem("computer");
    }

    public Enum<?> getGameItem(String gameItemName) {
        try {
            return getGameItemForString(gameItemName);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    protected abstract Enum<?> getGameItemForString(String gameItemName);

    private Enum<?> getGameItemOfComputerPlayer() {
        List<Enum<?>> itemsWithoutComputerGameItem = getItemsWithoutComputerGameItem();
        int randomIndex = createRandom().nextInt(itemsWithoutComputerGameItem.size());
        return itemsWithoutComputerGameItem.get(randomIndex);
    }

    private List<Enum<?>> getItemsWithoutComputerGameItem() {
        List<Enum<?>> itemsWithoutComputer = new ArrayList<>(items);
        itemsWithoutComputer.remove(getComputerGameItem());
        return itemsWithoutComputer;
    }

    /**
     * Should only be overridden for testing.
     */
    protected Random createRandom() {
        return new Random();
    }

    private GameResult getGameResult(Enum<?> itemPlayer1, Enum<?> itemPlayer2) {
        if (itemPlayer1 == itemPlayer2) {
            return Tie;
        }
        return getGameRules().beats(itemPlayer1, itemPlayer2) ? Player1Wins : Player2Wins;
    }

    private GameRules getGameRules() {
        if (rules == null) {
            rules = createGameRules();
        }
        return rules;
    }

    protected abstract GameRules createGameRules();

    private String getGameResultReason(RunOfPlay runOfPlay) {
        Enum<?> itemPlayer1 = runOfPlay.getItemPlayer1();
        Enum<?> itemPlayer2 = runOfPlay.getItemPlayer2();

        switch (runOfPlay.getGameResult()) {
            case Player1Wins:
                return itemPlayer1 + SPACE + getGameRules().getBeatsDescription(itemPlayer1, itemPlayer2) + SPACE + itemPlayer2;

            case Player2Wins:
                return itemPlayer2 + SPACE + getGameRules().getBeatsDescription(itemPlayer2, itemPlayer1) + SPACE + itemPlayer1;

            case Tie:
                return itemPlayer1 + " and " + itemPlayer2 + " are the same";

            default:
                throw new IllegalArgumentException("Not yet implemented:" + runOfPlay.getGameResult());
        }
    }

    public String getName() {
        return name;
    }

    public List<Enum<?>> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        if (name != null ? !name.equals(game.name) : game.name != null) return false;
        if (items != null ? !items.equals(game.items) : game.items != null) return false;
        return !(getGameRules() != null ? !getGameRules().equals(game.getGameRules()) : game.getGameRules() != null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + (getGameRules() != null ? getGameRules().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", items=" + items +
                ", rules=" + getGameRules() +
                '}';
    }
}
