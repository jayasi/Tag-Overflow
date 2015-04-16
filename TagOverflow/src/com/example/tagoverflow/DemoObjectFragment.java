package com.example.tagoverflow;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DemoObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.activity_home, container, false);
        Bundle args = getArguments();
       // ((TextView) rootView.findViewById(android.R.id.text1)).setText(
            //    Integer.toString(args.getInt(ARG_OBJECT)));
        return rootView;
    }
    
}
