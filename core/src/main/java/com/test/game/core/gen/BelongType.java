package com.test.game.core.gen;

/** 归属枚举 */
public enum BelongType {
    CLIENT(true, false),
    SERVER(false, true),
    BOTH(true, true),
    NULL(false, false);

    private final boolean client;
    private final boolean server;

    BelongType(boolean client, boolean server) {
        this.client = client;
        this.server = server;
    }

    public boolean isClient() {
        return this.client;
    }

    public boolean isServer() {
        return this.server;
    }

    public BelongType add(BelongType belong) {
        if (belong == null) {
            return this;
        } else {
            boolean client = this.client || belong.client;
            boolean server = this.server || belong.server;
            if (client) {
                return server ? BOTH : CLIENT;
            } else {
                return server ? SERVER : NULL;
            }
        }
    }

    public static BelongType valueOf(boolean client, boolean server) {
        for (BelongType type : values()) {
            if (type.client == client && type.server == server) {
                return type;
            }
        }
        return null;
    }
}
