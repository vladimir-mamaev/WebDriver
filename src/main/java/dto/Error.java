package dto;

public class Error {
    private String formFieldName;
    private String validationErrorMessage;


    public String getValidationErrorMessage() {
        return validationErrorMessage;
    }

    public Error(String formFieldName, String validationErrorMessage) {
        this.formFieldName = formFieldName;
        this.validationErrorMessage = validationErrorMessage;
    }
}
