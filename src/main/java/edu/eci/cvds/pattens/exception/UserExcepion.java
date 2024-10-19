package edu.eci.cvds.pattens.exception;

public class UserExcepion extends Exception{
    public UserExcepion(String message){
        super(message);
    }

    public static class UserNotFoundException extends UserExcepion{
        public UserNotFoundException(String message){
            super(message);
        }
    }

    public class UserAlreadyExistsException extends UserExcepion{
        public UserAlreadyExistsException(String message){
            super(message);
        }
    }

    public static class UserIncorrectPasswordException extends UserExcepion{
        public UserIncorrectPasswordException(String message){
            super(message);
        }
    }
}
