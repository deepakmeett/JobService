package com.example.jobservice;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyTag" ;
    Button button, button1; 
    public static final int  JOB_ID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        
        button = findViewById( R.id.buttonPanel1);
        button1 = findViewById( R.id.buttonPanel2);
        
        button.setOnClickListener( new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                JobScheduler jobScheduler = (JobScheduler) getSystemService( JOB_SCHEDULER_SERVICE );
                ComponentName componentName = new ComponentName( getApplicationContext(), JobServed.class );
                JobInfo jobInfo = new JobInfo.Builder( JOB_ID, componentName )
                        .setRequiredNetworkType( JobInfo.NETWORK_TYPE_CELLULAR )
                        .setMinimumLatency( 0 )
                        .setPersisted( true )
                        .build();
                
                int result = jobScheduler.schedule( jobInfo );
                
                if (result == JobScheduler.RESULT_SUCCESS){
                    Log.d( TAG, "Job Scheduled" );
                }else {
                    Log.d( TAG, "Job Not Scheduled" );
                }
            }
        } );

        button1.setOnClickListener( new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                JobScheduler jobScheduler = (JobScheduler) getSystemService( JOB_SCHEDULER_SERVICE );
                jobScheduler.cancel( JOB_ID );
                Log.d( TAG, "Job Cancelled" );
            }
        } );

    }
}
