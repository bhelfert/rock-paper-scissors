package com.sevenlist.rockpaperscissors.game.classic;

import com.sevenlist.rockpaperscissors.game.Game;
import com.sevenlist.rockpaperscissors.game.GameRules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sevenlist.rockpaperscissors.game.classic.ClassicGameItem.*;

public class ClassicGame extends Game {

    public static final String NAME = "classic";

    public ClassicGame() {
        super(NAME, getClassicItems());
    }

    protected ClassicGame(String name, List<Enum<?>> items) {
        super(name, addToClassicItems(items));
    }

    private static List<Enum<?>> getClassicItems() {
        return new ArrayList<>(Arrays.asList(ClassicGameItem.values()));
    }

    private static List<Enum<?>> addToClassicItems(List<Enum<?>> items) {
        List<Enum<?>> allItems = getClassicItems();
        allItems.addAll(items);
        return allItems;
    }

    @Override
    protected GameRules createGameRules() {
        GameRules rules = new GameRules();
        rules.addRule(rock, "crushes", scissors);
        rules.addRule(paper, "covers", rock);
        rules.addRule(scissors, "cuts", paper);
        return rules;
    }

    @Override
    protected Enum<?> getGameItemForString(String gameItemName) {
        return ClassicGameItem.valueOf(gameItemName);
    }

    @Override
    public String toString() {
        return "ClassicGame{" +
                "super=" + super.toString() +
                '}';
    }
}
