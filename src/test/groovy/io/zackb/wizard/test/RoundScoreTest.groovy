package io.zackb.wizard.test

import io.zackb.wizard.data.RoundScore

class RoundScoreTest extends GroovyTestCase{
    void 'test bid matching trick earns points'(){
        for(int bid=0;bid<10;bid++){
            RoundScore rs = new RoundScore(bid, bid, 1)
            assert rs.getScore() == 20 + 10*bid
        }
    }
    void 'test mismatched bid and tricks won loses points'(){
        int tricksWon = 11
        for(int bid=0;bid<tricksWon;bid++){
            RoundScore rs = new RoundScore(bid, tricksWon, 1)
            assert rs.getScore() == -10*(tricksWon-bid)
        }
        tricksWon = 0
        for(int bid = 10; bid>0; bid--){
            RoundScore rs = new RoundScore(bid, tricksWon, 1)
            assert rs.getScore() == -10*(bid-tricksWon)
        }
    }
}
