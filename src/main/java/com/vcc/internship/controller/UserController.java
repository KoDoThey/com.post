package com.vcc.internship.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcc.internship.common.config.Configuration;
import com.vcc.internship.model.User;
import com.vcc.internship.restful.ResponseStatus;
import com.vcc.internship.services.impl.PostService;
import com.vcc.internship.services.impl.UserService;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.vcc.internship.common.utils.Strings.isNullOrEmptyString;

@Path("user")
public class UserController {
    @GET
    @Path("/get")
//    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getUser(@QueryParam("userName") String userName) throws Exception {
        if (isNullOrEmptyString(userName)) return ResponseStatus.toClientErrorResponse("User ID is required!");
        JSONObject result = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        try (UserService userService = new UserService(new Configuration())) {
            User user = userService.getUserByUserName(userName);
            if (user == null) return ResponseStatus.toServerErrorResponse("User is deleted!");
            result = new JSONObject(objectMapper.writeValueAsString(user));
        }
        return ResponseStatus.toSuccessResponse("OK", result);
    }


    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addUser(String jsonParam) throws Exception {
        JSONObject payloadResponse = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonParam, User.class);
        JSONObject obj = new JSONObject(jsonParam);
        if (isNullOrEmptyString(obj.getString("userName"))) {
            return ResponseStatus.toClientErrorResponse("User name is required!");
        }
        try (UserService userService = new UserService(new Configuration())) {
            userService.addUser(user);
            payloadResponse = new JSONObject(objectMapper.writeValueAsString(user));
        }
        return ResponseStatus.toSuccessResponse("OK", payloadResponse);
    }


    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updateUser(String jsonParam) throws Exception {
        JSONObject result = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonParam, User.class);
        JSONObject obj = new JSONObject(jsonParam);
        if (isNullOrEmptyString(obj.getString("userName")))
            return ResponseStatus.toClientErrorResponse("User name is required!");
        try (UserService userService = new UserService(new Configuration())) {
            userService.updateUser(user);
            result = new JSONObject(objectMapper.writeValueAsString(user));
        }
        return ResponseStatus.toSuccessResponse("OK", result);
    }


    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deleteUser(String jsonParam) throws Exception {
        JSONObject result = new JSONObject();
        JSONObject obj = new JSONObject(jsonParam);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(jsonParam, User.class);
        if (isNullOrEmptyString(obj.getString("userName")))
            return ResponseStatus.toClientErrorResponse("user Name is null");
        try (UserService userService = new UserService(new Configuration())) {
            userService.deleteUser(user);
            result = new JSONObject(objectMapper.writeValueAsString(user));
        }
        return ResponseStatus.toSuccessResponse("OK", result);
    }


    @POST
    @Path("/change-password")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response changePassword(String jsonParam) throws Exception {
        JSONObject userChangePass = new JSONObject(jsonParam);
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject result = new JSONObject();
        if (isNullOrEmptyString(userChangePass.getString("userName"))) {
            return ResponseStatus.toClientErrorResponse("User name is null");
        }
        if (isNullOrEmptyString(userChangePass.getString("password")) || isNullOrEmptyString(userChangePass.getString("passwordNew"))) {
            return ResponseStatus.toClientErrorResponse("Password is null");
        }
        if (userChangePass.getString("password").equals(userChangePass.getString("passwordNew"))) {
            return ResponseStatus.toClientErrorResponse("New password must be different old password!");
        }

        try (UserService userService = new UserService(new Configuration())) {
            User user = new User();
            user.setUserName(userChangePass.getString("userName"));
            String userName = user.getUserName();
            user.setPassword(userChangePass.getString("password"));
            String passwordOld = user.getPassword();
            String passwordNew = userChangePass.getString("passwordNew");

            user = userService.getUserByUserName(userName);
            if (user == null) {
                return ResponseStatus.toClientErrorResponse("User is deleted!");
            }
            if (user.getUserName() == null) {
                return ResponseStatus.toClientErrorResponse("User name is not exist!");
            } else {
//                if (userName.equals(user.getUserName()) && passwordOld.equals(user.getPassword())) { // userName đã check exist
                if (passwordOld.equals(user.getPassword())) {
                    userService.changePassword(userChangePass.getString("userName"), passwordNew);
                } else return ResponseStatus.toClientErrorResponse("Password is incorrect!");
            }
            return ResponseStatus.toSuccessResponse("OK");
        }
    }
}




