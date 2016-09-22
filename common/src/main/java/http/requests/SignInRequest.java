package http.requests;
import http.HttpMethod;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "URI", "cookie" })
public class SignInRequest extends HttpRequest {

    private final static String URI = "/api/auth/tokens";

    public SignInRequest(String username, String password) {
        super(URI);

        SignInRequest.User user = new SignInRequest.User();
        user.setName(username);
        user.setPassword(password);

        this
                .setHttpMethod(HttpMethod.POST)
                .setPayload(user);
    }

    class User {
        private String name;
        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
