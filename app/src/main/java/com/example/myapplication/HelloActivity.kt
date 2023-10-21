package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ArgumentKey
import com.example.myapplication.databinding.ActivityHelloBinding
import java.util.Locale

class HelloActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHelloBinding
    private var seconds: Long = 0
    private var initialTime: Long = 0
    private var running: Boolean = false
    private var wasRunning: Boolean = false
    private lateinit var timer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelloBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            val time = it.getString(ArgumentKey.NAME.name)

            val newTime = it.getString(Intent.EXTRA_TEXT)

            binding.timeView.text = getString(R.string.hello_any, time)
            if (time != null) {
                seconds = time.toLong()
                initialTime = time.toLong()
            }

//            if (newTime != null) {
//                seconds = newTime.toLong()
//                initialTime = newTime.toLong()
//            }


        }


        with(binding) {
            startBtn.setOnClickListener {
                startClick(seconds)
            }

            pauseBtn.setOnClickListener {
                pauseClick()
            }

            resetBtn.setOnClickListener {
                resetClick(initialTime)
            }

            savedInstanceState?.let {
                seconds = it.getLong(State.SECONDS.name)
                running = it.getBoolean(State.RUNNING.name)
                wasRunning = it.getBoolean(State.WAS_RUNNING.name)
            }

            runTimer(seconds)
        }
    }

    private fun runTimer(time: Long) {
        val minutes =  (time) / 60;
        val seconds = (time) % 60;
        val timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        binding.timeView.text = timeFormatted
    }



    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(State.SECONDS.name, seconds)
        outState.putBoolean(State.RUNNING.name, running)
        outState.putBoolean(State.WAS_RUNNING.name, running)
        super.onSaveInstanceState(outState)
    }

    private fun pauseClick(){
        timer.cancel()
    }


    private fun startClick(time: Long){


        timer = object : CountDownTimer(time * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes_timer =  (millisUntilFinished / 1000) / 60;
                val seconds_timer = (millisUntilFinished / 1000) % 60;
                binding.timeView.text =String.format(Locale.getDefault(), "%02d:%02d", minutes_timer, seconds_timer)
                seconds = millisUntilFinished / 1000
            }
            override fun onFinish() {
                binding.timeView.text = "00:00"
            }
        }.start()
    }

    private fun resetClick(time: Long){
        timer.cancel()
        timer.onTick(time * 1000)
        val minutes =  (time) / 60;
        val seconds = (time) % 60;
        val timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        binding.timeView.text = timeFormatted
    }

    override fun onStart() {
        super.onStart()
        Log.e(this.javaClass.name, ">>> onStart")
    }

    override fun onResume() {
        super.onResume()
        running = wasRunning
        Log.e(this.javaClass.name, ">>> onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(this.javaClass.name, ">>> onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e(this.javaClass.name, ">>> onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(this.javaClass.name, ">>> onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(this.javaClass.name, ">>> onRestart")
    }
}

enum class State{
    RUNNING, SECONDS, WAS_RUNNING
}