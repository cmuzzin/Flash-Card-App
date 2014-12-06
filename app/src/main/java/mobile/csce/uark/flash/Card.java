package mobile.csce.uark.flash;

/**
 * Created by Jonathan on 11/12/14.
 */
public class Card {


    private String FrontSide;
    private String BackSide;
    private long Number;
    public long ID;
    public long DeckID;

    public long getDeckID() {
        return DeckID;
    }



    public Card(long id, String frontSide, String backSide, long number, long deckID) {
        FrontSide = frontSide;
        BackSide = backSide;
        Number = number;
        ID = id;
        DeckID = deckID;
    }
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getFrontSide() {
        return FrontSide;
    }

    public void setFrontSide(String frontSide) {
        FrontSide = frontSide;
    }

    public String getBackSide() {
        return BackSide;
    }

    public void setBackSide(String backSide) {
        BackSide = backSide;
    }
    public long getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }


}


