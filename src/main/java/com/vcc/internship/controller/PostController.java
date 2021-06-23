package com.vcc.internship.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcc.internship.common.config.Configuration;
import com.vcc.internship.common.config.Properties;
import com.vcc.internship.model.Post;
import com.vcc.internship.restful.ResponseStatus;
import com.vcc.internship.services.impl.PostService;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.vcc.internship.common.utils.Strings.isNullOrEmptyString;

@Path("/post")
public class PostController {
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getPost(@QueryParam("postID") String postID) throws Exception {
        String message;
        if (isNullOrEmptyString(postID)) {
            return ResponseStatus.toClientErrorResponse("postID is null");
        }

        JSONObject result = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        try (PostService postService = new PostService(new Configuration())) {
            Post post = postService.getPostById(postID);
            postService.getPostById(postID);
            if (post == null) {
                return ResponseStatus.toServerErrorResponse("Post is deleted!");
            }
            result = new JSONObject(objectMapper.writeValueAsString(post));
        }
        return ResponseStatus.toSuccessResponse("OK", result);
    }

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

    @POST
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response updatePost(String jsonParam) throws Exception {
        JSONObject payloadResponse = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        String message;
        Post post = objectMapper.readValue(jsonParam, Post.class);
        JSONObject obj = new JSONObject(jsonParam);
        if (!obj.has("postID")) {
            message = "postID is missing!";
            return ResponseStatus.toClientErrorResponse(message);
        }
        if (isNullOrEmptyString(obj.getString("postID"))) {
            message = "postID is null";
            return ResponseStatus.toClientErrorResponse(message);
        }
        PostService postService = new PostService(new Configuration());
        postService.updatePost(post);
        payloadResponse = new JSONObject(objectMapper.writeValueAsString(post));
        return ResponseStatus.toSuccessResponse("OK", payloadResponse);
    }

    @POST
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response deletePost(String jsonParam) throws Exception {
        JSONObject payloadResponse = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        String message;
        Post post = objectMapper.readValue(jsonParam, Post.class);
        JSONObject obj = new JSONObject(jsonParam);
        if (!obj.has("postID")) {
            message = "postID is missing!";
            return ResponseStatus.toClientErrorResponse(message);
        }
        if (isNullOrEmptyString(obj.getString("postID"))) {
            message = "postID is null";
            return ResponseStatus.toClientErrorResponse(message);
        }
        PostService postService = new PostService(new Configuration());
        postService.deletePost(post);
        payloadResponse = new JSONObject(objectMapper.writeValueAsString(post));
        return ResponseStatus.toSuccessResponse("OK", payloadResponse);
    }
}
