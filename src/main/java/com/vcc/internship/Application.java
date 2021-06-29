package com.vcc.internship;

import com.vcc.internship.controller.PostController;
import com.vcc.internship.controller.UserController;
import com.vcc.internship.model.User;
import com.vcc.internship.restful.ClientExceptionMapper;
import com.vcc.internship.restful.UncaughtExceptionMapper;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.UUID;

public class Application {
    public static void main(String[] args) throws Exception{
        final String url = "http://localhost:" + 8080;
        URI uri = URI.create(url);
        ResourceConfig serverConfig = new ResourceConfig(
                PostController.class,
                UserController.class,
                UncaughtExceptionMapper.class,
                ClientExceptionMapper.class);
        Server server = JettyHttpContainerFactory.createServer(uri, serverConfig, false);
        try {
            server.start();
            server.join();
        } finally {
            server.stop();
        }
//
//        User user = new User();
//        long hashID = System.identityHashCode(user);
//        System.out.println(hashID);
//
//        String uuid = UUID.randomUUID().toString();
//        System.out.println(uuid);

    }
}
