package io.zackb.wizard.main

import io.zackb.wizard.main.JesterCard
import io.zackb.wizard.main.PlayingCard
import io.zackb.wizard.main.WizardCard
import io.zackb.wizard.service.CardService

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

