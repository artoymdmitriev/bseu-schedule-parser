import java.util.ArrayList;

/**
 * Created by Artoym on 06.05.2017.
 */
public class ParseNormalizer {
    private ArrayList<ParsedItem> parsedItems;
    public ArrayList<NormalItem> normalItems = new ArrayList<>();

    public ParseNormalizer(ArrayList<ParsedItem> parsedItems) {
        this.parsedItems = parsedItems;
    }

    public ArrayList<NormalItem> normalize() {
        for(ParsedItem pi : parsedItems) {
            String day = pi.getDay();
            String time = pi.getTime();
            String week = pi.getWeek();
            String discipline = pi.getDiscipline();
            String teacher = pi.getTeacher();
            String place = pi.getPlace();
            String subgroup = pi.getSubgroup();
            String lessonType = pi.getLessonType();

            ArrayList<Integer> weeks = parseWeeksToArray(week);
            for(int i = 0; i < weeks.size(); i++) {
                NormalItem normalItem = new NormalItem();
                normalItem.setDayOfWeek(day);
                normalItem.setTime(time);
                normalItem.setNumberOfWeek(Integer.parseInt(weeks.get(i).toString()));
                normalItem.setDiscipline(discipline);
                normalItem.setTeacher(teacher);
                normalItem.setPlace(place);
                normalItem.setSubgroup(subgroup);
                normalItem.setLessonType(lessonType.substring(1, lessonType.length() - 1));

                switch (pi.getDay()) {
                    case "понедельник" : normalItem.setNumberOfDayOfWeek(1);
                    case "вторник" : normalItem.setNumberOfDayOfWeek(2);
                    case "среда" : normalItem.setNumberOfDayOfWeek(3);
                    case "четверг" : normalItem.setNumberOfDayOfWeek(4);
                    case "пятница" : normalItem.setNumberOfDayOfWeek(5);
                    case "суббота" : normalItem.setNumberOfDayOfWeek(6);
                }

                normalItems.add(normalItem);
            }
        }

        return normalItems;
    }

    private ArrayList<Integer> parseWeeksToArray(String string) {
        char[] charsWithBrackets = string.toCharArray();
        char[] charsWithoutBrackets = new char[charsWithBrackets.length - 2];
        int x = 0;
        for(int i = 1; i <= charsWithoutBrackets.length; i++) {
            charsWithoutBrackets[x] = charsWithBrackets[i];
            x++;
        }

        string = new String(charsWithoutBrackets);
        String[] weeks = string.split(",");
        ArrayList<Integer> weeksExtended = new ArrayList<>();
        for(String str : weeks) {
            if(str.contains("-")) {
                String[] elements = str.split("-");
                weeksExtended.add(Integer.parseInt(elements[0]));
                int minElement = Integer.parseInt(elements[0]);
                int maxElement = Integer.parseInt(elements[1]);
                int difference = maxElement - minElement;
                for(int i = 1; i <= difference; i++) {
                    weeksExtended.add(minElement + i);
                }
            } else {
                weeksExtended.add(Integer.parseInt(str));
            }
        }
        return weeksExtended;
    }
}
