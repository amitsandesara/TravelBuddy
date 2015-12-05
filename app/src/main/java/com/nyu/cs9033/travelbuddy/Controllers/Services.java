package com.nyu.cs9033.travelbuddy.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class Services extends AsyncTask<String, Void, String> {
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected String doInBackground(String... params) {
        return null;
    }

    public static boolean netConnect(Context ctx)
    {
        ConnectivityManager cm;
        NetworkInfo info = null;
        try
        {
            cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = cm.getActiveNetworkInfo();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (info != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
