package bot.telegram.exception;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
public class ParserException extends Exception {
    private String exceptionMessage;

    @Override
    public String getMessage() {
        return exceptionMessage;
    }
}
