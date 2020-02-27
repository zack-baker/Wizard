package io.zackb.wizard.test

import io.zackb.wizard.enums.Suit
import io.zackb.wizard.main.Card
import io.zackb.wizard.main.PlayingCard
import io.zackb.wizard.main.WizardCard
import io.zackb.wizard.main.JesterCard
import io.zackb.wizard.service.CardService

import javax.swing.plaf.SeparatorUI

class CardTest extends GroovyTestCase{


    void 'test creating a traditional card'(){
        PlayingCard c = new PlayingCard(rank: "2", suit: Suit.HEART)
        assert c.toString() == "2♥"
    }
    void 'test playing card map constructor is equivalent to argument constructor'(){
        PlayingCard args = new PlayingCard('A',Suit.SPADE)
        PlayingCard map = new PlayingCard(rank: 'A', suit: Suit.SPADE)
        assert args == map
    }
    void 'test providing an invalid rank throws an InvalidRankException'(){
        try{
            PlayingCard c = new PlayingCard("Z",Suit.CLUB)
        }catch(Exception ire){
            assert true
            return
        }
        assert false
    }
    void 'test wizard toString'(){
        WizardCard wiz = new WizardCard()
        assert wiz.toString() == "WIZ"
    }
    void 'test jester toString'(){
        JesterCard jester = new JesterCard()
        assert jester.toString() == "JEST"
    }
    void 'test wizard is wizard'(){
        WizardCard wiz = new WizardCard()
        assert wiz.isWizard()
    }
    void 'test jester is jester'(){
        JesterCard jester = new JesterCard()
        assert jester.isJester()
    }
    void 'test playing card is playing card'(){
        PlayingCard playingCard = new PlayingCard(rank: "A", suit: Suit.SPADE)
        assert playingCard.isPlayingCard()
    }
    void 'test comparison 1 hand wizard loses to played wizard'(){
        WizardCard hand = new WizardCard()
        WizardCard down = new WizardCard()
        assert CardService.cardComparator(hand, down) == -1
    }
    void 'test comparison 2 hand wizard wins against non-wizard'(){
        WizardCard hand = new WizardCard()
        PlayingCard down1 = new PlayingCard(rank: "A", suit: Suit.SPADE )
        assert CardService.cardComparator(hand, down1, Suit.SPADE, Suit.SPADE) == 1
        assert CardService.cardComparator(hand, down1, Suit.HEART, Suit.SPADE) == 1
        assert CardService.cardComparator(hand, down1, Suit.HEART, Suit.CLUB) == 1
        JesterCard down2 = new JesterCard()
        assert CardService.cardComparator(hand, down2) == 1
    }
    void 'test comparison 3 Jester loses to any card'(){
        JesterCard hand = new JesterCard()
        WizardCard down1 = new WizardCard()
        assert CardService.cardComparator(hand,down1) == -1
        JesterCard down2 = new JesterCard()
        assert CardService.cardComparator(hand, down2) == -1
        PlayingCard down3 = new PlayingCard(rank: "A", suit: Suit.SPADE)
        assert CardService.cardComparator(hand, down3) == -1
    }
    void 'test comparison 4 any non jesters beat jesters'(){
        PlayingCard hand1 = new PlayingCard(rank: "2", suit: Suit.SPADE)
        JesterCard down = new JesterCard()
        assert CardService.cardComparator(hand1, down, Suit.HEART, Suit.CLUB) == 1
        assert CardService.cardComparator(hand1, down, Suit.HEART, Suit.SPADE) == 1
        assert CardService.cardComparator(hand1, down, Suit.SPADE) == 1
        WizardCard hand2 = new WizardCard()
        assert CardService.cardComparator(hand2, down) == 1
    }
    void 'test comparison 5 trump suit beats non-trump suit cards'(){
        PlayingCard hand = new PlayingCard('2',Suit.SPADE)
        PlayingCard down = new PlayingCard('A', Suit.HEART)
        assert CardService.cardComparator(hand, down, Suit.SPADE, Suit.HEART) == 1
        assert CardService.cardComparator(hand, down, Suit.SPADE, Suit.CLUB) == 1
    }
    void 'test comparison 6 non trump suit loses to trump suit'() {
        PlayingCard hand = new PlayingCard('A', Suit.HEART)
        PlayingCard down = new PlayingCard('2', Suit.SPADE)
        assert CardService.cardComparator(hand, down, Suit.SPADE, Suit.HEART) == -1
        assert CardService.cardComparator(hand, down, Suit.SPADE, Suit.CLUB) == -1
    }
    void 'test comparison 7 lead suit beats non-lead suit'(){
        PlayingCard hand = new PlayingCard('2', Suit.SPADE)
        PlayingCard down = new PlayingCard('A', Suit.HEART)
        assert CardService.cardComparator(hand, down, Suit.CLUB, Suit.SPADE) == 1
    }
    void 'test comparison 8 non-lead suit loses to lead suit'(){
        PlayingCard hand = new PlayingCard('A', Suit.HEART)
        PlayingCard down = new PlayingCard('2',Suit.SPADE)
        assert CardService.cardComparator(hand,down, Suit.CLUB, Suit.SPADE) == -1
    }
    void 'test comparison 9 same-suit cards are ordered by rank'(){
        PlayingCard hand1 = new PlayingCard('2', Suit.SPADE)
        PlayingCard down1 = new PlayingCard('A', Suit.SPADE)
        assert CardService.cardComparator(hand1, down1, Suit.SPADE, Suit.HEART) == -1
        assert CardService.cardComparator(hand1, down1, Suit.HEART, Suit.SPADE) == -1
        assert CardService.cardComparator(hand1, down1, Suit.CLUB, Suit.DIAMOND) == -1
        PlayingCard hand2 = new PlayingCard('A',Suit.SPADE)
        PlayingCard down2 = new PlayingCard('2', Suit.SPADE)
        assert CardService.cardComparator(hand2, down2, Suit.SPADE, Suit.HEART) == 1
        assert CardService.cardComparator(hand2, down2, Suit.HEART, Suit.SPADE) == 1
        assert CardService.cardComparator(hand2, down2, Suit.CLUB, Suit.DIAMOND) == 1
    }
    /*
    void 'test Wizard is high'(){
        Card nonWizard = new Card()
        Card wizard = new Card(wizard: true)

        assert wizard > nonWizard
    }
    */
}
