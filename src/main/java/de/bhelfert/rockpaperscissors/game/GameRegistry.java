package de.bhelfert.rockpaperscissors.game;

import de.bhelfert.rockpaperscissors.game.classic.ClassicGame;
import de.bhelfert.rockpaperscissors.game.lizardspock.LizardSpockGame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameRegistry {

    private final Map<String, Game> nameToGame = new HashMap<>();

    public GameRegistry() {
        registerGame(new ClassicGame());
        registerGame(new LizardSpockGame());
    }

    private void registerGame(Game game) {
        nameToGame.put(game.getName(), game);
    }

    public Game getGame(String name) {
        return nameToGame.get(name);
    }

    public Collection<Game> getGames() {
        return nameToGame.values();
    }

    @Override
    public String toString() {
        return "GameRegistry{" +
                "nameToGame=" + nameToGame +
                '}';
    }
}
