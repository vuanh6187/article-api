package mm.com.mytel.articleapi.exception;

public class BadRequestException extends ApiException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BadRequestException(String message) {
        super(ErrorCode.BAD_REQUEST, message);
    }
}
