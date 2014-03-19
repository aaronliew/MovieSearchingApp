package com.example.moviesearchapp.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.moviesearchapp.R;
import com.example.moviesearchapp.model.LatestMovie;
import com.example.moviesearchapp.services.MovieSeeker;

public class MovieSearchWidget extends AppWidgetProvider {
	
	private static final String IMDB_BASE_URL = "http://m.imdb.com/title/";
	
	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // To prevent any ANR timeouts, we perform the update in a service
        context.startService(new Intent(context, UpdateService.class));
    }
	
    public static class UpdateService extends Service {
    	
    	private MovieSeeker movieSeeker = new MovieSeeker();
    	
        @Override
        public void onStart(Intent intent, int startId) {
        	final Context context = this;
        
            // Build the widget update for today
        	new Thread(new Runnable() {
    	        public void run() {
            	RemoteViews updateViews = buildUpdate(context);

            // Push update for this widget to the home screen
            ComponentName thisWidget = new ComponentName(context, MovieSearchWidget.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.updateAppWidget(thisWidget, updateViews);
    	       }}).start();
        }
        
		public RemoteViews buildUpdate(Context context) {
			LatestMovie movie = movieSeeker.findLatest();
			String imdbUrl = IMDB_BASE_URL + movie.getImdb_id();

			// Build an update that holds the updated widget contents
			RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

			updateViews.setTextViewText(R.id.app_name, getString(R.string.app_name));
			updateViews.setTextViewText(R.id.movie_name, movie.getTitle());
			
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.BROWSABLE");
			intent.setData(Uri.parse(imdbUrl));
			
			PendingIntent pendingIntent = 
				PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	        
			updateViews.setOnClickPendingIntent(R.id.movie_name, pendingIntent);
			
			return updateViews;
		}

        @Override
        public IBinder onBind(Intent intent) {
            // We don't need to bind to this service
            return null;
        }
        
    }

}
