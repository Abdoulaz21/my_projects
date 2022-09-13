package yml;

import java.util.ArrayList;

public class Document extends Markup{
    public void print(){
        this.printMarkups();
    }

    protected void printMarkups(){
        for (var m : MarkupList){ m.print(); }
    }

    public void addMarkup(Markup m){
        this.MarkupList.add(m);
    }

    ArrayList<Markup> MarkupList = new ArrayList<Markup>();
}
