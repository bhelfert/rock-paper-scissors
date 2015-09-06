package com.sevenlist.rockpaperscissors.game.lizardspock;

import com.sevenlist.rockpaperscissors.game.GameRules;
import com.sevenlist.rockpaperscissors.game.classic.ClassicGame;

import java.util.Arrays;

import static com.sevenlist.rockpaperscissors.game.classic.ClassicGameItem.*;
import static com.sevenlist.rockpaperscissors.game.lizardspock.LizardSpockGameItem.lizard;
import static com.sevenlist.rockpaperscissors.game.lizardspock.LizardSpockGameItem.spock;

public class LizardSpockGame extends ClassicGame {

    public static final String NAME = "lizard-spock";

    public LizardSpockGame() {
        super(NAME, Arrays.asList(LizardSpockGameItem.values()));
    }

    @Override
    protected GameRules createGameRules() {
        GameRules rules = super.createGameRules();
        rules.addRule(rock, "crushes", lizard);
        rules.addRule(paper, "disproves", spock);
        rules.addRule(scissors, "decapitates", lizard);
        rules.addRule(lizard, "eats", paper);
        rules.addRule(lizard, "poisons", spock);
        rules.addRule(spock, "vaporizes", rock);
        rules.addRule(spock, "smashes", scissors);
        return rules;
    }

    @Override
    protected Enum<?> getGameItemForString(String gameItemName) {
        try {
            return super.getGameItemForString(gameItemName);
        } catch (IllegalArgumentException e) {
            return LizardSpockGameItem.valueOf(gameItemName);
        }
    }

    @Override
    public String toString() {
        return "LizardSpockGame{" +
                "super=" + super.toString() +
                '}';
    }
}
