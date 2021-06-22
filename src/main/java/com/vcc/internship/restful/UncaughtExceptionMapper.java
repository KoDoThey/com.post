package com.vcc.internship.restful;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ParamException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UncaughtExceptionMapper extends Exception implements ExceptionMapper<Exception> {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(UncaughtExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        Response res = null;
        logger.error(exception.getMessage(), exception);
        if (exception.getClass().equals(NotFoundException.class)) {
            res = ((NotFoundException) exception).getResponse();
        } else if (exception.getClass().equals(NotAllowedException.class)) {
            res = ((NotAllowedException) exception).getResponse();
        } else if (exception.getClass().equals(JSONException.class)) {
            res = MyResponseStatus.toClientErrorResponse("Invalid request's body format");
        } else if (exception.getClass().equals(ParamException.QueryParamException.class)) {
            res = MyResponseStatus.toClientErrorResponse("Invalid request's params format");
        } else if (exception.getClass().equals(NotSupportedException.class)) {
            res = MyResponseStatus.toClientErrorResponse("Unsupported Media Type");
        } else {
            JSONObject resObj = new JSONObject();
            JSONObject result = new JSONObject().put("data", new JSONArray());

            resObj = resObj.put("status", MyResponseStatus.ERROR_STATUS)
                    .put("message", "Internal Server Error")
                    .put("code", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                    .put("result", result);

            return Response.ok().entity(resObj.toString()).build();
        }
        return res;
    }
}
