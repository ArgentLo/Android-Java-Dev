public class Demo {

    public static void main(String[] args) {
        
        VampyreKing horror_king = new VampyreKing("HORROR KING");
        horror_king.showInfo();

        horror_king.setLives(0);

        do {
            if (horror_king.dodges()) {
                horror_king.setLives(horror_king.getLives() + 1);
                continue;
            }

            if (horror_king.runAway()) {
                System.out.println("Dracula ran away");
                break;
            } else {
                horror_king.takeDamage(80);
                horror_king.showInfo();
            }
        } while (horror_king.getLives() > 0);
        System.out.println("====================================");



        // Player argent = new Player("Argent");
        // argent.pickUpLoot(new Loot("Invisibility", LootType.POTION, 4));
        // argent.pickUpLoot(new Loot("Mithril", LootType.ARMOR, 183));
        // argent.pickUpLoot(new Loot("Ring of speed", LootType.RING, 25));
        // argent.pickUpLoot(new Loot("Red Potion", LootType.POTION, 2));
        // argent.pickUpLoot(new Loot("Cursed Shield", LootType.ARMOR, -8));
        // argent.pickUpLoot(new Loot("Brass Ring", LootType.RING, 1));
        // argent.pickUpLoot(new Loot("Chain Mail", LootType.ARMOR, 4));
        // argent.pickUpLoot(new Loot("Gold Ring", LootType.RING, 12));
        // argent.pickUpLoot(new Loot("Health Potion", LootType.POTION, 3));
        // argent.pickUpLoot(new Loot("Silver Ring", LootType.RING, 6));
        // argent.showInventory();

        // System.out.println(argent.score());
        // argent.dropLoot("Cursed Shield");
        // System.out.println(argent.score());
    }
}