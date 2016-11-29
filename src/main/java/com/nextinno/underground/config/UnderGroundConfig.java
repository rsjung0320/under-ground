package com.nextinno.underground.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Under Ground 환경 설정 클래스.
 * 
 * @author rsjung
 *
 */
public class UnderGroundConfig {

    private static final Logger logger = LoggerFactory.getLogger(UnderGroundConfig.class);

    private static final String PROCESS_NAME = "underground"; // 프로세스 이름
    public static final String DEFAULT_BASE_PACKAGE = "com.nextinno.underground";
    public static final String MAPPER_LOCATIONS_PATH = "classpath:/mybatis/mapper/**/*.xml";
    
    // [system]
    public static final String SERVICE_PORT = "ServicePort";
    
    // [security]
    public static final String SSL_ENABLED = "SSLEnabled";
    public static final String SSL_ENABLED_PROTOCOLS = "SSLEnabledProtocols";
    public static final String KEY_STORE_FILE = "KeystoreFile";
    public static final String KEY_STORE_PASS = "KeystorePass";
    public static final String KEY_STORE_TYPE = "KeystoreType";
    public static final String KEY_STORE_PROVIDER = "KeystoreProvider";
    public static final String KEY_STORE_ALIAS = "KeystoreAlias";
    
    // [logs]
    public static final String LOG_LEVEL = "LogLevel";
    public static final String LOG_PATH = "LogPath";
    public static final String LOG_MAX_FILE_SIZE = "LogMaxFileSize";
    public static final String LOG_MAX_HISTORY = "LogMaxHistory";
    
    // 로그 레벨
    public static final String LOG_LEVEL_DEBUG = "LOG_DEBUG";
    public static final String LOG_LEVEL_INFO = "LOG_INFO";
    public static final String LOG_LEVEL_ERROR = "LOG_ERROR";
    
    private static Properties properties;

    static {
        try (FileInputStream fis = new FileInputStream(getConfigFilePath());) {
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
            logger.error("Failed to read the config file.", e);
        }
    }
    
    private UnderGroundConfig() {}
    
    /**
     * Clay 설치 루트 정보를 반환한다.
     * 
     * @return
     */
    private static String getMainPath() {
        String clayPath = System.getProperty("clay.path");

        // 같은 파티션에 있으면 "D:"를 지정하지 않아도 인식이 된다.
        // 불필요해보여 주석 처리함.
        if (isWindows()) {
            clayPath = "C:/nextinno";
        }

        if (clayPath == null) {
            clayPath = "/nextinno";
        }

        return clayPath;
    }
    
    /**
     * 윈도우 계열 운영체제에서 실행되었는지 판단한다.
     * 
     * @return 윈도우 계열이면 true, 그 외는 false를 반환한다.
     */
    public static boolean isWindows() {
        String osType = System.getProperty("os.name");

        if (osType.startsWith("Windows")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 프로세스 설정 파일의 경로를 반환한다.
     * 
     * @return
     */
    public static String getConfigFilePath() {
        return getMainPath() + "/" + PROCESS_NAME + "/current/conf/" + PROCESS_NAME + ".conf";
    }
    
    /**
     * key에 해당하는 설정값을 반환한다.
     * 
     * @param key
     * @return 설정값이 존재한다면 해당 값을 반환하고 존재하지 않으면 빈값을 반환한다.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key, "");
    }
    
    /**
     * key에 해당하는 설정값을 반환한다.
     * 
     * @param key
     * @return 설정값이 존재한다면 해당 값을 반환하고 존재하지 않으면 defaultValue 값을 반환한다.
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * key에 해당하는 정수(int)값을 반환한다.
     * 
     * @param key
     * @return 설정값이 존재한다면 해당 값을 반환하고 존재하지 않으면 defaultValue 값을 반환한다.
     */
    public static int getProperty(String key, int defaultValue) {
        String value = getProperty(key);
        
        if (value.equals("")) {
            return defaultValue;
        } else {
            return Integer.valueOf(value);
        }
    }
    
    /**
     * key에 해당하는 boolean 값을 반환한다.
     * 즉 yes, no 로 설정하는 항목에 대해 yes면 true, no면 false 를 반환한다.
     * 
     * @param key
     * @return 설정값이 존재한다면 해당 값을 반환하고 존재하지 않으면 defaultValue 값을 반환한다.
     */
    public static boolean getProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        
        if (value.equals("")) {
            return defaultValue;
        } else if (value.equals("yes")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 설정 파일을 다시 불러온다.
     * 
     * @return
     */
    public static boolean reloadConfig() {

        return true;
    }
}
