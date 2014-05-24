package tw.fakenews.android.view.fragment;

import java.util.ArrayList;

import tw.fakenews.android.Constants;
import tw.fakenews.android.R;
import tw.fakenews.android.view.adapter.CategoriesListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ComplainListFragment extends ListFragment {
    
    private OnComplainSelectedListener mCallback;
    
    private LinearLayout headerLayout;
    private CategoriesListAdapter adapter;
    
    public interface OnComplainSelectedListener {
        public void onComplainSelected(int position);
        public void onBackToChannel();
    }
    
    public ComplainListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        
        headerLayout = (LinearLayout) rootView.findViewById(R.id.headerLayout);
        
        setHeaderLayout(getArguments());
        setListViewHeader();
        setAdapter();
        
        return rootView;
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        try {
            mCallback = (OnComplainSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    
    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
        mCallback.onComplainSelected(position);
    }

    private void setHeaderLayout(Bundle b) {        
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View categoriesView = inflater.inflate(R.layout.listitem_categories, null);
        
        String categoriesName = b.getString(Constants.KEY_CATEGORIES_NAME);
        String categoriesCount = b.getString(Constants.KEY_CATEGORIES_COUNT);
        
        TextView textviewChannelName = (TextView) categoriesView.findViewById(R.id.textview_categories_name);
        if (categoriesName != null) {
            textviewChannelName.setText(categoriesName);
        }
        
        TextView textviewChannelCount = (TextView) categoriesView.findViewById(R.id.textview_categories_count);
        if (categoriesCount != null) {
            textviewChannelCount.setText(categoriesCount);
        }
        
        headerLayout.addView(categoriesView);
        headerLayout.setClickable(true);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                mCallback.onBackToChannel();
            }
        });
    }
    
    private void setListViewHeader() {

    }
    
    private void setAdapter() {
            
        /* dumb channel list for testing*/ 
        ArrayList<String> categoriesList = new ArrayList<String>();
        categoriesList.add("1");
        categoriesList.add("2");
        categoriesList.add("3");
        /* dumb channel list for testing*/              
            
//        adapter = new CategoriesListAdapter(getActivity(), categoriesList);
//        setListAdapter(adapter);
    }
}
