package mm.com.mytel.articleapi.exception;

public class ConflictException extends ApiException {

    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ConflictException(String message) {
        super(ErrorCode.CONFLICT, message);
    }
}
