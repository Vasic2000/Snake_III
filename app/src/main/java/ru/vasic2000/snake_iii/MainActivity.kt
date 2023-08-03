package ru.vasic2000.snake_iii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import ru.vasic2000.snake_iii.SnakeCore.SnakeCore
import ru.vasic2000.snake_iii.SnakeCore.SnakeCore.StartTheGame

const val HEAD_SIZE = 100
const val FIELD_CELLS = 10

class MainActivity : AppCompatActivity() {

    private val allTable = mutableListOf<PartOfTail>()
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
        container.layoutParams = LinearLayout.LayoutParams(HEAD_SIZE * FIELD_CELLS, HEAD_SIZE * FIELD_CELLS)
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
        generateNewHuman(container)

    }

    private fun checkIfSnakeEatsPerson(head : View, human : View, container: FrameLayout) {
        if((head.left == human.left) && (head.top == human.top)) {
            generateNewHuman(container)
            addPartOfTail(head.top, head.left, container)
        }
    }

    private fun makeTailMove(headTop: Int, headLeft: Int, container: FrameLayout) {
        var tempTalePart: PartOfTail? = null
        for(index in 0 until allTable.size) {
            val talePart = allTable[index]
            container.removeView(talePart.imageView)
            if (index == 0) {
                tempTalePart = talePart
                allTable[index] =
                    PartOfTail(headTop, headLeft, drawPartOfTail(headTop, headLeft, container))
            } else {
                var anotherTempPartOfTail = allTable[index]
                tempTalePart?.let {
                    allTable[index] =
                        PartOfTail(it.top, it.left, drawPartOfTail(it.top, it.left, container))
                }
                tempTalePart = anotherTempPartOfTail
            }
        }
    }

    private fun addPartOfTail(top: Int, left: Int, container: FrameLayout) {
        val talePart = drawPartOfTail(top, left, container)
        allTable.add(PartOfTail(top, left, talePart))
    }

    private fun drawPartOfTail(top: Int, left: Int, container: FrameLayout): ImageView {
        val taleImage = ImageView(this)
        taleImage.setImageResource(R.drawable.circle_tail)
        taleImage.layoutParams = FrameLayout.LayoutParams(HEAD_SIZE/2, HEAD_SIZE/2)
        (taleImage.layoutParams as FrameLayout.LayoutParams).topMargin = top + HEAD_SIZE/4;
        (taleImage.layoutParams as FrameLayout.LayoutParams).leftMargin = left + HEAD_SIZE/4;

        container.addView(taleImage)

        return taleImage
    }

    fun generateNewHuman(container: FrameLayout) {
        human.layoutParams = FrameLayout.LayoutParams(HEAD_SIZE, HEAD_SIZE)
        human.setImageResource(R.drawable.baseline_cruelty)
        (human.layoutParams as FrameLayout.LayoutParams).topMargin = (0 until FIELD_CELLS).random() * HEAD_SIZE
        (human.layoutParams as FrameLayout.LayoutParams).leftMargin = (0 until FIELD_CELLS).random() * HEAD_SIZE
        container.removeView(human)
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
            makeTailMove(head.top, head.left, container)
            checkIfSnakeEatsPerson(head, human, container)
            container.removeView(head)
            container.addView(head)
        }
    }
}