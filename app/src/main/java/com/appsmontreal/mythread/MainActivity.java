package com.appsmontreal.mythread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    private Handler myHandler = new Handler();//take the thread where it is instantiated in this case UI thread
                                              //so I could use wherever I want just usin method post(new Runnable)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButton);
    }

    public void start(View view){
        //////////////Solution 1//////////////////
//        ExampleThread exampleThread = new ExampleThread(5);
//        exampleThread.start();

        //////////////Solution 2//////////////////
        ExampleRunnable exampleRunnable = new ExampleRunnable(5);
        new Thread(exampleRunnable).start();
    }

    public void stop(View view) {
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
                if (i == 4 ){
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            startButton.setText("hello");
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

