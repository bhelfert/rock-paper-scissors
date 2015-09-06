package com.sevenlist.rockpaperscissors.game

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject
import spock.lang.Specification

import static com.sevenlist.rockpaperscissors.game.GameRulesTest.EnumForTest.*
import static com.sevenlist.rockpaperscissors.game.classic.ClassicGameItem.*
import static com.sevenlist.rockpaperscissors.game.lizardspock.LizardSpockGameItem.spock

class GameRulesTest extends Specification {

    @Collaborator
    Map mapStub = [:]

    @Subject
    GameRules gameRules

    def "rule is added for game item when it is the first rule for that game item"() {
        given:
        assert !mapStub.containsKey(rock)

        when:
        gameRules.addRule(rock, 'crushes', scissors);

        then:
        mapStub.get(rock)
        mapStub.get(rock).get(scissors) == 'crushes'
    }

    def "rule is added for a game item that a rule has already been added for"() {
        given:
        Map losingItemsMap = [:]
        losingItemsMap.put(rock, 'covers')
        mapStub.put(paper, losingItemsMap)

        when:
        gameRules.addRule(paper, 'disproves', spock);

        then:
        mapStub.get(paper)
        mapStub.get(paper).get(rock) == 'covers'
        mapStub.get(paper).get(spock) == 'disproves'
    }

    def "exception is thrown when unknown item is passed to beats()"() {
        given:
        when:
        gameRules.beats(unknownGameItem, rock)

        then:
        thrown(IllegalArgumentException)
    }

    def "game item 1 beats game item 2 when that rule exists"() {
        given:
        Map losingItemsMap = [:]
        losingItemsMap.put(paper, 'cuts')
        mapStub.put(scissors, losingItemsMap)

        when:
        boolean scissorsBeatsPaper = gameRules.beats(scissors, paper)

        then:
        scissorsBeatsPaper
    }

    def "game item 1 does not beat game item 2 when no such rule exists"() {
        given:
        Map losingItemsMap = [:]
        losingItemsMap.put(paper, 'cuts')
        mapStub.put(scissors, losingItemsMap)

        when:
        boolean scissorsDoesNotBeatRock = !gameRules.beats(scissors, rock)

        then:
        scissorsDoesNotBeatRock
    }

    def "exception is thrown when beats description shall be get for an unknown item"() {
        when:
        gameRules.getBeatsDescription(unknownGameItem, paper)

        then:
        def e = thrown(IllegalArgumentException)
        e.message.contains 'No GameRule added for Enum'
    }

    def "exception is thrown when beats description shall be get for a wrong losing item"() {
        given:
        mapStub.put(scissors, [:])

        when:
        gameRules.getBeatsDescription(scissors, wrongLosingGameItem)

        then:
        def e = thrown(IllegalArgumentException)
        e.message.contains 'is not being beaten by Enum'
    }

    def "beat description can be got when one exists for the items passed"() {
        given:
        Map losingItemsMap = [:]
        losingItemsMap.put(rock, 'covers')
        mapStub.put(paper, losingItemsMap)

        when:
        String beatsDescription = gameRules.getBeatsDescription(paper, rock)

        then:
        beatsDescription == 'covers'
    }

    enum EnumForTest {
        unknownGameItem, wrongLosingGameItem
    }
}
