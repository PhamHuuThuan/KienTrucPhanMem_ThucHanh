package org.factory;

public class FreeUser extends User {
    private String permissions;

    public FreeUser(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getPermissions() {
        return permissions;
    }
}
