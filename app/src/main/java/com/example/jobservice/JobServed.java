package com.example.jobservice;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobServed extends JobService {
    public static final String TAG = "MyTag";
    private boolean isJobCancelled = false;
    private boolean mSuccess = false;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        
        Log.d( TAG, "onStartJob: " + Thread.currentThread().getName() );
        
        new Thread( new Runnable() {
            @Override
            public void run() {
                if (isJobCancelled)return;
                for (int i = 0; i < 100 ; i++) {
                    try {
                        Thread.sleep( 1000 );
                        Log.d( TAG, "loop: " + i );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mSuccess = true;
                jobFinished( jobParameters, mSuccess );
            }
        } ).start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        isJobCancelled = true;
        return true;
    }
}
