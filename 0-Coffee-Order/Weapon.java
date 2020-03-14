public class Weapon {

    private String mWeapon;
    private int mDamage;
    private int mHitPoint;

    public Weapon(String weapon_name, int damage, int hitpoint) {
        this.mWeapon = weapon_name;
        this.mDamage = damage;
        this.mHitPoint = hitpoint;
    }

    public int getDamageInflicted() {
        return mDamage;
    }

    public void setDamageInflicted(int damageInflicted) {
        this.mDamage = damageInflicted;
    }

    public int getHitPoints() {
        return mHitPoint;
    }

    public void setHitPoints(int hitPoints) {
        this.mHitPoint = hitPoints;
    }

    public String getName() {
        return mWeapon;
    }

    public void setName(String name) {
        this.mWeapon = name;
    }

}