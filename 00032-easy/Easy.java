import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Dailyprogrammer: 32 - easy
 * https://www.reddit.com/r/dailyprogrammer/comments/rhrmx/3282012_challenge_32_easy/
 */
public class Easy {

    public static void main(String[] args) {
        List<Bet> bets = Arrays.asList(
            new DoubleZeroBet(BigDecimal.valueOf(10)),
            new ZeroBet(BigDecimal.valueOf(15)),
            new DozenBet(3, BigDecimal.valueOf(30)),
            new BlackColorBet(BigDecimal.valueOf(5)),
            new RedColorBet(BigDecimal.valueOf(7)),
            new LowBet(BigDecimal.valueOf(23)),
            new HighBet(BigDecimal.valueOf(32)),
            new OddBet(BigDecimal.valueOf(3)),
            new EvenBet(BigDecimal.valueOf(4)));

        Wheel wheel = new Wheel();
        wheel.spin();
        if (wheel.isDoubleZero()) {
            System.out.println("00");
        } else {
            System.out.println(wheel.get());
        }

        BigDecimal totalPayout = BigDecimal.ZERO;
        for (Bet bet : bets) {
            if (bet.won(wheel)) {
                totalPayout = totalPayout.add(bet.payout());
                System.out.println(String.format("Bet '%s' payout: %s", bet.getName(), bet.payout()));
            }
        }

        System.out.println();
        System.out.println("Total payout: " + totalPayout);
    }

    private static class Wheel {

        private static final HashSet<Integer> RED = new HashSet<Integer>(
            Arrays.asList(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36));

        private static final HashSet<Integer> BLACK = new HashSet<Integer>(
            Arrays.asList(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35));

        int result = -1;

        void spin() {
            result = (int) Math.floor(Math.random() * 38);
        }

        boolean isDoubleZero() {
            checkState();
            return result == 37;
        }

        boolean isZero() {
            checkState();
            return result == 0;
        }

        int get() {
            checkState();
            if (isDoubleZero()) return 0;
            else return result;
        }

        boolean isBlack() {
            checkState();
            return BLACK.contains(result);
        }

        boolean isRed() {
            checkState();
            return RED.contains(result);
        }

        private void checkState() {
            if (result == -1) throw new IllegalStateException("Spin a wheel first");
        }
    }

    private abstract static class Bet {

        private String name;
        private BigDecimal bet;
        private BigDecimal multiplier;

        Bet(String name, BigDecimal bet, BigDecimal multiplier) {
            this.name = name;
            this.bet = bet;
            this.multiplier = multiplier;
        }

        abstract boolean won(Wheel wheel);

        String getName() {
            return name;
        }

        BigDecimal payout() {
            return multiplier.multiply(bet);
        }
    }

    private static class ZeroBet extends Bet {

        ZeroBet(BigDecimal bet) {
            super("0", bet, BigDecimal.valueOf(35));
        }

        @Override
        boolean won(Wheel wheel) {
            return wheel.isZero();
        }
    }

    private static class DoubleZeroBet extends Bet {

        DoubleZeroBet(BigDecimal bet) {
            super("00", bet, BigDecimal.valueOf(35));
        }

        @Override
        boolean won(Wheel wheel) {
            return wheel.isDoubleZero();
        }
    }

    private static class StraightUpBet extends Bet {

        private int number;

        StraightUpBet(int number, BigDecimal bet) {
            super("StraightUp", bet, BigDecimal.valueOf(35));
            this.number = number;
        }

        @Override
        boolean won(Wheel wheel) {
            return wheel.get() == number;
        }
    }

    private static class DozenBet extends Bet {

        private int dozen;

        DozenBet(int dozen, BigDecimal bet) {
            super("Dozen", bet, BigDecimal.valueOf(2));
            this.dozen = dozen;
        }

        @Override
        boolean won(Wheel wheel) {
            return wheel.get() != 0 && wheel.get() / 12 + 1 == dozen;
        }
    }

    private static class RedColorBet extends Bet {

        RedColorBet(BigDecimal bet) {
            super("Color(Red)", bet, BigDecimal.valueOf(1));
        }

        @Override
        boolean won(Wheel wheel) {
            return wheel.isRed();
        }
    }

    private static class BlackColorBet extends Bet {

        BlackColorBet(BigDecimal bet) {
            super("Color(Black)", bet, BigDecimal.valueOf(1));
        }

        @Override
        boolean won(Wheel wheel) {
            return wheel.isBlack();
        }
    }

    private static class LowBet extends Bet {

        LowBet(BigDecimal bet) {
            super("LowBet", bet, BigDecimal.valueOf(1));
        }

        @Override
        boolean won(Wheel wheel) {
            return wheel.get() >= 1 && wheel.get() <= 18;
        }
    }

    private static class HighBet extends Bet {

        HighBet(BigDecimal bet) {
            super("HighBet", bet, BigDecimal.valueOf(1));
        }

        @Override
        boolean won(Wheel wheel) {
            return wheel.get() >= 19 && wheel.get() <= 36;
        }
    }

    private static class OddBet extends Bet {

        OddBet(BigDecimal bet) {
            super("OddBet", bet, BigDecimal.valueOf(1));
        }

        @Override
        boolean won(Wheel wheel) {
            return wheel.get() != 0 && wheel.get() % 2 != 0;
        }
    }

    private static class EvenBet extends Bet {

        EvenBet(BigDecimal bet) {
            super("EvenBet", bet, BigDecimal.valueOf(1));
        }

        @Override
        boolean won(Wheel wheel) {
            return wheel.get() != 0 && wheel.get() % 2 == 0;
        }
    }
}
