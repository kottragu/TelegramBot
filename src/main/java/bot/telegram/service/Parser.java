package bot.telegram.service;

import bot.telegram.entity.Event;
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

    public Event parse(String s) throws ParserException { //надо передавать в LowerCase()
        s = s.trim();
        s = s.substring(s.indexOf("\n")+1);
        System.out.println("\n" + s + "\n");
        ArrayList<String> parameters = new ArrayList<>();
        Map<String, String> userParameters = new HashMap<>();
        while (s.contains("\n")) {
            parameters.add(s.substring(0,s.indexOf("\n")).trim());
            s = s.substring(s.indexOf("\n")+1);
            if (!s.contains("\n")) {
                parameters.add(s);
            }
        }

        for (String param: parameters) {
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
                throw new ParserException();
            }
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
            Event event = objectMapper.convertValue(userParameters, Event.class);

            if (event.getGroup() == null || event.getDayOfWeek() == null || event.getFrequency() == null || event.getTitle() == null || event.getTime() == null)
                throw new ParserException("Doesn't exist one or more statements");

            if (event.getTime().getHours() > 23 || event.getTime().getHours() < 0 || event.getTime().getMinutes() < 0 || event.getTime().getMinutes() > 59)
                throw new ParserException("Incorrect time");


            //TODO сделать для одноразовых мероприятий
            /*if (event.getFrequency() == Frequency.ONCE && (event.getDay() == null || !event.getDay().matches("[0-9]{1,2}[.][0-9]{1,2}"))) {
                throw new ParserException();
            }*/

            return event;
        } catch (Exception e) {
            log.error(e.getMessage() + "caused by " + e.getCause());
            throw new ParserException();
        }

    }
}
