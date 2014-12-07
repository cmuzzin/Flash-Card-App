package mobile.csce.uark.flash;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 12/6/2014.
 */
public class Fragmenttwo extends Fragment {

public Fragmenttwo() {}
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_two_layout, container, false);
    }




}
