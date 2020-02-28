package io.zackb.wizard.test

import io.zackb.wizard.AiPlayer
import io.zackb.wizard.HumanPlayer
import io.zackb.wizard.Player
import io.zackb.wizard.data.Card
import io.zackb.wizard.data.JesterCard
import io.zackb.wizard.data.PlayingCard
import io.zackb.wizard.data.RoundScore
import io.zackb.wizard.data.WizardCard
import io.zackb.wizard.enums.Suit
import io.zackb.wizard.service.GameService

class GameTest extends GroovyTestCase{
    void 'test trick evaluator wizard wins trick'(){
        Card c1 = new WizardCard()
        Card c2 = new JesterCard()
        Card c3 = new PlayingCard("A", Suit.SPADE)
        Card c4 = new PlayingCard("A",Suit.HEART)
        List<Card> trick = [c1,c2,c3,c4]
        Collections.shuffle(trick)
        assert GameService.evaluateTrick(trick, Suit.SPADE) == c1
        trick = [c2,c3,c4,c1]
        assert GameService.evaluateTrick(trick, Suit.SPADE) == c1
        Card c5 = new WizardCard()
        trick = [c5,c1,c2,c3]
        assert GameService.evaluateTrick(trick, Suit.SPADE) == c5

    }
    void 'test trick evaluator trump suit wins trick with no wizard'(){
        Card c1 = new PlayingCard('2',Suit.SPADE)
        Card c2 = new PlayingCard('A', Suit.HEART)
        Card c3 = new PlayingCard('A', Suit.CLUB)
        Card c4 = new PlayingCard('A',Suit.DIAMOND)
        Card c5 = new JesterCard()
        List<Card> trick = [c1,c2,c3,c4,c5]
        Collections.shuffle(trick)
        assert GameService.evaluateTrick(trick, Suit.SPADE) == c1
    }
    void 'test trick evaluator lead suit wins trick with no wizard and trump'(){
        Card c1 = new PlayingCard('2',Suit.HEART)
        Card c2 = new PlayingCard('A',Suit.SPADE)
        Card c3 = new PlayingCard('A',Suit.DIAMOND)
        Card c4 = new PlayingCard('A',Suit.CLUB)
        List<Card> trick = [c1,c2,c3,c4]
        assert GameService.evaluateTrick(trick, null) == c1
    }
    void 'test high card all same suit wins trick '(){
        Card c1 = new PlayingCard('7', Suit.SPADE)
        Card c2 = new PlayingCard('10', Suit.SPADE)
        Card c3 = new PlayingCard('3', Suit.SPADE)
        Card c4 = new PlayingCard('5', Suit.SPADE)
        List<Card> trick = [c1,c2,c3,c4]
        Collections.shuffle(trick)
        assert GameService.evaluateTrick(trick,Suit.SPADE) == c2
        assert GameService.evaluateTrick(trick,null) == c2
    }
    void 'test order players by score'(){
        HumanPlayer hp1 = new HumanPlayer("Phil")
        HumanPlayer hp2 = new HumanPlayer("Larry")
        AiPlayer ai = new AiPlayer("Steve Gorbatron")
        hp1.scores = [new RoundScore(1,1,0), new RoundScore(2,2,1),new RoundScore(2,3,2), new RoundScore(4,4,1)]
        ai.scores = [new RoundScore(1,1,1), new RoundScore(1,2,1), new RoundScore(1,3,1), new RoundScore(3,4,3)]
        hp2.scores = [new RoundScore(1,1,0), new RoundScore(2,2,0),new RoundScore(3,3,0), new RoundScore(4,4,0)]
        List<Player> players = [hp1, ai, hp2]

        assert GameService.sortPlayersByScore(players) == [ai,hp1, hp2]

    }



}
