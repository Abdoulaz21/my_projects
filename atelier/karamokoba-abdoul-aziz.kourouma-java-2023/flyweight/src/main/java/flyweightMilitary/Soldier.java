package flyweightMilitary;

import java.util.Locale;

public class Soldier implements Military{
    Color color;
    Weapon prefered;

    public Soldier(Color color, Weapon weapon){
        this.color = color;
        this.prefered = weapon;
    }

    @Override
    public void attack() {
        var att = new StringBuilder("Soldier's enemy hit: lost ");
        int damage = this.getRank() * this.prefered.power;
        att.append(damage);
        att.append(" health points...");
        System.out.println(new String(att));
    }

    @Override
    public void shield() {
        System.out.println("Soldier's enemy missed!");
    }

    @Override
    public boolean move(Position pos, Direction direction, int speed) {
        if (speed <= 0)
            return false;
        System.out.println("Moving " + direction.toString().toLowerCase() + "!");
        if (direction.equals(Direction.UP)){ pos.setY(pos.getY() + speed); }
        else if (direction.equals(Direction.LEFT)){ pos.setX(pos.getX() - speed); }
        else if (direction.equals(Direction.RIGHT)){ pos.setX(pos.getX() + speed); }
        else { pos.setY(pos.getY() - speed); }
        return true;
    }

    @Override
    public int loseHealth(int health) {
        var att = new StringBuilder("Soldier hit, lost ");
        int damage = health / (this.getRank() + 1);
        att.append(damage);
        att.append(" health points!");
        System.out.println(new String(att));
        return health - damage;
    }

    @Override
    public void currentHealth(int health) {
        var att = new StringBuilder("Soldier ");
        att.append(this.getColor());
        att.append(": ");
        if (health <= 0) {
            att.append("dead");
        }else {
            att.append(health);
        }
        System.out.println(new String(att));
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public Weapon getWeapon() {
        return this.prefered;
    }

    @Override
    public int getRank() {
        return this.color.getRank();
    }
}
