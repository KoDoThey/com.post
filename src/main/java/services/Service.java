package services;

import model.Post;
import model.User;

import java.util.UUID;

public class Service {

    /*public long createHashID() {
        User user = new User();
        long hashID = System.identityHashCode(user);
        return hashID;
    }

     */

    public static String uuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public void excutePost(Post post) {

    }

}
