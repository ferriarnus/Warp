package com.ferri.arnus.warp.core.json;

public class Instance {

    public String name;
    public String version;

    public Profile profile;

    public Instance(String name, String version, Profile profile) {
        this.name = name;
        this.version = version;
        this.profile = profile;
    }

    public static class Profile {

        public String created;
        public String icon;
        public String lastUsed;
        public String lastVersionId;
        public String name;
        public String type;


        public Profile(String created, String icon, String lastUsed, String lastVersionId, String name, String type) {
            this.created = created;
            this.icon = icon;
            this.lastUsed = lastUsed;
            this.lastVersionId = lastVersionId;
            this.name = name;
            this.type = type;
        }

    }
}
