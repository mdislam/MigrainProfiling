package org.mis.profiling.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.jaeger.library.StatusBarUtil;

import org.mis.datewizard.Utils;
import org.mis.profiling.ApplicationMain;
import org.mis.profiling.R;
import org.mis.profiling.models.MigrainEntry;
import org.mis.profiling.models.dao.MigrainEntryDao;
import org.mis.profiling.ui.adapter.MigrainEntryAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LandingActivity extends AppBaseActivity implements View.OnClickListener {
    private static final String TAG = LandingActivity.class.getSimpleName();

    private ApplicationMain appGlobal;
    private MigrainEntryDao mMigrainDao;

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

    private List<MigrainEntry> mMigrainItems;
    //recycler views
    RecyclerView mRecyclerView;
    MigrainEntryAdapter mAdapter;

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

        initViews();

        getMingrainEntries();
    }

    protected void initViews(){
        welcomeTxt = (TextView) findViewById(R.id.app_welcome_text);
        if(!appGlobal.getAppUserName().isEmpty()){
            String name = appGlobal.getAppUserName();

            Log.d("Success", "Welcome, " + name + "!");
            welcomeTxt.setText("Welcome, " + name + "!");
        }

        cal = Calendar.getInstance();
        monthText = (TextView) findViewById(R.id.month_text);
        setMonthYearTextView(Utils.getMonthName(cal), cal.get(Calendar.YEAR));

        prevMonthBtn = (ImageButton) findViewById(R.id.prev_month);
        prevMonthBtn.setOnClickListener(this);

        nextMonthBtn = (ImageButton) findViewById(R.id.next_month);
        nextMonthBtn.setOnClickListener(this);

        mMigrainItems = new ArrayList<>();

        /******************************************************
         * Recycler Views
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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
            case R.id.action_edit_profile:
                Toast.makeText(appGlobal, "Open Edit Profile", Toast.LENGTH_SHORT).show();
                break;
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

    protected void setMonthYearTextView(String monthName, int year){
        monthText.setText(monthName + " " + year);
    }

    private void getMingrainEntries(){
        mMigrainDao = appGlobal.getDaoSession().getMigrainEntryDao();

        mMigrainItems = mMigrainDao.loadAll();

        showMigrainEntries(mMigrainItems);
    }

    private List<MigrainEntry> filterDataByMonth(List<MigrainEntry> items, int month){

        List<MigrainEntry> resultItems = new ArrayList<>();

        Calendar cal = Calendar.getInstance();

        for (MigrainEntry entry : items ) {
            Date started = entry.getStarted();
            cal.setTime(started);
            int monthNumber = cal.get(Calendar.MONTH);
            if(monthNumber == month){
                resultItems.add(entry);
            }
        }

        return resultItems;
    }

    private void showMigrainEntries(List<MigrainEntry> items){
        List<MigrainEntry> itemsByMonth = filterDataByMonth(items, cal.get(Calendar.MONTH));

        Log.d(TAG, "========LIST==========");
        for (MigrainEntry entry : itemsByMonth ) {
            Log.d(TAG, "Entry " + entry.getId() + ": " + entry.getStarted() + ", " + entry.getEnded() + ", " + entry.getLevel() + ", " + entry.getTimeofday() + ", " + entry.getSymptoms() + ", " + entry.getRemedy() + ", " + entry.getMedicine());
        }

        mAdapter = new MigrainEntryAdapter(this, itemsByMonth);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        int id  = view.getId();

        switch (id){
            case R.id.prev_month:
                cal.add(Calendar.MONTH, -1);
                setMonthYearTextView(Utils.getMonthName(cal), cal.get(Calendar.YEAR));
                showMigrainEntries(mMigrainItems);
                break;
            case R.id.next_month:
                cal.add(Calendar.MONTH, 1);
                setMonthYearTextView(Utils.getMonthName(cal), cal.get(Calendar.YEAR));
                showMigrainEntries(mMigrainItems);
                break;
        }
    }
}
