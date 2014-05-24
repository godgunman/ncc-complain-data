package tw.fakenews.android.test;

import android.util.Log;
import tw.fakenews.android.models.Channel;
import tw.fakenews.android.models.Channel.ChannelCallback;
import tw.fakenews.android.models.Complain;
import tw.fakenews.android.models.Complain.ComplainCallback;

public class ApiTest {

	public static void complainTest() {

		Log.i("[complainTest]", "test starts");
		Complain.find(0, 10, new ComplainCallback() {
			@Override
			public void done(Complain[] complains) {
				Log.i("[complainTest]", "complains.length" + complains.length);
				for (Complain complain : complains) {
					Log.i("[complainTest]", "cid: " + complain.cid);
					Log.i("[complainTest]", "complainTitle: "
							+ complain.complainTitle);
					Log.i("[complainTest]", "complainCategory: "
							+ complain.complainCategory);
					Log.i("[complainTest]", "complainContent: "
							+ complain.complainContent);
				}
				Log.i("[complainTest]", "test done");

			}
		});
	}

	public static void complainTest2() {
		Complain.findByChannelNameAndComplainCategory("中天新聞台", "內容不實、不公", 0,
				10, new ComplainCallback() {
					@Override
					public void done(Complain[] complains) {
						Log.i("[complainTest]", "complains.length"
								+ complains.length);
						for (Complain complain : complains) {
							Log.i("[complainTest]", "cid: " + complain.cid);
							Log.i("[complainTest]", "complainTitle: "
									+ complain.complainTitle);
							Log.i("[complainTest]", "complainCategory: "
									+ complain.complainCategory);
							Log.i("[complainTest]", "complainContent: "
									+ complain.complainContent);
						}
						Log.i("[complainTest]", "test done");

					}
				});

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
				Log.i("[chennalTest]", "test done");
			}
		});
	}
}
