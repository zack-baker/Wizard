package io.zackb.wizard

import io.zackb.wizard.service.GameService
import java.io.Console

class Main {
    static void main(String[] args) {
        println("🧙 Welcome to Wizard! 🧙")
        Game g = GameService.createGame()
        println("Starting Game ~~${g}~~")
        GameService.playGame(g)

        //Game game = GameService.createGame()
    }
}
