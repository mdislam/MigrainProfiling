package org.mis.profiling;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.jaeger.library.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LandingActivity extends AppCompatActivity {
    private static final String TAG = LandingActivity.class.getSimpleName();

    private ApplicationMain appGlobal;

    private Toolbar mToolbar;
    private View mViewNeedOffset;

    private int mAlpha;

    //floating toolbar
    private FloatingToolbar mFloatingToolbar;

    private static Calendar cal;

    private TextView welcomeTxt;
    private ImageButton prevMonthBtn;
    private ImageButton nextMonthBtn;
    private TextView monthText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        setStatusBar();

        appGlobal = (ApplicationMain) getApplicationContext();

        /**************************************
         * Toolbar configuration
         */
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewNeedOffset = findViewById(R.id.view_need_offset);

        setSupportActionBar(mToolbar);
        setTitle("");

        mAlpha = StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA;
        mAlpha = 10;
        StatusBarUtil.setTranslucentForImageView(LandingActivity.this, mAlpha, mViewNeedOffset);

        /**************************************
         * Floating Toolbar
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingToolbar = (FloatingToolbar) findViewById(R.id.floatingToolbar);

        mFloatingToolbar.setClickListener(new FloatingToolbar.ItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                int itemId = item.getItemId();

                switch (itemId){
                    case R.id.action_task_views:
                        break;
                    case R.id.action_manage:
                        Intent createEntryIntent = new Intent(LandingActivity.this, MigrainEntryActivity.class);
                        startActivity(createEntryIntent);
                        break;
                    case R.id.action_statistics:
                        break;
                }
            }

            @Override
            public void onItemLongClick(MenuItem item) {

            }
        });
        mFloatingToolbar.attachFab(fab);

        welcomeTxt = (TextView) findViewById(R.id.app_welcome_text);
        if(!appGlobal.getAppUserName().isEmpty()){
            String name = appGlobal.getAppUserName();

            Log.d("Success", "Welcome, " + name + "!");
            welcomeTxt.setText("Welcome, " + name + "!");
        }

        cal = Calendar.getInstance();
        monthText = (TextView) findViewById(R.id.month_text);
        monthText.setText(getMonthYear(cal));

        prevMonthBtn = (ImageButton) findViewById(R.id.prev_month);
        prevMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.add(Calendar.MONTH, -1);
                monthText.setText(getMonthYear(cal));
            }
        });

        nextMonthBtn = (ImageButton) findViewById(R.id.next_month);
        nextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.add(Calendar.MONTH, 1);
                monthText.setText(getMonthYear(cal));
            }
        });
    }

    // pass context to Calligraphy
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_exit_app:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setStatusBar() {
        mViewNeedOffset = findViewById(R.id.view_need_offset);
        StatusBarUtil.setTranslucentForImageView(this, mViewNeedOffset);
    }

    private String getMonthYear(Calendar cal){
        String resultText = "";
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month = month_date.format(cal.getTime());
        int year = cal.get(Calendar.YEAR);
        resultText = month + " " + year;

        return resultText;
    }
}
