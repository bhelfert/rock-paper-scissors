package com.sevenlist.rockpaperscissors.cli

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import com.sevenlist.rockpaperscissors.game.GameRegistry
import com.sevenlist.rockpaperscissors.game.classic.ClassicGame
import com.sevenlist.rockpaperscissors.game.lizardspock.LizardSpockGame
import groovy.ui.SystemOutputInterceptor
import spock.lang.Specification

import static com.sevenlist.rockpaperscissors.game.classic.ClassicGameItem.computer
import static com.sevenlist.rockpaperscissors.game.classic.ClassicGameItem.rock

class CommandLineParserTest extends Specification {

    StringBuilder output = new StringBuilder()
    SystemOutputInterceptor soutInterceptor = new SystemOutputInterceptor({
        output.append(it)
        true
    })

    @Collaborator
    GameRegistry gameRegistryMock = Mock()

    @Subject
    CommandLineParser commandLineParser

    def "prints usage and returns with PARSING_ERROR when no arguments are specified"() {
        given:
        gameRegistryMock.getGames() >> [new ClassicGame()]
        String[] noArgs = []

        when:
        soutInterceptor.start()
        def gameInput = commandLineParser.parseCommandLine(noArgs)
        soutInterceptor.stop()

        then:
        output.toString() == """usage: rockPaperScissors [<game variant>] <game item>
Game variants and game items:
  classic: [computer, rock, paper, scissors]

If the game variant is omitted, 'classic' will be used as default.
"""
        and:
        gameInput == CommandLineParser.PARSING_ERROR
    }

    def "usage prints available game variants and game items"() {
        gameRegistryMock.getGames() >> [new ClassicGame(), new LizardSpockGame()]
        String[] noArgs = []

        when:
        soutInterceptor.start()
        commandLineParser.parseCommandLine(noArgs)
        soutInterceptor.stop()
        def outputAsString = output.toString()

        then:
        outputAsString.contains 'classic: [computer, rock, paper, scissors]'
        outputAsString.contains 'lizard-spock: [computer, rock, paper, scissors, lizard, spock]'
    }

    def "prints usage and returns with PARSING_ERROR when unknown game variant is specified"() {
        given:
        gameRegistryMock.getGames() >> [new ClassicGame()]
        String[] unknownArgs = ['doesNotExistGameVariant', 'scissors']

        when:
        soutInterceptor.start()
        def gameInput = commandLineParser.parseCommandLine(unknownArgs)
        soutInterceptor.stop()
        def outputAsString = output.toString()

        then:
        outputAsString.contains "Error: Game variant 'doesNotExistGameVariant' is unknown."
        outputAsString.contains 'usage: rockPaperScissors'

        and:
        gameInput == CommandLineParser.PARSING_ERROR
    }

    def "prints usage and returns with PARSING_ERROR when invalid game item is specified"() {
        given:
        def classicGame = new ClassicGame()
        gameRegistryMock.getGames() >> [classicGame, new LizardSpockGame()]
        gameRegistryMock.getGame(_) >> classicGame

        String[] invalidArgs = ['doesNotExistGameItem']

        when:
        soutInterceptor.start()
        def gameInput = commandLineParser.parseCommandLine(invalidArgs)
        soutInterceptor.stop()
        def outputAsString = output.toString()

        then:
        outputAsString.contains "Error: Game item 'doesNotExistGameItem' is invalid."
        outputAsString.contains 'usage: rockPaperScissors'

        and:
        gameInput == CommandLineParser.PARSING_ERROR
    }

    def "creates a GameInput with the default game and the game item specified"() {
        given:
        String[] args = ['rock']
        def classicGame = new ClassicGame()
        gameRegistryMock.getGames() >> [classicGame]
        def expectedGameInput = new GameInput(classicGame, rock)

        when:
        def gameInput = commandLineParser.parseCommandLine(args)

        then:
        1 * gameRegistryMock.getGame(ClassicGame.NAME) >> classicGame
        gameInput == expectedGameInput
    }

    def "creates a GameInput for the game variant and game item specified"() {
        given:
        String[] args = [LizardSpockGame.NAME, 'computer']
        def lizardSpockGame = new LizardSpockGame()
        gameRegistryMock.getGames() >> [new ClassicGame(), lizardSpockGame]
        def expectedGameInput = new GameInput(lizardSpockGame, computer)

        when:
        def gameInput = commandLineParser.parseCommandLine(args)

        then:
        1 * gameRegistryMock.getGame(LizardSpockGame.NAME) >> lizardSpockGame
        gameInput == expectedGameInput
    }
}
