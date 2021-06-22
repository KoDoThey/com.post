package com.vcc.internship.restful;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class ClientExceptionMapper implements ExceptionMapper<ClientException> {
    private static final Logger logger = Logger.getLogger(ClientExceptionMapper.class);

    public Response toResponse(ClientException exception)
    {
        JSONObject res = new JSONObject();
        JSONObject result = new JSONObject().put("data", new JSONArray());

        res = res.put("status", MyResponseStatus.ERROR_STATUS)
                .put("message", exception.getMessage())
                .put("code", Response.Status.BAD_REQUEST.getStatusCode())
                .put("result", result);

        return Response.ok().entity(res.toString()).build();
    }
}

