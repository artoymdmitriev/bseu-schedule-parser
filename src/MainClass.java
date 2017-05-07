import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Artoym on 07.05.2017.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();

        HashMap<Integer, String> faculties = parser.getFaculties();
        parser.scheduleInfo.setFaculty(11);

        HashMap<Integer, String> forms = parser.getForms();
        parser.scheduleInfo.setForm(10);

        HashMap<Integer, String> courses = parser.getCourses();
        parser.scheduleInfo.setCourse(2);

        HashMap<Integer, String> groups = parser.getGroups();
        parser.scheduleInfo.setGroup(6316);

        //get schedule and turns it into arraylist
        ArrayList<ParsedItem> parsedItems = parser.getSchedule();

        ParseNormalizer parseNormalizer = new ParseNormalizer(parsedItems);
        ArrayList<NormalItem> normalItems = parseNormalizer.normalize();

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите день недели");
        String enteredDayOfTheWeek = sc.nextLine();
        System.out.println("Введите номер недели");
        int enteredWeekNumber = sc.nextInt();

        System.out.println("Показываю расписание:");
        for(NormalItem it : normalItems) {
            if(it.getDayOfWeek().equals(enteredDayOfTheWeek) && it.getNumberOfWeek() == enteredWeekNumber) {
                System.out.println(it);
            }
        }
    }
}
