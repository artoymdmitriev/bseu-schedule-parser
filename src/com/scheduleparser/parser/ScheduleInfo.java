package com.scheduleparser.parser;

/**
 * Created by Artoym on 07.05.2017.
 */
public class ScheduleInfo {
    private int faculty;
    private int form;
    private int course;
    private int group;

    public int getFaculty() {
        return faculty;
    }

    public int getForm() {
        return form;
    }

    public int getCourse() {
        return course;
    }

    public int getGroup() {
        return group;
    }

    public void setFaculty(int faculty) {
        this.faculty = faculty;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
