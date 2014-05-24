package tw.fakenews.android.test;

import android.util.Log;
import tw.fakenews.android.models.Channel;
import tw.fakenews.android.models.Channel.ChannelCallback;

public class ApiTest {

	public static void complainTest() {

	}

	public static void chennalTest() {

		Log.i("[chennalTest]", "test starts");

		Channel.find(new ChannelCallback() {
			@Override
			public void done(Channel[] channels) {
				Log.i("[chennalTest]", "channels.length" + channels.length);
				for (Channel channel : channels) {
					Log.i("[chennalTest]", channel.channelName);
				}
			}
		});
	}
}
