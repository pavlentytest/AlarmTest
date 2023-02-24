package com.example.alarmtest

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiverNotify : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val CID = "TestNotify"
        val penIntent = PendingIntent.getActivity(context,1,
            Intent(context,ArtActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context,CID)
            .setSmallIcon(android.R.drawable.ic_dialog_email)
            .setContentTitle("Вам письмо")
            .setAutoCancel(true)
            .setContentText("Примите самые искренние пожелания добра и удачи!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(penIntent)
            .build()
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(102, builder)}

    }

}
