package com.kexie.acloud.util;

import java.io.File;

/**
 * Created : wen
 * DateTime : 2017/5/11 22:55
 * Description :
 */
public class PathUtil {

    public static final String BASE_PATH = "http://112.74.214.252:8080/acloud/";

    // 保存在数据库的路径
    public static final String USER_LOGO_PATH = BASE_PATH + "/resources/society/logo/";

    // 操作系统路径
    public static final String USER_LOGO_SYSTEM_PATH =
            File.separator + "resources" + File.separator + "society" + File.separator + "logo";


}
