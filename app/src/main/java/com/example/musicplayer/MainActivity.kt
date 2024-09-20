package com.example.musicplayer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mp : MediaPlayer
    private lateinit var toggle: ActionBarDrawerToggle
    private var totalTme = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        mp = MediaPlayer.create(this,R.raw.music)

        binding.apply {
        toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayouts, R.string.open, R.string.close)

            play.setOnClickListener {
                if (mp.isPlaying){
                    mp.pause()
                    play.setImageResource(R.drawable.play_icon)
                }else{
                    mp.start()
                    play.setImageResource(R.drawable.pause_icon)
                }
            }
            mp.isLooping = true
            mp.setVolume(0.5f, 0.5f)
            totalTme = mp.duration
            position.max = totalTme

            Music.setSeekBar(volumeSeek, mp, true)
            Music.setSeekBar(position, mp, null, true)

            val handler = @SuppressLint("HandlerLeak")
            object : Handler(){
                override fun handleMessage(msg: Message) {
                    val currentPosition = msg.what
                    position.progress = currentPosition
                    val elapsedTime = Music.createTimeLabel(currentPosition)
                    elapsed.text = elapsedTime
                    val remainingTime = Music.createTimeLabel(totalTme - currentPosition)
                    remaining.text = "- $remainingTime"
                }
            }

            Thread(Runnable {
                while(true){
                    try{
                        val msg = Message()
                        msg.what = mp.currentPosition
                        handler.sendMessage(msg)
                        Thread.sleep(1000)
                    }catch (e: InterruptedException){
                        Log.d("Thread", e.message.toString())
                    }
                }

            }).start()

            drawerLayouts.addDrawerListener(toggle)
            toggle.syncState()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.home -> Toast.makeText(this@MainActivity, "Clicked Home", Toast.LENGTH_SHORT).show()
                    R.id.file_music -> Toast.makeText(this@MainActivity, "Clicked Home", Toast.LENGTH_SHORT).show()
                    R.id.sync -> Toast.makeText(this@MainActivity, "Clicked Home", Toast.LENGTH_SHORT).show()
                    R.id.trash -> Toast.makeText(this@MainActivity, "Clicked Home", Toast.LENGTH_SHORT).show()
                    R.id.settings -> Toast.makeText(this@MainActivity, "Clicked Home", Toast.LENGTH_SHORT).show()
                    R.id.logging -> Toast.makeText(this@MainActivity, "Clicked Home", Toast.LENGTH_SHORT).show()
                    R.id.share -> Toast.makeText(this@MainActivity, "Clicked Home", Toast.LENGTH_SHORT).show()
                    R.id.rate -> Toast.makeText(this@MainActivity, "Clicked Home", Toast.LENGTH_SHORT).show()
                }
                true
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}