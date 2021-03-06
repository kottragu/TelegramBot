package bot.telegram.controller;


import bot.telegram.service.Help;
import bot.telegram.service.interfaces.ScheduleService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@NoArgsConstructor
@ConfigurationProperties("bot")
public class Bot extends TelegramLongPollingBot {
    private ScheduleService scheduleService;
    @Setter
    private String botUsername;
    @Setter
    private String botToken;

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setReplyToMessageId(update.getMessage().getMessageId());
        String incomingMessage = update.getMessage().getText();
        log.info(update.getMessage().getFrom().getUserName() + " input: " + incomingMessage);

        if (incomingMessage.startsWith("/addSchedule")) {
            if(scheduleService.createEvent(update)) {
                message.setText("Мероприятие успешно добавлено");
            } else {
                message.setText("Мероприятие не внесено");
            }
        } else if (incomingMessage.startsWith("/schedule")) {
            message.enableMarkdown(true);
            message.setText(scheduleService.getSchedule(update));
        } else if (incomingMessage.startsWith("/help") || incomingMessage.startsWith("/start")) {
            message.setText(Help.getHelp());
        } else {
                message.setText("Неправильное использование бота, попробуйте ещё раз");
        }
        try {
            execute(message);
        }  catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }

 }
