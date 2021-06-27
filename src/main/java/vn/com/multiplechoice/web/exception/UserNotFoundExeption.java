package vn.com.multiplechoice.web.exception;

public class UserNotFoundExeption extends Exception {
    private static final long serialVersionUID = 6664867926321010803L;
    
    private String message;

    public UserNotFoundExeption(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
