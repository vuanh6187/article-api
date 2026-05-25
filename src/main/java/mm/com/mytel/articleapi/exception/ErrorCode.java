package mm.com.mytel.articleapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    OK(20000, "OK", HttpStatus.OK),
    CREATED(20100, "Created", HttpStatus.CREATED),
    NO_CONTENT(20400, "No Content", HttpStatus.NO_CONTENT),
    BAD_REQUEST(40000, "Bad request", HttpStatus.BAD_REQUEST),
    VALIDATION_FAILED(40001, "Validation failed", HttpStatus.BAD_REQUEST),
    ARTICLE_COMMENTS_LOCKED(40002, "Article comments are locked", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(40100, "Unauthorized", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS(40101, "Invalid email or password", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(40300, "Forbidden", HttpStatus.FORBIDDEN),
    ARTICLE_NOT_OWNER(40301, "Not article owner", HttpStatus.FORBIDDEN),
    NOT_FOUND(40400, "Resource not found", HttpStatus.NOT_FOUND),
    ARTICLE_NOT_FOUND(40401, "Article not found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(40402, "User not found", HttpStatus.NOT_FOUND),
    CONFLICT(40900, "Conflict", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS(40901, "Email already exists", HttpStatus.CONFLICT),
    USERNAME_ALREADY_EXISTS(40902, "Username already exists", HttpStatus.CONFLICT),
    INTERNAL_ERROR(50000, "Server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String defaultMessage, HttpStatus httpStatus) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }
}
