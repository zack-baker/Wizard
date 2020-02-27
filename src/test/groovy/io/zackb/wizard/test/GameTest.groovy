package io.zackb.wizard.test

import io.zackb.wizard.data.Card
import io.zackb.wizard.data.JesterCard
import io.zackb.wizard.data.PlayingCard
import io.zackb.wizard.data.WizardCard
import io.zackb.wizard.enums.Suit
import io.zackb.wizard.service.GameService

class GameTest extends GroovyTestCase{
    void 'test trick evaluator wizard wins trick'(){
        Card c1 = new WizardCard()
        Card c2 = new JesterCard()
        Card c3 = new PlayingCard("A", Suit.SPADE)
        Card c4 = new PlayingCard("A",Suit.HEART)
        List<Card> trick = [c1,c2,c3,c4]
        assert GameService.evaluateTrick(trick, Suit.SPADE, Suit.HEART) == trick[0]
        trick = [c2,c3,c4,c1]
        assert GameService.evaluateTrick(trick, Suit.SPADE, Suit.HEART) == trick[3]
        Card c5 = new WizardCard()
        trick = [c5,c1,c2,c3]
        assert GameService.evaluateTrick(trick, Suit.SPADE, Suit.HEART) == trick[0]

    }
}
