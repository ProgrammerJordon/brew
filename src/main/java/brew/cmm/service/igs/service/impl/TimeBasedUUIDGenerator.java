package brew.cmm.service.igs.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;

final class TimeBasedUUIDGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeBasedUUIDGenerator.class);
    public static final Object LOCK = new Object();
    private static long lastTime;
    private static long clockSequence = 0L;
    private static final long HOST_IDENTIFIER = getHostId();

    private TimeBasedUUIDGenerator() {
    }

    public static final UUID generateId() {
        return generateIdFromTimestamp(System.currentTimeMillis(), 0L);
    }

    public static final UUID generateId(long hostId) {
        return generateIdFromTimestamp(System.currentTimeMillis(), hostId);
    }

    public static final UUID generateIdFromTimestamp(long currentTimeMillis, long hostId) {
        synchronized(LOCK) {
            if (currentTimeMillis > lastTime) {
                lastTime = currentTimeMillis;
                clockSequence = 0L;
            } else {
                ++clockSequence;
            }
        }

        long time = currentTimeMillis << 32;
        time |= (currentTimeMillis & 281470681743360L) >> 16;
        time |= 4096L | currentTimeMillis >> 48 & 4095L;
        long clockSequenceHi = clockSequence;
        clockSequenceHi <<= 48;
        long lsb = hostId != 0L ? clockSequenceHi | hostId : clockSequenceHi | HOST_IDENTIFIER;
        return new UUID(time, lsb);
    }

    private static final long getHostId() {
        long macAddressAsLong = 0L;

        try {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            if (ni != null) {
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    for(int i = 0; i < mac.length; ++i) {
                        macAddressAsLong <<= 8;
                        macAddressAsLong ^= (long)mac[i] & 255L;
                    }
                }
            }
        } catch (UnknownHostException | SocketException | IllegalArgumentException var6) {
            LOGGER.error("[IllegalArgumentException] getHostId Exception : " + var6.getMessage());
        }

        LOGGER.debug("MAC Address (from Network Interface) : " + getMacAddressAsString(getMacAddress(macAddressAsLong)));
        return macAddressAsLong;
    }

    public static byte[] getMacAddress(long address) {
        byte[] addressInBytes = new byte[]{(byte)((int)(address >> 40 & 255L)), (byte)((int)(address >> 32 & 255L)), (byte)((int)(address >> 24 & 255L)), (byte)((int)(address >> 16 & 255L)), (byte)((int)(address >> 8 & 255L)), (byte)((int)(address >> 0 & 255L))};
        return addressInBytes;
    }

    public static String getMacAddressAsString(byte[] address) {
        StringBuilder builder = new StringBuilder();
        byte[] var2 = address;
        int var3 = address.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            if (builder.length() > 0) {
                builder.append(":");
            }

            builder.append(String.format("%02X", b & 255));
        }

        return builder.toString();
    }

    public static long getMacAddressAsLong(byte[] address) {
        long mac = 0L;

        for(int i = 0; i < 6; ++i) {
            long t = ((long)address[i] & 255L) << (5 - i) * 8;
            mac |= t;
        }

        return mac;
    }
}
