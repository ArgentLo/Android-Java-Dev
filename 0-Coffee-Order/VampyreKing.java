import java.util.Random;

public class VampyreKing extends Vampyre {

    public VampyreKing(String vam_Name) {
        super(vam_Name);
        setHitPoints(140);
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage / 2);
    }

    public boolean runAway() {
        return (getLives() < 2);
    }

    public boolean dodges() {
        Random rand = new Random();
        int chance = rand.nextInt(6);
        if(chance < 3) {
            System.out.println(this.getName() + " dodges!!");
            return true;
        }
        return false;
    }

}