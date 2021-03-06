package bot.telegram.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class Help {
    private static final String help = "Этот бот позволяет вести учёт повторяющихся (еженедельно, чёт/нечёт недели) мероприятий (пар)\n" +
            "Доступные команды:\n" +
            "/addSchedule - добавляет расписание. (Все параметры необходимо начинать с новой строки, а их значения вводить после двоеточия. Порядок не важен) " +
            "После этого необходимо ввести группу, либо какой-угодно идентификатор, (group), " +
            "как часто повторяется (frequency) (на данный момент доступны: every_week, odd_week, even_week), " +
            "день недели проведения мероприятия (dayofweek), " +
            "время, " +
            "заголовок, " +
            "опционально можно ввести описание. \n" +
            "/schedule - после неё ввести свой идентификатор (group). Будет отображены все запланированные на сегодня мероприятия для выбранного идентификатора.\n\n" +
            "примеры: \n" +

            "/schedule m32071\n\n" +

            "/addSchedule\n" +
            "group:m32071\n" +
            "frequency:every_week\n" +
            "dayofweek:saturday\n" +
            "title:день(прошёл)\n" +
            "time:23:00\n\n" +

            "/addSchedule\n" +
            "group:m32071\n" +
            "date:2021-06-18\n" +
            "period:1\n" +
            "title:день(середина)\n" +
            "time:12:00\n\n" +

            "/addSchedule\n" +
            "group:m32071\n" +
            "day:18\n" +
            "month:june\n" +
            "title:ночь(пришла)\n" +
            "time:23:49";

    public static String getHelp() {
        return help;
    }
}
