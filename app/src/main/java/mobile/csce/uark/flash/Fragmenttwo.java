package mobile.csce.uark.flash;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by user on 12/6/2014.
 */
public class Fragmenttwo extends Fragment {
String text;
    boolean Exists = false;
public Fragmenttwo() {}
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getArguments().getString("T") != null)
            text = getArguments().getString("T");
        //getArguments().getString("T");

        return inflater.inflate(R.layout.fragment_two_layout, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EditText textView = (EditText) getView().findViewById(R.id.BackCardText);
        textView.setText(text);
        textView.setMovementMethod(null);
        textView.setMaxLines(12);
        //textView.setClickable(false);
        textView.setFocusableInTouchMode(true);
        //textView.setFocusable(false);
        Exists = true;

    }

    public void setText(String s)
    {
        if (Exists == true)
        {
            EditText textView = (EditText) getView().findViewById(R.id.BackCardText);
            text = s;
            textView.setText(s);
        }
    }
    public String getText()
    {
        if (Exists == true)
        {
            EditText textView = (EditText) getView().findViewById(R.id.BackCardText);
            text = textView.getText().toString();
            return text;
        }
        else
            return text;
    }

    public void Edit()
    {
        EditText textView = (EditText) getView().findViewById(R.id.BackCardText);
        textView.setFocusableInTouchMode(true);
        textView.requestFocus();






    }



}

