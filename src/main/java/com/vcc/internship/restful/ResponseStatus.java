package com.vcc.internship.restful;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.Response;

public class ResponseStatus {
    public static final int OK_STATUS = 1;
    public static final int ERROR_STATUS = 0;

    public static Response toSuccessResponse(String message, JSONObject entity) {
        JSONObject res = new JSONObject();
        res.put("status", OK_STATUS)
                .put("message", message)
                .put("code", Response.Status.OK.getStatusCode())
                .put("result", entity);
        return Response.ok().entity(res.toString()).build();
    }


    public static Response toClientErrorResponse(String message) {
        JSONObject res = new JSONObject();
        res.put("status", ERROR_STATUS)
                .put("message", message)
                .put("code", Response.Status.BAD_REQUEST.getStatusCode())
                .put("result", new JSONObject());
        return Response.status(Response.Status.BAD_REQUEST).entity(res.toString()).build();
    }

    public static Response toClientErrorResponse(String message, JSONObject entity) {
        JSONObject res = new JSONObject();
        res.put("status", ERROR_STATUS)
                .put("message", message)
                .put("code", Response.Status.BAD_REQUEST.getStatusCode())
                .put("result", new JSONObject());
        return Response.status(Response.Status.BAD_REQUEST).entity(res.toString()).build();
    }

    public static Response toServerErrorResponse(String message) {
        JSONObject res = new JSONObject();
        res.put("status", ERROR_STATUS)
                .put("message", message)
                .put("code", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .put("result", new JSONArray());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res.toString()).build();
    }
}









