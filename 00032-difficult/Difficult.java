/**
 * Dailyprogrammer: 32 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/rhrr0/3282012_challenge_32_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        String prg = "public class Difficult { public static void main(String[] args) { String prg=System.out.println(prg.substring(0, 77) + String.valueOf((char) 34) + prg + String.valueOf((char) 34) + String.valueOf((char) 59) + prg.substring(77)); } }";
        System.out.println(prg.substring(0, 77) + String.valueOf((char) 34) + prg + String.valueOf((char) 34) + String.valueOf((char) 59) + prg.substring(77));
    }
}
