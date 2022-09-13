package flyweightMilitary;

public class SoldierContext {
    Position position;
    int health;
    int speed;
    Direction direction;
    Military soldier;

    public SoldierContext(Position pos,
                          int health,
                          int speed,
                          Direction direction,
                          Military soldier){
        this.position = pos;
        this.health = health;
        this.speed = speed;
        this.direction = direction;
        this.soldier = soldier;
    }
}
