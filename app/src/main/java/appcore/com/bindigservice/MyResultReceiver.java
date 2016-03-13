package appcore.com.bindigservice;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Created by Jeremy on 13/3/2016.
 */
public class MyResultReceiver extends ResultReceiver {

    public MyResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);

        if (resultCode == 0)
            Log.v("Movie nite", "Ok we are going to the movies today");

        if (resultCode == 1)
            Log.v("Movie nite and dinner", "Ok we are going to the dinner and movies today");

    }
}
