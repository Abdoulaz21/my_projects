package flyweightMilitary;

import java.util.ArrayList;

public class Army {
    ArrayList<SoldierContext> soldiers = new ArrayList<>();

    public void addNewSoldier(Position pos,
                              Color color,
                              Weapon weapon,
                              int health,
                              Direction direction,
                              int speed){
        var mili = new Soldier(color, weapon);
        soldiers.add(new SoldierContext(pos, health, speed, direction, mili));
    }

    public void moveSoldiers(Direction direction, int speed){
        for (var x : soldiers){
            x.soldier.move(x.position, direction, speed);
        }
    }

    public void removeDeadSoldiers(){
        soldiers.removeIf(x -> x.health <= 0);
    }

    public void cureSoldiers(int bonusHealth) throws IllegalArgumentException{
        if (bonusHealth < 0) {throw new IllegalArgumentException(); }
        if (bonusHealth == 0) { return; }
        for (var x : soldiers) {
            if (x.health == 0){
                System.out.println("Cannot bring back dead to life!");
            } else { x.health += bonusHealth; }
        }
    }

    int getNumberOfSoldiers(){
        return this.soldiers.size();
    }

    void printSoldiersHealth(){
        for (var x : soldiers) { x.soldier.currentHealth(x.health); }
    }

    void armyAttacks(){
        for (var x : soldiers) { x.soldier.attack(); }
    }

    void soldiersLoseHealth(){
        for (var x : soldiers) {
            if (x.health != 0){
                x.health = x.soldier.loseHealth(x.health);
            }
        }
    }
}
