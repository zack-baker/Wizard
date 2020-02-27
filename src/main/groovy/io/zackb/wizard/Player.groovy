package io.zackb.wizard

import io.zackb.wizard.data.Card
import io.zackb.wizard.data.RoundScore

abstract class Player {
    List<RoundScore> scores

    List<Card> hand
    String name

    Player(String name){
        this.name = name
    }

    boolean isHuman(){
        return this instanceof HumanPlayer
    }
    boolean isAi(){
        return this instanceof AiPlayer
    }

    abstract void makeBid(int roundNumber)

    /**
     * This method prints the players hand.
     * e.g. if the player has a 3 of Clubs and a Jack of diamonds, this function will print
     *  * (1) 3♣
     *  * (2) J♦
     */
    void printHand(){
        if(hand){
            hand.eachWithIndex{card, index ->
                println("* (${index+1}): ${card}")
            }
        }
    }
    @Override
    /**
     * Overriden toString method. For now, just display the player name
     */
    String toString() {
        return name
    }
}
