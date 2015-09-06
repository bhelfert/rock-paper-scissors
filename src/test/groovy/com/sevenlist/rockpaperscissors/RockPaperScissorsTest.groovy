package com.sevenlist.rockpaperscissors

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import com.sevenlist.rockpaperscissors.cli.CommandLineParser
import com.sevenlist.rockpaperscissors.cli.GameInput
import com.sevenlist.rockpaperscissors.game.RunOfPlay
import com.sevenlist.rockpaperscissors.game.classic.ClassicGame
import com.sevenlist.rockpaperscissors.game.classic.ClassicGameItem
import com.sevenlist.rockpaperscissors.presentation.RunOfPlayConsolePrinter
import spock.lang.Specification

class RockPaperScissorsTest extends Specification {

    @Collaborator
    CommandLineParser commandLineParserMock = Mock()

    @Collaborator
    RunOfPlayConsolePrinter runOfPlayConsolePrinterMock = Mock()

    @Subject
    RockPaperScissors rockPaperScissors

    def "program exits when not enough CLI arguments have been provided"() {
        given:
        String[] noArgs = []

        when:
        rockPaperScissors.runGame(noArgs)

        then:
        1 * commandLineParserMock.parseCommandLine(noArgs) >> CommandLineParser.PARSING_ERROR
        0 * runOfPlayConsolePrinterMock.printRunOfPlay(_)
    }

    def "game is being played and its run is printed when valid CLI argument is provided"() {
        given:
        String[] validArgs = ['paper']

        and:
        ClassicGame classicGameMock = Mock()
        GameInput input = new GameInput(classicGameMock, ClassicGameItem.paper)

        and:
        def runOfPlay = new RunOfPlay()

        when:
        rockPaperScissors.runGame(validArgs)

        then:
        1 * commandLineParserMock.parseCommandLine(validArgs) >> input
        1 * classicGameMock.play(input) >> runOfPlay
        1 * runOfPlayConsolePrinterMock.printRunOfPlay(runOfPlay)
    }
}
