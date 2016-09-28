package org.mis.profiling.custom.views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by mis on 8/25/2016.
 */
public class DateTimePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private IDateTimeSet listener;
    private PickerType pickerType;

    public static DateTimePickerFragment newInstance(IDateTimeSet callBack, PickerType type) {
        DateTimePickerFragment ret = new DateTimePickerFragment();
        ret.initialize(callBack, type);
        return ret;
    }

    private void initialize(IDateTimeSet listener, PickerType type){
        this.listener = listener;
        this.pickerType = type;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        Dialog dialog = null;

        if(pickerType == PickerType.DATE){
            dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        }
        else if(pickerType == PickerType.TIME){
            dialog = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        return dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        this.listener.onDateSet(year, month, day);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        this.listener.onTimeSet(hour, minute);
    }
}
