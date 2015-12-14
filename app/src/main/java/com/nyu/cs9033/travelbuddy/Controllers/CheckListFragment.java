package com.nyu.cs9033.travelbuddy.Controllers;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyu.cs9033.travelbuddy.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CheckListFragment extends Fragment {

    public CheckListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_list, container, false);
    }
}
