package bot.telegram.service;

import bot.telegram.entity.Event;
import bot.telegram.entity.RepeatingEvent;
import bot.telegram.entity.WeekRepeatingEvent;
import bot.telegram.entity.SingleEvent;
import bot.telegram.exception.ParserException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class Parser {

    public Parser() {
    }

    public Event parse(String s) throws Exception {
        s = s.toLowerCase();
        s = s.trim();
        s = s.substring(s.indexOf("\n")+1);

        ArrayList<String> parametersInLine = new ArrayList<>();
        Map<String, String> userParameters = new HashMap<>();

        while (s.contains("\n")) {
            parametersInLine.add(s.substring(0,s.indexOf("\n")).trim());
            s = s.substring(s.indexOf("\n")+1);
            if (!s.contains("\n")) {
                parametersInLine.add(s);
            }
        }

        for (String param: parametersInLine) {
            try {
                if (!param.contains(":") || param.indexOf(":") == param.length()) {
                    throw new Exception("Incorrect length of parameter");
                }

                String[] array = new String[2];
                array[0] = param.substring(0, param.indexOf(":"));
                param = param.substring(param.indexOf(":") + 1);
                array[1] = param;
                userParameters.put(array[0].trim(), array[1].trim());

            } catch (Exception e) {
                log.error(e.getMessage());
                throw new ParserException(e.getMessage());
            }
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);

            if ( userParameters.containsKey("frequency")) {
                WeekRepeatingEvent event = objectMapper.convertValue(userParameters, WeekRepeatingEvent.class);
                if (!checkMistakeRepeatingEvent(event)) {
                    throw new ParserException("Incorrect data");
                }
                return event;
            } else if (userParameters.containsKey("month")) {
                SingleEvent event = objectMapper.convertValue(userParameters, SingleEvent.class);
                if (!checkMistakeSingleEvent(event)){
                    throw new ParserException("Incorrect data");
                }
                return event;
            } else if (userParameters.containsKey("date")) {
                RepeatingEvent event = objectMapper.convertValue(userParameters, RepeatingEvent.class);
                return event;
            } else
                throw new ParserException();

        } catch (Exception e) {
            log.error(e.getMessage() + "caused by " + e.getCause());

            if (e instanceof IllegalArgumentException)
                throw new Exception("Added not all arguments");

            if (e instanceof ParserException)
                throw new ParserException();

            throw new Exception();
        }
    }

    private boolean checkMistakeSingleEvent(SingleEvent event) {
        return checkTime(event);
    }

    private boolean checkMistakeRepeatingEvent(WeekRepeatingEvent event) {
        return checkTime(event);
    }

    private boolean checkTime(Event event) {
        return event.getTime().getHours() <= 23 && event.getTime().getHours() >= 0 && event.getTime().getMinutes() >= 0 && event.getTime().getMinutes() <= 59;
    }
}
