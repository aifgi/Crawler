import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author aifgi
 */
public class Main {
    public static void main(String[] args) {
        final Pattern pattern = Pattern.compile("((\\w)+)\\d((\\w)+)");
        final String s = "abcd1def";
        final Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            for (int i = 0; i < matcher.groupCount(); ++i) {
                System.out.println(matcher.group(i));
            }
        }
    }
}
