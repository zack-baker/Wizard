package io.zackb.wizard.main


abstract class Card {

    boolean isWizard(){
        return this instanceof WizardCard
    }
    boolean isJester(){
        return this instanceof JesterCard
    }
    boolean isPlayingCard(){
        return this instanceof PlayingCard
    }
}

