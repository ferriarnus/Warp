package com.ferri.arnus.warp.core;

import com.ferri.arnus.warp.core.json.Util;
import com.ferri.arnus.warp.core.json.VersionList;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class DownloadUtils {

    public static VersionList getVersionList(String address) {
        InputStream stream = getURL(address);
        if (stream != null) {
            return Util.GSON.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), VersionList.class);
        }
        return null;
    }

    public static InputStream getURL(String address) {
        try {
            URL url = new URL(address);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            if (connection instanceof HttpURLConnection hcon) {
                int res = hcon.getResponseCode();
                if (res == HttpURLConnection.HTTP_MOVED_PERM || res == HttpURLConnection.HTTP_MOVED_TEMP) {
                    hcon.disconnect();
                }
                return connection.getInputStream();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
