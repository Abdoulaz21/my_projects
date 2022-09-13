package flyweightMilitary;

import java.util.HashMap;

public class MilitaryFactory {
    private static final HashMap<Integer, Military> milis = new HashMap<Integer, Military>();

    public static Military getMilitary(final Color color, final Weapon weapon){
        Integer key = color.getRank() + weapon.power;
        if (milis.containsKey(key)){
            return milis.get(key);
        } else {
            Military res = new Soldier(color, weapon);
            milis.put(key, res);
            return res;
        }
    }
}
