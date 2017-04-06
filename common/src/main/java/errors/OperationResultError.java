package errors;

import http.OperationResult;

public class OperationResultError extends RuntimeException {

    public OperationResultError(String message) {
        super(message);
    }

    public OperationResultError(String message, Throwable e) {
        super(message, e);
    }

    public OperationResultError(OperationResult result) {
        super(String.format("%s code in response: \n%s", result.getCode(), result.getMessage()));
    }
}
