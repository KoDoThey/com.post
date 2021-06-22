package com.vcc.internship.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcc.internship.common.config.Properties;
import org.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Contains helper methods
 *
 *
 */
public class Utils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    public static <T> T fromJson(String input, Class<T> tClass) throws IOException {
        return OBJECT_MAPPER.readValue(input, tClass);
    }

    public static <T> T fromJson(String input, Class<T> tClass, T defVal) {
        try {
            return OBJECT_MAPPER.readValue(input, tClass);
        } catch (IOException e) {
            return defVal;
        }
    }
    public static Object getJSONWithDefault(JSONObject obj, String key, Object defaultVal){
        try {
            return obj.get(key);
        } catch(Exception e) {
            return defaultVal;
        }
    }
    public static Long generateUniqueId_old()
    {
        long val = -1;
        do
        {
            final UUID uid = UUID.randomUUID();
            final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
            buffer.putLong(uid.getLeastSignificantBits());
            buffer.putLong(uid.getMostSignificantBits());
            final BigInteger bi = new BigInteger(buffer.array());
            val = bi.longValue();
        }
        // We also make sure that the ID is in positive space, if its not we simply repeat the process
        while (val < 0);
        return val;
    }


    @SuppressWarnings("unchecked")
    public static <T> int compare(Comparable<T> c1, Comparable<T> c2) {
        return c1.compareTo((T) c2);
    }

    public static <T> T lastItem(Iterable<T> collection) {
        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(list.size() - 1);
        }
        Iterator<T> iterator = collection.iterator();
        T last = iterator.next();
        while (iterator.hasNext()) last = iterator.next();
        return last;
    }

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof CharSequence) {
            return ((CharSequence) o).length() == 0;
        } else if (o.getClass().isArray()) {
            return ((Object[]) ((Object[]) o)).length == 0;
        } else if (o instanceof Collection) {
            return ((Collection) o).isEmpty();
        } else {
            return o instanceof Map ? ((Map) o).isEmpty() : false;
        }
    }

    @Deprecated
    public static void sleepIgnoredException(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public static ExecutorService newCachedThreadPool(int coreSize, int maxSize, int queueSize) {
        return new ThreadPoolExecutor(coreSize,
                maxSize,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize));
    }

    @Deprecated
    public static void stopExecutor(ExecutorService executor) {
        executor.shutdown();
    }

    @Deprecated
    public static void stopExecutor(ExecutorService executor, long timeout, TimeUnit unit) {
        try {
            executor.awaitTermination(timeout, unit);
            executor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void systemExit(String message) {
        System.out.println(message);
        System.exit(0);
    }

    public static void systemError(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void systemError(Throwable throwable) {
        throwable.printStackTrace();
        System.exit(1);
    }

    public static Properties loadPropsOrDefault(InputStream in) {
        try {
            return loadProps(in);
        } catch (IOException e) {
            return new Properties();
        }
    }

    public static Properties loadPropsOrDefault(String path) {
        try {
            return loadProps(new FileInputStream(path));
        } catch (IOException e) {
            return new Properties();
        }
    }

    public static Properties loadProps(InputStream in) throws IOException {
        Properties p = new Properties();
        p.load(in);
        return p;
    }

    public static void writeProps(String path, Properties properties) throws IOException {
        String dirPath = path.substring(0, path.lastIndexOf(File.separator));
        File dir = new File(dirPath);
        if (!dir.exists() && !dir.mkdirs()) throw new IOException("Make intermediate folders failed");

        try (PrintWriter writer = new PrintWriter(new FileWriter(path, false))) {
            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String next = enumeration.nextElement().toString();
                writer.println(next + " = " + properties.getProperty(next));
            }
            writer.println();
        }
    }

    public static String wrapWithThreadInfo(String msg) {
        return "[" + Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + "] " + msg;
    }

    public static String currentThreadName() {
        return "[" + Thread.currentThread().getName() + "] ";
    }

    public static long reverseTimestamp() {
        return Long.MAX_VALUE - System.currentTimeMillis();
    }

    public static void addShutdownHook(Runnable target) {
        Runtime.getRuntime().addShutdownHook(new Thread(target));
    }

    public static <E> boolean notEquals(E e1, E e2) {
        return !e1.equals(e2);
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "Unknown";
        }
    }

    public static String getDomain(String url) {
        url = url.replaceAll("http://|https://", "");
        String[] urlSplit = url.split("/");
        return urlSplit.length > 0? urlSplit[0]:"";
    }

    public static long getTimestampNow(){
        return new Date().getTime();
    }

    public static void getOrNull(ResultSet rs, Object object, String key) throws SQLException {
        object = rs.getObject(key);
        if (rs.wasNull()){
            object = null;
        }
    }
}
