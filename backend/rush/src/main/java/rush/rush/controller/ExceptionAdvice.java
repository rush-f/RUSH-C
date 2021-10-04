package rush.rush.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rush.rush.dto.ErrorResponse;
import rush.rush.exception.AlreadyExistedEmailException;
import rush.rush.exception.AlreadySignedUpException;
import rush.rush.exception.NotArticleExistsException;
import rush.rush.exception.NotAuthorizedOrExistException;
import rush.rush.exception.NotAuthorizedRedirectUriException;
import rush.rush.exception.NotCommentExistException;
import rush.rush.exception.NotIncludedMapException;
import rush.rush.exception.NotInvitationCodeExistsException;
import rush.rush.exception.NotLikeExistsException;
import rush.rush.exception.NotUserExistsException;
import rush.rush.exception.OAuth2AuthenticationProcessingException;
import rush.rush.exception.WrongGroupIdException;
import rush.rush.exception.WrongInputException;
import rush.rush.exception.WrongMapTypeException;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AlreadyExistedEmailException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistException(
        AlreadyExistedEmailException e) {

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

    @ExceptionHandler(NotUserExistsException.class)
    public ResponseEntity<ErrorResponse> handleNotUserExistsException(NotUserExistsException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NotArticleExistsException.class)
    public ResponseEntity<ErrorResponse> handleNotArticleExistsException(
        NotArticleExistsException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NotCommentExistException.class)
    public ResponseEntity<ErrorResponse> handleNotCommentExistException(
        NotCommentExistException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NotLikeExistsException.class)
    public ResponseEntity<ErrorResponse> handleNotLikeExistsException(NotLikeExistsException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NotInvitationCodeExistsException.class)
    public ResponseEntity<ErrorResponse> handleNotInvitationCodeExistsException(
        NotInvitationCodeExistsException e) {

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
