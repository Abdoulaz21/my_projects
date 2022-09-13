package yml;

import java.util.List;

public class MarkupP extends Markup {
    public MarkupP(){ super(); }
    public MarkupP(String s){ super(s); }

    @Override
    public void print(){
        this.printTab();
        System.out.print("<[p");
        if (this.MarkupName != null){
            System.out.print("(" + this.MarkupName + ")");
        }
        System.out.println("]>");
        this.printMarkups();
        this.printTab();
        System.out.println("<[!p]>");
    }
}
