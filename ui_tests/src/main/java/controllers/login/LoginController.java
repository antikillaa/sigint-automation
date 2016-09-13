package controllers.login;

import controllers.APILogin;
import controllers.PageController;
import model.User;
import pages.Pages;
import pages.login.LoginPage;

public class LoginController  extends PageController<LoginPage>{
    
    
    public LoginController() {
        super(Pages.loginPage(), LoginPage.class);
    }
    
   
    public void sign_in(User user) {
        getPage().load();
        getPage().typeUsername(user.getName());
        getPage().typePassword(user.getPassword());
        getPage().clickSignInButton();
        new APILogin().signInAsUser(user);
    }
}
