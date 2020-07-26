package de.bhelfert.rockpaperscissors.cli;

import de.bhelfert.rockpaperscissors.game.Game;
import de.bhelfert.rockpaperscissors.game.GameRegistry;
import de.bhelfert.rockpaperscissors.game.classic.ClassicGame;

public class CommandLineParser {

    public static final GameInput PARSING_ERROR = null;

    private GameRegistry gameRegistry = new GameRegistry();

    public GameInput parseCommandLine(String[] args) {
        if (args.length == 0) {
            printUsage();
            return PARSING_ERROR;
        }

        Game game = getGame(args);
        if (game == null) {
            printErrorMessageAndUsage("Game variant '" + args[0] + "' is unknown.");
            return PARSING_ERROR;
        }

        Enum<?> gameItem = getGameItem(args, game);
        if (gameItem == null) {
            printErrorMessageAndUsage("Game item '" + getGameItemName(args) + "' is invalid.");
            return PARSING_ERROR;
        }

        return new GameInput(game, gameItem);
    }

    private void printUsage() {
        System.out.println("usage: rockPaperScissors [<game variant>] <game item>");
        System.out.println("Game variants and game items:");
        for (Game game : gameRegistry.getGames()) {
            System.out.println("  " + game.getName() + ": " + game.getItems());
        }
        System.out.println();
        System.out.println("If the game variant is omitted, 'classic' will be used as default.");
    }

    private Game getGame(String[] args) {
        return hasOnlyOneArgument(args) ? getClassicGame() : getGameVariant(args);
    }

    private boolean hasOnlyOneArgument(String[] args) {
        return args.length == 1;
    }

    private Game getClassicGame() {
        return gameRegistry.getGame(ClassicGame.NAME);
    }

    private Game getGameVariant(String[] args) {
        String gameVariant = args[0];
        return gameRegistry.getGame(gameVariant);
    }

    private void printErrorMessageAndUsage(String errorMessage) {
        System.out.println("Error: " + errorMessage);
        System.out.println();
        printUsage();
    }

    private Enum<?> getGameItem(String[] args, Game game) {
        return game.getGameItem(getGameItemName(args));
    }

    private String getGameItemName(String[] args) {
        return hasOnlyOneArgument(args) ? args[0] : args[1];
    }

    @Override
    public String toString() {
        return "CommandLineParser{" +
                "gameRegistry=" + gameRegistry +
                '}';
    }
}
