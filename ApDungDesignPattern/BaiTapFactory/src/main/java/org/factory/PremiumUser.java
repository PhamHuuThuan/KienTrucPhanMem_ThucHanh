package org.factory;

public class PremiumUser extends User {
    private String permissions;

    public PremiumUser(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getPermissions() {
        return  permissions;
    }
}
