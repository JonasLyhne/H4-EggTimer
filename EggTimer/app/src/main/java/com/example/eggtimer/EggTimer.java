package com.example.eggtimer;

import java.util.ArrayList;

public class EggTimer extends Thread {

    private long countdown;
    private boolean isRunning = false;
    private ArrayList<EggTimerListener> listeners;

    /**
     * @param countdownTime the time for the countdown timer in milliseconds.
     */
    public EggTimer(long countdownTime) {
        listeners = new ArrayList<>();
        countdown = countdownTime;
    }

    @Override
    public void run() {
        if (!isRunning){
            isRunning = true;
            while (countdown > 0 && isRunning) {
                try {
                    countdown -= 1000;
                    for (EggTimerListener listener :
                            listeners) {
                        listener.onCountDown(countdown);
                    }

                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        isRunning = false;

        for (EggTimerListener listener :
                listeners) {
            listener.onEggTimerStopped();
        }
    }


    public void stopTimer(){
        isRunning = false;
    }

    public long getCountdown(){
        return this.countdown;
    }

    public boolean getIsRunning() {
        return this.isRunning;
    }

    public void addListener(EggTimerListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(EggTimerListener listener) {
        this.listeners.remove(listener);
    }
}
