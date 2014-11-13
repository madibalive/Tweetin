package io.github.mthli.Tweetin.Task.Discovery;

import android.content.Context;
import android.os.AsyncTask;
import io.github.mthli.Tweetin.Fragment.DiscoveryFragment;
import io.github.mthli.Tweetin.Unit.Database.DatabaseUnit;
import io.github.mthli.Tweetin.Unit.Notification.NotificationUnit;
import io.github.mthli.Tweetin.Unit.Tweet.Tweet;
import io.github.mthli.Tweetin.Unit.Tweet.TweetAdapter;
import twitter4j.Twitter;

import java.util.List;

public class DiscoveryFavoriteTask extends AsyncTask<Void, Integer, Boolean> {

    private DiscoveryFragment discoveryFragment;
    private Context context;
    private Twitter twitter;

    private TweetAdapter tweetAdapter;
    private List<Tweet> tweetList;
    private Tweet oldTweet;
    private Tweet newTweet;
    private int position;

    private NotificationUnit notificationUnit;

    public DiscoveryFavoriteTask(
            DiscoveryFragment discoveryFragment,
            int position
    ) {
        this.discoveryFragment = discoveryFragment;
        this.position = position;
    }

    @Override
    protected void onPreExecute() {
        context = discoveryFragment.getContentView().getContext();
        twitter = discoveryFragment.getTwitter();

        tweetAdapter = discoveryFragment.getTweetAdapter();
        tweetList = discoveryFragment.getTweetList();
        oldTweet = tweetList.get(position);

        notificationUnit = new NotificationUnit(context, oldTweet);
        notificationUnit.favoriting();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            twitter.createFavorite(oldTweet.getStatusId());

            newTweet = new Tweet();
            newTweet.setStatusId(oldTweet.getStatusId());
            newTweet.setReplyToStatusId(oldTweet.getReplyToStatusId());
            newTweet.setUserId(oldTweet.getUserId());
            newTweet.setRetweetedByUserId(oldTweet.getRetweetedByUserId());
            newTweet.setAvatarURL(oldTweet.getAvatarURL());
            newTweet.setCreatedAt(oldTweet.getCreatedAt());
            newTweet.setName(oldTweet.getName());
            newTweet.setScreenName(oldTweet.getScreenName());
            newTweet.setProtect(oldTweet.isProtect());
            newTweet.setCheckIn(oldTweet.getCheckIn());
            newTweet.setPhotoURL(oldTweet.getPhotoURL()); //
            newTweet.setText(oldTweet.getText());
            newTweet.setRetweet(oldTweet.isRetweet());
            newTweet.setRetweetedByUserName(
                    oldTweet.getRetweetedByUserName()
            );
            newTweet.setFavorite(true);

            DatabaseUnit.updatedByFavorite(context, oldTweet);
            notificationUnit.favoriteSuccessful();
        } catch (Exception e) {
            notificationUnit.favoriteFailed();

            return false;
        }

        if (isCancelled()) {
            return false;
        }
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
        if (result) {
            tweetList.remove(position);
            tweetList.add(position, newTweet);
            tweetAdapter.notifyDataSetChanged();
        }
    }
}