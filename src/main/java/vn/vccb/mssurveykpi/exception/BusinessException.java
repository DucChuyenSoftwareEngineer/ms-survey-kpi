package vn.vccb.mssurveykpi.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -739726867701128090L;

    public BusinessException(String message) {
        super(message);
    }
}
