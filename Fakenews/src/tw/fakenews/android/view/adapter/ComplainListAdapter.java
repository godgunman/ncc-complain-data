package tw.fakenews.android.view.adapter;

import java.util.LinkedList;
import java.util.List;

import tw.fakenews.android.R;
import tw.fakenews.android.models.Complain;
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
        
        Typeface chnFace = Typeface.createFromAsset(mContext.getAssets(),
                "fonts/DFHeiStd-W5.otf");
        
        final String complainId = c.cid;
        final String complainName = c.complainTitle;
        final String complainProgress = c.status;
        
        holder.textViewComplainId.setText("案號" + complainId);
        holder.textViewComplainName.setText(complainName);        
        
        if (complainProgress != null) {
            if (complainProgress.equals("pending")) {
                holder.textViewComplainProgress.setText("處理中");
                holder.textViewComplainProgress.setBackgroundResource(R.color.bg_gray);
            } else {
                holder.textViewComplainProgress.setText("已結案");
                holder.textViewComplainProgress.setBackgroundResource(R.color.bg_aqua);
            }
        }
        
        holder.textViewComplainId.setTypeface(chnFace);
        holder.textViewComplainName.setTypeface(chnFace);
        holder.textViewComplainProgress.setTypeface(chnFace);
        
        return convertView;
    }
    
    protected static class ViewHolder {
        TextView textViewComplainId;
        TextView textViewComplainName;
        TextView textViewComplainProgress;
    }
}
