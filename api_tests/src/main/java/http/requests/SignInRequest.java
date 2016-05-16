package http.requests;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "URI", "cookie" })
public class SignInRequest extends HttpRequest{

    private final static String URI = "/api/auth/tokens";
    private String name;
    private String password;

    public SignInRequest() {
        super(URI);
    }


    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPassword() {return password;}

    public void setPassword(String password){this.password = password;}}
