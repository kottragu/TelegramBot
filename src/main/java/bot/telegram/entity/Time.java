package bot.telegram.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Time implements Comparable<Time>{
    @Id
    @GeneratedValue
    private Long id;
    private int hours;
    private int minutes;

    public Time(String s) {
        String[] arr = s.split(":");
        hours = Integer.parseInt(arr[0]);
        minutes = Integer.parseInt(arr[1]);
    }

    @Override
    public int compareTo(Time newTime) {
        if (newTime.getHours() > hours) {
            return -1;
        }
        if (newTime.getHours() < hours) {
            return 1;
        }
        if (newTime.getHours() == hours) {
            if (newTime.getMinutes() > minutes)
                return -1;
            if (newTime.getMinutes() < minutes)
                return 1;
        }
        return 0; //if hours and minutes are equals
    }


    public String toString() {
        return hours + ":" + minutes;
    }
}
