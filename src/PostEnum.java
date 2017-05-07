import com.google.gson.annotations.SerializedName;

/**
 * Created by Artoym on 07.05.2017.
 */
public class PostEnum {

    @SerializedName("value")
    private String value;

    @SerializedName("text")
    private String text;

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
