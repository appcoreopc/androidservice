package appcore.com.bindigservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private MyBindingService service;
    private boolean serviceAvailablity = false;
    private TextView timestampTxt;
    private TextView timestampTxtView2;
    private boolean intentServiceAvailable = false;
    private MyIntentBindingService myIntentBindingService;

    private MyResultReceiver myResultReceiver;

    private Thread fakeDownloadThread = new Thread() {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                 myImageDownloadProcessHandler.sendEmptyMessage(0);
            }
            catch (InterruptedException ex)
            {

            }
        }
    };

    private Handler myImageDownloadProcessHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getBaseContext(), "Handler demo", Toast.LENGTH_SHORT).show();
        }
    };

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myResultReceiver = new MyResultReceiver(new Handler());

        Button getTimeStampBtn = (Button) findViewById(R.id.btnTimestamp);
        Button stopBtn = (Button) findViewById(R.id.btnStop);
        Button startAsyncBtn = (Button) findViewById(R.id.btnStartAsync);
        Button handlerBtn = (Button) findViewById(R.id.btnHandler);

        Button resultReceiverBtn = (Button) findViewById(R.id.bntResultReceiver);
        resultReceiverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        handlerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fakeDownloadThread.start();
            }
        });

        startAsyncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MyAsyncService.class);
                startService(i);
            }
        });

        timestampTxtView2 = (TextView) findViewById(R.id.textViewIntentService);

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);
            }
        });

        timestampTxt = (TextView) findViewById(R.id.textView);

        getTimeStampBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mytime = "";

                if (serviceAvailablity) {
                    mytime = service.getTimestamp();
                    Log.v("timestamp", mytime);
                    timestampTxt.setText(mytime);
                }

                if (intentServiceAvailable) {
                    mytime = myIntentBindingService.getTimestamp();
                    Log.v("timestamp", mytime);
                    timestampTxtView2.setText(mytime);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.v("creating service task", "we are creating service");
 //       Intent bindingServiceIntent = new Intent(this, MyBindingService.class);

 //       startService(bindingServiceIntent);
 //       bindService(bindingServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

  //      Intent intentBindingService = new Intent(this, MyIntentBindingService.class);
  //      startService(intentBindingService);
  //      bindService(intentBindingService, intentServiceConnection, Context.BIND_AUTO_CREATE);

        Intent i = new Intent(this, ResultReceiverService.class);
        i.putExtra("resultreceiver", myResultReceiver);
        startService(i);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binderService) {
            serviceAvailablity = true;
            MyBindingService.MyBindingServiceBinder binder = (MyBindingService.MyBindingServiceBinder)
                    binderService;
            service = ((MyBindingService.MyBindingServiceBinder) binderService).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceAvailablity = false;
        }
    };

    private ServiceConnection intentServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MyIntentBindingService.IntentServiceBinder svc = (MyIntentBindingService.IntentServiceBinder) service;
            myIntentBindingService = svc.getService();
            intentServiceAvailable = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            intentServiceAvailable = false;
        }
    };
}
