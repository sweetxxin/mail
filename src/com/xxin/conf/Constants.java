package com.xxin.conf;

public class Constants {
    public static final String DB_NAME = "mail";
    public static final String DB_USER="root";
    public static final String DB_PASSWORD="";
    public static final String DB_URL="jdbc:mysql://localhost:3306/"+DB_NAME+"?useUnicode=true&characterEncoding=UTF-8";
    public static final String JDBC_DRIVE = "com.mysql.jdbc.Driver";

    public static final int USER_NAME_EXIST = -1;
    public static final int USER_NAME_OK= 1;
    public static final int USER_NO_EXIST = -1;
    public static final int REGISTER_OK = 200;
    public static final int PASSWORD_ERROR = 0;

    public static final int RESPONSE_OK = 200;
    public static final int RESPONSE_FAIL = -200;
    public static final int INNER_ERROR = 500;

    public static final String QQ_MAIL_HOST="smtp.qq.com";
    public static final String SINA_MAIL_HOST="smtp.sina.com";
    public static final String GOOGLE_MAIL_HOST="smtp.gmail.com";
    public static final String ALI_MAIL_HOST="smtp.aliyun.com";

    public static final String QQ_MAIL_POP_HOST="pop.qq.com";
    public static final String SINA_MAIL_POP_HOST="pop3.sina.com";
    public static final String GOOGLE_MAIL_POP_HOST="pop3.gmail.com";
    public static final String ALI_MAIL_POP_HOST="pop3.aliyun.com";

    public static final String QQ_MAIL_IMAP_HOST="imap.qq.com";
    public static final String SINA_MAIL_IMAP_HOST="imap.sina.com";
    public static final String GOOGLE_MAIL_IMAP_HOST="imap.gmail.com";
    public static final String ALI_MAIL_IMAP_HOST="imap.aliyun.com";

    public static final String STATIC_RESOURCES_PATH = "http://mails.sweetxxin.top/static";

}
