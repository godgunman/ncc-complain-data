package tw.fakenews.android.view.activities;

import tw.fakenews.android.Constants;
import tw.fakenews.android.R;
import tw.fakenews.android.models.Channel.Category;
import tw.fakenews.android.view.fragment.CategoryListFragment;
import tw.fakenews.android.view.fragment.ComplainListFragment;
import tw.fakenews.android.view.fragment.CategoryListFragment.OnCategorySelectedListener;
import tw.fakenews.android.view.fragment.ComplainListFragment.OnComplainSelectedListener;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChannelActivity extends ActionBarActivity implements
		OnCategorySelectedListener, OnComplainSelectedListener {

	private Category[] mCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel);

		LinearLayout layout = (LinearLayout) findViewById(R.id.container);

		Bundle b = this.getIntent().getExtras();

		View view = getHeaderView(b);
		layout.addView(view);

		setMyCategories();

		Fragment f = new CategoryListFragment();
		f.setArguments(b);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, f).commit();
		}

		getSupportActionBar().setIcon(R.drawable.action_bar_title);
		getSupportActionBar().setTitle("");
	}

	private View getHeaderView(Bundle b) {
		Typeface engFace = Typeface.createFromAsset(this.getAssets(),
				"fonts/BebasNeue.otf");

		Typeface chnFace = Typeface.createFromAsset(this.getAssets(),
				"fonts/DFHeiStd-W5.otf");

		String channelName = b.getString(Constants.KEY_CHANNEL_NAME);
		String channelRank = b.getString(Constants.KEY_CHANNEL_RANK);
		String channelCount = b.getString(Constants.KEY_CHANNEL_COUNT);

		LayoutInflater inflater = LayoutInflater.from(this);
		View channelView = inflater.inflate(R.layout.listitem_channel, null);

		TextView textviewChannelName = (TextView) channelView
				.findViewById(R.id.textview_channel_name);
		if (channelName != null) {
			textviewChannelName.setText(channelName);
			textviewChannelName.setTypeface(chnFace);
		}

		TextView textviewChannelRank = (TextView) channelView
				.findViewById(R.id.textview_channel_rank);
		if (channelRank != null) {
			textviewChannelRank.setText(channelRank);
		}

		TextView textviewChannelCount = (TextView) channelView
				.findViewById(R.id.textview_channel_count);
		if (channelCount != null) {
			textviewChannelCount.setText(channelCount);
			textviewChannelCount.setTypeface(engFace);
		}

		channelView.setPadding(0, 0, 0, 20);

		return channelView;
	}

	private void setMyCategories() {
		Bundle b = this.getIntent().getExtras();
		Parcelable[] parcels = b
				.getParcelableArray(Constants.KEY_CHANNEL_CATEGORIES);

		mCategories = new Category[parcels.length];
		for (int i = 0; i < parcels.length; i++) {
			mCategories[i] = (Category) parcels[i];
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

	@Override
	public void onCategorySelected(int position) {
		Fragment f = new ComplainListFragment();

		if (mCategories != null) {
			Category c = mCategories[position];

			Bundle b = new Bundle();
			b.putString(Constants.KEY_CATEGORIES_NAME, c.categoryName);
			b.putString(Constants.KEY_CATEGORIES_COUNT,
					Integer.toString(c.size));
			f.setArguments(b);

			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, f).commit();
		}
	}

	@Override
	public void onComplainSelected(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackToChannel() {
		Fragment f = new CategoryListFragment();
		f.setArguments(this.getIntent().getExtras());

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, f).commit();
	}

}