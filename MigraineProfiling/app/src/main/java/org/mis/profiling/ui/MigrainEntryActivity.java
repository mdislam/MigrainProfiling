package org.mis.profiling.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.jaeger.library.StatusBarUtil;

import org.mis.datewizard.DateWizardLayout;
import org.mis.datewizard.DateWizardListener;
import org.mis.datewizard.Utils;
import org.mis.profiling.ApplicationMain;
import org.mis.profiling.R;
import org.mis.profiling.custom.views.DateTimePickerFragment;
import org.mis.profiling.custom.views.IDateTimeSet;
import org.mis.profiling.custom.views.PickerType;
import org.mis.profiling.models.MigrainEntry;
import org.mis.profiling.models.dao.MigrainEntryDao;

import java.util.Calendar;
import java.util.Date;

public class MigrainEntryActivity extends AppBaseActivity implements View.OnClickListener, IDateTimeSet, DateWizardListener {

    private static final String TAG = MigrainEntryActivity.class.getSimpleName();

    private ApplicationMain appGlobal;

    private Toolbar mToolbar;
    private View mViewNeedOffset;

    private int mAlpha;

    private ImageButton btnLevelInfo;
    private ImageButton btnDayTimeInfo;
    private ImageButton btnCauseInfo;

    private Button btnFromTime;
    private Button btnToTime;

    private DateWizardLayout dayView;

    // migrain Entry Views
    private SimpleRatingBar levelRating;
    private RadioGroup groupTimeOfDay;
    private EditText etRemedy;
    private EditText etMedicine;

    //migrain entry items
    private int painLevelValue;
    private String timeOfDayValue;
    private String symptomsValue;
    private String remedyValue;
    private String medicineValue;

    private int mYear, mMonth, mDay, mStartedHour, mStartedMinute, mEndedHour, mEndedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_migrain_entry);

        appGlobal = (ApplicationMain) getApplicationContext();

        setStatusBar();

        /**************************************
         * Toolbar configuration
         */
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewNeedOffset = findViewById(R.id.view_need_offset);

        setSupportActionBar(mToolbar);
        setTitle("");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mAlpha = StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA;
        mAlpha = 10;
        StatusBarUtil.setTranslucentForImageView(MigrainEntryActivity.this, mAlpha, mViewNeedOffset);

        initViews();
    }

    private void initViews(){
        btnLevelInfo = (ImageButton) findViewById(R.id.btn_level_info);
        btnDayTimeInfo = (ImageButton) findViewById(R.id.btn_day_time_info);
        btnCauseInfo = (ImageButton) findViewById(R.id.btn_symptoms_info);

        btnFromTime = (Button) findViewById(R.id.from_time);
        btnToTime = (Button) findViewById(R.id.to_time);

        btnLevelInfo.setOnClickListener(this);
        btnDayTimeInfo.setOnClickListener(this);
        btnCauseInfo.setOnClickListener(this);

        btnFromTime.setOnClickListener(this);
        btnToTime.setOnClickListener(this);

        dayView = (DateWizardLayout) findViewById(R.id.day_view);
        dayView.addDateWizardUpdateListener(this);
        dayView.setOnClickListener(this);

        levelRating = (SimpleRatingBar) findViewById(R.id.level_rating);
        groupTimeOfDay = (RadioGroup) findViewById(R.id.group_time_of_day);
        etRemedy = (EditText) findViewById(R.id.et_remedy);
        etMedicine = (EditText) findViewById(R.id.et_medicine);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.entry_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_save:
                Toast.makeText(this, "Implement actions to Save form Data", Toast.LENGTH_SHORT).show();
                addMigrainEntry(prepareMigrainEntry());
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setStatusBar() {
        mViewNeedOffset = findViewById(R.id.view_need_offset);
        StatusBarUtil.setTranslucentForImageView(this, mViewNeedOffset);
    }

    protected void showTimePickerDialog() {
        DateTimePickerFragment pickerFragment = DateTimePickerFragment.newInstance(this, PickerType.TIME);
        pickerFragment.show(getFragmentManager(), "timePicker");
    }

    protected MigrainEntry prepareMigrainEntry(){

        Calendar cal = Calendar.getInstance();
        cal.set(mYear, mMonth, mDay, mStartedHour, mStartedMinute);
        Date startedAt = cal.getTime();

        cal.set(mYear, mMonth, mDay, mEndedHour, mEndedMinute);
        Date endedAt = cal.getTime();

        timeOfDayValue = "Night";
        symptomsValue = "Vomiting,Lights or Sound";
        remedyValue = etRemedy.getText().toString();
        medicineValue = etMedicine.getText().toString();

        MigrainEntry entry = new MigrainEntry(null, startedAt, endedAt, levelRating.getRating(), timeOfDayValue, symptomsValue, remedyValue, medicineValue);

        return entry;
    }

    private MigrainEntryDao mMigrainDao;

    protected void addMigrainEntry(MigrainEntry entry){
        mMigrainDao = appGlobal.getDaoSession().getMigrainEntryDao();

        mMigrainDao.insert(entry);

        Log.d(TAG, "Migrain Entry Inserted new row: " + entry.getId() + ", " + entry.getStarted() + ", " + entry.getEnded() + ", " + entry.getLevel() + ", " + entry.getTimeofday() + ", " + entry.getSymptoms() + ", " + entry.getRemedy() + ", " + entry.getMedicine());
    }

    private int viewIdForTimePicker;

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btn_level_info:
                super.showInfoDialog("Level", getResources().getString(R.string.sample_text));
                break;
            case R.id.btn_day_time_info:
                super.showInfoDialog("Time of Day", getResources().getString(R.string.sample_text));
                break;
            case R.id.btn_symptoms_info:
                break;
            case R.id.day_view:
                DateTimePickerFragment pickerFragment = DateTimePickerFragment.newInstance(this, PickerType.DATE);
                pickerFragment.show(getFragmentManager(), "datePicker");
                break;
            case R.id.from_time:
                viewIdForTimePicker = id;
                showTimePickerDialog();
                break;
            case R.id.to_time:
                viewIdForTimePicker = id;
                showTimePickerDialog();
                break;
        }
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        dayView.setDateWizard(year, month, day);
    }

    @Override
    public void onTimeSet(int hour, int minute) {
        Log.d("MigrainEntry", "" + hour + "-" + minute);
        switch (viewIdForTimePicker){
            case R.id.from_time:
                btnFromTime.setText("Started " + hour + ":" + minute);
                mStartedHour = hour;
                mStartedMinute = minute;
                break;
            case R.id.to_time:
                btnToTime.setText("Ended " + hour + ":" + minute);
                mEndedHour = hour;
                mEndedMinute = minute;
                break;
        }
    }

    @Override
    public void onDateWizardUpdated(int date, int month, int year) {
        Log.d(TAG, "result: " + date + "/" + Utils.getMonthName(month) + "/" + year);
        mYear = year;
        mMonth = month;
        mDay = date;
    }
}
