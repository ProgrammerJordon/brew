package brew.cmm.service.igs.service.impl;

import brew.cmm.service.igs.service.IdGnrStrategy;

public class IdGnrStrategyImpl implements IdGnrStrategy {

    private static final int DEFAULT_CIPERS = 5;
    private String prefix;
    private int cipers = 5;
    private char fillChar = '0';

    public String makeId(String originalId) {
        return this.prefix + fillString(originalId, this.fillChar, this.cipers);
    }

    public void setCipers(int cipers) {
        this.cipers = cipers;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setFillChar(char fillChar) {
        this.fillChar = fillChar;
    }

    public IdGnrStrategyImpl(String prefix, int cipers, char fillChar) {
        this.prefix = prefix;
        this.cipers = cipers;
        this.fillChar = fillChar;
    }

    public IdGnrStrategyImpl() {
    }

    public static String fillString(String originalStr, char ch, int cipers) {
        int originalStrLength = originalStr.length();
        if (cipers < originalStrLength) {
            return null;
        } else {
            int difference = cipers - originalStrLength;
            StringBuffer strBuf = new StringBuffer();

            for(int i = 0; i < difference; ++i) {
                strBuf.append(ch);
            }

            strBuf.append(originalStr);
            return strBuf.toString();
        }
    }
}
