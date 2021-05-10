package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements EggTimerPresenter.View {

    EditText timerText;
    ImageView softBoilButton;
    ImageView mediumBoilButton;
    ImageView hardBoilButton;
    Button startStopButton;
    Button resetButton;
    TextView timerFormatText;


    private EggTimerPresenter presenter;
    private long countdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerText = findViewById(R.id.timerText);
        timerFormatText = findViewById(R.id.timerFormatText);
        softBoilButton = findViewById(R.id.softBoilButton);
        mediumBoilButton = findViewById(R.id.mediumBoilButton);
        hardBoilButton = findViewById(R.id.hardBoilButton);
        startStopButton = findViewById(R.id.startStopButton);
        resetButton = findViewById(R.id.resetButton);
        presenter = new EggTimerPresenter(this);
    }

    /**
     * Starts or stops the timer.
     * If the timer hasn't been started before it will instantiate a new EggTimer and start the timer.
     *
     * @param view
     */
    public void startStopTimer(View view) {
        if (presenter.getEggTimerIsNull()){ // If timer is null instantiate new timer and start it.
            startStopButton.setText("Stop");
            presenter.startTimer(countdown);
        }
        // If timer is running, stop the timer and set local countdown to the remaining time in EggTimer.countdown
        // this is done so the timer can resume from where it stopped.
        else if (presenter.timerIsRunning()) {
            presenter.stopTimer();
            countdown = presenter.timeLeftOnCountdown();
            startStopButton.setText("Start");
            resetButton.setVisibility(View.VISIBLE); // show hidden reset button.
            resetButton.setEnabled(true); // enable reset button.
        }
        // If timer is not running, start timer from where it stopped before.
        else if (!presenter.timerIsRunning()){
            startStopButton.setText("Stop");
            resetButton.setVisibility(View.INVISIBLE);
            resetButton.setEnabled(false);
            presenter.startTimer(countdown);
        }
    }


    /**
     * This function sets the egg timer to the desired time based on which egg button is selected
     * 5 min for soft, 7 min for medium and 10 min for hard.
     * @param view
     */
    public void onEggSelect(View view){
        switch (view.getId()) {
            case R.id.softBoilButton:
                countdown = 300000; // 5 minutes
                break;
            case R.id.mediumBoilButton:
                countdown = 420000; // 7 minute
                break;
            case R.id.hardBoilButton:
                countdown = 600000; // 10 minutes
                break;
            default:
                throw new RuntimeException("UnknownException");
        }
        if (countdown > 0) {
            timerText.setText(String.format(Locale.getDefault(), "%02d:%02d",((int)Math.floor(countdown / 60000)), ((int)countdown % 60000 / 1000)));
            startStopButton.setEnabled(true);
        }
    }

    /**
     * Resets the egg timer. User must select new time to start the timer again.
     * @param view
     */
    public void resetEggTimer(View view) {
        countdown = 0;
        timerText.setText(String.format(Locale.getDefault(), "%02d:%02d", 0,0));
        startStopButton.setEnabled(false);
        resetButton.setVisibility(View.INVISIBLE);
        resetButton.setEnabled(false);
        presenter.resetEggTimer();
    }

    @Override
    public void onCountDown(long time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timerText.setText(String.format(Locale.getDefault(), "%02d:%02d",((int)Math.floor(time / 60000)), ((int)time % 60000 / 1000)));
            }
        });
    }

    @Override
    public void onEggTimerStopped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


}