import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Dailyprogrammer: 5 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pniaw/2132012_challenge_5_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        Player[] players = new Player[] { new Player("1"), new Player("2") };
        int playerIdx = 0;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("-------------------------------------------------------");
            Player currentPlayer = players[playerIdx++ % players.length];
            Player againstPlayer = players[playerIdx % players.length];

            for (Effect e : currentPlayer.effects) {
                e.beforeMove();
            }

            for (int i = currentPlayer.effects.size() - 1; i >= 0; i--) {
                if (currentPlayer.effects.get(i).over) {
                    currentPlayer.effects.remove(i);
                }
            }

            if (currentPlayer.health <= 0) {
                System.out.println(againstPlayer.name + " won");
                break;
            }

            System.out.println();
            System.out.println(String.format("%s: %d HP, %d DEF, effects: %s", currentPlayer.name, currentPlayer.health, currentPlayer.defence, currentPlayer.effects));
            System.out.println(String.format("%s: %d HP, %d DEF, effects: %s", againstPlayer.name, againstPlayer.health, againstPlayer.defence, againstPlayer.effects));
            System.out.println();

            System.out.println(String.format("%s your move:", currentPlayer.name));
            if (currentPlayer.pistolReady) {
                System.out.println("p) Shoot with pistol (15 damage, 3 turns cooldown, 15% critical hit x2");
            }
            System.out.println("s) Attack with sabre (5 damage, 50% bleed (1hp per turn) for 3 turns");
            System.out.println("b) Take a block stance (+10 defence for this move)");

            if (!scanner.hasNextLine()) break;
            String action = scanner.nextLine();
            if (!"p".equals(action) && !"s".equals(action) && !"b".equals(action)) {
                System.out.println("Unknown move: " + action);
                continue;
            }

            if ("p".equals(action)) {
                currentPlayer.attackWithPistol(againstPlayer);
            } else if ("s".equals(action)) {
                currentPlayer.attackWithSabre(againstPlayer);
            } else if ("b".equals(action)) {
                currentPlayer.blockStance();
            }

            if (againstPlayer.health <= 0) {
                System.out.println(currentPlayer.name + " won");
                break;
            }

            for (Effect e : currentPlayer.effects) {
                e.afterMove();
            }

            System.out.println("-------------------------------------------------------");
        }
    }

    private static class Player {

        private List<Effect> effects;
        private int defence;
        private int health;
        private final String name;

        private boolean pistolReady;

        Player(String name) {
            this.name = name;
            this.defence = 0;
            this.health = 100;
            this.effects = new ArrayList<Effect>();
            this.pistolReady = true;
        }

        void attackWithPistol(Player player) {
            System.out.println(String.format("%s shoots %s with pistol", name, player.name));
            int damage;
            if (Math.random() < 0.8) {
                damage = Math.max(1, 15 - player.defence);
            } else {
                damage = Math.max(1, 30 - player.defence);
                System.out.println("Critical hit!");
            }

            player.health -= damage;
            System.out.println(String.format("%s loses %d health", name, damage));
            this.pistolReady = false;
            this.effects.add(new PistolReloadEffect(this));
        }

        void attackWithSabre(Player player) {
            System.out.println(String.format("%s attacks %s with sabre", name, player.name));
            player.health -= Math.max(1, 5 - player.defence);
            if (Math.random() < 0.5) {
                System.out.println("Attack causes bleed!");
                player.effects.add(new BleedEffect(player));
            }
        }

        void blockStance() {
            this.effects.add(new DefenceEffect(this));
        }
    }

    private static abstract class Effect {

        private boolean over;
        private int turns;
        private final int duration;
        protected final Player player;
        private final String name;

        Effect(String name, int duration, Player player) {
            this.duration = duration;
            this.player = player;
            this.name = name;
            this.turns = -1;
        }

        void beforeMove() {
            turns++;
            if (turns == duration) {
                onEnd();
                over = true;
            }
        }

        void afterMove() {
            if (turns == -1) {
                onStart();
                turns = 0;
            }
        }

        void onStart() { }

        void onEnd() { }

        @Override
        public String toString() {
            return name;
        }
    }

    private static class BleedEffect extends Effect {

        BleedEffect(Player player) {
            super("bleed", 3, player);
        }

        void afterMove() {
            this.player.health -= 1;
            System.out.println(String.format("%s bleeds for %d health", player.name, 1));
        }
    }

    private static class PistolReloadEffect extends Effect {

        PistolReloadEffect(Player player) {
            super("pistol reload", 3, player);
        }

        void onEnd() {
            this.player.pistolReady = true;
            System.out.println(String.format("Pistol has been reloaded for player %s", player.name));
        }
    }

    private static class DefenceEffect extends Effect {

        DefenceEffect(Player player) {
            super("defence", 1, player);
        }

        void onStart() {
            player.defence += 10;
            System.out.println(String.format("%s gains %d defence", player.name, 10));
        }

        void onEnd() {
            player.defence -= 10;
            System.out.println(String.format("%s loses %d defence", player.name, 10));
        }
    }
}
