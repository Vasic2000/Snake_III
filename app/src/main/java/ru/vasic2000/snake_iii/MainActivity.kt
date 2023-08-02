package ru.vasic2000.snake_iii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import ru.vasic2000.snake_iii.SnakeCore.SnakeCore
import ru.vasic2000.snake_iii.SnakeCore.SnakeCore.StartTheGame

const val HEAD_SIZE = 100

class MainActivity : AppCompatActivity() {

    private val human by lazy {
        ImageView(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val head = View(this)
        head.layoutParams = FrameLayout.LayoutParams(HEAD_SIZE, HEAD_SIZE)
        head.background = ContextCompat.getDrawable(this, R.drawable.circle)

        val container = findViewById<FrameLayout>(R.id.container)
        container.addView(head)

        val ivArrowUp = findViewById<ImageView>(R.id.ivArrowUp)
        val ivArrowRight = findViewById<ImageView>(R.id.ivArrowRight)
        val ivArrowDown = findViewById<ImageView>(R.id.ivArrowDown)
        val ivArrowLeft = findViewById<ImageView>(R.id.ivArrowLeft)
        val ivPause = findViewById<ImageView>(R.id.ivPause)

        ivArrowUp.setOnClickListener {
            SnakeCore.nextMove = {move(Directions.UP, head, container)
            }
            SnakeCore.isPlay = true
            ivPause.setImageResource(R.drawable.baseline_pause_24)
        }
        ivArrowRight.setOnClickListener {
            SnakeCore.nextMove = {move(Directions.RIGHT, head, container)
            }
            SnakeCore.isPlay = true
            ivPause.setImageResource(R.drawable.baseline_pause_24)
        }
        ivArrowDown.setOnClickListener {
            SnakeCore.nextMove = {move(Directions.DOWN, head, container)
            }
            SnakeCore.isPlay = true
            ivPause.setImageResource(R.drawable.baseline_pause_24)
        }
        ivArrowLeft.setOnClickListener {
            SnakeCore.nextMove = {move(Directions.LEFT, head, container)
            }
            SnakeCore.isPlay = true
            ivPause.setImageResource(R.drawable.baseline_pause_24)
        }

        ivPause.setOnClickListener {
            SnakeCore.isPlay = !SnakeCore.isPlay
            if(SnakeCore.isPlay) {
                ivPause.setImageResource(R.drawable.baseline_pause_24)
            } else {
                ivPause.setImageResource(R.drawable.baseline_play_arrow_24)
            }
        }

        StartTheGame()

    }

    fun checkIfSnakeEatsPerson(head : View, human : View, container: FrameLayout) {
        if((head.left == human.left) && (head.top == human.top)) {
            container.removeView(human)
            generateNewHuman(container)
        }
    }

    fun generateNewHuman(container: FrameLayout) {
        human.layoutParams = FrameLayout.LayoutParams(HEAD_SIZE, HEAD_SIZE)
        human.setImageResource(R.drawable.baseline_cruelty)
        (human.layoutParams as FrameLayout.LayoutParams).topMargin = (1..8).random() * HEAD_SIZE
        (human.layoutParams as FrameLayout.LayoutParams).leftMargin = (1..7).random() * HEAD_SIZE
        container.addView(human)
    }

    fun move(directions: Directions, head: View, container: FrameLayout) {
        when(directions) {
            Directions.UP -> {(head.layoutParams as FrameLayout.LayoutParams).topMargin -=HEAD_SIZE}
            Directions.RIGHT -> {(head.layoutParams as FrameLayout.LayoutParams).leftMargin +=HEAD_SIZE}
            Directions.DOWN -> {(head.layoutParams as FrameLayout.LayoutParams).topMargin +=HEAD_SIZE}
            Directions.LEFT -> {(head.layoutParams as FrameLayout.LayoutParams).leftMargin -=HEAD_SIZE}
        }
        runOnUiThread {
            checkIfSnakeEatsPerson(head, human, container)
            container.removeView(head)
            container.addView(head)
        }
    }
}