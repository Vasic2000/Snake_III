package ru.vasic2000.snake_iii

class SnakeCore {
    object SnakeCore {

        var nextMove: () -> Unit = {}

        fun StartTheGame() {
            Thread(Runnable {
                while (true) {
                    Thread.sleep(500)
                    nextMove()
                    }
            }).start()
        }
    }
}