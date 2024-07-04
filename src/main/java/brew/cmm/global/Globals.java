package brew.cmm.global;

import brew.cmm.service.ppt.BrewProperties;

public class Globals {
    //OS 유형
    public static final String OS_TYPE = BrewProperties.getProperty("Globals.OsType");
    //DB 유형
    public static final String DB_TYPE = BrewProperties.getProperty("Globals.DbType");
    //Public메인 페이지
    public static final String SVC_MAIN_PAGE = BrewProperties.getProperty("Globals.PublicMainPage");
    //Task메인 페이지
    public static final String MNG_MAIN_PAGE = BrewProperties.getProperty("Globals.TaskMainPage");
    //Public메인 페이지
    public static final String PUBLIC_URL = BrewProperties.getProperty("Globals.PublicUrl");
    //Task메인 페이지
    public static final String TASK_URL = BrewProperties.getProperty("Globals.TaskUrl");
    //ShellFile 경로
    public static final String SHELL_FILE_PATH = BrewProperties.getProperty("Globals.ShellFilePath");
    //퍼로퍼티 파일 위치
    public static final String CONF_PATH = BrewProperties.getProperty("Globals.ConfPath");
    //Server정보 프로퍼티 위치
    public static final String SERVER_CONF_PATH = BrewProperties.getProperty("Globals.ServerConfPath");
    //Client정보 프로퍼티 위치
    public static final String CLIENT_CONF_PATH = BrewProperties.getProperty("Globals.ClientConfPath");
    //파일포맷 정보 프로퍼티 위치
    public static final String FILE_FORMAT_PATH = BrewProperties.getProperty("Globals.FileFormatPath");

    //파일 업로드 원 파일명
    public static final String ORIGIN_FILE_NM = "originalFileName";
    //파일 확장자
    public static final String FILE_EXT = "fileExtension";
    //파일크기
    public static final String FILE_SIZE = "fileSize";
    //업로드된 파일명
    public static final String UPLOAD_FILE_NM = "uploadFileName";
    //파일경로
    public static final String FILE_PATH = "filePath";
    //파일 구분자
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    //메일발송요청 XML파일경로
    public static final String MAIL_REQUEST_PATH = BrewProperties.getProperty("Globals.MailRequestPath");
    //메일발송응답 XML파일경로
    public static final String MAIL_RESPONSE_PATH = BrewProperties.getProperty("Globals.MailRResponsePath");

    // G4C 연결용 IP (localhost)
    public static final String LOCAL_IP = BrewProperties.getProperty("Globals.LocalIp");

    //SMS 정보 프로퍼티 위치
    public static final String SMSDB_CONF_PATH = BrewProperties.getProperty("Globals.SmsDbConfPath");

    //파일 업로드 가능 확장자들
    public static final String FILE_UP_EXTS = BrewProperties.getProperty("Globals.fileUpload.Extensions");
    //파일 업로드 최대 용량
    public static final String FILE_UP_MAX_SIZE = BrewProperties.getProperty("Globals.fileUpload.maxSize");

    //레디스 host
    public static final String REDIS_HOST = BrewProperties.getProperty("Globals.redis.host");

    //레디스 port
    public static final int REDIS_PORT = Integer.parseInt(BrewProperties.getProperty("Globals.redis.port"));

    public static final String REDIS_PASSWORD = BrewProperties.getProperty("Globals.redis.password");

}

