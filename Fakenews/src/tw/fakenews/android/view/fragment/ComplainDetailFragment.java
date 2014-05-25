package tw.fakenews.android.view.fragment;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tw.fakenews.android.Constants;
import tw.fakenews.android.R;
import tw.fakenews.android.models.Complain;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ComplainDetailFragment extends Fragment {

    public ComplainDetailFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.complain_detail, container, false);
        
        Complain c = (Complain) getArguments().getParcelable(Constants.KEY_COMPLAIN);
                
        ((TextView) rootView.findViewById(R.id.textview_channelName)).setText(c.channelName);
        ((TextView) rootView.findViewById(R.id.textview_programName)).setText(c.programName);
        
        ((TextView) rootView.findViewById(R.id.textview_cid)).setText("申訴案號 " + c.cid);
        ((TextView) rootView.findViewById(R.id.textview_date)).setText("申訴日期 " + 
                formateDateFromstring("yyyy-MM-dd'T'HH:mm:ss", "yyyy/MM/dd", c.date));
        ((TextView) rootView.findViewById(R.id.textview_broadcastDate)).setText("播出日期 " + c.broadcastDate);
        ((TextView) rootView.findViewById(R.id.textview_broadcastTime)).setText("播出時段 " + c.broadcastTime);
        
        ((TextView) rootView.findViewById(R.id.textview_complainTitle)).setText(c.complainTitle);
        ((TextView) rootView.findViewById(R.id.textview_complainContent)).setText(c.complainContent);
        ((TextView) rootView.findViewById(R.id.textview_replyContent)).setText(c.replyContent);

        return rootView;
    }
    
    private static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        if (outputDate.length() == 10) {
            outputDate = outputDate.substring(1, 9);
        }
        
        return outputDate;

    }
}
