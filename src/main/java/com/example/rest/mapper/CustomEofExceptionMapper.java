package com.example.rest.mapper;

import java.io.EOFException;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.example.misc.ErrorCode;
import com.example.misc.ErrorMessage;
import com.example.pojo.ErrorResponse;

@Provider
public class CustomEofExceptionMapper implements
		ExceptionMapper<java.io.EOFException> {
	@Produces("application/json")
	public Response toResponse(EOFException ex) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ErrorCode.JSON_INPUT_NULL_ERROR);
		errorResponse.setErrorMessage(ErrorMessage.JSON_INPUT_NULL_FAILURE);

		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.header("Content-Type", "application/json")
				.entity(errorResponse).build();
	}
}
