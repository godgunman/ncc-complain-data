package tw.fakenews.android.view.adapter;

import java.util.LinkedList;
import java.util.List;

import tw.fakenews.android.Constants;
import tw.fakenews.android.R;
import tw.fakenews.android.models.Channel;
import tw.fakenews.android.view.activities.ChannelActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChannelListAdapter extends ArrayAdapter<Channel> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    
    
    public ChannelListAdapter(Context context, LinkedList<Channel> channelList) {
        super(context, 0, channelList);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
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
            convertView = mLayoutInflater.inflate(R.layout.listitem_channel, parent, false);
            
            holder = new ViewHolder();
            holder.textViewRank = (TextView) convertView.findViewById(R.id.textview_channel_rank);
            holder.textViewChannelName = (TextView) convertView.findViewById(R.id.textview_channel_name);
            holder.textViewCount = (TextView) convertView.findViewById(R.id.textview_channel_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        Channel c = getItem(position);
        
        final String channelRank = Integer.toString(position + 1);
        final String channelName = c.channelName;
        final String channelCount = Integer.toString(c.size);
        
        holder.textViewRank.setText(channelRank);
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
