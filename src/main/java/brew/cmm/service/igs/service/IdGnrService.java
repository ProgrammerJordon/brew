package brew.cmm.service.igs.service;

import brew.cmm.exception.BrewFdlException;

import java.math.BigDecimal;

public interface IdGnrService {

    BigDecimal getNextBigDecimalId() throws BrewFdlException;

    long getNextLongId() throws BrewFdlException;

    int getNextIntegerId() throws BrewFdlException;

    short getNextShortId() throws BrewFdlException;

    byte getNextByteId() throws BrewFdlException;

    String getNextStringId() throws BrewFdlException;

    String getNextStringId(String var1) throws BrewFdlException;

    String getNextStringId(IdGnrStrategy var1) throws BrewFdlException;
}
