package com.example.its_2.stopwatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playFab.setOnClickListener {
            isRunning = !isRunning

            if(isRunning==true) {
                start()
            }
            else {
                pause()
            }
        }

        labButton.setOnClickListener(){
            recordLabTime()
        }

        resetFab.setOnClickListener(){
            reset()
        }
    }

    private fun start() {
        playFab.setImageResource(R.drawable.ic_pause_black_24dp)

        timerTask = timer(period=10){
            time++
            var sec = time / 100
            var milli = time % 100

            runOnUiThread({
                secTextView.text="$sec"
                milliTextView.text="$milli"
            })
        }
    }

    private fun pause(){
        playFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)

        timerTask?.cancel()
    }

    private fun reset(){
        timerTask?.cancel()

        // 모든 변수 초기화
        time = 0
        isRunning = false
        playFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text="0"
        milliTextView.text="00"

        // 모든 랩타임 제거
        labLayout.removeAllViews()
        lap = 1
    }

    private fun recordLabTime() {
        val textView = TextView(this)
        textView.text="$lap LAB : ${time/100}.${time%100}"

        labLayout.addView(textView, 0)
        lap++
    }
}