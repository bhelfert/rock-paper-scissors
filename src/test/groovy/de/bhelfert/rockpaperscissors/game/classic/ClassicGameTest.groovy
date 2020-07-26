package de.bhelfert.rockpaperscissors.game.classic

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import de.bhelfert.rockpaperscissors.game.GameRules
import de.bhelfert.rockpaperscissors.cli.GameInput
import de.bhelfert.rockpaperscissors.game.RunOfPlay
import spock.lang.Specification
import spock.lang.Unroll

import static de.bhelfert.rockpaperscissors.game.GameResult.Player1Wins
import static ClassicGameItem.*

class ClassicGameTest extends Specification {

    @Collaborator
    Random randomMock = Mock()

    @Collaborator
    GameRules gameRulesMock = Mock()

    @Subject
    ClassicGameForTest classicGameForTest

    @Unroll
    def "rule '#item1 #beatsDescription #item2' is implemented"() {
        given:
        GameInput gameInput = new GameInput(classicGameForTest, item1)

        and:
        randomMock.nextInt(_) >> item2.ordinal() - 1
        gameRulesMock.beats(_, _) >> true
        gameRulesMock.getBeatsDescription(_, _) >> beatsDescription

        and:
        RunOfPlay expectedRunOfPlay = new RunOfPlay()
        expectedRunOfPlay.itemPlayer1 = item1
        expectedRunOfPlay.itemPlayer2 = item2
        expectedRunOfPlay.gameResult = Player1Wins
        expectedRunOfPlay.gameResultReason = "$item1 $beatsDescription $item2"

        when:
        def runOfPlay = classicGameForTest.play(gameInput)

        then:
        runOfPlay == expectedRunOfPlay

        where:
        item1    | beatsDescription | item2
        rock     | 'crushes'        | scissors
        paper    | 'covers'         | rock
        scissors | 'cuts'           | paper
    }

    class ClassicGameForTest extends ClassicGame {

        private GameRules gameRulesMock
        private Random randomMock

        ClassicGameForTest(Random randomMock, GameRules gameRulesMock) {
            super('test', [])
            this.randomMock = randomMock
            this.gameRulesMock = gameRulesMock
        }

        @Override
        protected Random createRandom() {
            randomMock
        }

        @Override
        protected GameRules createGameRules() {
            gameRulesMock
        }
    }
}
