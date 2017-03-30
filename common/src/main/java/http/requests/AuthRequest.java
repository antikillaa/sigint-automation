package http.requests;

import http.HttpMethod;

public class AuthRequest extends HttpRequest {

    private final static String URI = "/api/auth/tokens";

    public AuthRequest() {
        super(URI);
    }

    public AuthRequest singOut() {
        this
                .setURI(URI + "/me")
                .setHttpMethod(HttpMethod.DELETE);
        return this;
    }

    public AuthRequest signIn(String username, String password) {
        AuthRequest.User user = new AuthRequest.User();
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
