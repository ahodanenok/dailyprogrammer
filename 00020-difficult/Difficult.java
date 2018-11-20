import java.awt.Image;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.SystemTray;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Dailyprogrammer: 20 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/qnl1d/382012_challenge_20_difficult/
 */
public class Difficult {

    private static final int REMINDER_RATE = 7200; // seconds

    public static void main(String[] args) throws Exception {
        SystemTray tray = SystemTray.getSystemTray();
        Image trayImg = Toolkit.getDefaultToolkit().createImage("icon.png");
        final TrayIcon trayIcon = new TrayIcon(trayImg, "Dailyprogrammer 20");
        trayIcon.setImageAutoSize(true);
        tray.add(trayIcon);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                trayIcon.displayMessage("Stop procrastinating!", null, TrayIcon.MessageType.INFO);
            }
        }, REMINDER_RATE, REMINDER_RATE, TimeUnit.SECONDS);
    }
}
