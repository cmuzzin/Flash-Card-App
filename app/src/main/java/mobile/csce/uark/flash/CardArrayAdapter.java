package mobile.csce.uark.flash;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jonathan on 12/6/14.
 */
public class CardArrayAdapter {
    List<Card> cards;
    Context mContext;
    LayoutInflater myInflater;



    public CardArrayAdapter( Context c, List<Card> card)
    {
        cards =  card;
        mContext = c;
        myInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return cards.size();
    }


    public Object getItem(int position) {

        return cards.get(position);
    }
    public void add(Card item)
    {
        cards.add(item);
    }

    public void remove(int position)
    {
        cards.remove(position);
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = myInflater.inflate(R.layout.deck_item_view,parent,false);

        TextView title = (TextView)convertView.findViewById(R.id.textview);
        title.setText(cards.get(position).getFrontSide());
        title.setTextColor(Color.BLACK);

        return convertView;
    }
}
