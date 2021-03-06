package org.weibeld.example.tabs;

import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.view.GestureDetector;
import android.view.LayoutInflater;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Fragment;

import org.weibeld.example.R;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/* Fragment used as page 3 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class Page3Fragment extends Fragment implements View.OnClickListener {
    // fresh
    private CameraManager mCameraManager;
    private String mCameraId;
    private Boolean isTorchOn;

    // timer countdown
    private static final long START_TIME_IN_MILLS = 3000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMills = START_TIME_IN_MILLS;

    public Vibrator vibe;

    // fragment layout
    private LinearLayout timerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page3, container, false);

        // layout event
        timerLayout = (LinearLayout) rootView.findViewById(R.id.timerLayout);
        timerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                        if (mTimeLeftInMills == 0) {
                            return true;
                        } else {
                            mTimeLeftInMills += 30000;
                            updateCountDownText();
                        }
                        break;
                }
                return true;
            }
        });

        // fresh
        isTorchOn = false;
        checkPermission();

        mCameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        // timer vibrate
        vibe = (Vibrator) inflater.getContext().getSystemService(Context.VIBRATOR_SERVICE);

        // timer countdown
        mTextViewCountDown = (TextView) rootView.findViewById(R.id.text_view_countdown);

        mButtonStartPause = (Button) rootView.findViewById(R.id.button_start_pause);
        mButtonReset = (Button) rootView.findViewById(R.id.button_reset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        updateCountDownText();
        return rootView;
    }

    public void onClick(View v) {
        try {
            if (isTorchOn) {
                turnOffFlashLight();
                isTorchOn = false;
            } else {
                turnOnFlashLight();
                isTorchOn = true;
                vibe.vibrate(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPermission() {
        Boolean isFlashAvailable = getActivity().getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
            alert.setTitle("Error !!");
            alert.setMessage("Your device doesn't support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    //getActivity().finish();
                    //System.exit(0);
                }
            });
            alert.show();
            return;
        }
    }

    public void turnOnFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turnOffFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMills, 1000) {
            @Override
            public void onTick(long millisUtilFinished) {
                mTimeLeftInMills = millisUtilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                turnOnFlashLight();
                long[] pattern = {100, 300, 700, 300, 2000};
                vibe.vibrate(pattern, 0);
                isTorchOn = true;
            }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    public void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("start");
        mButtonReset.setVisibility(View.VISIBLE);
        vibe.cancel();
    }

    public void resetTimer() {
        mTimeLeftInMills = START_TIME_IN_MILLS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
        turnOffFlashLight();
        isTorchOn = false;
        vibe.cancel();
    }

    public void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMills / 1000) / 60;
        int seconds = (int) (mTimeLeftInMills / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}
