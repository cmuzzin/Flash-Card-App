package mobile.csce.uark.flash;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by user on 12/6/2014.
 */
public class FragmentOne extends Fragment {
    String text;
    boolean Exists = false;

    public FragmentOne() {
    }

    private EditText textView;

    //View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

            text = getArguments().getString("T");
        //getArguments().getString("T");

        return inflater.inflate(R.layout.fragment_one_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = (EditText) getView().findViewById(R.id.FrontCardText);
        textView.setText(text);
        // textView.setClickable(false);
        textView.setFocusableInTouchMode(false);
        //textView.setFocusable(false);
        Exists = true;
        //textView.setFilterTouchesWhenObscured(true);


    }

    public void setText(String s) {
        if (Exists == true) {
            textView = (EditText) getView().findViewById(R.id.FrontCardText);
            text = s;
            textView.setText(s);
        }
    }

    public String getText() {
        if (Exists == true) {
            textView = (EditText) getView().findViewById(R.id.FrontCardText);
            text = textView.getText().toString();
            return text;
        } else
            return text;
    }

    public void Edit() {
        EditText textView = (EditText) getView().findViewById(R.id.FrontCardText);
        textView.setFocusableInTouchMode(true);
        textView.requestFocus();





    }
}

