package mobile.csce.uark.flash;

/**
 * Created by Jonathan on 11/12/14.
 */
public class Card {


    private String FrontSide;
    private String BackSide;
    private Integer Number;
    public Integer ID;
    public Integer DeckID;

    public Card(String frontSide, Integer number, Integer ID, Integer deckID) {
        FrontSide = frontSide;
        Number = number;
        this.ID = ID;
        DeckID = deckID;
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
    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }


}


