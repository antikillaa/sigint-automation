package http;

import errors.OperationResultError;

import java.util.ArrayList;
import java.util.List;

public class OperationResult<T> {
    
    private static final List<Integer> successfulCodes = new ArrayList<>();
    static {
        successfulCodes.add(200);
        successfulCodes.add(201);
    }
    private G4Response response;
    private T responseObject;
    private Class<T> objectTypeClass;
    
    public  OperationResult(G4Response response, Class<T> resultCastType) {
        this.response = response;
        this.objectTypeClass = resultCastType;
    }
    
    public OperationResult(G4Response response) {
        this.response = response;
    }
    
    public OperationResult(G4Response response, T responseObject) {
        this.response = response;
        this.responseObject = responseObject;
    }
    
    private T getObject(Class<T> castType) {
        try {
            responseObject = JsonConverter.fromJsonToObject(response.getMessage(), castType);
            return responseObject;
        } catch (Exception e) {
            e.fillInStackTrace();
            throw new OperationResultError("Cannot get result of type:" + castType, e);
        }
    }
    
    
    public boolean isSuccess() {
        return successfulCodes.contains(response.getCode());
    }
    
    public T getResult()  {
        if (responseObject == null) {
            responseObject = getObject(objectTypeClass);
        }
        return responseObject;
    }
    
    public String getMessage() {
        return response.getMessage();
    }
    
    int getCode() {return response.getCode();}
}
