package io.zackb.wizard.main

import io.zackb.wizard.main.Deck
import io.zackb.wizard.main.Player

class Game {
    List<Player> players
    Deck deck

    /**
     * Default constructor for the Game. Game objects
     * should be build using the {@link io.zackb.wizard.service.GameService#createGame}
     * service method
     *
     * @param players the list of players in the game
     * @param deck the deck to be used in the game
     */
    Game(List<Player> players, Deck deck){
        this.players = players
        this.deck = deck
    }
}
