package flyweightMilitary;

import flyweightMilitary.Color;
import flyweightMilitary.Position;
import flyweightMilitary.Weapon;

public interface Military {
    public void attack();

    public void shield();

    public boolean move(Position pos, Direction direction, int speed);

    public int loseHealth(int health);

    public void currentHealth(int health);

    public Color getColor();

    public Weapon getWeapon();

    public int getRank();
}
