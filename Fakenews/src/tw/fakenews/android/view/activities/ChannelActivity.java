package tw.fakenews.android.view.activities;


import tw.fakenews.android.Constants;
import tw.fakenews.android.R;
import tw.fakenews.android.view.fragment.CategoryListFragment;
import tw.fakenews.android.view.fragment.ComplainListFragment;
import tw.fakenews.android.view.fragment.CategoryListFragment.OnCategorySelectedListener;
import tw.fakenews.android.view.fragment.ComplainListFragment.OnComplainSelectedListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChannelActivity extends ActionBarActivity 
    implements OnCategorySelectedListener, OnComplainSelectedListener {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        LinearLayout layout = (LinearLayout) findViewById(R.id.container);

        View view = getHeaderView(this.getIntent().getExtras());
        layout.addView(view);
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CategoryListFragment())
                    .commit();
        }
    }

    private View getHeaderView(Bundle b) {
        String channelName = b.getString(Constants.KEY_CHANNEL_NAME);
        String channelRank = b.getString(Constants.KEY_CHANNEL_RANK);
        String channelCount = b.getString(Constants.KEY_CHANNEL_COUNT);
        
        LayoutInflater inflater = LayoutInflater.from(this);
        View channelView = inflater.inflate(R.layout.listitem_channel, null);
        
        TextView textviewChannelName = (TextView) channelView.findViewById(R.id.textview_channel_name);
        if (channelName != null) {
            textviewChannelName.setText(channelName);
        }
        
        TextView textviewChannelRank = (TextView) channelView.findViewById(R.id.textview_channel_rank);
        if (channelRank != null) {
            textviewChannelRank.setText(channelRank);
        }
        
        TextView textviewChannelCount = (TextView) channelView.findViewById(R.id.textview_channel_count);
        if (channelCount != null) {
            textviewChannelCount.setText(channelCount);
        }
        
        return channelView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCategorySelected(int position) {
        Fragment f = new ComplainListFragment();
        Bundle b = new Bundle();
        b.putString(Constants.KEY_CATEGORIES_NAME, "FOOOOO");
        b.putString(Constants.KEY_CATEGORIES_COUNT, Integer.toString(position));
        f.setArguments(b);
        
        getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, f)
        .commit();
    }

    @Override
    public void onComplainSelected(int position) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onBackToChannel() {
        getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, new CategoryListFragment())
        .commit();
    }

}
