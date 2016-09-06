package org.mis.profiling;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jaeger.library.StatusBarUtil;

import org.mis.profiling.custom.views.DatePickerFragment;
import org.mis.profiling.custom.views.TimePickerFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MigrainEntryActivity extends AppCompatActivity {

    private static final String TAG = MigrainEntryActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private View mViewNeedOffset;

    private int mAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_migrain_entry);

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
    }

    // pass context to Calligraphy
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
            case R.id.action_calendar:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
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

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
}
