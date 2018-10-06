package com.example.rest.mapper;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.JsonProcessingException;

import com.example.misc.ErrorCode;
import com.example.misc.ErrorMessage;
import com.example.pojo.ErrorResponse;

@Provider
public class CustomJsonProcessingExceptionMapper implements
		ExceptionMapper<org.codehaus.jackson.JsonProcessingException> {
	@Produces("application/json")
	public Response toResponse(JsonProcessingException ex) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ErrorCode.JSON_PARSE_FAILURE);
		errorResponse.setErrorMessage(ErrorMessage.JSON_PARSE_FAILURE);

		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.header("Content-Type", "application/json")
				.entity(errorResponse).build();
	}
}
