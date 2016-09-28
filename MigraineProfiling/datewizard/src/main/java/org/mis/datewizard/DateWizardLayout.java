package org.mis.datewizard;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.mis.datewizard.Utils.getDate;
import static org.mis.datewizard.Utils.getMonthName;
import static org.mis.datewizard.Utils.getWeekDay;

/**
 * Created by mis on 9/27/2016.
 */

public class DateWizardLayout extends RelativeLayout implements View.OnClickListener {
    // Context
    protected Context context;
    protected Activity activity;

    // Views
    protected LayoutInflater mInflater;
    protected LinearLayout mDateContainer;
    protected ImageButton btnLeftNav;
    protected ImageButton btnRightNav;
    protected TextView tvDate;
    protected TextView tvWeekDay;
    protected TextView tvMonthYear;
    protected Calendar mCalendar;

    protected DateWizardListener mListener;

    public DateWizardLayout(Context context) {
        super(context);
        init(context);
    }

    public DateWizardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DateWizardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.date_wizard_layout, this, true);

//        mDateContainer = (LinearLayout) findViewById(R.id.date_container);
//        mDateContainer.setOnClickListener(this);

        btnLeftNav = (ImageButton) findViewById(R.id.btn_left_nav);
        btnRightNav = (ImageButton) findViewById(R.id.btn_right_nav);

        btnLeftNav.setOnClickListener(this);
        btnRightNav.setOnClickListener(this);

        tvDate = (TextView) findViewById(R.id.tv_date);
        tvWeekDay = (TextView) findViewById(R.id.tv_week_day);
        tvMonthYear = (TextView) findViewById(R.id.tv_month_year);

        initDateWizard();
    }

    /**
     * init the wizard with current date
     */
    private void initDateWizard(){
        mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        setDateWizard(year, month, day);
    }

    /**
     * init the wizard with user specified date
     * @param cal
     */
    public void initDateWizard(Calendar cal){
        mCalendar = cal;
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        setDateWizard(year, month, day);
    }

    public void addDateWizardUpdateListener(DateWizardListener listener){
        mListener = listener;
    }

    private void changeDateBy(int i){
        Calendar c = mCalendar;
        c.add(Calendar.DATE, i);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        setDateWizard(year, month, day);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(btnLeftNav.getId() == id){
            changeDateBy(-1);
        }
        else if(btnRightNav.getId() == id){
            changeDateBy(1);
        }
        else if(id == R.id.date_container){

        }
    }

    /****************** UTILITY METHODS ***************************/
    public void setDateWizard(int year, int month, int day){
        mCalendar.set(year, month, day);
        if(mListener != null)
            mListener.onDateWizardUpdated(day, month, year);

        tvDate.setText("" + day);
        tvWeekDay.setText(getWeekDay(getDate(year, month, day)));
        tvMonthYear.setText("" + getMonthName(month) + " " + year);
    }
}
