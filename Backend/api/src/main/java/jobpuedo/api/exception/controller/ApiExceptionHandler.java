package jobpuedo.api.exception.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jobpuedo.api.exception.BadRequestException;
import jobpuedo.api.exception.EmailAlreadyRegisteredException;
import jobpuedo.api.exception.ErrorMessage;
import jobpuedo.api.exception.InactivatedUserException;
import jobpuedo.api.exception.NoAuthenticatedException;
import jobpuedo.api.exception.NoContentException;
import jobpuedo.api.exception.NoExistsResourceException;
import jobpuedo.api.exception.UserNotFoundException;

//Runtime exceptions handler
@ControllerAdvice
public class ApiExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ BadRequestException.class, NoContentException.class, NoExistsResourceException.class,
			javax.validation.ConstraintViolationException.class,
			org.springframework.transaction.TransactionSystemException.class,
			org.springframework.http.converter.HttpMessageNotReadableException.class,
			EmailAlreadyRegisteredException.class,
			org.springframework.security.authentication.BadCredentialsException.class,
		})
	@ResponseBody
	public ErrorMessage badRequest(HttpServletRequest request, Exception exception) {
		return new ErrorMessage(exception, request.getRequestURI());
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler({ NoAuthenticatedException.class, UserNotFoundException.class,
			org.springframework.security.access.AccessDeniedException.class })
	@ResponseBody
	public ErrorMessage unauthorized(HttpServletRequest request, Exception exception) {
		return new ErrorMessage(exception, request.getRequestURI());
	}

	@ResponseStatus(HttpStatus.LOCKED)
	@ExceptionHandler({ InactivatedUserException.class })
	@ResponseBody
	public ErrorMessage lockedUser(HttpServletRequest request, InactivatedUserException exception) {
		return new ErrorMessage(exception, request.getRequestURI(),exception.getId());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorMessage internalServerError(HttpServletRequest request, Exception exception) {
		return new ErrorMessage(exception, request.getRequestURI());
	}

}
