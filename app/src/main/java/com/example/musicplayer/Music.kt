package com.example.musicplayer

import android.health.connect.datatypes.units.Volume
import android.media.MediaPlayer
import android.widget.SeekBar

object Music {
    fun setSeekBar(seekBar: SeekBar, mp:MediaPlayer, volume: Boolean? = null, seekTo: Boolean? = null){
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    when{
                        volume == null -> mp.seekTo(progress)
                        seekTo == null -> {
                            val volumeNum = progress / 100.0f
                            mp.setVolume(volumeNum, volumeNum)
                        }
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    fun createTimeLabel(time: Int): String{
        var timeLabel = ""
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel

    }
}