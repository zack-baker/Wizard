package io.zackb.wizard.service

import io.zackb.wizard.AiPlayer
import io.zackb.wizard.Game
import io.zackb.wizard.HumanPlayer
import io.zackb.wizard.Player
import io.zackb.wizard.enums.Suit
import io.zackb.wizard.data.Card
import io.zackb.wizard.data.Deck

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
        while(numPlayers < 2){
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

        println("Enter the number of tricks to play. if empty, defaults to ${60/numPlayers}")
        int numRounds = System.console().readLine() as int
        if(numRounds<0 || numRounds > 60/numPlayers){
            numRounds = 60/numPlayers
        }

        Deck deck = new Deck()
        Game game = new Game(players, deck, numRounds)

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
            //Order players in order based on the round number
            game.players = reorderStartingWith(game.players[(round-1)%(game.players.size())],game.players)
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

            Player previousTrickWinner = null

            for(int trickNum = 0; trickNum < round; trickNum++){
                //Reorder deal order based on player who won last trick
                if(previousTrickWinner)
                    game.players = reorderStartingWith(previousTrickWinner, game.players)

                //3.1 Each player plays a card from their hand in order
                List<Card> trickCards = []
                game.players.each{player->
                    trickCards << player.playCard(trickCards)
                }
                //3.2 Evaluate trick winner
                Card winningCard = evaluateTrick(trickCards, trumpSuit)
                previousTrickWinner = game.players[trickCards.indexOf(winningCard)]

                //3.3 increment score of trickWinner
                previousTrickWinner.increaseScore(round)
            }
            //Start a new round.
        }

        //Display scores and show winner
        println("Scores are: ")
        game.players.sort{player->
            int scoreSum = 0
            player.scores.each{score->
                scoreSum += score.getScore()
            }
            scoreSum
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

    static List reorderStartingWith(def s, List list){
        int startIndex = list.indexOf(s)
        if(!startIndex) {
            println("[reorderStartingWith] Error - list ${list} does not contain value ${s}")
            return list
        }
        List newList = []
        for(int i=startIndex;i<startIndex+list.size();i++){
            newList << list[i%list.size()]
        }
        return newList
    }

    static Card evaluateTrick(List<Card> trick, Suit trumpSuit){
        //set the lead suit. If it is a playing card, set the lead suit as the suit of the card played,
        //otherwise, set it to null.

        Suit leadSuit
        if(trick[0].isPlayingCard()){
            leadSuit = trick[0].suit
        }else{
            leadSuit = null
        }

        Card winningCard = null
        trick.each{card ->
            if(!winningCard) {
                winningCard = card
            }
            else {
                //if the winning card doesn't beat the upcoming card, set the current card to be the winning card
                if (CardService.cardComparator(card, winningCard, trumpSuit, leadSuit) == 1) {
                    winningCard = card
                }
            }
        }
        return winningCard
    }

    /**
     * This method returns the list of players in order of score from highest to lowest
     * @param players the list of players to sort
     * @return the list of players in order of score
     */
    static List<Player> sortPlayersByScore(List<Player> players){
        //the list must be reversed, because the sort closure sorts lowest to highest, when we want to sort highest to lowest
        return players.sort{player->
            player.getTotalScore()
        }.reverse()

    }

}
