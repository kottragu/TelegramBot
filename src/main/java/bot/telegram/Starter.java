package bot.telegram;

import bot.telegram.controller.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@SpringBootApplication
@EnableConfigurationProperties
public class Starter {

    public static void main(String[] args) {

        SpringApplication.run(Starter.class, args);

    }
}
