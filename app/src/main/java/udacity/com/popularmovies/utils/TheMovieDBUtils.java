package udacity.com.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class TheMovieDBUtils {
    private static final String TAG = TheMovieDBUtils.class.getSimpleName();

    private static final String BASE_PATH = "https://api.themoviedb.org/3";
    private static final String API_KEY_PARAM = "api_key";
    private static final String API_KEY = "";
    private static final String MOVIE_PATH = "movie";
    private static final String TOP_RATED_PATH = "top_rated";
    private static final String MOST_POPULAR_PATH = "popular";
    private static final String NOW_PLAYING_PATH = "now_playing";

    public static URL buildUrl(String path) {
        Uri builtUri = Uri.parse(BASE_PATH).buildUpon()
                .appendPath(MOVIE_PATH)
                .appendPath(path)
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String topRatedList() {
        URL url = buildUrl(TOP_RATED_PATH);
        try {
            return getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String nowPlayingList() {
        URL url = buildUrl(NOW_PLAYING_PATH);
        try {
            return getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }    }

    public static String mostPopularList() {
        URL url = buildUrl(MOST_POPULAR_PATH);
        try {
            return getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
