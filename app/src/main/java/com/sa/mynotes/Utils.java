package com.sa.mynotes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by temp on 14/11/17.
 */

public class Utils {

    public static String getTodaysDateFormatinString(String formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        return sdf.format(new Date());
    }
}
