package model;

public class LoggedUser {
    
    private User user;
    private Token token;
    
    public String getId() {
        return user.getId();
    }
    
    public Token getToken() {
        return token;
    }
    
    public void setToken(Token token) {
        this.token = token;
    }
    
    
    public LoggedUser(User user, Token token) {
        this.user = user;
        this.token = token;
    }
    
    public LoggedUser(User user) {
        this.user = user;
    }
    
    
}
