package vn.scrip.buoi29.exception;
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}