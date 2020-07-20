package com.wxy.wjl.testspringboot2.job.utils;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.StringUtils;

public class MrSnowflakeKeyGenerator {
    private static final long EPOCH = 1514736000000L;
    private static final long SEQUENCE_BITS = 12L;
    private static final long WORKER_ID_BITS = 10L;
    private static final long SEQUENCE_MASK = 4095L;
    private static final long WORKER_ID_LEFT_SHIFT_BITS = 12L;
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = 22L;
    private static final long BACKUP_COUNT = 2L;
    private static final long WORKER_ID_MAX_VALUE = 341L;
    private static long workerId = 0L;
    private static long sequence;
    private static long lastTime;
    private static Map<Long, Long> workerIdLastTimeMap = new ConcurrentHashMap();
    private static final long MAX_BACKWARD_MS = 4L;

    public MrSnowflakeKeyGenerator() {
    }

    public static void initWorkerId() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException var8) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }

        byte[] ipAddressByteArray = address.getAddress();
        long workerId = 0L;
        byte[] var4;
        int var5;
        int var6;
        byte byteNum;
        if (ipAddressByteArray.length == 4) {
            var4 = ipAddressByteArray;
            var5 = ipAddressByteArray.length;

            for(var6 = 0; var6 < var5; ++var6) {
                byteNum = var4[var6];
                workerId += (long)(byteNum & 255);
            }
        } else {
            if (ipAddressByteArray.length != 16) {
                throw new IllegalStateException("Bad LocalHost InetAddress, please check your network!");
            }

            var4 = ipAddressByteArray;
            var5 = ipAddressByteArray.length;

            for(var6 = 0; var6 < var5; ++var6) {
                byteNum = var4[var6];
                workerId += (long)(byteNum & 63);
            }
        }

        setWorkerId(workerId);
    }

    public static void setWorkerId(long workerId) {
        if (workerId >= 0L && workerId <= 341L) {
            for(int i = 0; (long)i <= 2L; ++i) {
                workerIdLastTimeMap.put(workerId + (long)i * 341L, 0L);
            }

            MrSnowflakeKeyGenerator.workerId = workerId;
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 341L));
        }
    }

    public static synchronized long nextId() {
        long currentMillis = System.currentTimeMillis();
        if (lastTime > currentMillis) {
            if (lastTime - currentMillis < 4L) {
                try {
                    TimeUnit.MILLISECONDS.sleep(lastTime - currentMillis);
                } catch (InterruptedException var3) {
                }
            } else {
                tryGenerateKeyOnBackup(currentMillis);
                System.err.println("触发了时钟回拨机制");
            }
        }

        if (lastTime == currentMillis) {
            if (0L == (sequence = ++sequence & 4095L)) {
                currentMillis = waitUntilNextTime(currentMillis);
            }
        } else {
            sequence = 0L;
        }

        lastTime = currentMillis;
        workerIdLastTimeMap.put(workerId, lastTime);
        return currentMillis - 1514736000000L << 22 | workerId << 12 | sequence;
    }

    private static long tryGenerateKeyOnBackup(long currentMillis) {
        Iterator var2 = workerIdLastTimeMap.entrySet().iterator();

        do {
            if (!var2.hasNext()) {
                throw new IllegalStateException("Clock is moving backwards, current time is " + currentMillis + " milliseconds, workerId map = " + workerIdLastTimeMap);
            }

            Entry<Long, Long> entry = (Entry)var2.next();
            workerId = (Long)entry.getKey();
            Long tempLastTime = (Long)entry.getValue();
            lastTime = tempLastTime == null ? 0L : tempLastTime;
        } while(lastTime > currentMillis);

        return lastTime;
    }

    private static long waitUntilNextTime(long lastTime) {
        long time;
        for(time = System.currentTimeMillis(); time <= lastTime; time = System.currentTimeMillis()) {
        }

        return time;
    }

    public static void main(String[] args) throws Exception {
        Map<Long, Long> cache = new HashMap();
        initWorkerId();
        int i = 0;

        for(byte n = 1; i < n; ++i) {
            long id = nextId();
            Long exist = (Long)cache.get(id);
            if (exist != null) {
                throw new IllegalArgumentException("发生重复ID生成,id=" + exist);
            }

            cache.put(id, id);
            System.out.println(i + ":" + StringUtils.leftPad(String.valueOf(id), 10, '0'));
        }

    }
}
