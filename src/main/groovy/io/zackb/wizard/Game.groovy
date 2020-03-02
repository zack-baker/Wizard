package io.zackb.wizard

import io.zackb.wizard.data.Deck

class Game {
    List<Player> players
    Deck deck
    int numRounds

    /**
     * Default constructor for the Game. Game objects
     * should be build using the {@link io.zackb.wizard.service.GameService#createGame}
     * service method
     *
     * @param players the list of players in the game
     * @param deck the deck to be used in the game
     */
    Game(List<Player> players, Deck deck, int numRounds){
        this.players = players
        this.deck = deck
        this.numRounds = numRounds
    }

    @Override
    /** Overridden toString method. Prints player names */
    String toString() {
        return players.collect{it.name}.join(", ")
    }
}
