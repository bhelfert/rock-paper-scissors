package de.bhelfert.rockpaperscissors.game

import de.bhelfert.rockpaperscissors.game.classic.ClassicGame
import de.bhelfert.rockpaperscissors.game.lizardspock.LizardSpockGame
import spock.lang.Specification

class GameRegistryTest extends Specification {

    GameRegistry gameRegistry = new GameRegistry()

    def "registers two games"() {
        expect:
        gameRegistry.games.size() == 2
    }

    def "registers the ClassicGame"() {
        when:
        Game game = gameRegistry.getGame(ClassicGame.NAME)

        then:
        game instanceof ClassicGame
    }

    def "registers the LizardSpockGame"() {
        when:
        Game game = gameRegistry.getGame(LizardSpockGame.NAME)

        then:
        game instanceof LizardSpockGame
    }
}
