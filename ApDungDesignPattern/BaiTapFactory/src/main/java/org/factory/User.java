package org.factory;

public abstract class User {
    public abstract String getPermissions();

    @Override
    public String toString() {
        return String.format("User{permissions=%s}", getPermissions());
    }
}

