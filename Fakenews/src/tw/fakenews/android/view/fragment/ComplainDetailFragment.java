package tw.fakenews.android.view.fragment;

import tw.fakenews.android.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ComplainDetailFragment extends Fragment {

    public ComplainDetailFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.complain_detail, container, false);

        return rootView;
    }
}
