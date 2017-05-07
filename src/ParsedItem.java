/**
 * Created by Artoym on 06.05.2017.
 */
public class ParsedItem {

    private String day;
    private String time;
    private String week;
    private String discipline;
    private String teacher;
    private String place;
    private String subgroup;
    private String lessonType;

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWeek(String week) {
        this.week = week;
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

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getWeek() {
        return week;
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
        return "ParsedItem{" +
                "day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", week='" + week + '\'' +
                ", discipline='" + discipline + '\'' +
                ", teacher='" + teacher + '\'' +
                ", place='" + place + '\'' +
                ", subgroup='" + subgroup + '\'' +
                '}';
    }
}
