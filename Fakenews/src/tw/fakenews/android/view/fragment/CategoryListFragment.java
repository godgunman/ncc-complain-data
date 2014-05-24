package tw.fakenews.android.view.fragment;

import java.util.ArrayList;

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
        setListViewHeader();
        setAdapter();
        
        return rootView;
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

    }
    
    private void setAdapter() {
            
        /* dumb channel list for testing*/ 
        ArrayList<String> categoriesList = new ArrayList<String>();
        categoriesList.add("節目與廣告未區分");
        categoriesList.add("節目分級不妥");
        categoriesList.add("妨害兒少身心");
        /* dumb channel list for testing*/              
            
        adapter = new CategoriesListAdapter(getActivity(), categoriesList);
        setListAdapter(adapter);
    }
}
