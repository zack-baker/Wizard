package io.zackb.wizard.data



import io.zackb.wizard.enums.Suit
import io.zackb.wizard.exceptions.InvalidRankException
import io.zackb.wizard.service.CardService

class PlayingCard extends Card {
    String rank
    Suit suit

    /**
     * Default constructor, creates the card setting rank and suit.
     * If rank is not a valid rank (2-10,J,Q,K,A), throw an exception
     * @param rank the rank of the card, must be 2-10, J, Q, K, or A
     * @param suit the {@link io.zackb.wizard.enums.Suit} of the card
     */
    PlayingCard(String rank, Suit suit){
        if(!(rank in CardService.validRanks)){
            throw new InvalidRankException("BAD RANK")
        }
        this.rank = rank
        this.suit = suit
    }
    /** Override the map constructor to redirect to the primary constructors
     *  @param map the map of parameters to set
     */
    PlayingCard(Map map){
        this(map["rank"], map["suit"])
    }


    @Override
    String toString() {
        return rank + CardService.getSuitString(suit)
    }

    @Override
    /** This function compares two cards.
     * @param card the card to compare. If not a PlayingCard, return false
     * @return true if the rank and suit of the provided card match the rank and suit of the card
     */
    boolean equals(Object card) {
        if(card.class != PlayingCard){
            return false
        }
        return (this.rank == card.rank && this.suit == card.suit)
    }
}
