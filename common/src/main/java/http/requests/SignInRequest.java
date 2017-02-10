package http.requests;
import http.HttpMethod;
import model.PegasusMediaType;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "URI", "cookie" })
public class SignInRequest extends HttpRequest {

    private final static String URI = "/api/auth/tokens";

    public SignInRequest() {
        super(URI);
        this.setMediaType(PegasusMediaType.PEGASUS_JSON);
    }

    public SignInRequest singOut() {
        this
                .setURI(URI + "/me")
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }

    public SignInRequest signIn(String username, String password) {
        SignInRequest.User user = new SignInRequest.User();
        user.setName(username);
        user.setPassword(password);

        this
                .setURI(URI)
                .setHttpMethod(HttpMethod.POST)
                .setPayload(user);
        return this;
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
