package com.ctingcter.booklisting;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** URL to query the USGS dataset for earthquake information */
    private static final String GOOGLEBOOK_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookAsyncTask task = new BookAsyncTask();
        task.execute(GOOGLEBOOK_REQUEST_URL);
    }

    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUi(Book book) {
        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(book.title);

        TextView authorTextView = (TextView) findViewById(R.id.author);
        authorTextView.setText(book.author);

    }

    private class BookAsyncTask extends AsyncTask<String, Void, Book> {
        @Override
        protected Book doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            // Perform the HTTP request for earthquake data and process the response.
            Book result = Utils.fetchBookData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Book result) {
            super.onPostExecute(result);
            if (result == null) {
                return;
            }
            
            updateUi(result);
        }
    }
}
