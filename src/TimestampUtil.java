import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


public class TimestampUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentTimestamp() {
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static int compareTimestamps(String timestamp1, String timestamp2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = dateFormat.parse(timestamp1);
            Date date2 = dateFormat.parse(timestamp2);

            return date1.compareTo(date2);
        } catch (ParseException e) {
            // Handle parsing errors
            return 0; // Default to no difference in case of errors
        }
    }

    public static Date timestampToDate(String timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(timestamp);
        } catch (ParseException e) {
            // Handle parsing errors
            return null;
        }
    }

}
