package com.scheduleparser.example;

import com.scheduleparser.parser.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Artoym on 07.05.2017.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        MainClass mainClass = new MainClass();
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        Parser parser = new Parser();

        //gets a map of faculties (id, name)
        HashMap<Integer, String> faculties = parser.getFaculties();
        //shows all faculties and their id's
        for(Map.Entry<Integer, String> entry : faculties.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        //user sets the faculty, e.g. 11
        scheduleInfo.setFaculty(mainClass.getID("Введите номер факультета: "));

        //gets a map of forms (id, name)
        HashMap<Integer, String> forms = parser.getForms(scheduleInfo);
        //shows all forms and their id's
        for(Map.Entry<Integer, String> entry : forms.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        //user sets the form, e.g. 10
        scheduleInfo.setForm(mainClass.getID("Введите номер формы получения образования: "));

        //gets a map of courses (id, number)
        HashMap<Integer, String> courses = parser.getCourses(scheduleInfo);
        //shows all courses and their id's
        for(Map.Entry<Integer, String> entry : courses.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        //user sets course, e.g. 2
        scheduleInfo.setCourse(mainClass.getID("Введите номер курса: "));

        //gets a map of groups (id, number)
        HashMap<Integer, String> groups = parser.getGroups(scheduleInfo);
        //shows all groups and their id's
        for(Map.Entry<Integer, String> entry : groups.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        //user sets group, e.g. 6316
        scheduleInfo.setGroup(mainClass.getID("Введите номер группы: "));

        //gets schedule and turns it into arraylist
        ArrayList<ParsedItem> parsedItems = parser.getSchedule(scheduleInfo);

        //turns an arraylist of parsed items into a list of
        //normal items
        ParseNormalizer parseNormalizer = new ParseNormalizer(parsedItems);
        ArrayList<NormalItem> normalItems = parseNormalizer.normalize();

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите день недели");
        //user enters a day e.g. "среда"
        String enteredDayOfTheWeek = sc.nextLine();
        System.out.println("Введите номер недели");
        //user enters a week number e.g. "14"
        int enteredWeekNumber = sc.nextInt();

        //prints a list of subjects which are going to be
        //on Wednesday on the 14th week e.g.:
        //com.scheduleparser.parser.NormalItem{dayOfWeek='среда', numberOfDayOfWeek=6, time='8:15-9:35', numberOfWeek=14, discipline='Экономика организации (предприятия)', teacher='Довыдова Ольга Григорьевна', place='4/501', subgroup='null', lessonType='Практические занятия'}
        //com.scheduleparser.parser.NormalItem{dayOfWeek='среда', numberOfDayOfWeek=6, time='9:45-11:05', numberOfWeek=14, discipline='Бизнес-офис организации (предприятия) и интернет-маркетинг', teacher='Шаврук Елена Юрьевна', place='4/806', subgroup='подгр.а', lessonType='Лабораторные занятия'}
        //com.scheduleparser.parser.NormalItem{dayOfWeek='среда', numberOfDayOfWeek=6, time='11:15-12:35', numberOfWeek=14, discipline='Экономика природопользования', teacher='Белоусова Татьяна Николаевна', place='4/908', subgroup='null', lessonType='Лекции'}
        //com.scheduleparser.parser.NormalItem{dayOfWeek='среда', numberOfDayOfWeek=6, time='16:05-17:25', numberOfWeek=14, discipline='Русский язык как иностранный. Профессиональная лексика', teacher='Божкова Марина Ивановна', place='4/906', subgroup='null', lessonType='Практические занятия'}
        System.out.println("Показываю расписание:");
        for(NormalItem it : normalItems) {
            if(it.getDayOfWeek().equals(enteredDayOfTheWeek) && it.getNumberOfWeek() == enteredWeekNumber) {
                System.out.println(it);
            }
        }
    }

    private int getID(String question) {
        System.out.println(question);
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
}
