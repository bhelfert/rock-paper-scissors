package de.bhelfert.rockpaperscissors;

import de.bhelfert.rockpaperscissors.cli.CommandLineParser;
import de.bhelfert.rockpaperscissors.cli.GameInput;
import de.bhelfert.rockpaperscissors.game.RunOfPlay;
import de.bhelfert.rockpaperscissors.presentation.RunOfPlayConsolePrinter;

public class RockPaperScissors {

    private CommandLineParser commandLineParser = new CommandLineParser();
    private RunOfPlayConsolePrinter consolePrinter = new RunOfPlayConsolePrinter();

    public static void main(String[] args) {
        new RockPaperScissors().runGame(args);
    }

    public void runGame(String[] args) {
        GameInput input = commandLineParser.parseCommandLine(args);
        if (input == CommandLineParser.PARSING_ERROR) {
            return;
        }
        RunOfPlay runOfPlay = input.getGame().play(input);
        consolePrinter.printRunOfPlay(runOfPlay);
    }

    @Override
    public String toString() {
        return "RockPaperScissors{" +
                "commandLineParser=" + commandLineParser +
                ", consolePrinter=" + consolePrinter +
                '}';
    }
}
