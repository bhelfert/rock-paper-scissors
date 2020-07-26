package de.bhelfert.rockpaperscissors.game

import spock.lang.Specification

class RunOfPlayTest extends Specification {

    RunOfPlay runOfPlay = new RunOfPlay()
    private boolean tie = runOfPlay.isTie()

    def "isTie() returns true if game is tie"() {
        given:
        runOfPlay.gameResult = GameResult.Tie

        when:
        def tie = runOfPlay.isTie()

        then:
        tie
    }

    def "isTie() returns false if game is not tie"() {
        given:
        runOfPlay.gameResult = GameResult.Player1Wins

        when:
        def notTie = !runOfPlay.isTie()

        then:
        notTie
    }

    def "hasPlayer1Won() returns true if player 1 has won"() {
        given:
        runOfPlay.gameResult = GameResult.Player1Wins

        when:
        def player1HasWon = runOfPlay.hasPlayer1Won()

        then:
        player1HasWon
    }

    def "hasPlayer1Won() returns false if player 1 has lost"() {
        given:
        runOfPlay.gameResult = GameResult.Player2Wins

        when:
        def player1HasLost = !runOfPlay.hasPlayer1Won()

        then:
        player1HasLost
    }
}
