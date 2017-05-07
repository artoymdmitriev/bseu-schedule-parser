import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;


/**
 * Created by Artoym on 05.05.2017.
 */
public class Parser {
    ArrayList<ParsedItem> items = new ArrayList<>();
    ScheduleInfo scheduleInfo = new ScheduleInfo();

    /**
     *
     * @return IDs of faculties and their names.
     */
    public HashMap<Integer, String> getFaculties() {
        HashMap<Integer, String> faculties = new HashMap<>();
        // parses the list of faculties from the website
        Document doc = null;
        try {
            doc = Jsoup.connect("http://bseu.by/schedule/").ignoreContentType(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element mySelect = doc.getElementsByAttributeValue("name", "faculty").get(0);
        Elements els = mySelect.children();
        els.remove(0);

        for(Element el : els) {
            faculties.put(Integer.parseInt(el.attr("value")), el.text());
        }

        return faculties;
    }

    /**
     *
     * @return IDs of forms and their names.
     */
    public HashMap<Integer, String> getForms() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("faculty", scheduleInfo.getFaculty());
        query.put("__act", "__id.22.main.inpFldsA.GetForms");

        HashMap<Integer, String> forms = null;
        try {
            forms = getScheduleConfigurationParams(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return forms;
    }

    /**
     *
     * @return IDs of courses and their names.
     */
    public HashMap<Integer, String> getCourses() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("faculty", scheduleInfo.getFaculty());
        query.put("form", scheduleInfo.getForm());
        query.put("__act", "__id.23.main.inpFldsA.GetCourse");

        HashMap<Integer, String> courses = null;
        try {
            courses = getScheduleConfigurationParams(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     *
     * @return IDs of groups and their names.
     */
    public HashMap<Integer, String> getGroups() {
        HashMap<String, Object> query = new HashMap<>();
        query.put("faculty", scheduleInfo.getFaculty());
        query.put("form", scheduleInfo.getForm());
        query.put("course", scheduleInfo.getCourse());
        query.put("__act", "__id.23.main.inpFldsA.GetGroups");

        HashMap<Integer, String> groups = null;
        try {
            groups = getScheduleConfigurationParams(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groups;
    }

    /**
     * Sends request to server and turns result into HashMap. Is used in such
     * methods as getForms(), getCourses(), getGroups().
     * @param params
     * @return
     * @throws Exception
     */
    private HashMap<Integer, String> getScheduleConfigurationParams(Map<String, Object> params) throws Exception {
        URL url = new URL("http://bseu.by/schedule/");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String result = "";
        for (int c; (c = in.read()) >= 0;)
            result += (char) c;


        Type collectionType = new TypeToken<Collection<PostEnum>>(){}.getType();
        Collection<PostEnum> enums = new Gson().fromJson(result, collectionType);

        HashMap<Integer, String> values = new HashMap<>();
        for(PostEnum pe : enums) {
            values.put(Integer.parseInt(pe.getValue()), pe.getText());
        }

        return values;
    }

    /**
     * Gets a schedule as html and passes it to parser that parses
     * into an ArrayList of schedule items.
     * @throws Exception
     */
    public ArrayList<ParsedItem> getSchedule() throws Exception {
        URL url = new URL("http://bseu.by/schedule/");
        HashMap<String, Object> params = new HashMap<>();
        params.put("faculty", scheduleInfo.getFaculty());
        params.put("form", scheduleInfo.getForm());
        params.put("course", scheduleInfo.getCourse());
        params.put("group", scheduleInfo.getGroup());
        params.put("tname", null);
        params.put("period", 3);
        params.put("__act", "__id.25.main.inpFldsA.GetSchedule__sp.7.results__fp.4.main");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        // get the schedule in html format
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "WINDOWS-1251"));
        String result = "";
        for (int c; (c = in.read()) >= 0;)
            result += (char)c;

        parseScheduleIntoObjects(result);
        return items;
    }

    /**
     * Parses schedule from html into objects.
     * @param str
     */
    private void parseScheduleIntoObjects(String str) {
        Document doc = Jsoup.parse(str);
        Elements tbodyObjects = doc.getElementsByTag("tbody");  // all objects that contain tbody tag (at that moment
                                                                        // website contains two of them
        Elements schedule = tbodyObjects.get(1).children(); // full schedule
        String dayOfWeek = "понедельник";

        for(int i = 1; i < schedule.size(); i++) {
            ParsedItem it = new ParsedItem();
            Elements details = schedule.get(i).children();

            if(compareFirstItem(details.get(0).text())) { // check if the string contains only the day of the week
                dayOfWeek = details.get(0).text();
            } else if (details.size() < 4) { // check if the string doesn't contain full info (occurs in subgroups)
                try {
                    Elements nextDetails = schedule.get(i + 1).children();
                    it.setDay(dayOfWeek);
                    it.setTime(details.get(0).text());
                    it.setWeek(details.get(1).text());

                    String toSubstring = details.get(2).text();
                    it.setDiscipline(toSubstring.substring(0, toSubstring.lastIndexOf("(") - 1));
                    it.setLessonType(toSubstring.substring(toSubstring.lastIndexOf("("), toSubstring.length()));

                    it.setSubgroup(nextDetails.get(0).text());
                    it.setTeacher(nextDetails.get(1).text());
                    it.setPlace(nextDetails.get(2).text());
                    items.add(it);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            } else { // adding standard items into schedule
                try {
                    it.setDay(dayOfWeek);
                    it.setTime(details.get(0).text());
                    it.setWeek(details.get(1).text());

                    String toSubstring = details.get(2).text();
                    it.setDiscipline(toSubstring.substring(0, toSubstring.lastIndexOf("(") - 1));
                    it.setTeacher(toSubstring.substring(toSubstring.indexOf(",") + 2, toSubstring.length()));
                    it.setLessonType(toSubstring.substring(toSubstring.lastIndexOf("("), toSubstring.lastIndexOf(")") + 1));

                    it.setPlace(details.get(3).text());
                    it.setSubgroup(null);
                    items.add(it);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Checks if String contains only day of the week.
     * @param str
     * @return
     */
    private boolean compareFirstItem(String str) {
        boolean bool = false;
        if(str.equals("понедельник") || str.equals("вторник") || str.equals("среда") || str.equals("четверг")
                || str.equals("пятница") || str.equals("суббота")) {
            bool = true;
        }
        return bool;
    }
}
