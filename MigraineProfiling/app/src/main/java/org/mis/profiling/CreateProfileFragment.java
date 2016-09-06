package org.mis.profiling;


import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mis.profiling.models.User;
import org.mis.profiling.models.dao.UserDao;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ApplicationMain appGlobal;

    private UserDao _userDao;

    private View rootView;
    private EditText etName;
    private TextView tvUserName;

    private String name;

    private RelativeLayout createProfileLayout;
    private RelativeLayout successlayout;

    public CreateProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateProfileFragment newInstance(String param1, String param2) {
        CreateProfileFragment fragment = new CreateProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_create_profile, container, false);

        appGlobal = (ApplicationMain) getActivity().getApplicationContext();

        initializeViews();

        if(!appGlobal.getAppUserName().isEmpty()){
            name = appGlobal.getAppUserName();
            successlayout.setVisibility(View.VISIBLE);
            createProfileLayout.setVisibility(View.INVISIBLE);

            Log.d("Success", "Welcome, " + name + "!");
            tvUserName.setText("Welcome, " + name + "!");
        }

        Button btnSetProfileData = (Button) rootView.findViewById(R.id.btn_finish);
        btnSetProfileData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateProfileForm()) {
                    // Create app user
                    addUser(name);

                    // reveal the success window
                    revealView(successlayout);
                }
            }
        });

        Button btnFinish = (Button) rootView.findViewById(R.id.btn_start_app);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set the first run flag to false
                appGlobal.setAppFirstRun(false);

                // Display App Landing
                Intent mainIntent = new Intent(getActivity(), LandingActivity.class);
                getActivity().startActivity(mainIntent);

                // finish Inro Activity
                getActivity().finish();
            }
        });

        return rootView;
    }

    private void initializeViews(){
        etName = (EditText) rootView.findViewById(R.id.et_name);

        tvUserName = (TextView) rootView.findViewById(R.id.tv_user_name);

        createProfileLayout = (RelativeLayout) rootView.findViewById(R.id.ll_create);
        successlayout = (RelativeLayout) rootView.findViewById(R.id.ll_success);
    }

    public boolean validateProfileForm(){
        boolean validated = false;

        name = etName.getText().toString();

        if(!name.isEmpty()){
            validated = true;
        }
        else {
            etName.setError("Please Enter a profile name");
        }

        return validated;
    }

    private void addUser(String name){
        _userDao = appGlobal.getDaoSession().getUserDao();

        User user = new User(null, name, 29, null, null);
        _userDao.insert(user);
        Log.d("UserTable", "Inserted new row: " + user.getId());

        appGlobal.setAppUsername(name);
    }

    /**
     *
     * @param view
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void revealView(View view){
        // reveal the view from center
        // get the center for the clipping circle
        int cx = view.getMeasuredWidth() / 2;
        int cy = view.getMeasuredHeight() / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight()) / 2;

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

        view.setVisibility(View.VISIBLE);
        createProfileLayout.setVisibility(View.INVISIBLE);

        Log.d("revealView", "Welcome, " + name);
        tvUserName.setText("Welcome, " + name + "!");

        anim.start();
    }
}
