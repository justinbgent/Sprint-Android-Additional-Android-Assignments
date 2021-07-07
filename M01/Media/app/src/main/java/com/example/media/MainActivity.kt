package com.example.media

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.MediaPlayer


class MainActivity : AppCompatActivity() {

    //Going to use the video's stats throughout activity
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // User can't start until video is loaded
        play_btn.isEnabled = false
        pause_btn.isEnabled = false

        play_btn.setOnClickListener {
            video_view.start()
        }
        pause_btn.setOnClickListener {
            video_view.pause()
        }

        // Display time minutes
        val currentTimeMinutes = "0:"

        //Check video progress and update seekBar and currentTime every second
        val handler = Handler()
        runOnUiThread(object : Runnable{
            override fun run() {
                if (mediaPlayer != null){
                    seek_bar.progress = (mediaPlayer as MediaPlayer).currentPosition
                    val seconds = mediaPlayer!!.currentPosition/1000
                    val currentSeconds: String = String.format("%02d", seconds)
                    val currentTime = currentTimeMinutes + currentSeconds
                    txt_current.text = currentTime
                }
                handler.postDelayed(this, 1000)
            }
        })

        // Where seekBar is dragged by user video will go to user selected position
        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null){
                    video_view.seekTo(seekBar.progress)
                }
            }
        })
    }


    override fun onStart() {
        super.onStart()
        //Set video to be displayed
        video_view.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.img_9724))

        video_view.setOnPreparedListener {
            play_btn.isEnabled = true
            pause_btn.isEnabled = true
            if (it != null){
                //Setup seekBar points
                seek_bar.max = it.duration
                //Display final video time
                val endTime = "0:" + (it.duration/1000).toString()
                txt_end.text = endTime
                //Set mediaPlayer variable
                mediaPlayer = it
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //Ensure video doesn't play in background
        video_view.pause()
    }
}
