import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.net.URL;
import java.net.URLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;

/**
 * Dailyprogrammer: 26 - difficult
 * https://www.reddit.com/r/dailyprogrammer/comments/qzig1/3162012_challenge_26_difficult/
 */
public class Difficult {

    public static void main(String[] args) {
        String albumId = "0NZBe";
        String destDir = "images";
        String renameTo = "wallpaper_#";
        int limit = Integer.MAX_VALUE;


        List<URL> imageUrls = getImageUrls(albumId);
        for (int i = 0; i < imageUrls.size() && i < limit; i++) {
            URL imageUrl = imageUrls.get(i);
            String name = imageUrl.getFile().substring(1);
            if (renameTo != null) {
                name = renameTo.replace("#", Integer.toString((i + 1))) + name.substring(name.lastIndexOf('.'));
            }

            BufferedOutputStream out = null;
            BufferedInputStream in = null;
            try {
                File imageFile;
                if (destDir != null) {
                    File destDirFile = new File(destDir);
                    if (!destDirFile.exists()) {
                        if (!destDirFile.mkdirs()) {
                            System.err.println("Can't create dir: " + destDirFile);
                            return;
                        }
                    }

                    imageFile = new File(destDir + File.separator + name);
                } else {
                    imageFile = new File(name);
                }

                System.out.print("Downloading: " + imageUrl + " .....");
                out = new BufferedOutputStream(new FileOutputStream(imageFile));
                in = new BufferedInputStream(imageUrl.openStream());
                int data;
                while ((data = in.read()) != -1) {
                    out.write(data);
                }

                out.flush();
                System.out.println(" Finished");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (Exception e) { }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e) { }
                }
            }
        }
    }

    private static List<URL> getImageUrls(String albumId) {
        List<URL> images = new ArrayList<URL>();
        BufferedReader reader = null;
        try {
            URL albumURL = new URL("https://imgur.com/a/" + albumId);
            URLConnection connection = albumURL.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            String html = sb.toString();
            String imagesHtml = html.toString().substring(
                html.indexOf("class=\"post-images\""),
                html.indexOf("id=\"recommendations\""));

            Pattern p = Pattern.compile("id=\"([^\"]+)");
            Matcher m = p.matcher(imagesHtml.toString());
            while (m.find()) {
                images.add(new URL("https://i.imgur.com/" + m.group(1) + ".jpg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) { }
            }
        }

        return images;
    }
}
