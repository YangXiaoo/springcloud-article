package online.yangxiao.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RandomGen {

    public static String getToken(String str){
        String uuid = UUID.randomUUID().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(new Date());
        String token = uuid + "-" + str + "-" + time;

        return token;
    }
}
