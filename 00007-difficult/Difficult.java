import java.net.URL;
import java.net.MalformedURLException;
import javax.net.ssl.HttpsURLConnection;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.IOException;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Dailyprogrammer: 7 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/pr4vo/2152012_challenge_7_difficult/
 */
public class Difficult {

    private static final int INTERVAL = 10;

    public static void main(String... args) {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("bot.properties"));
        } catch (IOException e) {
            System.out.println("Can't load properties from 'bot.properties'");
            e.printStackTrace();
        }

        final JokeBot bot = new JokeBot(properties);
        bot.activate();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                bot.deactivate();
            }
        }));
    }

    private static class JokeBot {

        static final String INTERVAL_PROPERTY = "interval";
        static final String USER_AGENT_PROPERTY = "user.agent";
        static final String GMAIL_USERNAME_PROPERTY = "gmail.username";
        static final String GMAIL_PASSWORD_PROPERTY = "gmail.password";
        static final String RECIPIENT_EMAIL_PROPERTY = "email.recipient";

        private static final URL JOKE_URL;
        private static final String VALUE_PREFIX = "\"value\":\"";
        private static final String VALUE_POSTFIX = "\"}";

        static {
            URL jokeUrl = null;
            try {
                jokeUrl = new URL("https://api.chucknorris.io/jokes/random");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            JOKE_URL = jokeUrl;
        }

        private ScheduledExecutorService scheduler;

        private String userAgent;
        private int interval;
        private String username;
        private String password;
        private String recipient;

        JokeBot(Properties properties) {
            this.userAgent = properties.getProperty(USER_AGENT_PROPERTY, "Chrome");
            this.interval = Integer.parseInt(properties.getProperty(INTERVAL_PROPERTY, "60"));
            this.username = properties.getProperty(GMAIL_USERNAME_PROPERTY);
            this.password = properties.getProperty(GMAIL_PASSWORD_PROPERTY);
            this.recipient = properties.getProperty(RECIPIENT_EMAIL_PROPERTY, username);
        }

        void activate() {
            if (scheduler != null) {
                throw new IllegalStateException("Already activated");
            }

            scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    String joke = getJoke();
                    if (username != null && password != null) {
                        sendJoke(joke);
                    }
                }
            }, 0, interval, TimeUnit.SECONDS);
        }

        void deactivate() {
            if (scheduler == null) {
                throw new IllegalStateException("Not activated");
            }

            scheduler.shutdown();
            scheduler = null;
        }

        private void sendJoke(String joke) {
            System.out.println(String.format("Sending joke to '%s'...", username));
            Properties properties = new Properties();
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.ssl.enable", true);
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.user", username);

            Session session = Session.getDefaultInstance(properties);

            MimeMessage message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(username));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                message.setSubject("Another Chuck Norris Joke");
                message.setText(joke);

                Transport t = session.getTransport();
                t.connect(null, password);
                t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
                System.out.println("Completed");
            } catch (MessagingException e) {
                System.out.println("Failed: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private String getJoke() {
            System.out.println();
            System.out.println("Retrieving random joke...");
            String joke = null;
            try {
                HttpsURLConnection conn = (HttpsURLConnection) JOKE_URL.openConnection();
                conn.setRequestProperty("User-Agent", userAgent);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                joke = sb.substring(
                    sb.indexOf(VALUE_PREFIX) + VALUE_PREFIX.length(),
                    sb.length() - VALUE_POSTFIX.length());
                joke = unescapeUnicode(joke);

                System.out.println("=========================");
                System.out.println(joke);
                System.out.println("=========================");
            } catch (IOException e) {
                System.out.println("Failed: " + e.getMessage());
                e.printStackTrace();
            }

            return joke;
        }

        // :)
        private String unescapeUnicode(String str) {
            try {
                Properties p = new Properties();
                p.load(new StringReader("str=" + str));
                return p.getProperty("str");
            } catch (IOException e) {
                e.printStackTrace();
                return str;
            }
        }
    }
}
