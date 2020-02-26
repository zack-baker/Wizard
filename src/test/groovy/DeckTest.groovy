package io.zackb.wizard.test

import io.zackb.wizard.main.Card
import io.zackb.wizard.main.Deck

class DeckTest extends GroovyTestCase{

    void 'test deck constructor'(){
        Deck d = new Deck()
        assert d.class == Deck
    }
    void 'test deck has 60 cards'(){
        Deck d = new Deck()
        assert d.cards.size() == 60
    }
    void 'test deck pop reduces deck size'(){
        Deck d = new Deck()
        d.popCard()
        assert  d.cards.size() == 59
    }
    void 'test popCards remove several cards'(){
        Deck d = new Deck()
        List<Card> poppedCards = d.popCards(5)
        assert poppedCards.size() == 5
    }
    void 'test popCards removing more cards than in deck does not fail'(){
        Deck d = new Deck()
        int startingDecksize = d.cards.size()
        List<Card> poppedCards = d.popCards(startingDecksize+10)
        assert poppedCards.size() == startingDecksize
    }


}
