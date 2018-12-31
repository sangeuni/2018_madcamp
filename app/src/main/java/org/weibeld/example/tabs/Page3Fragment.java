package org.weibeld.example.tabs;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.app.Fragment;

import org.weibeld.example.R;

import android.hardware.Camera;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.Policy;
import java.util.Locale;

/* Fragment used as page 3 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class Page3Fragment extends Fragment implements View.OnClickListener {
    // fresh
    private CameraManager mCameraManager;
    private String mCameraId;
//    private TextView mTorchOnOffButton;
    private Boolean isTorchOn;

    // timer countdown
    private static final long START_TIME_IN_MILLS = 3000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mcountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMills = START_TIME_IN_MILLS;

    public Vibrator vibe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page3, container, false);

        //fresh
//        mTorchOnOffButton = (TextView) rootView.findViewById(R.id.btnSwitch);
        isTorchOn = false;
        checkPermission();

        mCameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
//        mTorchOnOffButton.setOnClickListener(this);

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
        mcountDownTimer = new CountDownTimer(mTimeLeftInMills, 1000) {
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
                long[] pattern ={100,300,700,300,2000};
                vibe.vibrate(pattern,0);
                isTorchOn = true;
            }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    public void pauseTimer() {
        mcountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("start");
        mButtonReset.setVisibility(View.VISIBLE);
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
