package com.sevenlist.rockpaperscissors.game.lizardspock

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import com.sevenlist.rockpaperscissors.cli.GameInput
import com.sevenlist.rockpaperscissors.game.GameRules
import com.sevenlist.rockpaperscissors.game.RunOfPlay
import spock.lang.Specification
import spock.lang.Unroll

import static com.sevenlist.rockpaperscissors.game.GameResult.Player1Wins
import static com.sevenlist.rockpaperscissors.game.classic.ClassicGameItem.*
import static com.sevenlist.rockpaperscissors.game.lizardspock.LizardSpockGameItem.lizard
import static com.sevenlist.rockpaperscissors.game.lizardspock.LizardSpockGameItem.spock

class LizardSpockGameTest extends Specification {

    static final int COMPUTER_GAME_ITEM = 1
    static final int ROCK_PAPER_SCISSORS_GAME_ITEMS = 3

    @Collaborator
    Random randomMock = Mock()

    @Collaborator
    GameRules gameRulesMock = Mock()

    @Subject
    LizardSpockGameForTest lizardSpockGameForTest

    @Unroll
    def "rule '#item1 #beatsDescription #item2' is implemented"() {
        given:
        GameInput gameInput = new GameInput(lizardSpockGameForTest, item1)

        and:
        randomMock.nextInt(_) >> item2ListIndex
        gameRulesMock.beats(_, _) >> true
        gameRulesMock.getBeatsDescription(_, _) >> beatsDescription

        and:
        RunOfPlay expectedRunOfPlay = new RunOfPlay()
        expectedRunOfPlay.itemPlayer1 = item1
        expectedRunOfPlay.itemPlayer2 = item2
        expectedRunOfPlay.gameResult = Player1Wins
        expectedRunOfPlay.gameResultReason = "$item1 $beatsDescription $item2"

        when:
        def runOfPlay = lizardSpockGameForTest.play(gameInput)

        then:
        runOfPlay == expectedRunOfPlay

        where:
        item1    | beatsDescription | item2    | item2ListIndex
        rock     | 'crushes'        | scissors | scissors.ordinal() - COMPUTER_GAME_ITEM
        rock     | "crushes "       | lizard   | ROCK_PAPER_SCISSORS_GAME_ITEMS + lizard.ordinal()

        paper    | 'covers'         | rock     | rock.ordinal() - COMPUTER_GAME_ITEM
        paper    | "disproves "     | spock    | ROCK_PAPER_SCISSORS_GAME_ITEMS + spock.ordinal()

        scissors | 'cuts'           | paper    | paper.ordinal() - COMPUTER_GAME_ITEM
        scissors | "decapitates "   | lizard   | ROCK_PAPER_SCISSORS_GAME_ITEMS + lizard.ordinal()

        lizard   | "eats "          | paper    | paper.ordinal() - COMPUTER_GAME_ITEM
        lizard   | "poisons "       | spock    | ROCK_PAPER_SCISSORS_GAME_ITEMS + spock.ordinal()

        spock    | "vaporizes "     | rock     | rock.ordinal() - COMPUTER_GAME_ITEM
        spock    | "smashes "       | scissors | scissors.ordinal() - COMPUTER_GAME_ITEM
    }

    class LizardSpockGameForTest extends LizardSpockGame {

        private GameRules gameRulesMock
        private Random randomMock

        LizardSpockGameForTest(Random randomMock, GameRules gameRulesMock) {
            super()
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
