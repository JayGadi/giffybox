package giphboxhq.com.giphybox.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import giphboxhq.com.giphybox.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControversialFragment extends Fragment implements ControversialView{


    public ControversialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_controversial, container, false);

        return view;
    }

}
