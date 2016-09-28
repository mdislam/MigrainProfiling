package org.mis.profiling.ui;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import org.mis.profiling.ApplicationMain;
import org.mis.profiling.R;
import org.mis.profiling.ui.fragments.CreateProfileFragment;

public class AppIntroActivity extends IntroActivity {

    private ApplicationMain appGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Enable / disable full screen mode
        setFullscreen(true);
        super.onCreate(savedInstanceState);

        appGlobal = (ApplicationMain) getApplicationContext();

        setSkipEnabled(false);
        setFinishEnabled(false);

        /**
         * Standard slide (like Google's intros)
         */
        addSlide(new SimpleSlide.Builder()
                .title(R.string.intro_title_1)
                .description(R.string.intro_desc_1)
                .image(R.drawable.list_info)
                .background(R.color.colorPrimaryGreen)
                .backgroundDark(R.color.colorPrimaryGreenDark)
                .build());

        /**
         * Standard slide (like Google's intros)
         */
        addSlide(new SimpleSlide.Builder()
                .title(R.string.intro_title_2)
                .description(R.string.intro_desc_2)
                .image(R.drawable.statistics)
                .background(R.color.colorPrimaryRed)
                .backgroundDark(R.color.colorPrimaryRedDark)
                .build());

        /**
         * Custom fragment slide for creating Profile
         */
        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimaryDark)
                .fragment(new CreateProfileFragment())
                .build());

        setNavigationPolicy(new NavigationPolicy() {
            @Override
            public boolean canGoForward(int i) {
                if(i == 2) return false;
                return true;
            }

            @Override
            public boolean canGoBackward(int i) {
                return true;
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
