package cinema;

public class ErrorResponse {

    private String error;

    public ErrorResponse() {
        // Default constructor needed for deserialization
    }

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
