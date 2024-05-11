package aren.kamalyan.mishlohatask.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateFormatter {
    private DateFormatter() {

    }

    public static String getCreatedOnText(Date createdAt) {
        Date now = new Date();
        long differenceInMilliseconds = now.getTime() - createdAt.getTime();
        long daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);
        //TODO: move to hardcoded strings to resource
        if (daysDifference < 1) {
            return "Today";
        } else if (daysDifference == 1) {
            return "Yesterday";
        } else if (daysDifference < 7) {
            return daysDifference + " days ago";
        } else if (daysDifference < 30) {
            long weeks = daysDifference / 7;
            return weeks + " week" + (weeks > 1 ? "s" : "") + " ago";
        } else if (daysDifference < 365) {
            long months = daysDifference / 30;
            return months + " month" + (months > 1 ? "s" : "") + " ago";
        } else {
            // Fallback for dates older than a year
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
            return simpleDateFormat.format(createdAt);
        }
    }
}