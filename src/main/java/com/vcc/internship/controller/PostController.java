package com.vcc.internship.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcc.internship.common.config.Configuration;
import com.vcc.internship.model.Post;
import com.vcc.internship.restful.ResponseStatus;
import com.vcc.internship.services.impl.PostService;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static com.vcc.internship.common.utils.Strings.isNullOrEmptyString;

@Path("/post")
public class PostController {
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response addPost(String jsonParam) throws Exception {
        JSONObject payloadResponse = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        String message;

        Post post = objectMapper.readValue(jsonParam, Post.class);
        JSONObject obj = new JSONObject(jsonParam);
        if (!obj.has("title")) {
            message = "Title is missing!";
            return ResponseStatus.toClientErrorResponse(message);
        }
        if (isNullOrEmptyString(obj.getString("title"))) {
            message = "Title is null";
            return ResponseStatus.toClientErrorResponse(message);
        }

        PostService postService = new PostService(new Configuration());
        postService.addPost(post);

        payloadResponse = new JSONObject(objectMapper.writeValueAsString(post));

        return ResponseStatus.toSuccessResponse("OK", payloadResponse);
    }
//    /post/update
//    /post/delete
}
