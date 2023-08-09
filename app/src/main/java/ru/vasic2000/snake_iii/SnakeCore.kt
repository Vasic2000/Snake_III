package ru.vasic2000.snake_iii

class SnakeCore {
    object SnakeCore {

        var nextMove: () -> Unit = {}
        var isPlay = true
        val gameThread: Thread = Thread {
            while (true) {
                Thread.sleep(500)
                if (isPlay) {
                    nextMove()
                }
            }
        }

        init{
            gameThread.start()
        }


        fun StartTheGame() {
            isPlay = true

        }
    }
}