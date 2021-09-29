package rush.rush.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rush.rush.dto.ErrorResponse;
import rush.rush.exception.AlreadyExistException;
import rush.rush.exception.AlreadySignedUpException;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.exception.NotAuthorizedRedirectUriException;
import rush.rush.exception.NotExistsException;
import rush.rush.exception.NotIncludedMapException;
import rush.rush.exception.OAuth2AuthenticationProcessingException;
import rush.rush.exception.WrongGroupIdException;
import rush.rush.exception.WrongInputException;
import rush.rush.exception.WrongMapTypeException;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistException(AlreadyExistException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(AlreadySignedUpException.class)
    public ResponseEntity<ErrorResponse> handleAlreadySignedUpException(
        AlreadySignedUpException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NotAuthorizedOrExistException.class)
    public ResponseEntity<ErrorResponse> handleNotAuthorizedOrExistException(
        NotAuthorizedOrExistException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NotExistsException.class)
    public ResponseEntity<ErrorResponse> handleNotExistsException(NotExistsException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NotIncludedMapException.class)
    public ResponseEntity<ErrorResponse> handleNotIncludedMapException(NotIncludedMapException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(WrongGroupIdException.class)
    public ResponseEntity<ErrorResponse> handleWrongGroupIdException(WrongGroupIdException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(WrongMapTypeException.class)
    public ResponseEntity<ErrorResponse> handleWrongMapTypeException(WrongMapTypeException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(WrongInputException.class)
    public ResponseEntity<ErrorResponse> handleWrongInputException(
        WrongInputException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NotAuthorizedRedirectUriException.class)
    public ResponseEntity<ErrorResponse> handleNotAuthorizedRedirectUriException(
        NotAuthorizedRedirectUriException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(OAuth2AuthenticationProcessingException.class)
    public ResponseEntity<ErrorResponse> handleOAuth2AuthenticationProcessingException(
        OAuth2AuthenticationProcessingException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(e.getMessage()));
    }
}
