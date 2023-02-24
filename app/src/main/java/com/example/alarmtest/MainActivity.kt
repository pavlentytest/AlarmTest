package com.example.alarmtest

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    //    запустим активность через 15 секунд после запуска
    //сигнализация в отмене не нуждается
    fun oneAlarm(v: View) {
        Toast.makeText(applicationContext,"Терпение! Через 15 секунд всё будет!",Toast.LENGTH_LONG).show()
        val penIntent = PendingIntent.getActivity(applicationContext,1,
            Intent(this,ArtActivity::class.java),PendingIntent.FLAG_UPDATE_CURRENT)
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+15000,penIntent)
    }

    //////////////////////////////
    //создадим настраиваемый по времени будильник
    fun repeatAlarm(v:View){
        val timePicker = TimePickerFragment()
        timePicker.show(supportFragmentManager,"picker")
    }
    //для чтения установленного времени имплементируем TimePickerDialog.OnTimeSetListener
    // зададим повтор каждую секунду до отключения
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
        calendar.set(Calendar.MINUTE,minute)
        val mIntent = Intent(this, AlarmReceiver::class.java)
        val   penIntent = PendingIntent.getBroadcast(this,4,mIntent,0)
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,1000,penIntent)
    }
    //обязательно отменим, он же с повторениями
    fun clearRep(v: View){
        val mIntent = Intent(this, AlarmReceiver::class.java)
        val  penIntent = PendingIntent.getBroadcast(this,4,mIntent,0)
        val  am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (penIntent != null && am != null) am.cancel(penIntent)
    }


    ////////////////////////////
    //теперь создадим "экономную" сигнализацию
    //она будет отправлять уведомления каждые 15 минут и не будет "будить" устройство
    fun nearAlarm(v: View){
        //чтобы уведомления приходили на Android 8.0 и выше, для них нужен канал
        createNotificationChannel()
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val mIntent = Intent(this, AlarmReceiverNotify::class.java)
        val  penIntent = PendingIntent.getBroadcast(this,25,mIntent,0)
        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
            SystemClock.uptimeMillis(),
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            penIntent)
    }
    // её тоже можно отключать
    fun clearNoti(v: View){
        val int = Intent(this,AlarmReceiverNotify::class.java)
        val    penIntent = PendingIntent.getBroadcast(this,25,int,0)
        val  am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (penIntent != null && am != null) am.cancel(penIntent)
    }
    fun createNotificationChannel(){
        val CID = "TestNotify"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "discription"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CID, name, importance)
            channel.description = descriptionText
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}