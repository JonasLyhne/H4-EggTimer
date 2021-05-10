package com.example.eggtimer;

public interface EggTimerListener {
    /**
     * Method for running some logic on each countDown.
     * @param timeLeft
     */
    void onCountDown(long timeLeft);

    /**
     * Method for running logic when timer is stopped.
     */
    void onEggTimerStopped();
}
