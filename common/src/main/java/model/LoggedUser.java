package model;

public class LoggedUser {
    
    private User user;

    public LoggedUser(User user) {
        this.user = user;
    }
    
    public String getId() {
        return user.getId();
    }
    
}
