package bot.telegram.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomException extends Exception {
    private final String error;

    @Override
    public String getMessage() {
        return error;
    }
}
