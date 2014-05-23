package tw.fakenews.android.models;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import tw.fakenews.android.Constants;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class Channel {

	public String categoryName;
	public Category[] category;
	int size;

	public class Category {
		public String categoryName;
		public Complain[] data;
	}

	public static void find(final ChannelCallback callback) {
		_find(callback);
	}

	private static void _find(final ChannelCallback callback) {
		new AsyncTask<String, Void, Channel[]>() {

			@Override
			protected Channel[] doInBackground(String... params) {

				DefaultHttpClient httpClient = new DefaultHttpClient();
				Uri.Builder builder = new Uri.Builder();
				builder.scheme("http").authority(Constants.API_HOST)
						.appendPath("channel");

				HttpGet target = new HttpGet(builder.build().toString());
				try {
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String content = httpClient
							.execute(target, responseHandler);
					JSONObject object = new JSONObject(content);
					if (object.has("error")) {
						Log.e("models.Complain._find()", object.get("error")
								.toString());
					} else {
						Gson gson = new Gson();
						return gson.fromJson(object.getJSONArray("result")
								.toString(), Channel[].class);
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(Channel[] channels) {
				callback.done(channels);
			};

		}.execute();
	}

	public interface ChannelCallback {
		public void done(Channel[] channels);
	}
}