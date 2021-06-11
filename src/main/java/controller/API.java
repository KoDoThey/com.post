package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mysql.cj.x.protobuf.Mysqlx;
import infrastructor.Repository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import services.Service;
import model.Post;
import model.User;
import controller.Strings;

import static controller.Strings.isNullOrEmptyString;

@Path("/")
public class API {

    @POST
    @Path("/user/create")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response createUser(String jsonParam) throws Exception {
        JSONObject payloadResponse = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // TODO: xử lý
            User userAPI = objectMapper.readValue(jsonParam, User.class);
            JSONObject obj = new JSONObject(jsonParam);
            if (!obj.has("userName")) {
                return ResponseStatus.toClientErrorResponse("user name is missed!");
            }
            if (isNullOrEmptyString(obj.getString("userName"))) {
                return ResponseStatus.toClientErrorResponse("User name is null!");
            }

            /* (createHashID)
            Service service = new Service() ;
            user.setUserID(service.createHashID());
             */
            userAPI.setUserID(Service.uuid());
            Repository.addUser(userAPI);
            payloadResponse = new JSONObject(objectMapper.writeValueAsString(userAPI));
            System.out.println(objectMapper.writeValueAsString(userAPI));

        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseStatus.toSuccessResponse("OK", payloadResponse);
    }

    @POST
    @Path("/post/create")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response createPost(String jsonParam) throws ClientException {
        JSONObject payloadResponse = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        String message;

        try{
            Post post = objectMapper.readValue(jsonParam, Post.class);
            JSONObject obj = new JSONObject(jsonParam);
            System.out.println(post.getTitle());
            if (!obj.has("title")) {
                message = "Title is missed!";
                return ResponseStatus.toClientErrorResponse(message);
            }
            if (isNullOrEmptyString(obj.getString("title"))) {
                return ResponseStatus.toClientErrorResponse("Title is null!");
            }

            post.setPostID(Service.uuid());
            payloadResponse = new JSONObject(objectMapper.writeValueAsString(post));
            System.out.println(objectMapper.writeValueAsString(post));

        } catch (JsonProcessingException | JSONException e) {
            e.printStackTrace();
        }
        return ResponseStatus.toSuccessResponse("OK", payloadResponse);
    }

}









