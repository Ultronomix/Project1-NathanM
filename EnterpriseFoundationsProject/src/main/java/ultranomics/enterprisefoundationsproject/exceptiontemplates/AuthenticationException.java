package ultranomics.enterprisefoundationsproject.exceptiontemplates;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        //logged at Servlet level
        super("Could not find a user account with the provided credentials!");
    }
}