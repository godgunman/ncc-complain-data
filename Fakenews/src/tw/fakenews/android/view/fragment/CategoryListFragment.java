package tw.fakenews.android.view.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import tw.fakenews.android.Constants;
import tw.fakenews.android.R;
import tw.fakenews.android.models.Channel;
import tw.fakenews.android.models.Channel.Category;
import tw.fakenews.android.view.adapter.CategoriesListAdapter;
import tw.fakenews.android.view.adapter.ChannelListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CategoryListFragment extends ListFragment {
    
    private OnCategorySelectedListener mCallback;
    
    private LinearLayout headerLayout;
    private CategoriesListAdapter adapter;
    
    public interface OnCategorySelectedListener {
        public void onCategorySelected(int position);
    }
    
    public CategoryListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        
        headerLayout = (LinearLayout) rootView.findViewById(R.id.headerLayout);
        
        setHeaderLayout();
        
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListViewHeader();
        setAdapter();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        try {
            mCallback = (OnCategorySelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    
    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        mCallback.onCategorySelected(position);
    }

    private void setHeaderLayout() {
        headerLayout.setVisibility(View.GONE);
    }
    
    private void setListViewHeader() {
        // No listview header in categoryListFragment
    }
    
    private void setAdapter() {                       
        Bundle b = getArguments();
        Parcelable[] parcels = b.getParcelableArray(Constants.KEY_CHANNEL_CATEGORIES);
        
        Category[] categories = new Category[parcels.length];
        for (int i = 0; i < parcels.length; i++) {
            categories[i] = (Category) parcels[i];
        }
        
        adapter = new CategoriesListAdapter(getActivity(), categories);
        setListAdapter(adapter);
    }
}
