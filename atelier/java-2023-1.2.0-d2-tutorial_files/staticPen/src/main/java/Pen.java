public class Pen {
    public Pen(final Color color){
        this.color = color;
        if (this.color.equals(Color.BLUE)){
            bluepen++;
        }
        else{
            redpen++;
        }
        pencounter++;
    }

    public static int getPenCounter(){
        return pencounter;
    }

    public static int getRedPenCounter(){
        return  redpen;
    }

    public static int getBluePenCounter(){
        return bluepen;
    }

    public void print(){
        var toprint = new StringBuilder("I'm a ");
        if (this.color.equals(Color.BLUE)){
            toprint.append("blue");
        }
        else{
            toprint.append("red");
        }
        toprint.append(" pen");
        System.out.println(new String(toprint));
    }

    public void changeColor(final Color color){
        if (this.color.equals(Color.BLUE)){
            bluepen--;
            redpen++;
        }
        else{
            redpen--;
            bluepen++;
        }
        this.color = color;
    }

    Color color;
    static int pencounter;
    static int redpen;
    static int bluepen;
}
