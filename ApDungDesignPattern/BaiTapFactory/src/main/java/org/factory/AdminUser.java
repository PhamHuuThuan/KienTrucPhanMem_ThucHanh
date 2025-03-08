package org.factory;

public class AdminUser extends User {
    private String permissions;

    public AdminUser(String permissions) {
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }
}
