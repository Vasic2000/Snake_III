package ru.vasic2000.snake_iii

class SnakeCore {
    object SnakeCore {

        var nextMove: () -> Unit = {}
        var isPlay = true

        fun StartTheGame() {
            Thread(Runnable {
                while (true) {
                    Thread.sleep(500)
                    if (isPlay) {
                        nextMove()
                    }
                }
            }).start()
        }
    }
}