package tw.fakenews.android.view.activities;

import java.util.ArrayList;

import tw.fakenews.android.R;
import tw.fakenews.android.models.Channel;
import tw.fakenews.android.models.Channel.ChannelCallback;
import tw.fakenews.android.view.adapter.ChannelListAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends ListFragment implements ChannelCallback {

        private LinearLayout headerLayout;
        private ChannelListAdapter adapter;
        
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            
            headerLayout = (LinearLayout) rootView.findViewById(R.id.headerLayout);
            setHeaderLayout();
            
            setAdapter();
            
            Channel.find(this);
            return rootView;
        }
        
        @Override
        public void onActivityCreated (Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListViewHeader();
        }
        
        private void setHeaderLayout() {
            if (headerLayout != null) {
                headerLayout.setVisibility(View.GONE);
            }
        }
        
        private void setListViewHeader() {
            ImageView imgView = new ImageView(getActivity());
            imgView.setImageResource(R.drawable.ic_launcher);
            imgView.setScaleType(ScaleType.FIT_XY);
            imgView.setAdjustViewBounds(true);
                
            this.getListView().addHeaderView(imgView);
        }
        
        private void setAdapter() {
                
            /* dumb channel list for testing*/ 
            ArrayList<String> channelList = new ArrayList<String>();
            channelList.add("中天新聞台");
            channelList.add("TVBS新聞台");
            channelList.add("東森新聞台");
            channelList.add("三立新聞台");
            channelList.add("era news年代新聞");
            channelList.add("民視");
            /* dumb channel list for testing*/              
            
//            for (Channel c: channels) {
//                channelList.add(c.categoryName);
//            }
                
            adapter = new ChannelListAdapter(getActivity(), channelList);
            setListAdapter(adapter);
       }

        @Override
        public void done(Channel[] channels) {
//            setAdapter(channels);
        }        
    }
}
