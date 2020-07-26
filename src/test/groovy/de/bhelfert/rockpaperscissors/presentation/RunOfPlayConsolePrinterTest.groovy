package de.bhelfert.rockpaperscissors.presentation

import de.bhelfert.rockpaperscissors.game.RunOfPlay
import groovy.ui.SystemOutputInterceptor
import spock.lang.Specification

import static de.bhelfert.rockpaperscissors.game.GameResult.*
import static de.bhelfert.rockpaperscissors.game.classic.ClassicGameItem.paper
import static de.bhelfert.rockpaperscissors.game.classic.ClassicGameItem.scissors

class RunOfPlayConsolePrinterTest extends Specification {

    StringBuilder output = new StringBuilder()
    SystemOutputInterceptor soutInterceptor = new SystemOutputInterceptor({
        output.append(it)
        true
    })

    RunOfPlayConsolePrinter consolePrinter = new RunOfPlayConsolePrinter()

    def "prints the correct run of play if player 1 has won"() {
        given:
        def runOfPlay = new RunOfPlay(
                itemPlayer1: scissors,
                itemPlayer2: paper,
                gameResult: Player1Wins,
                gameResultReason: 'scissors cuts paper'
        )

        when:
        soutInterceptor.start()
        consolePrinter.printRunOfPlay(runOfPlay)
        soutInterceptor.stop()

        then:
        def outputAsString = output.toString()
        outputAsString == """Player 1 has chosen: scissors
Player 2 has chosen: paper
---
Result: Player 1 has won, as scissors cuts paper!

"""
    }

    def "prints the correct run of play if player 2 has won"() {
        given:
        def runOfPlay = new RunOfPlay(
                itemPlayer1: paper,
                itemPlayer2: scissors,
                gameResult: Player2Wins,
                gameResultReason: 'scissors cuts paper'
        )

        when:
        soutInterceptor.start()
        consolePrinter.printRunOfPlay(runOfPlay)
        soutInterceptor.stop()

        then:
        def outputAsString = output.toString()
        outputAsString == """Player 1 has chosen: paper
Player 2 has chosen: scissors
---
Result: Player 2 has won, as scissors cuts paper!

"""
    }

    def "prints the correct run of play if it is a tie game"() {
        given:
        def runOfPlay = new RunOfPlay(
                itemPlayer1: paper,
                itemPlayer2: paper,
                gameResult: Tie,
                gameResultReason: 'paper and paper are the same'
        )

        when:
        soutInterceptor.start()
        consolePrinter.printRunOfPlay(runOfPlay)
        soutInterceptor.stop()

        then:
        def outputAsString = output.toString()
        outputAsString == """Player 1 has chosen: paper
Player 2 has chosen: paper
---
Result: Tie, as paper and paper are the same!

"""
    }
}
