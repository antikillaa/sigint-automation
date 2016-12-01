package http;

public class G4Response {
    
    private String jsonResponseString;
    private int code;
    
    G4Response(String jsonResponseString, int code) {
        this.jsonResponseString = jsonResponseString;
        this.code = code;
    }
    
    int getCode() {
        return code;
    }
    
    String getMessage() {
        return jsonResponseString;
    }
}
