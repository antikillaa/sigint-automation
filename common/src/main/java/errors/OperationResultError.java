package errors;

public class OperationResultError extends RuntimeException {
    
    
    public OperationResultError(int code, String message) {
        super(String.format("Exception occurred while completing request. Code:%s, Message:%s",
                code, message));
    }
    
    public OperationResultError(String message) {
        super(message);
    }
    
    public OperationResultError(String message, Throwable e) {
        super(message, e);
    }
}
