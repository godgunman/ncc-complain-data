package tw.fakenews.android.models;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import tw.fakenews.android.Constants;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Channel {

	public String channelName;
	public Category[] category;
	public int size;

	public static class Category implements Parcelable {
	    
	    public static final Parcelable.Creator<Category> CREATOR = new Creator();
	    
		public String categoryName;
		public Complain[] data;
		public int size;
		
        @Override
        public int describeContents() {
            return 0;
        }
        
        @Override
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeString(categoryName);
            parcel.writeInt(size);
        }
        
        public static class Creator implements Parcelable.Creator<Category> {
            public Category createFromParcel(Parcel in) {
                return new Category(in);
            }

            public Category[] newArray(int size) {
                return new Category[size];
            }
        }
        
        private Category (Parcel parcel) {
            categoryName = parcel.readString();
            size = parcel.readInt();
        }

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
						.appendPath("api").appendPath("channel");

				Log.d("Channel api", builder.build().toString());
				HttpGet target = new HttpGet(builder.build().toString());
				try {
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String content = httpClient
							.execute(target, responseHandler);
					JSONObject object = new JSONObject(content);
					if (object.has("error")) {
						Log.e("models.Channel._find()", object.get("error")
								.toString());
					} else {
						Gson gson = new Gson();
						JSONArray array = object.getJSONArray("result");
						Channel[] channels = new Channel[array.length()];
						for (int i = 0; i < channels.length; i++) {
							channels[i] = gson.fromJson(array.getJSONObject(i)
									.toString(), Channel.class);
						}
						return channels;
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
				if (channels != null) {
					callback.done(channels);
				}
			};

		}.execute();
	}

	public interface ChannelCallback {
		public void done(Channel[] channels);
	}
}