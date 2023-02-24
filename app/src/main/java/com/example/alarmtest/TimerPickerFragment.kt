package com.example.alarmtest

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        var h = calendar.get(Calendar.HOUR_OF_DAY)
        var m = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(activity,activity as TimePickerDialog.OnTimeSetListener,h,m,true)
    }
}