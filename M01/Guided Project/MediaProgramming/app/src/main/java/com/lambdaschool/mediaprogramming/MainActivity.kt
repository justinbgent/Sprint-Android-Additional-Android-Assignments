package com.lambdaschool.mediaprogramming

import android.content.Intent
import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/*
This activity demonstrates how to use the VideoView class to play a locally stored file in the Raw
resources directory.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //todoes 1: Disable the play/pause button by default (you'll enable it later)
        play_pause_button.isEnabled = false

        //todoes 2: Add the play/pause functionality including the animation for the button and
        //todoes playing or pausing the VideoView.
        playOrPauseFunctionality()

        //todoes 3: Add functionality to listen to seekbar drag events to set videoview to the
        //todoes appropriate part of the video.
        seekBarFunctionality()
    }

    private fun playOrPauseFunctionality() {
        // In the on-click listener for the button,
        // If the video is not playing, start it
        // else pause the video
        // Start the animation

        play_pause_button.setOnClickListener {
            if(video_view.isPlaying){
                // pause
                video_view.pause()
                play_pause_button.setImageDrawable(getDrawable(R.drawable.avd_anim_pause_play))
            }else{
                //play
                video_view.start()
                play_pause_button.setImageDrawable(getDrawable(R.drawable.avd_anim_play_pause))
            }
            // Start the animation and avoid crash using if statement
            val drawable = play_pause_button.drawable
            if(drawable is Animatable){
                (drawable as Animatable).start()
            }
        }
    }

    private fun seekBarFunctionality() {
        // In the SeekBar listener, when the seekbar progress is changed,
        // update the video progress
        video_seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                //if seekBar not null do this
                seekBar?.let{
                    video_view.seekTo(progress)
                }


            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    override fun onStart() {
        super.onStart()

        //todoes 4: Set the video URI to the videoview (This video is an mp4 file in the raw folder )
        video_view.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.live_views_of_starman))

        //todoes 5: Set an onPreparedListener to the videoview and enable the play/pause button in the
        //todoes callback
        video_view.setOnPreparedListener {
            play_pause_button.isEnabled = true
            it?.let{
                video_seek_bar.max = it.duration
            }
        }
    }

    //todoes 8: Pause the video when onStop is called, if user navigates away from the screen
    override fun onStop() {
        super.onStop()
        video_view.pause()
    }

    //todoes 7: Notice that we create an overflow menu to handle navigation between activities
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_file, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //todoes 6: Notice that we will use an overflow menu to navigate to the ExoPlayerActivity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this@MainActivity, ExoPlayerActivity::class.java))
        // logic that can be used for the intent when...
//        when(item.itemId){
//            R.id.menu_red ->
//        }
        startActivity(Intent(this@MainActivity, ExoPlayerActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

}
