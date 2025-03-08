package org.factory;

public class UserFactory {
    public static User getUser(String type, String permissions) {
        if(type.equals("admin")){
            return new AdminUser(permissions);
        }
        else if(type.equals("free")){
            return new FreeUser(permissions);
        }
        else if(type.equals("premium")){
            return new PremiumUser(permissions);
        }
        return null;
    }
}
