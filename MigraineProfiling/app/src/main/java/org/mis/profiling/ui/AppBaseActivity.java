package org.mis.profiling.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import org.mis.profiling.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by mis on 9/28/2016.
 */

public abstract class AppBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // pass context to Calligraphy
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void showInfoDialog(String title, String message){
        new LovelyInfoDialog(this)
                .setTopColorRes(R.color.colorAccent)
                .setIcon(R.drawable.ic_info_dialog)
                //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
                .setNotShowAgainOptionEnabled(0)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}
