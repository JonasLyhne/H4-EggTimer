package com.example.eggtimer;

public class EggTimerPresenter implements EggTimerListener {

    private View view;
    private EggTimer eggTimer;


    public EggTimerPresenter (View view) {
        this.view = view;
    }

    public void startTimer(long timeToCook) {
        eggTimer = new EggTimer(timeToCook);
        eggTimer.addListener(this);
        eggTimer.start();
    }

    public void stopTimer() {
        eggTimer.stopTimer();
    }

    public void resetEggTimer() {
        eggTimer.removeListener(this);
        eggTimer = null;
    }

    public boolean getEggTimerIsNull() {
        return (this.eggTimer != null) ? false : true;
    }

    public boolean timerIsRunning(){
        return eggTimer.getIsRunning();
    }

    public long timeLeftOnCountdown() {
        return eggTimer.getCountdown();
    }

    @Override
    public void onCountDown(long timeLeft) {
        view.onCountDown(timeLeft);
    }

    @Override
    public void onEggTimerStopped() {
        view.onEggTimerStopped();
    }

    public interface View {
        void onCountDown(long time);
        void onEggTimerStopped();
    }
}
