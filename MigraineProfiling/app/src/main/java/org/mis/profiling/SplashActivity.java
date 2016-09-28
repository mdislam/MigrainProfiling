package org.mis.profiling;

import android.content.Context;
import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import org.mis.profiling.ApplicationMain;
import org.mis.profiling.R;
import org.mis.profiling.ui.AppIntroActivity;
import org.mis.profiling.ui.LandingActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by mis on 3/23/2016.
 */
public class SplashActivity extends AwesomeSplash {

    private ApplicationMain appGlobal;

    @Override
    public void initSplash(ConfigSplash configSplash) {
        /* you don't have to override every property */

        appGlobal = (ApplicationMain) getApplicationContext();

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorCyanLight); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.migraine_icon_250); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //Customize Title
        configSplash.setTitleSplash("Migraine Profiling");
        configSplash.setTitleTextColor(R.color.colorPrimaryDark);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.FadeInUp);
    }

    // pass context to Calligraphy
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void animationsFinished() {
        // set up the database
        appGlobal.setUpApplicationDB();
        // If the application is running for first time
        if(appGlobal.isAppFirstRun()){
            // Display the App Intro
            Intent introIntent = new Intent(this, AppIntroActivity.class);
            startActivity(introIntent);
        }
        else {
            // Display the App Landing
            Intent landingIntent = new Intent(this, LandingActivity.class);
            startActivity(landingIntent);
        }

        // finish splash
        finish();
    }
}
