package flyweightMilitary;

public enum Weapon {
    CROSSBOW(10),
    SPEAR(20),
    SWORD(30);

    int power;
    Weapon(int power){
        this.power = power;
    }
}
