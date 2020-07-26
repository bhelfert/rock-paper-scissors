package de.bhelfert.rockpaperscissors

import groovy.ui.SystemOutputInterceptor
import spock.lang.Specification

class EndToEndTest extends Specification {

    static final String ANY_CHAR_REGEX = /.*/
    static final String ONE_WORD_REGEX = /(\w+\b){1}/
    static final String LINEBREAK_REGEX = /\R/
    static final String RESULT_REGEX = /${ANY_CHAR_REGEX}Result: $ANY_CHAR_REGEX, as $ANY_CHAR_REGEX!/

    StringBuilder output = new StringBuilder()
    SystemOutputInterceptor soutInterceptor = new SystemOutputInterceptor({
        output.append(it)
        true
    })
    String outputAsString
    String[] args

    def "playing the classic game works as expected"() {
        given:
        args = ['scissors']

        when:
        soutInterceptor.start()
        RockPaperScissors.main(args)
        soutInterceptor.stop()
        outputAsString = output.toString()

        then:
        outputAsString.contains 'Player 1 has chosen: scissors'
        outputAsString =~ regExForPlayerHasChosen(2)
        outputAsString.contains '---'
        outputAsString =~ RESULT_REGEX
    }

    def "let the computer play the classic game works as expected"() {
        given:
        args = ['computer']

        when:
        soutInterceptor.start()
        RockPaperScissors.main(args)
        soutInterceptor.stop()
        outputAsString = output.toString()

        then:
        outputAsString =~ regExForPlayerHasChosen(1)
        outputAsString =~ regExForPlayerHasChosen(2)
        outputAsString.contains '---'
        outputAsString =~ RESULT_REGEX
    }

    def "playing the lizard spock game works as expected"() {
        given:
        args = ['lizard-spock', 'lizard']

        when:
        soutInterceptor.start()
        RockPaperScissors.main(args)
        soutInterceptor.stop()
        outputAsString = output.toString()

        then:
        outputAsString.contains 'Player 1 has chosen: lizard'
        outputAsString =~ regExForPlayerHasChosen(2)
        outputAsString.contains '---'
        outputAsString =~ RESULT_REGEX
    }

    def "let the computer play the lizard spock game works as expected"() {
        given:
        args = ['lizard-spock', 'computer']

        when:
        soutInterceptor.start()
        RockPaperScissors.main(args)
        soutInterceptor.stop()
        outputAsString = output.toString()

        then:
        outputAsString =~ regExForPlayerHasChosen(1)
        outputAsString =~ regExForPlayerHasChosen(2)
        outputAsString.contains '---'
        outputAsString =~ RESULT_REGEX
    }

    private def regExForPlayerHasChosen(int whichPlayer) {
        /${ANY_CHAR_REGEX}Player $whichPlayer has chosen: $ONE_WORD_REGEX$LINEBREAK_REGEX$ANY_CHAR_REGEX/
    }
}
