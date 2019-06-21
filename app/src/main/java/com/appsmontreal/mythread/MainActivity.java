package com.appsmontreal.mythread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    private volatile Boolean stopThread = false;
    private Handler myHandler = new Handler();//take the thread where it is instantiated in this case UI thread
                                              //so I could use wherever I want just usin method post(new Runnable)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButton);
    }

    public void start(View view) {
        //////////////Solution 1//////////////////
//        ExampleThread exampleThread = new ExampleThread(5);
//        exampleThread.start();

        //////////////Solution 2//////////////////
        stopThread = false;
        ExampleRunnable exampleRunnable = new ExampleRunnable(6);
        new Thread(exampleRunnable).start();


        ///////////Solution 3//////////////Anonymous Class Thread
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 6; i++) {
//                    if (i == 2) {
//                        myHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                startButton.setText("hello");
//                            }
//                        });
//                    }
//                    Log.d(TAG, "start: " + i);
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        }).start();
    }

    public void stop(View view) {
        stopThread = true;
    }

    //////////////Solution 1//////////////////
    public class ExampleThread extends Thread{
        int seconds;

        public ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++){
                Log.d(TAG, "start: " + i);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //////////////Solution 2//////////////////
    public class ExampleRunnable implements Runnable{
        int seconds;

        public ExampleRunnable(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++){
                if(stopThread){
                    return;
                }
                if (i == 2 ){
                    myHandler.post(new Runnable() {//It's simpler do it like that than  //***
                        @Override
                        public void run() {
                            startButton.setText("hello");
                        }
                    });
                }
                else if(i == 5){//*** Another way scroll down to see explanation
                    Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            startButton.setText("start");
                        }
                    });

                }
                Log.d(TAG, "start: " + i);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
//***
// Another way to do it it's just creating a new Handler inside my new Thread(ExampleRunnable)
//and getting main looper with the method ===> Looper.getMainLooper()
