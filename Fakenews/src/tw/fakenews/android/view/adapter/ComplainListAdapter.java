package tw.fakenews.android.view.adapter;

import java.util.LinkedList;
import java.util.List;

import tw.fakenews.android.R;
import tw.fakenews.android.models.Complain;
import tw.fakenews.android.view.activities.ChannelActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ComplainListAdapter extends ArrayAdapter<Complain> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    
    public ComplainListAdapter(Context context, LinkedList<Complain> complainList) {
        super(context, 0, complainList);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    
    public synchronized void setComplainList(List<Complain> complainList) {
        clear();
        for (int i = 0; i < complainList.size(); i++) {
            add(complainList.get(i));
        }
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.listitem_complain, parent, false);
            
            holder = new ViewHolder();
            holder.textViewComplainId = (TextView) convertView.findViewById(R.id.textview_complain_id);
            holder.textViewComplainName = (TextView) convertView.findViewById(R.id.textview_complain_name);
            holder.textViewComplainProgress = (TextView) convertView.findViewById(R.id.textview_complain_progress);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        Complain c = getItem(position);
        
        final String complainId = c.cid;
        final String complainName = c.complainTitle;
        final String complainProgress = c.programName;
        
        holder.textViewComplainId.setText(complainId);
        holder.textViewComplainName.setText(complainName);
        holder.textViewComplainProgress.setText(complainProgress);
        
        convertView.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChannelActivity.class);
                
                Bundle b = new Bundle();
//                b.putString(Constants.KEY_CHANNEL_RANK, channelRank);
//                b.putString(Constants.KEY_CHANNEL_NAME, channelName);
//                b.putString(Constants.KEY_CHANNEL_COUNT, channelCount);
//                b.putParcelableArray(Constants.KEY_CHANNEL_CATEGORIES, channelCategories);
                intent.putExtras(b);
                
                mContext.startActivity(intent);
            }
        });
        
        return convertView;
    }
    
    protected static class ViewHolder {
        TextView textViewComplainId;
        TextView textViewComplainName;
        TextView textViewComplainProgress;
    }
}
