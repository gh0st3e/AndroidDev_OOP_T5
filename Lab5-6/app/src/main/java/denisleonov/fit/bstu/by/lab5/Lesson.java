package denisleonov.fit.bstu.by.lab5;

import java.io.Serializable;
import java.util.Objects;

public class Lesson implements Serializable {
    public String Name;
    public String Time;
    public String Room;
    public String Teacher;
    public String Day;
    public int Week;

    public Lesson(String name, String time, String room, String teacher, String day, int week) {
        Name = name;
        Time = time;
        Room = room;
        Teacher = teacher;
        Day = day;
        Week = week;
    }

    @Override
    public String toString() {
        return Name + "\n"
                + Room + "\n"
                + Time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Week == lesson.Week && Objects.equals(Name, lesson.Name) && Objects.equals(Time, lesson.Time) && Objects.equals(Room, lesson.Room) && Objects.equals(Teacher, lesson.Teacher) && Objects.equals(Day, lesson.Day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Time, Room, Teacher, Day, Week);
    }
}
