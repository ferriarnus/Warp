package com.ferri.arnus.warp.core;

import com.ferri.arnus.warp.core.json.Instance;
import com.ferri.arnus.warp.core.json.Util;
import com.ferri.arnus.warp.core.json.VersionList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.ferri.arnus.warp.core.Constants.*;


public class GameInstance {

    private String name;
    private String version;
    private Instance.Profile profile;

    public GameInstance(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public void makeJson() {
        File instance = new File(INSTANCELOC, name);
        instance.mkdirs();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime  localDateTime  = LocalDateTime .now();
        this.profile = new Instance.Profile(dtf.format(localDateTime), instance.getAbsolutePath(), "", "Crafting_Table", dtf.format(localDateTime), version, name, "custom");
        Instance gameInstance = new Instance(name, version, profile);
        try {
            File json = new File(instance, "\\instance.json");
            FileWriter writer = new FileWriter(json);
            writer.write(Util.GSON.toJson(gameInstance));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeVersion() {
        File versions = new File(MINECRAFTLOC, "versions\\" + version);
        versions.mkdirs();
        File json = new File(versions, version +".json");
        VersionList list = DownloadUtils.getVersionList(VANILLAVERSIONS);
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

    public void getLibraries() {
        File lib = new File(MINECRAFTLOC, "libraries");
    }

    public void injectProfile() {
        File json = new File(MINECRAFTLOC, "launcher_profiles.json");
        JsonObject profiles = null;
        try {
            profiles = JsonParser.parseReader(new InputStreamReader(new FileInputStream(json), StandardCharsets.UTF_8)).getAsJsonObject();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JsonObject _profiles = profiles.getAsJsonObject("profiles");
        _profiles.add(name, Util.GSON.toJsonTree(this.profile));
        String jstring = Util.GSON.toJson(profiles);
        try {
            Files.write(json.toPath(), jstring.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
