package io.zackb.wizard.test

import io.zackb.wizard.HumanPlayer
import io.zackb.wizard.AiPlayer
import io.zackb.wizard.data.RoundScore

class PlayerTest extends GroovyTestCase {
    void 'test create HumanPlayer with parent constructor'(){
        HumanPlayer hp = new HumanPlayer("Greg")
        assert hp instanceof HumanPlayer
    }
    void 'test create AiPlayer with parent constructor'(){
        AiPlayer ai = new AiPlayer("RB-88")
        assert ai instanceof AiPlayer
    }
    void 'test HumanPlayer is human'(){
        HumanPlayer hp = new HumanPlayer("Nick")
        assert hp.isHuman()
    }
    void 'test AiPlayer is AI'(){
        AiPlayer ai = new AiPlayer()
        assert ai.isAi()
    }
    void 'test get total scores'(){
        HumanPlayer hp = new HumanPlayer("Tonto")
        hp.scores = [new RoundScore(1,1,1),new RoundScore(2,2,0), new RoundScore(1,3,2)]
        assert hp.getTotalScore() == 0
        AiPlayer ai = new AiPlayer("Sentinel")
        ai.scores = [new RoundScore(1,1,1), new RoundScore(2,2,2), new RoundScore(3,3,3)]
        assert ai.getTotalScore() == 120

    }
    void 'test increment player score'(){
        HumanPlayer hp = new HumanPlayer("Ryan")
        int roundNumber = 1
        hp.scores << new RoundScore(3,roundNumber)
        hp.increaseScore(1)
        assert hp.scores.find{it.roundNumber==roundNumber}.tricksWon == 1

    }
}
