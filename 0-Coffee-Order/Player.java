import java.util.ArrayList;

public class Player {

    // init vars
    private String mPlayerName;
    private int mLives;
    private int mLevel;
    private int mScore;
    private Weapon mWeapon;
    private ArrayList<Loot> mInventory;

    // constructor (Overloading)
    public Player() {
        // when no input argument.
        this("Unknow Player");
    }

    public Player(String name) {
        this(name, 1);
    }

    public Player(String name, int startingLevel) {
        setPlayerName(name);
        setLevel(startingLevel);
        setLives(3);
        setScore(0);
        setDefaultWeapon();
        mInventory = new ArrayList<>();
    }

    // getter; setter
    public void setPlayerName(String name) {
        if(name.length() < 3) {
            System.out.println("The name " + name + "is too short, please provide name longer than 3 chars.");
            return;
        }
        this.mPlayerName = name;
    }

    public String getPlayerName() {
        return mPlayerName;
    }

    public void setDefaultWeapon() {
        this.mWeapon = new Weapon("Sword", 10, 20);
    }

    public void setNameAndLevel(String name, int level) {
        setPlayerName(name);
        setLevel(level);
    }

    public void setLevel(int level) {
        this.mLevel = level;
    }

    public int getLevel() {
        return mLevel;
    }


    public int getLives() {
        return mLives;
    }

    public void setLives(int lives) {
        this.mLives = lives;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        this.mScore = score;
    }

    public Weapon getWeapon() {
        return mWeapon;
    }

    public void setWeapon(Weapon weapon) {
        this.mWeapon = weapon;
    }  

    public ArrayList<Loot> getInventory() {
        return mInventory;
    }   

    public void pickUpLoot(Loot newLoot) {
        mInventory.add(newLoot);
    }

    public boolean dropLoot(Loot loot) {
        if(this.mInventory.contains(loot)) {
            mInventory.remove(loot);
            return true;
        }
        return false;
    }

    public boolean dropLoot(String lootName) {
        for(Loot cur_Loot: mInventory) {
            if(cur_Loot.getName().equals(lootName)) {
                mInventory.remove(cur_Loot);
                return true;
            }
        }
        return false;
    }

    public void showInventory() {
        for(Loot item : mInventory) {
            System.out.println(item.getName());
        }
        System.out.println("\n===================================\n");
    }

    public int score() {
        int total = 0;
        for(Loot cur_Loot : mInventory) {
            System.out.println(cur_Loot.getName() + " is worth "
                    + cur_Loot.getValue() + ".");
            total = total + cur_Loot.getValue();
        }
        return total;
    }


}