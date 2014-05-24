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
import tw.fakenews.android.models.Channel.Category;
import tw.fakenews.android.models.Channel.Category.Creator;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Complain implements Parcelable {
    
    public static final Parcelable.Creator<Complain> CREATOR = new Creator();

	public String cid;
	public String date;
	public String channelName;
	public String programName;

	public String broadcastDate;
	public String broadcastTime;

	public String channelCategory;
	public String complainCategory;

	public String complainTitle;
	public String complainContent;
	public String replyContent;
	public String status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(cid);
        parcel.writeString(date);
        parcel.writeString(channelName);
        parcel.writeString(programName);

        parcel.writeString(broadcastDate);
        parcel.writeString(broadcastTime);

        parcel.writeString(channelCategory);
        parcel.writeString(complainCategory);

        parcel.writeString(complainTitle);
        parcel.writeString(complainContent);
        parcel.writeString(replyContent);
        parcel.writeString(status);
    }

    public static class Creator implements Parcelable.Creator<Complain> {
        public Complain createFromParcel(Parcel in) {
            return new Complain(in);
        }

        public Complain[] newArray(int size) {
            return new Complain[size];
        }
    }

    private Complain(Parcel parcel) {
        cid = parcel.readString();
        date = parcel.readString();
        channelName = parcel.readString();
        programName = parcel.readString();

        broadcastDate = parcel.readString();
        broadcastTime = parcel.readString();

        channelCategory = parcel.readString();
        complainCategory = parcel.readString();

        complainTitle = parcel.readString();
        complainContent = parcel.readString();
        replyContent = parcel.readString();
        status = parcel.readString();
}
	
	public static void find(int skip, int limit, final ComplainCallback callback) {
		_find(new String[0], skip, limit, callback);
	}

	public static void findByChannelNameAndComplainCategory(String channelName,
			String complainCategory, int skip, int limit,
			final ComplainCallback callback) {
		_find(new String[] { "channelName", channelName, "complainCategory",
				complainCategory }, skip, limit, callback);
	}

	public static void findByProgram(String programName, int skip, int limit,
			final ComplainCallback callback) {
		_find(new String[] { "programName", programName }, skip, limit,
				callback);
	}

	private static void _find(String[] params, final int skip, final int limit,
			final ComplainCallback callback) {
		new AsyncTask<String, Void, Complain[]>() {

			@Override
			protected Complain[] doInBackground(String... params) {

				Uri.Builder builder = new Uri.Builder();
				builder.scheme("http").authority(Constants.API_HOST)
						.appendPath("api").appendPath("complain");
				for (int i = 0; i < params.length; i += 2) {
					builder.appendQueryParameter(params[i], params[i + 1]);
				}
				builder.appendQueryParameter("limit", String.valueOf(limit))
						.appendQueryParameter("skip", String.valueOf(skip));

				HttpGet target = new HttpGet(builder.build().toString());
				Log.d("Complain._find", "api url = "
						+ builder.build().toString());

				DefaultHttpClient httpClient = new DefaultHttpClient();
				try {
					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					String content = httpClient
							.execute(target, responseHandler);
					JSONObject object = new JSONObject(content);
					if (object.has("error") && object.get("error") != null
							&& object.get("result") == null) {
						Log.e("[models.Complain]",
								"error: " + object.get("error").toString());
					} else {
						Gson gson = new Gson();
						JSONArray array = object.getJSONArray("result");
						return gson
								.fromJson(array.toString(), Complain[].class);
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

			protected void onPostExecute(Complain[] Complains) {
				callback.done(Complains);
			};

		}.execute(params);
	}

	public interface ComplainCallback {
		public void done(Complain[] complains);
	}
}