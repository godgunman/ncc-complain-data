package tw.fakenews.android.view.adapter;

import java.util.LinkedList;
import java.util.List;

import tw.fakenews.android.Constants;
import tw.fakenews.android.R;
import tw.fakenews.android.models.Channel;
import tw.fakenews.android.models.Channel.Category;
import tw.fakenews.android.view.activities.ChannelActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChannelListAdapter extends ArrayAdapter<Channel> {

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private Typeface engFace;
	private Typeface chnFace;

	public ChannelListAdapter(Context context, LinkedList<Channel> channelList) {
		super(context, 0, channelList);
		mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
		engFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/BebasNeue.otf");
		chnFace = Typeface.createFromAsset(context.getAssets(),
				"fonts/DFHeiStd-W5.otf");
	}

	public synchronized void setChannelList(List<Channel> channelList) {
		clear();
		for (int i = 0; i < channelList.size(); i++) {
			add(channelList.get(i));
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.listitem_channel,
					parent, false);
			holder = new ViewHolder();
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textViewRank = (TextView) convertView
				.findViewById(R.id.textview_channel_rank);
		holder.textViewRank.setTypeface(engFace);
		holder.textViewRank.setText(Integer.toString(position + 1));

		if (position == 0) {
			holder.textViewRank.setBackgroundResource(R.drawable.rank_no_1);
			holder.textViewRank.setText("");
		} else if (position <= 4) {
			holder.textViewRank.setBackgroundResource(R.drawable.rank_no);
		} else {
			holder.textViewRank.setBackgroundResource(R.drawable.rank_nogray);
		}

		holder.textViewChannelName = (TextView) convertView
				.findViewById(R.id.textview_channel_name);
		holder.textViewChannelName.setTypeface(chnFace);

		holder.textViewCount = (TextView) convertView
				.findViewById(R.id.textview_channel_count);
		holder.textViewCount.setTypeface(engFace);

		convertView.setTag(holder);

		Channel c = getItem(position);

		final String channelRank = Integer.toString(position + 1);
		final String channelName = c.channelName;
		final String channelCount = Integer.toString(c.size);
		final Category[] channelCategories = c.category;

		holder.textViewChannelName.setText(channelName);
		holder.textViewCount.setText(channelCount);

		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ChannelActivity.class);

				Bundle b = new Bundle();
				b.putString(Constants.KEY_CHANNEL_RANK, channelRank);
				b.putString(Constants.KEY_CHANNEL_NAME, channelName);
				b.putString(Constants.KEY_CHANNEL_COUNT, channelCount);
				b.putParcelableArray(Constants.KEY_CHANNEL_CATEGORIES,
						channelCategories);
				intent.putExtras(b);

				mContext.startActivity(intent);
			}
		});

		return convertView;
	}

	protected static class ViewHolder {
		TextView textViewRank;
		TextView textViewChannelName;
		TextView textViewCount;
	}
}
