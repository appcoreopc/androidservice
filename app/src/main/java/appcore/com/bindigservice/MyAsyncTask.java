package appcore.com.bindigservice;

/**
 * Created by Jeremy on 12/3/2016.
 */

import android.os.AsyncTask;
import android.util.Log;

/// asynctask suitable for very simple task that
/// do not get complicated over time
public class MyAsyncTask extends AsyncTask<String, Void, Void>
{
    @Override
    protected Void doInBackground(String... params) {
        try {
            for (int a=0; a < 5;a++) {
                Thread.sleep(5000);
                Log.v("app slep mode", " diving into sleep mode!!!");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}