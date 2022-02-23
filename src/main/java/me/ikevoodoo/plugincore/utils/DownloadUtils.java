package me.ikevoodoo.plugincore.utils;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;

public class DownloadUtils {

    private static File downloadFolder;

    public static File getDownloadFolder() {
        if(downloadFolder == null) {
            downloadFolder = new File(Bukkit.getWorldContainer(), "downloads");
            if(!downloadFolder.exists()) {
                downloadFolder.mkdirs();
            }
        }

        return downloadFolder;
    }

    public static void download(String url) throws IOException {
        download(new URL(url), new File(getDownloadFolder(), url.substring(url.lastIndexOf("/") + 1)));
    }

    public static void download(String url, File path) throws IOException {
        download(new URL(url), path);
    }

    public static void download(String url, Path path) throws IOException {
        download(new URL(url), path.toFile());
    }

    public static void download(URL url, File path) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(true);
        ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
        try {
            path.getParentFile().mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream fos = new FileOutputStream(path);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

}
