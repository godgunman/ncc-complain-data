package tw.fakenews.android.view.activities;

import java.util.Arrays;
import java.util.LinkedList;

import tw.fakenews.android.R;
import tw.fakenews.android.models.Channel;
import tw.fakenews.android.models.Channel.ChannelCallback;
import tw.fakenews.android.test.ApiTest;
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
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		// ApiTest.chennalTest();
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
	public static class PlaceholderFragment extends ListFragment implements
			ChannelCallback {

		private LinearLayout headerLayout;
		private ChannelListAdapter mAdapter;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			headerLayout = (LinearLayout) rootView
					.findViewById(R.id.headerLayout);
			setHeaderLayout();

			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			setListViewHeader();
			mAdapter = new ChannelListAdapter(getActivity(),
					new LinkedList<Channel>());
			setListAdapter(mAdapter);

			Channel.find(this);
		}

		private void setHeaderLayout() {
			if (headerLayout != null) {
				headerLayout.setVisibility(View.GONE);
			}
		}

		private void setListViewHeader() {
			LayoutInflater layoutInfalter = getActivity().getLayoutInflater();
			View view = layoutInfalter.inflate(R.layout.rank_header, null);
			this.getListView().addHeaderView(view);
		}

		@Override
		public void done(Channel[] channels) {
			mAdapter.setChannelList(Arrays.asList(channels));
		}
	}
}
