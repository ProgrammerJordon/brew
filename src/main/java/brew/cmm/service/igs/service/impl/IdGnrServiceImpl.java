package brew.cmm.service.igs.service.impl;

import brew.cmm.exception.BrewFdlException;
import brew.cmm.service.igs.service.IdGnrService;
import brew.cmm.service.igs.service.IdGnrStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;

@Service("IdGnrService")
public class IdGnrServiceImpl implements IdGnrService, ApplicationContextAware {
    private MessageSource messageSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(IdGnrServiceImpl.class);
    private static final String ERROR_STRING = "address in the configuration should be a valid IP or MAC Address";
    private String mAddressId;
    private long hostId;

    public IdGnrServiceImpl() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.messageSource = (MessageSource)applicationContext.getBean("messageSource");
    }

    public BigDecimal getNextBigDecimalId() throws BrewFdlException {
        String newId = this.getNextStringId().replaceAll("-", "");
        BigInteger bi = new BigInteger(newId, 16);
        BigDecimal bd = new BigDecimal(bi);
        return bd;
    }

    public byte getNextByteId() throws BrewFdlException {
        throw new BrewFdlException(this.messageSource, "error.idgnr.not.supported", new String[]{"Byte"}, (Throwable)null);
    }

    public int getNextIntegerId() throws BrewFdlException {
        throw new BrewFdlException(this.messageSource, "error.idgnr.not.supported", new String[]{"Integer"}, (Throwable)null);
    }

    public long getNextLongId() throws BrewFdlException {
        throw new BrewFdlException(this.messageSource, "error.idgnr.not.supported", new String[]{"Long"}, (Throwable)null);
    }

    public short getNextShortId() throws BrewFdlException {
        throw new BrewFdlException(this.messageSource, "error.idgnr.not.supported", new String[]{"Short"}, (Throwable)null);
    }

    public String getNextStringId() throws BrewFdlException {
        return this.getUUId();
    }

    public String getNextStringId(IdGnrStrategy strategy) throws BrewFdlException {
        throw new BrewFdlException(this.messageSource, "error.idgnr.not.supported", new String[]{"String"}, (Throwable)null);
    }

    public String getNextStringId(String strategyId) throws BrewFdlException {
        throw new BrewFdlException(this.messageSource, "error.idgnr.not.supported", new String[]{"String"}, (Throwable)null);
    }

    public void setAddress(String address) throws BrewFdlException, NoSuchAlgorithmException {
        byte[] addressBytes = new byte[6];
        new Random();
        Random random = SecureRandom.getInstanceStrong();
        random.setSeed(System.currentTimeMillis());
        if (null == address) {
            LOGGER.warn("IDGeneration Service : Using a random number as the base for id's.This is not the best method for many purposes, but may be adequate in some circumstances. Consider using an IP or ethernet (MAC) address if available. ");

            for(int i = 0; i < 6; ++i) {
                addressBytes[i] = (byte)((int)(random.nextDouble() * 255.0 + 0.0));
            }
        } else {
            int i;
            StringTokenizer stok;
            if (address.indexOf(".") > 0) {
                stok = new StringTokenizer(address, ".");
                if (stok.countTokens() != 4) {
                    throw new BrewFdlException("address in the configuration should be a valid IP or MAC Address");
                }

                addressBytes[0] = -1;
                addressBytes[1] = -1;

                for(i = 2; stok.hasMoreTokens(); addressBytes[i++] = Integer.valueOf(stok.nextToken(), 16).byteValue()) {
                }
            } else {
                if (address.indexOf(":") <= 0) {
                    throw new BrewFdlException("address in the configuration should be a valid IP or MAC Address");
                }

                stok = new StringTokenizer(address, ":");
                if (stok.countTokens() != 6) {
                    throw new BrewFdlException("address in the configuration should be a valid IP or MAC Address");
                }

                for(i = 0; stok.hasMoreTokens(); addressBytes[i++] = Integer.valueOf(stok.nextToken(), 16).byteValue()) {
                }
            }
        }

        this.mAddressId = TimeBasedUUIDGenerator.getMacAddressAsString(addressBytes);
        this.hostId = TimeBasedUUIDGenerator.getMacAddressAsLong(addressBytes);
        LOGGER.debug("Address Id : " + this.mAddressId);
    }

    private String getUUId() {
        return this.mAddressId == null ? UUID.randomUUID().toString() : TimeBasedUUIDGenerator.generateId(this.hostId).toString();
    }
}