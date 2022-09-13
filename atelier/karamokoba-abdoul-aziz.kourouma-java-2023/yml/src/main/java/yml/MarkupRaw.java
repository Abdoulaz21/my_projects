package yml;

import java.util.List;

public class MarkupRaw extends Markup{
    public MarkupRaw(){ super(); }
    public MarkupRaw(String s){ super(s); }

    @Override
    public void print(){
        if (this.MarkupName != null){
            this.printTab();
            System.out.println(this.MarkupName);
        }
    }
}
