package com.ferri.arnus.warp.core;

import com.ferri.arnus.warp.core.json.Instance;
import com.ferri.arnus.warp.core.json.Util;
import com.ferri.arnus.warp.core.json.VersionList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class GameInstance {

    private String name;
    private String version;
    public GameInstance(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public void makeJson() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime  localDateTime  = LocalDateTime .now();
        Instance gameInstance = new Instance(name, version, new Instance.Profile(dtf.format(localDateTime), "Crafting_Table", dtf.format(localDateTime), version, name, "custom"));
        try {
            File instance = new File(Warp.INSTANCELOC, name);
            instance.mkdirs();
            File json = new File(instance, "\\instance.json");
            FileWriter writer = new FileWriter(json);
            writer.write(Util.GSON.toJson(gameInstance));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeVersion() {
        File versions = new File(Warp.MINECRAFTLOC, "versions\\" + version);
        versions.mkdirs();
        File json = new File(versions, version +".json");
        VersionList list = DownloadUtils.getVersionList("https://launchermeta.mojang.com/mc/game/version_manifest.json");
        if (list != null) {
            String url = list.getUrl(version);
            if (url != null) {
                InputStream stream = DownloadUtils.getURL(url);
                if (stream != null) {
                    try {
                        Files.copy(stream, json.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void injectVersion() {

    }
}
