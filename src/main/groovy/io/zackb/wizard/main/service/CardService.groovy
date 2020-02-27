package io.zackb.wizard.service

import io.zackb.wizard.enums.Suit
import io.zackb.wizard.main.Card
import io.zackb.wizard.main.PlayingCard
import io.zackb.wizard.main.WizardCard

class CardService {
    static Class serviceClass = PlayingCard

    static List validRanks = ["2","3","4","5","6","7","8","9","10","J","Q","K","A"]
    static String getSuitString(Suit s){
        switch(s){
            case Suit.SPADE:
                return "♠"
            case Suit.HEART:
                return "♥"
            case Suit.CLUB:
                return "♣"
            case Suit.DIAMOND:
                return "♦"
        }
    }
    /**
     * This method compares two cards, c1 and c2, determining which card will win a trump
     * factoring in the trump suit, if any. C1 is assumed to be the player's card, and c2 is
     * the card down to be evaluated
     * Cards are ordered as follows:
     *
     * 1. (Wizard,Wizard): loss, as the first wizard played wins the trick automatically
     * 2. (Wizard, nonWizard): win
     * 3. (Jester, any): loss
     * 4. (non-Jester, Jester): win
     * 5. (Playing Card (trump-suit), Playing Card (off-trump)): win
     * 6. (Playing Card (off-trump), Playing Card (trump-suit)): loss
     * 7. (Playing Card (lead-suit), Playing Card (off-lead)): win
     * 8. (Playing Card (off-lead)), Playing Card (lead-suit)): loss
     * 9. (Playing Card (*-suit), Playing Card (*-suit)): high rank wins
     *
     * @param c1 the target card
     * @param c2 the card being compared against
     * @param lead the leading suit in the hand.
     * @param trump the trump suit, if any
     * @return the status of the comparison between c1 and c2. If c1 would beat c2, return ComparatorStatus.WINS.
     * If c1 would lose to c2, return C
     */
    static int cardComparator(Card c1, Card c2, Suit trump = null, Suit lead=null){
        //set the lead if possible and it is not provided
        if(c2.isPlayingCard() && !lead){
            lead = c2.suit
        }
        // CASE 1
        if(c1.isWizard() && c2.isWizard()){
            return -1
        }
        // CASE 2
        if(c1.isWizard() && !c2.isWizard()){
            return 1
        }
        // CASE 3
        if(c1.isJester()) {
            return -1
        }
        // CASE 4
        if(!c1.isJester() && c2.isJester()){
            return 1
        }
        if(trump){
            // CASE 5
            if(c1.suit == trump && c2.suit != trump){
                return 1
            }
            // CASE 6
            if(c1.suit != trump && c2.suit == trump){
                return -1
            }
        }
        // CASE 7
        if(c1.suit == lead && c2.suit != lead){
            return 1
        }
        // CASE 8
        if(c1.suit != lead && c2.suit == lead){
            return -1
        }
        // CASE 9
        if(c1.isPlayingCard() && c2.isPlayingCard()){
            return CardService.validRanks.indexOf(c1.rank) <=> CardService.validRanks.indexOf(c2.rank)
        }
        throw new RuntimeException("Uncaught comparison: ${c1} compared to ${c2} (Trump: ${trump}, lead: ${lead}")
    }


}
