package mm.com.mytel.articleapi.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class ApiErrorResponse {

    private int code;
    private String message;
    private Map<String, String> errors;
}
