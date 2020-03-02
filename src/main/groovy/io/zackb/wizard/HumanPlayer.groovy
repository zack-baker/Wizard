package io.zackb.wizard

import io.zackb.wizard.data.Card
import io.zackb.wizard.data.RoundScore
import io.zackb.wizard.enums.Suit

class HumanPlayer extends Player{

    HumanPlayer(String name){
        super(name)
    }

    @Override
    /**
     * This function makes a bid for a human player for a given round
     * @param roundNumber
     */
    void makeBid(int roundNumber) {
        println("Making bid for ${name}")
        RoundScore existingScore = scores.find{it.roundNumber == roundNumber}
        if(existingScore){
            println("Existing bid exists for round ${roundNumber}. Bid is ${existingScore.bid}")
            return
        }

        int bid = -1
        println("${this}'s hand: ")
        printHand()
        while(bid < 0 || bid > roundNumber){
            println("Make bid [0-${roundNumber}]: ")
            bid = System.console().readLine() as int
        }
        RoundScore rs = new RoundScore(bid, roundNumber)
        scores << rs
    }


    Card playCard(Suit leadSuit) {
        int chosenCardIndex = 0
        Card chosenCard = null
        while(chosenCardIndex < 1 || chosenCardIndex > hand.size()) {
            println("${name}, play a card: ")
            printHand()
            chosenCardIndex = System.console().readLine() as int
            chosenCard = hand[chosenCardIndex-1]
            if(!chosenCard){
                chosenCardIndex = 0
                continue
            }

            List handLeadSuitCards = hand.findAll{it.isPlayingCard() && it.suit == leadSuit}
            if((chosenCard.isPlayingCard() && chosenCard.suit != leadSuit) && handLeadSuitCards){
                println("If playing a playing card, you must play a card of the leading suit (${leadSuit}). You may play a wizard or jester at any time")
                chosenCardIndex = 0
            }
        }
        hand.remove(chosenCard)
        return chosenCard



    }
}
