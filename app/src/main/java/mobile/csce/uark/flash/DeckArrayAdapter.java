package mobile.csce.uark.flash;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 11/25/14.
 */
public class DeckArrayAdapter extends BaseAdapter {
    List<Deck> decks;
    Context mContext;
    LayoutInflater myInflater;



    public DeckArrayAdapter( Context c, List<Deck> deck)
    {
        decks =  deck;
        mContext = c;
        myInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return decks.size();
    }

    @Override
    public Object getItem(int position) {

        return decks.get(position);
    }
    public void add(Deck item)
    {
        decks.add(item);
    }

    public void remove(int position)
    {
        decks.remove(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            convertView = myInflater.inflate(R.layout.deck_item_view,parent,false);

        TextView title = (TextView)convertView.findViewById(R.id.textview);
        title.setText(decks.get(position).DeckName);
        title.setTextColor(Color.WHITE);

        return convertView;
    }
}
