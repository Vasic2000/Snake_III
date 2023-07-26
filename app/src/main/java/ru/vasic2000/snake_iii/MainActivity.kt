package ru.vasic2000.snake_iii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import ru.vasic2000.snake_iii.SnakeCore.SnakeCore
import ru.vasic2000.snake_iii.SnakeCore.SnakeCore.StartTheGame

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val head = View(this)
        head.layoutParams = LinearLayout.LayoutParams(100, 100)
        head.background = ContextCompat.getDrawable(this, R.drawable.circle)

        val container = findViewById<LinearLayout>(R.id.activity_main)
        container.addView(head)

        val ivArrowUp = findViewById<ImageView>(R.id.ivArrowUp)
        val ivArrowRight = findViewById<ImageView>(R.id.ivArrowRight)
        val ivArrowDown = findViewById<ImageView>(R.id.ivArrowDown)
        val ivArrowLeft = findViewById<ImageView>(R.id.ivArrowLeft)

        ivArrowUp.setOnClickListener {
            SnakeCore.nextMove = {move(Directions.UP, head, container)
            }
        }
        ivArrowRight.setOnClickListener {
            SnakeCore.nextMove = {move(Directions.RIGHT, head, container)
            }
        }
        ivArrowDown.setOnClickListener {
            SnakeCore.nextMove = {move(Directions.DOWN, head, container)
            }
        }
        ivArrowLeft.setOnClickListener {
            SnakeCore.nextMove = {move(Directions.LEFT, head, container)
            }
        }

        StartTheGame()

    }

    fun move(directions : Directions, head: View, container: LinearLayout) {
        when(directions) {
            Directions.UP -> {(head.layoutParams as LinearLayout.LayoutParams).topMargin -=100}
            Directions.RIGHT -> {(head.layoutParams as LinearLayout.LayoutParams).leftMargin +=100}
            Directions.DOWN -> {(head.layoutParams as LinearLayout.LayoutParams).topMargin +=100}
            Directions.LEFT -> {(head.layoutParams as LinearLayout.LayoutParams).leftMargin -=100}
        }
        runOnUiThread {
            container.removeView(head)
            container.addView(head)
        }
    }
}