package io.zackb.wizard.data

import io.zackb.wizard.enums.Suit
import io.zackb.wizard.service.CardService

class Deck {
    List<Card> cards = []

    /**
     * Construct the deck of cards
     * @param shuffle shuffle the deck after construction
     */
    Deck(boolean shuffle=true){
        //Add each playing card
        CardService.validRanks.each{rank->
            Suit.values().each{ suit ->
                cards <<  new PlayingCard(rank: rank, suit: suit)
            }
        }
        //add wizards and  jesters
        cards += [new WizardCard()]*4
        cards += [new JesterCard()]*4

        if(shuffle){
            this.shuffle()
        }
    }

    /** Wrapper function to shuffle the deck */
    void shuffle(){
        Collections.shuffle(cards)
    }

    /** Wrapper function to remove 1 card from the deck*/
    Card popCard(){
        if(cards.size()>0)
            return cards.pop()
        return null
    }
    /** Wrapper function to remove several cards from the deck
     *  Iterates over the singleton version of this function'
     *  @param cardsToPop the number of cards to remove from the deck
     *  @return the list of cards removed from the deck. This list may not neccesarily contain cardsToPop cards,
     *  since the user may request to pop more cards than are left in the deck
     */
    List<Card> popCards(int cardsToPop){
        if(cardsToPop<0){
            return []
        }
        List<Card> poppedCards = []
        while(cardsToPop>0 && cards.size()>0){
            poppedCards << this.popCard()
            cardsToPop-=1
        }

        return poppedCards
    }
}
