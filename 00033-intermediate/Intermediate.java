import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

/**
 * Dailyprogrammer: 33 - intermediate
 * https://www.reddit.com/r/dailyprogrammer/comments/rl3g1/3302012_challenge_33_intermediate/
 */
public class Intermediate {

    public static void main(String[] args) {
        String[] hand = args[0].split(" ");
        List<Card> cards = new ArrayList<Card>();
        for (String c : hand) {
            cards.add(Card.parse(c));
        }

        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card a, Card b) {
                return -a.compareTo(b);
            }
        });

        boolean sameSuite = true;
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i - 1).suite != cards.get(i).suite) {
                sameSuite = false;
                break;
            }
        }

        boolean sequential = true;
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i - 1).rank.value != cards.get(i).rank.value + 1) {
                sequential = false;
                break;
            }
        }

        Map<Rank, List<Card>> groupsByRank = new HashMap<Rank, List<Card>>();
        for (Card c : cards) {
            List<Card> group = groupsByRank.get(c.rank);
            if (group == null) {
                group = new ArrayList<Card>();
                groupsByRank.put(c.rank, group);
            }

            group.add(c);
        }

        List<List<Card>> groups = new ArrayList<List<Card>>(groupsByRank.values());
        Collections.sort(groups, new Comparator<List<Card>>() {
            @Override
            public int compare(List<Card> a, List<Card> b) {
                return -Integer.compare(a.size(), b.size());
            }
        });

        if (cards.size() == 5 && sameSuite && sequential && cards.get(0).rank == Rank.TEN) {
            System.out.println("Royal Flush: " + cards.get(0).suite);
        } else if (cards.size() == 5 && sameSuite && sequential) {
            System.out.println(String.format("Straight Flush: %s-%s%s", cards.get(4).rank, cards.get(0).rank, cards.get(0).suite));
        } else if (cards.size() >= 4 && groups.size() <= 2 && groups.get(0).size() == 4) {
            System.out.println("Four Of a Kind: " + cards.get(0).rank);
        } else if (cards.size() == 5 && groups.size() == 2 && groups.get(0).size() == 3) {
            System.out.println("Full House: " + toString(groups.get(0)) + ", " + toString(groups.get(1)));
        } else if (cards.size() == 5 && sameSuite) {
            System.out.println("Flush: " + cards.get(0).suite);
        } else if(cards.size() == 5 && sequential) {
            System.out.println(String.format("Straight: %s-%s", cards.get(4).rank, cards.get(0).rank));
        } else if (cards.size() >= 3 && groups.get(0).size() == 3) {
            System.out.println("Three of a Kind: " + toString(groups.get(0)));
        } else if (cards.size() >= 4 && groups.get(0).size() == 2 && groups.get(1).size() == 2) {
            System.out.println("Two Pairs: " + toString(groups.get(0)) + ", " + toString(groups.get(1)));
        } else if (cards.size() >= 2 && groups.get(0).size() == 2) {
            System.out.println("Pair: " + toString(groups.get(0)));
        } else {
            System.out.println("High Card: " + cards.get(0));
        }
    }

    private static String toString(List<Card> cards) {
        String str = "";
        for (int i = 0; i < cards.size(); i++) {
            str += cards.get(i);
            if (i < cards.size() - 1) str += " ";
        }

        return str;
    }

    private enum Suite {

        SPADES("S"),
        HEADERS("H"),
        DIAMONDS("D"),
        CLUBS("C");

        static Suite parse(String str) {
            for (Suite s : values()) {
                if (s.representation.equals(str)) {
                    return s;
                }
            }

            throw new IllegalArgumentException("Unknown suite: " + str);
        }

        private String representation;

        Suite(String representation) {
            this.representation = representation;
        }

        @Override
        public String toString() {
            return representation;
        }
    }

    private enum Rank {

        TWO(2, "2"),
        THREE(3, "3"),
        FOUR(4, "4"),
        FIVE(5, "5"),
        SIX(6, "6"),
        SEVEN(7, "7"),
        EIGHT(8, "8"),
        NINE(9, "9"),
        TEN(10, "T"),
        JACK(11, "J"),
        QUEEN(12, "Q"),
        KING(13, "K"),
        ACE(14, "A");

        static Rank parse(String str) {
            for (Rank r : values()) {
                if (r.representation.equals(str)) {
                    return r;
                }
            }

            throw new IllegalArgumentException("Unknown rank: " + str);
        }

        private int value;
        private String representation;

        Rank(int value, String representation) {
            this.value = value;
            this.representation = representation;
        }

        @Override
        public String toString() {
            return representation;
        }
    }

    private static class Card implements Comparable<Card> {

        static Card parse(String str) {
            return new Card(
                Rank.parse(str.charAt(0) + ""),
                Suite.parse(str.charAt(1) + ""));
        }

        private final Rank rank;
        private final Suite suite;

        Card(Rank rank, Suite suite) {
            this.rank = rank;
            this.suite = suite;
        }

        @Override
        public int compareTo(Card c) {
            return Integer.compare(rank.value, c.rank.value);
        }

        @Override
        public String toString() {
            return "" + rank + suite;
        }
    }
}
