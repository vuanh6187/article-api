package mm.com.mytel.articleapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    OK(200, "OK", HttpStatus.OK),
    CREATED(201, "Created", HttpStatus.CREATED),
    NO_CONTENT(204, "No Content", HttpStatus.NO_CONTENT),
    BAD_REQUEST(400, "Validation / bad request", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, "Not logged in / wrong credentials / bad token", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, "Not owner", HttpStatus.FORBIDDEN),
    NOT_FOUND(404, "Resource not found", HttpStatus.NOT_FOUND),
    CONFLICT(409, "Duplicate username/email", HttpStatus.CONFLICT),
    INTERNAL_ERROR(500, "Server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String defaultMessage, HttpStatus httpStatus) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatus = httpStatus;
    }
}
