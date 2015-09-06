package com.sevenlist.rockpaperscissors.game;

import java.util.HashMap;
import java.util.Map;

public class GameRules {

    private Map<Enum<?>, Map<Enum<?>, String>> winningItemBeatsLosingItems = new HashMap<>();

    public void addRule(Enum<?> winningItem, String beatsDescription, Enum<?> losingItem) {
        Map<Enum<?>, String> losingItems;
        if (!winningItemBeatsLosingItems.containsKey(winningItem)) {
            losingItems = new HashMap<>();
            winningItemBeatsLosingItems.put(winningItem, losingItems);
        }
        else {
            losingItems = winningItemBeatsLosingItems.get(winningItem);
        }
        losingItems.put(losingItem, beatsDescription);
    }

    public boolean beats(Enum<?> itemPlayer1, Enum<?> itemPlayer2) {
        if (!winningItemBeatsLosingItems.containsKey(itemPlayer1)) {
            throw new IllegalArgumentException("No GameRule added for Enum" + itemPlayer1);
        }
        Map<Enum<?>, String> losingItems = winningItemBeatsLosingItems.get(itemPlayer1);
        return losingItems.containsKey(itemPlayer2);
    }

    public String getBeatsDescription(Enum<?> winningItem, Enum<?> losingItem) {
        if (!winningItemBeatsLosingItems.containsKey(winningItem)) {
            throw new IllegalArgumentException("No GameRule added for Enum " + winningItem);
        }

        Map<Enum<?>, String> losingItems = winningItemBeatsLosingItems.get(winningItem);
        if (!losingItems.containsKey(losingItem)) {
            throw new IllegalArgumentException("Enum " + losingItem + " is not being beaten by Enum " + winningItem);
        }

        return losingItems.get(losingItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRules gameRules = (GameRules) o;
        return !(winningItemBeatsLosingItems != null ? !winningItemBeatsLosingItems.equals(gameRules.winningItemBeatsLosingItems) : gameRules.winningItemBeatsLosingItems != null);
    }

    @Override
    public int hashCode() {
        return winningItemBeatsLosingItems != null ? winningItemBeatsLosingItems.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GameRules{" +
                "winningItemBeatsLosingItems=" + winningItemBeatsLosingItems +
                '}';
    }
}
