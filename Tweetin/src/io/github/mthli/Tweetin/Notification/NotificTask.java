package io.github.mthli.Tweetin.Notification;

import android.os.AsyncTask;

public class NotificTask extends AsyncTask<Void, Integer, Boolean> {
    /* Do something */

    @Override
    protected void onPreExecute() {
        /* Do something */
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        /* Do something */
        return true;
    }

    @Override
    protected void onCancelled() {
        /* Do nothing */
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        /* Do nothing */
    }

    @Override
    protected void onPostExecute(Boolean result) {
        /* Do something */
    }
}