package com.appsmontreal.mythread;

import android.os.Looper;

import java.util.logging.Handler;

public class LooperThread extends Thread {
    public Handler handler;
    public Looper looper;

    @Override
    public void run() {
        super.run();
    }
}
