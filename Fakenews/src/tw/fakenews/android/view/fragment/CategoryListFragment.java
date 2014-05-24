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
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CategoryListFragment extends Fragment {
    
    private OnCategorySelectedListener mCallback;
    
    private LinearLayout containerView;
    private Typeface engFace;

    public interface OnCategorySelectedListener {
        public void onCategorySelected(int position);
    }
    
    public CategoryListFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_channel, container, false);
        
        containerView = (LinearLayout) rootView.findViewById(R.id.container);
        
        engFace = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/BebasNeue.otf");
        
        setCategoryList();
        
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
    
//    @Override
//    public void onListItemClick (ListView l, View v, int position, long id) {
//        mCallback.onCategorySelected(position);
//    }
    
    private void setCategoryList() {
        
        Category[] categories = getCategories();
        
        int categoriesSize = categories.length;
        int rowNum = categoriesSize / 3;
        int residualElementNum = categoriesSize % 3;
                       
        for (int i = 0; i < rowNum + 1; i++) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View rowView = inflater.inflate(R.layout.row_categories, null);
            
            int[] catId = { R.id.category1, R.id.category2, R.id.category3};
            int[] imgId = { R.id.categoryImg1, R.id.categoryImg2, R.id.categoryImg3};
            int[] coundId = { R.id.categoryCount1, R.id.categoryCount2, R.id.categoryCount3};
            
            for (int j = 0; j < 3; j++) {
                RelativeLayout rowContainer = (RelativeLayout) rowView.findViewById(catId[j]);
                if (i == rowNum && residualElementNum < j + 1) {
                    rowContainer.setVisibility(View.INVISIBLE);
                } else {
                    final int position = i * 3 + j;
                    Category c = categories[position];
                    ImageView imgView = (ImageView) rowView.findViewById(imgId[j]);
                    imgView.setImageResource(findCategoryRes(c.categoryName));
                    
                    TextView textView = (TextView) rowView.findViewById(coundId[j]);
                    textView.setText(Integer.toString(c.size));
                    textView.setTypeface(engFace);
                    
                    rowContainer.setClickable(true);
                    rowContainer.setOnClickListener(new View.OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                            mCallback.onCategorySelected(position);
                        }
                    });
                }
            }
            
            containerView.addView(rowView);
        }
        
    }
    
    private Category[] getCategories() {
        Bundle b = getArguments();
        Parcelable[] parcels = b.getParcelableArray(Constants.KEY_CHANNEL_CATEGORIES);
        
        Category[] categories = new Category[parcels.length];
        for (int i = 0; i < parcels.length; i++) {
            categories[i] = (Category) parcels[i];
        }
        return categories;
    }
    
    private int findCategoryRes(String name) {
        
        // ugly hard code LOLLLL
        
        if (name.equals("節目與廣告未區分")) {
            return R.drawable.category_ads;
        } else if (name.equals("異動未告知")) {
            return R.drawable.category_change;
        } else if (name.equals("內容不實、不公")) {
            return R.drawable.category_content;
        } else if (name.equals("妨害公序良俗")) {
            return R.drawable.category_custom;
        } else if (name.equals("節目分級不妥")) {
            return R.drawable.category_grade;
        } else if (name.equals("違規使用插播式字幕")) {
            return R.drawable.category_insert;
        } else if (name.equals("妨害兒少身心")) {
            return R.drawable.category_kid;
        } else if (name.equals("法規/資訊查詢")) {
            return R.drawable.category_law;
        } else if (name.equals("針對整體傳播環境、監理政策/法規或本會施政提供個人想法")) {
            return R.drawable.category_media;
        } else if (name.equals("違反新聞製播倫理")) {
            return R.drawable.category_moral;
        } else if (name.equals("廣告內容或排播不妥")) {
            return R.drawable.category_notgood;
        } else if (name.equals("重播次數過於頻繁")) {
            return R.drawable.category_repeat;
        } else if (name.equals("其他")) {
            return R.drawable.category_other;
        } else if (name.equals("針對特定頻道/節目/廣告內容、語言用字表達個人想法")) {
            return R.drawable.category_specific;
        } else if (name.equals("廣電收訊、畫質或音量等技術性問題")) {
            return R.drawable.category_tech;
        } else if (name.equals("廣告超秒")) {
            return R.drawable.category_toolong;
        } else {
            return R.drawable.category_other;
        }
    }
}
