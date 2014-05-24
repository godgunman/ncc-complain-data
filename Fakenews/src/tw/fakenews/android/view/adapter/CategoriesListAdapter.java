package tw.fakenews.android.view.adapter;

import java.util.ArrayList;

import tw.fakenews.android.R;
import tw.fakenews.android.view.activities.ChannelActivity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoriesListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    
    public CategoriesListAdapter(Context context, ArrayList<String> channelList) {
        super(context, 0, channelList);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.listitem_categories, parent, false);
            
            holder = new ViewHolder();
            holder.textViewCategoriesName = (TextView) convertView.findViewById(R.id.textview_categories_name);
            holder.textViewCount = (TextView) convertView.findViewById(R.id.textview_categories_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        String categoriesName = getItem(position);
        
        holder.textViewCategoriesName.setText(categoriesName);
        holder.textViewCount.setText("5");
        
        return convertView;
    }
    
    protected static class ViewHolder {
        TextView textViewCategoriesName;
        TextView textViewCount;
    }

}
