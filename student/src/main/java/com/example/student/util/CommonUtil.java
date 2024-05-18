package com.example.student.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
    public static String createData(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy--MM--dd");
        return format.format(new Date());
    }
}
