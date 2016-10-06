package http;

/**
 * Container for response message string and http status code
 */
public class G4Response {

    private String message;
    private int status;

    public G4Response(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

}
