package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("start")
        playFAB.setOnClickListener{
            if(isRunning){
                pause()
            }
            else{
                start()
            }
            isRunning = !isRunning
        }
        lapBtn.setOnClickListener{
            recordLap()
        }
        refreshFAB.setOnClickListener {
            reset()
        }
    }

    private fun start(){
        println("play")
        playFAB.setImageResource(R.drawable.ic_pause_black_24dp)
        timerTask  = timer(period = 10){
            time++
            val min = time / 1000 / 60
            val sec = time % 60/ 100
            val milli = time % 60 % 100
            runOnUiThread{
                minTv.text = "$min"
                secTv.text = "$sec"
                millTv.text = "$milli"
            }
        }
    }

    private fun pause(){
        playFAB.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        timerTask?.cancel()
    }

    private fun reset(){
        timerTask?.cancel()

        time = 0
        isRunning = false
        playFAB.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        minTv.text = getString(R.string.zero)
        secTv.text = getString(R.string.zero)
        millTv.text = getString(R.string.zero)

        lapLayout.removeAllViews()
        lap = 1
    }

    private fun recordLap(){
        val lapTime = this.time
        val lapTv = TextView(this)
        lapTv.text = "$lap LAB  : ${lapTime/60/100}.${lapTime%60/100}.${lapTime%100}"

        lapLayout.addView(lapTv,0)
        lap++
    }
}
