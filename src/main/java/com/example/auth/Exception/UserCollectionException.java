package com.example.auth.Exception;

public class UserCollectionException extends Exception {
    private static final long serialVersionUID= 1L;
    public UserCollectionException(String message){
        super(message);
    }
    public static String NotFoundException(String id){
        return "user with"+" "+id+" "+"not found";
    }  public static String UserAlreadyExist(){
        return "user with given id already exist ";
    }


}
