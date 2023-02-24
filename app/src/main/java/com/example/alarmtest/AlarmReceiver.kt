package com.example.alarmtest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val mp = MediaPlayer.create(context, alert)
        if (mp != null) {
            mp.setVolume(100f, 100f)
            mp.start()
            mp.setOnCompletionListener { mp -> mp.release() }
        }

    }
}