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

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play_btn.isEnabled = false
        pause_btn.isEnabled = false

        play_btn.setOnClickListener {
            video_view.start()
        }
        pause_btn.setOnClickListener {
            video_view.pause()
        }

        val handler = Handler()
        runOnUiThread(object : Runnable{
            override fun run() {
                if (mediaPlayer != null){
                    seek_bar.progress = (mediaPlayer as MediaPlayer).currentPosition
                }
                handler.postDelayed(this, 1000)
            }
        })

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
        video_view.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.img_9724))


        video_view.setOnPreparedListener {
            mediaPlayer = it
            play_btn.isEnabled = true
            pause_btn.isEnabled = true
            if (it != null){
                seek_bar.max = it.duration
            }
        }


    }

    override fun onStop() {
        super.onStop()
        video_view.pause()
    }
}
