package http;

import javax.ws.rs.core.Response;

public class G4Response {

    private String message;
    private int status;

    public G4Response(Response response) {
        message = response.readEntity(String.class);
        status = response.getStatus();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

}
