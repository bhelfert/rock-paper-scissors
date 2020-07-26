package de.bhelfert.rockpaperscissors.presentation;

import de.bhelfert.rockpaperscissors.game.RunOfPlay;

public class RunOfPlayConsolePrinter {

    public void printRunOfPlay(RunOfPlay runOfPlay) {
        System.out.println("Player 1 has chosen: " + runOfPlay.getItemPlayer1());
        System.out.println("Player 2 has chosen: " + runOfPlay.getItemPlayer2());
        System.out.println("---");
        System.out.print("Result: ");
        if (runOfPlay.isTie()) {
            System.out.print("Tie");
        }
        else {
            int whichPlayerHasWon = (runOfPlay.hasPlayer1Won() ? 1 : 2);
            System.out.print("Player " + whichPlayerHasWon + " has won");
        }
        System.out.println(", as " + runOfPlay.getGameResultReason() + "!");
        System.out.println();
    }
}
