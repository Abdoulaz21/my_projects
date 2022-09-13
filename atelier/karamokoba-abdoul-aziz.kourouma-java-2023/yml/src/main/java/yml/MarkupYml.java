package yml;

import java.util.List;

public class MarkupYml extends Markup {
    public MarkupYml(){ super(); }
    public MarkupYml(String s){ super(s); }

    @Override
    public void print(){
        this.printTab();
        System.out.print("<[yml");
        if (this.MarkupName != null){ System.out.print("(" + this.MarkupName + ")"); }
        System.out.println("]>");
        this.printMarkups();
        this.printTab();
        System.out.println("<[!yml]>");
    }
}
