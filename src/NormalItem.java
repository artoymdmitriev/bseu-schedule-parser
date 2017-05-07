/**
 * Created by Artoym on 06.05.2017.
 */
public class NormalItem {

    private String dayOfWeek; // e.g. "понедельник"
    private int numberOfDayOfWeek; // e.g. 1 (in case day of week is Monday)
    private String time; // e.g. "8.15-9.45"
    private int numberOfWeek; // e.g. 20
    private String discipline; // e.g. "Информационный менеджмент и реинжиниринг бизнес-процессов"
    private String teacher; // e.g. "Петров Петр Петрович"
    private String place; // e.g. "4/806"
    private String subgroup; // e.g. "подгр. а"
    private String lessonType; // e.g. "Лабораторные занятия"

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setNumberOfDayOfWeek(int numberOfDayOfWeek) {
        this.numberOfDayOfWeek = numberOfDayOfWeek;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNumberOfWeek(int numberOfWeek) {
        this.numberOfWeek = numberOfWeek;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getNumberOfDayOfWeek() {
        return numberOfDayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public int getNumberOfWeek() {
        return numberOfWeek;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getPlace() {
        return place;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public String getLessonType() {
        return lessonType;
    }

    @Override
    public String toString() {
        return "NormalItem{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", numberOfDayOfWeek=" + numberOfDayOfWeek +
                ", time='" + time + '\'' +
                ", numberOfWeek=" + numberOfWeek +
                ", discipline='" + discipline + '\'' +
                ", teacher='" + teacher + '\'' +
                ", place='" + place + '\'' +
                ", subgroup='" + subgroup + '\'' +
                ", lessonType='" + lessonType + '\'' +
                '}';
    }
}
