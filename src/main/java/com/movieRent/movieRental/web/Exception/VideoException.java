package com.movieRent.movieRental.web.Exception;

public class VideoException extends Exception{
    public VideoException() {
    }

    public VideoException(String message) {
        super(message);
    }

    public VideoException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoException(Throwable cause) {
        super(cause);
    }
}
