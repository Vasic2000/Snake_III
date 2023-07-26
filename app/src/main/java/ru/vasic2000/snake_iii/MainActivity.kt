package ru.vasic2000.snake_iii

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val head = View(this)
        head.layoutParams = LinearLayout.LayoutParams(100, 100)
        head.background = ContextCompat.getDrawable(this, R.drawable.circle)

        val container = findViewById<LinearLayout>(R.id.activity_main)
        container.addView(head)

        Thread(Runnable {
            while (true) {
                Thread.sleep(500)
                runOnUiThread {
                    (head.layoutParams as LinearLayout.LayoutParams).topMargin += 100
                    container.removeView(head)
                    container.addView(head)
                }
            }

        }).start()

    }
}