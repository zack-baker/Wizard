package io.zackb.wizard

import io.zackb.wizard.enums.Suit
import io.zackb.wizard.main.PlayingCard

class Main {
    static void main(String[] args) {
        //Card c = new Card("99",Suit.CLUB)
        PlayingCard c = new PlayingCard(rank: "2", suit: Suit.CLUB)
        println(c)
    }
}
