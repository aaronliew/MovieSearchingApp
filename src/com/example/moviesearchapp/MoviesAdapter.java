package com.example.moviesearchapp;


import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesearchapp.R;
import com.example.moviesearchapp.io.FlushedInputStream;
import com.example.moviesearchapp.model.ImdbMovie;
import com.example.moviesearchapp.services.HttpRetriever;

public class MoviesAdapter extends ArrayAdapter<ImdbMovie> {
    
    private HttpRetriever httpRetriever = new HttpRetriever();
    
    private ArrayList<ImdbMovie> movieDataItems;
    
    private Activity context;
    
    public MoviesAdapter(Activity context, int textViewResourceId, ArrayList<ImdbMovie> movieDataItems) {
        super(context, textViewResourceId, movieDataItems);
        this.context = context;
        this.movieDataItems = movieDataItems;
    }

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
  
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.movies_data_row, null);
        }
        
        ImdbMovie movie = movieDataItems.get(position);
        
        if (movie != null) {
            
            // name
            TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);
            nameTextView.setText(movie.getTitle());
            
            // rating
            TextView ratingTextView = (TextView) view.findViewById(R.id.rating_text_view);
            ratingTextView.setText("Popularity: " + movie.getPopularity());
            
            // released
            TextView releasedTextView = (TextView) view.findViewById(R.id.released_text_view);
            releasedTextView.setText("Release Date: " + movie.getRelease_date());
         
            
            // thumb image
            ImageView imageView = (ImageView) view.findViewById(R.id.movie_thumb_icon);
            String url = movie.retrieveThumbnail();
            
            if (url!=null) {
                Bitmap bitmap = fetchBitmapFromCache(url);
                if (bitmap==null) {                
                    new BitmapDownloaderTask(imageView).execute(url);
                }
                else {
                	imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300, 330, false));
                    //imageView.setImageBitmap(bitmap);                    
                }
            }
            else {
                imageView.setImageBitmap(null);
            }
            
        }
        
        return view;
        
    }
    
    private LinkedHashMap<String, Bitmap> bitmapCache = new LinkedHashMap<String, Bitmap>();
    
    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (bitmapCache) {
                bitmapCache.put(url, bitmap);
            }
        }
    }
    
    private Bitmap fetchBitmapFromCache(String url) {
        
        synchronized (bitmapCache) {
            final Bitmap bitmap = bitmapCache.get(url);
            if (bitmap != null) {
                // Bitmap found in cache
                // Move element to first position, so that it is removed last
                bitmapCache.remove(url);
                bitmapCache.put(url, bitmap);
                return bitmap;
            }
        }

        return null;
        
    }
    
    private class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        
        private String url;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }
        
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            InputStream is = httpRetriever.retrieveStream(url);
            if (is==null) {
                  return null;
            }
            return BitmapFactory.decodeStream(new FlushedInputStream(is));
        }
        
        @Override
        protected void onPostExecute(Bitmap bitmap) {            
            if (isCancelled()) {
                bitmap = null;
            }
            
            addBitmapToCache(url, bitmap);

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                	imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300, 330, false));
                    //imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
    
}
