package com.example.moviesearchapp;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesearchapp.io.FlushedInputStream;
import com.example.moviesearchapp.model.ImdbMovie;
import com.example.moviesearchapp.services.HttpRetriever;
import com.example.moviesearchapp.util.Utils;

public class MoviesListActivity extends ListActivity {
    
    private static final String IMDB_BASE_URL = "http://m.imdb.com/title/";
    
    private ArrayList<ImdbMovie> moviesList = new ArrayList<ImdbMovie>();
    private MoviesAdapter moviesAdapter;
    
    private static final int ITEM_VISIT_IMDB = 0;
    private static final int ITEM_VIEW_FULL_IMAGE = 1;
    
    private HttpRetriever httpRetriever = new HttpRetriever();
    
    private ProgressDialog progressDialog;
	private ImageView imageView;
	private int ListPosition;
	
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_layout);

        moviesAdapter = new MoviesAdapter(this, R.layout.movies_data_row, moviesList);
        moviesList = (ArrayList<ImdbMovie>) getIntent().getSerializableExtra("movies");
        
        setListAdapter(moviesAdapter);
        
        if (moviesList!=null && !moviesList.isEmpty()) {
            
            moviesAdapter.notifyDataSetChanged();
            moviesAdapter.clear();
            for (int i = 0; i < moviesList.size(); i++) {
                moviesAdapter.add(moviesList.get(i));
            }
        }
        
        moviesAdapter.notifyDataSetChanged();
        
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, ITEM_VISIT_IMDB, 0, 
				getString(R.string.visit_imdb)).setIcon(android.R.drawable.ic_menu_set_as);
		menu.add(Menu.NONE, ITEM_VIEW_FULL_IMAGE, 0, 
				getString(R.string.view_full_image)).setIcon(android.R.drawable.ic_menu_zoom);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case ITEM_VISIT_IMDB:
				visitImdbMoviePage();
				return true;
			case ITEM_VIEW_FULL_IMAGE:
				viewFullImagePoster();
				return true;
		}
		return false;
	}
	
	private void viewFullImagePoster() {
		
		final ImdbMovie movie = retrieveSelectedMovie();
		
		if (movie==null) {
			longToast(getString(R.string.no_movie_selected));
			return;
		}
		
		String imageUrl = movie.retrieveCoverImage();
		if (Utils.isMissing(imageUrl)) {
			longToast(getString(R.string.no_imdb_id_found));
			return;
		}
		
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.full_image_layout);
		
		final Button closeDialogButton = (Button) dialog.findViewById(R.id.close_full_image_dialog_button);
		imageView = (ImageView) dialog.findViewById(R.id.image_view);								
		
		closeDialogButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		final ImageDownloaderTask task = new ImageDownloaderTask();
    	task.execute(imageUrl);
		
		dialog.show();
		
		progressDialog = ProgressDialog.show(MoviesListActivity.this,
        		"Please wait...", "Retrieving data...", true, true);
        
        progressDialog.setOnCancelListener(new OnCancelListener() {				
			@Override
			public void onCancel(DialogInterface dialog) {
				if (task!=null) {
					task.cancel(true);
				}
			}
		});
		
	}
	
	private void visitImdbMoviePage() {
		
		final ImdbMovie movie = retrieveSelectedMovie();
		
		if (movie==null) {
			longToast(getString(R.string.no_movie_selected));
			return;
		}
		
		String imdbId = movie.getImdb_id();
		if (Utils.isMissing(imdbId)) {
			longToast(getString(R.string.no_imdb_id_found));
			return;
		}
		
		String imdbUrl = IMDB_BASE_URL + movie.getImdb_id();
		Intent imdbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl));				
		startActivity(imdbIntent);
		
	}
	
	private void showMovieOverviewDialog(final String title, final String overview) {
		
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.movie_overview_dialog);

		dialog.setTitle(title);

		final TextView overviewTextView = (TextView) dialog.findViewById(R.id.movie_overview_text_view);
		overviewTextView.setText(overview);
		
		final Button closeButton = (Button) dialog.findViewById(R.id.movie_overview_close_button);
		
		closeButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
		
	}
	
	private class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            InputStream is = httpRetriever.retrieveStream(url);
            if (is==null) {
				return null;
			}
            return BitmapFactory.decodeStream(new FlushedInputStream(is));
        }
        
        @Override
		protected void onPostExecute(final Bitmap result) {			
			runOnUiThread(new Runnable() {
		    	@Override
		    	public void run() {
		    		if (progressDialog!=null) {
		    			progressDialog.dismiss();
		    			progressDialog = null;
		    		}
		    		if (result!=null) {
		    			imageView.setImageBitmap(result);
		    		}		    		
		    	}
		    });
		}
        
    }
	
	private ImdbMovie retrieveSelectedMovie() {
		
		//int position = getSelectedItemPosition();
		if (ListPosition==-1) {
			return null;
		}
		return moviesAdapter.getItem((int)ListPosition);
	}
	
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
    	super.onListItemClick(l, v, position, id);
    	ListPosition=position;
		final ImdbMovie movie = moviesAdapter.getItem((int)position);
    	showMovieOverviewDialog(movie.getTitle(), movie.getOverview());	
    	
        /*super.onListItemClick(l, v, position, id);
        ImdbMovie movie = moviesAdapter.getItem(position);
        
        String imdbId = movie.getImdb_id();
        if (imdbId==null || imdbId.length()==0) {
            longToast(getString(R.string.no_imdb_id_found));
            return;
        }
        
        String imdbUrl = IMDB_BASE_URL + movie.getImdb_id();
        
        Intent imdbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl));                
        startActivity(imdbIntent);*/
        
    }
    
    public void longToast(CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
}


