package org.factory;

public class TestFactory {
    public static void main(String[] args) {
        User freeUser = UserFactory.getUser("free", "FREE");
        User adminUser = UserFactory.getUser("admin", "ADMIN");
        User PremiumUser = UserFactory.getUser("premium", "PREMIUM");

        System.out.println(freeUser.getPermissions());
        System.out.println(adminUser.getPermissions());
        System.out.println(PremiumUser.getPermissions());
    }
}
