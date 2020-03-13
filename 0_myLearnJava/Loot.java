/**
 *  `enum`: to limit the range
 *  - No ";".
 *  - UPPER case by convention.
 */

enum LootType { RING, ARMOR, POTION }

public class Loot {
    private String name;
    private LootType type;
    private int value;

    public Loot(String name, LootType type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LootType getType() {
        return type;
    }

    public void setType(LootType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
