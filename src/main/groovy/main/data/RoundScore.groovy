package io.zackb.wizard.main

class RoundScore {
    int bid
    int tricksWon
    int roundNumber

    RoundScore(int bid, int roundNumber){
        this.bid = bid
        this.roundNumber = roundNumber
    }
    RoundScore(int bid, int tricksWon, int roundNumber){
        this.bid = bid
        this.tricksWon = tricksWon
        this.roundNumber = roundNumber
    }

    /**
     * This function calculates the score for the round.
     * The score is calculated as a simple function:
     * 1. If the number of bids made equals the number of tricks won in the round, the score for the round is 20 + 10 times the bid
     * 2. If the number of bids is not equal to the number of tricks won, the score for the round is -10 points per trick won over or under the bid
     * @return
     */
    int getScore(){
        if(bid==tricksWon){
            return 20 + bid*10
        }else{
            return -10*Math.abs(bid-tricksWon)
        }
    }
}
