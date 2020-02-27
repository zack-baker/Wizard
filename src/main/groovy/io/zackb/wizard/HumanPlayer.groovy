package io.zackb.wizard.main

import io.zackb.wizard.main.Player
import io.zackb.wizard.main.RoundScore

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
        RoundScore existingScore = scores.find{it.roundNumber == roundNumber}
        if(existingScore){
            println("Existing bid exists for round ${roundNumber}. Bid is ${existingScore.bid}")
            return
        }

        int bid = -1
        println("${this}'s hand: ${this.printHand()}")
        while(bid < 0 && bid > roundNumber){
            println("Make bid [0-${roundNumber}]: ")
            bid = System.console().readLine() as int
        }
        RoundScore rs = new RoundScore(bid, roundNumber)
        scores << rs
    }
}
