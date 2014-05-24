package tw.fakenews.android.view.adapter;

import java.util.ArrayList;

import tw.fakenews.android.Constants;
import tw.fakenews.android.R;
import tw.fakenews.android.models.Channel;
import tw.fakenews.android.models.Channel.Category;
import tw.fakenews.android.view.activities.ChannelActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ChannelListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    
    public ChannelListAdapter(Context context, ArrayList<String> channelList) {
        super(context, 0, channelList);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.listitem_channel, parent, false);
            
            holder = new ViewHolder();
            holder.textViewRank = (TextView) convertView.findViewById(R.id.textview_channel_rank);
            holder.textViewChannelName = (TextView) convertView.findViewById(R.id.textview_channel_name);
            holder.textViewCount = (TextView) convertView.findViewById(R.id.textview_channel_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        final String channelRank = Integer.toString(position + 1);
        final String channelName = getItem(position);
        final String channelCount = "20";
        
        holder.textViewRank.setText(channelRank);
        holder.textViewChannelName.setText(channelName);
        holder.textViewCount.setText(channelCount);
        
        convertView.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO send channel name
                Intent intent = new Intent(mContext, ChannelActivity.class);
                Bundle b = new Bundle();
                b.putString(Constants.KEY_CHANNEL_RANK, channelRank);
                b.putString(Constants.KEY_CHANNEL_NAME, channelName);
                b.putString(Constants.KEY_CHANNEL_COUNT, channelCount);
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
