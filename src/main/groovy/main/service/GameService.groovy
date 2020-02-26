package io.zackb.wizard.service

import io.zackb.wizard.Game
import io.zackb.wizard.HumanPlayer
import io.zackb.wizard.enums.Suit
import io.zackb.wizard.main.AiPlayer
import io.zackb.wizard.main.Card
import io.zackb.wizard.main.Deck
import io.zackb.wizard.main.Game
import io.zackb.wizard.main.Player

class GameService {
    static Class serviceClass = Game

    /**
     * This method creates a Game object, going through
     * the hoops of requesting information about players
     *
     * @return the new Game object
     */
    static Game createGame(){
        int numPlayers = 0
        while(numPlayers < 0){
            println("Enter the number of players: ")
            numPlayers = System.console().readLine() as int
        }
        List<Player> players = []
        for(int i=0;i<numPlayers;i++){
            String playerType = ""
            while(playerType!="H" && playerType!="A"){
                println("Enter the player type for player ${i} [A,H]: ")
                playerType = System.console().readLine()
            }
            String name = ""
            while(!name){
                println("Enter the player name")
                name = System.console().readLine()
            }
            if(playerType=="H"){
                players << new HumanPlayer(name)
            }
            else if(playerType=="A"){
                players << new AiPlayer(name)
            }
        }
        Deck deck = new Deck()
        Game game = new Game(players, deck)

        return game
    }

    /**
     * This method iterates through the rounds of the game, playing each round until the game is over
     * @param game the Game object to play
     */
    static void playGame(Game game){
        //calculate the number of rounds to play; Calculated by deck size / number of players
        int numRounds = game.deck.size() / game.players.size()
        //filter human and non-human players for easy access throughout the game
        List<HumanPlayer> humans = game.players.findAll{player.isHuman()}
        List<AiPlayer> ais = game.players.findAll{player.isAi()}
        for(int round = 1; round < numRounds+1; round++){
            //PHASE 1 Deal hands to each player
            game.players.each{player->
                player.hand = GameService.dealHand(round, game.deck)
            }
            //1.1 Establish trump suit
            Suit trumpSuit = null
            Card trumpCard = game.deck.popCard()
            //if there is no trump card (last round), then keep the trumpSuit null
            if(trumpCard){
                //1.1.1 If the trump card is a PlayingCard, the trump suit becomes the suit of the playing card
                if(trumpCard.isPlayingCard()){
                    trumpSuit = trumpCard.suit
                }
                //1.1.2 If the trump card is a wizard, the dealer chooses the trump suit
                else if(trumpCard.isWizard()){
                    trumpSuit = GameService.chooseSuit()
                }
                //1.1.3 If the trump card is a Jester, there is no trump suit, so do nothing
            }
            //PHASE 2 Each player bids
            game.players.each{player ->
                player.makeBid(round)
            }

            //PHASE 3 Play round. Iterate through each player and acquire 1 card per player, then evaluate the trick. Award one point to the winner
            

        }
    }

    static List<Card> dealHand(int handsize, Deck deck){
        return deck.popCard(handsize)
    }

    static Suit chooseSuit(){
        String chosenSuit = ""
        while(chosenSuit != "SPADES" && chosenSuit != "HEARTS" && chosenSuit != "CLUBS" && chosenSuit != "DIAMONDS"){
            println("Choose the trump suit [SPADES,HEARTS,CLUBS,DIAMONDS]:")
            chosenSuit = System.console().readLine()
        }
        switch (chosenSuit){
            case "SPADES": return Suit.SPADE
            case "HEARTS": return Suit.HEART
            case "CLUBS": return Suit.CLUB
            case "DIAMONDS": return Suit.DIAMOND
            default: return null
        }

    }
}
