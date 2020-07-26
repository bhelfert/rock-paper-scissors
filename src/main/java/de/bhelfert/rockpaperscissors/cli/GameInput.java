package de.bhelfert.rockpaperscissors.cli;

import de.bhelfert.rockpaperscissors.game.Game;

public class GameInput {

    private final Game game;
    private final Enum<?> gameItem;

    public GameInput(Game game, Enum<?> gameItem) {
        this.game = game;
        this.gameItem = gameItem;
    }

    public Game getGame() {
        return game;
    }

    public Enum<?> getGameItem() {
        return gameItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInput gameInput = (GameInput) o;
        if (game != null ? !game.equals(gameInput.game) : gameInput.game != null) return false;
        return !(gameItem != null ? !gameItem.equals(gameInput.gameItem) : gameInput.gameItem != null);
    }

    @Override
    public int hashCode() {
        int result = game != null ? game.hashCode() : 0;
        result = 31 * result + (gameItem != null ? gameItem.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GameInput{" +
                "game=" + game +
                ", gameItem=" + gameItem +
                '}';
    }
}
