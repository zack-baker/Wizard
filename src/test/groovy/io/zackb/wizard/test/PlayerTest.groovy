package io.zackb.wizard.test

import io.zackb.wizard.main.HumanPlayer
import io.zackb.wizard.main.AiPlayer

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
        HumanPlayer hp = new HumanPlayer()
        assert hp.isHuman()
    }
    void 'test AiPlayer is AI'(){
        AiPlayer ai = new AiPlayer()
        assert ai.isAi()
    }
}
