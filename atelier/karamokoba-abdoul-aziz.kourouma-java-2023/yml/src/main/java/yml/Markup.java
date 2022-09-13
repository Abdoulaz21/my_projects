package yml;

import java.util.ArrayList;

public abstract class Markup {
    public Markup(){ }

    public Markup(String s){ this.MarkupName = s; }

    public abstract void print();

    protected void printMarkups(){
        for (var m : MarkupList){
            m.tab = this.tab + 1;
            m.print();
        }
    }

    public void addMarkup(Markup m){
        this.MarkupList.add(m);
    }

    public void printTab(){
        for (Integer i = 0;i < this.tab;i++)
            System.out.print("\t");
    }

    protected Integer tab = 0;
    String MarkupName;
    ArrayList<Markup> MarkupList = new ArrayList<Markup>();
}
