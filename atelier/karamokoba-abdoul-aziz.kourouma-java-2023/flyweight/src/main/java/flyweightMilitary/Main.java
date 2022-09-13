package flyweightMilitary;

public class Main {
    public static void main(String[] args) {
        Army army = new Army();

        Position pos = new Position(25, 30);
        army.addNewSoldier(pos, Color.BLUE, Weapon.SWORD, 210, Direction.DOWN, 0);
        army.addNewSoldier(pos, Color.RED, Weapon.CROSSBOW, 520, Direction.DOWN, 0);
        army.addNewSoldier(pos, Color.GREEN, Weapon.SWORD, 140, Direction.DOWN, 0);

        army.moveSoldiers(Direction.RIGHT, 30);
        army.armyAttacks();
        army.soldiersLoseHealth();
        army.cureSoldiers(80);
        army.printSoldiersHealth();

        // The above example should output:
        // Moving right!
        // Moving right!
        // Moving right!
        // Soldier's enemy hit: lost 60 health points...
        // Soldier's enemy hit: lost 30 health points...
        // Soldier's enemy hit: lost 30 health points...
        // Soldier hit, lost 70 health points!
        // Soldier hit, lost 130 health points!
        // Soldier hit, lost 70 health points!
        // Soldier BLUE: 220
        // Soldier RED: 470
        // Soldier GREEN: 150
    }
}
