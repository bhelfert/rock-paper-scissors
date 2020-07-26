package de.bhelfert.rockpaperscissors.game

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import de.bhelfert.rockpaperscissors.cli.GameInput
import spock.lang.Specification

import static GameResult.*
import static de.bhelfert.rockpaperscissors.game.GameTest.TestGameItem.*

class GameTest extends Specification {

    @Collaborator
    Random randomMock = Mock()

    @Collaborator
    GameRules gameRulesMock = Mock()

    @Subject
    TestGame testGame

    def "returns the expected RunOfPlay when player 1 wins"() {
        given:
        GameInput gameInput = new GameInput(testGame, testGameItem1)

        and:
        RunOfPlay expectedRunOfPlay = new RunOfPlay()
        expectedRunOfPlay.itemPlayer1 = testGameItem1
        expectedRunOfPlay.itemPlayer2 = testGameItem2
        expectedRunOfPlay.gameResult = Player1Wins
        expectedRunOfPlay.gameResultReason = "$testGameItem1 beats $testGameItem2"

        when:
        def runOfPlay = testGame.play(gameInput)

        then:
        1 * randomMock.nextInt(_) >> testGameItem2.ordinal()
        1 * gameRulesMock.beats(testGameItem1, testGameItem2) >> true
        1 * gameRulesMock.getBeatsDescription(testGameItem1, testGameItem2) >> 'beats'
        runOfPlay == expectedRunOfPlay
    }

    def "returns the expected RunOfPlay when player 2 wins"() {
        given:
        GameInput gameInput = new GameInput(testGame, testGameItem1)

        and:
        RunOfPlay expectedRunOfPlay = new RunOfPlay()
        expectedRunOfPlay.itemPlayer1 = testGameItem1
        expectedRunOfPlay.itemPlayer2 = testGameItem2
        expectedRunOfPlay.gameResult = Player2Wins
        expectedRunOfPlay.gameResultReason = "$testGameItem2 beats $testGameItem1"

        when:
        def runOfPlay = testGame.play(gameInput)

        then:
        1 * randomMock.nextInt(_) >> testGameItem2.ordinal()
        1 * gameRulesMock.beats(testGameItem1, testGameItem2) >> false
        1 * gameRulesMock.getBeatsDescription(testGameItem2, testGameItem1) >> 'beats'
        runOfPlay == expectedRunOfPlay
    }

    def "returns the expected RunOfPlay when tie"() {
        given:
        GameInput gameInput = new GameInput(testGame, testGameItem1)

        and:
        RunOfPlay expectedRunOfPlay = new RunOfPlay()
        expectedRunOfPlay.itemPlayer1 = testGameItem1
        expectedRunOfPlay.itemPlayer2 = testGameItem1
        expectedRunOfPlay.gameResult = Tie
        expectedRunOfPlay.gameResultReason = "$testGameItem1 and $testGameItem1 are the same"

        when:
        def runOfPlay = testGame.play(gameInput)

        then:
        1 * randomMock.nextInt(_) >> testGameItem1.ordinal()
        0 * gameRulesMock.beats(_, _)
        0 * gameRulesMock.getBeatsDescription(_, _)
        runOfPlay == expectedRunOfPlay
    }

    def "returns the expected RunOfPlay when computer plays vs computer"() {
        given:
        GameInput gameInput = new GameInput(testGame, computer)

        and:
        randomMock.nextInt(_) >>> [testGameItem1.ordinal(), testGameItem2.ordinal()]
        gameRulesMock.beats(_, _) >> true
        gameRulesMock.getBeatsDescription(_, _) >> 'beats'

        and:
        RunOfPlay expectedRunOfPlay = new RunOfPlay()
        expectedRunOfPlay.itemPlayer1 = testGameItem1
        expectedRunOfPlay.itemPlayer2 = testGameItem2
        expectedRunOfPlay.gameResult = Player1Wins
        expectedRunOfPlay.gameResultReason = "$testGameItem1 beats $testGameItem2"

        when:
        def runOfPlay = testGame.play(gameInput)

        then:
        runOfPlay == expectedRunOfPlay
    }

    def "returns enum game item for string"() {
        given:
        String gameItemAsString = testGameItem1.name()

        when:
        def gameItemAsEnum = testGame.getGameItem(gameItemAsString)

        then:
        gameItemAsEnum == testGameItem1
    }

    def "returns null when no enum game item exists for string"() {
        given:
        String nonExistentGameItem = 'nonExistentGameItem'

        when:
        def gameItem = testGame.getGameItem(nonExistentGameItem)

        then:
        !gameItem
    }

    def "returns immutable game items list"() {
        when:
        testGame.items << testGameItem1

        then:
        thrown(UnsupportedOperationException)
    }

    class TestGame extends Game {

        private GameRules gameRulesMock
        private Random randomMock

        TestGame(Random randomMock, GameRules gameRulesMock) {
            super('test', TestGameItem.values())
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

        @Override
        protected Enum<?> getGameItemForString(String gameItemName) {
            TestGameItem.valueOf(gameItemName)
        }
    }

    enum TestGameItem {
        testGameItem1, testGameItem2, computer
    }
}
