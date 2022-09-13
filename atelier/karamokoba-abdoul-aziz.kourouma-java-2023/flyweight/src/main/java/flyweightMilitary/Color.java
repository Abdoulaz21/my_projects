package flyweightMilitary;

public enum Color {
    GREEN(1),
    BLUE(2),
    RED(3);

    int rank;
    Color(int rank){
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}

