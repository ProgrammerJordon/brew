package brew.cmm.enm;

public enum SampleEnum {

    CODE_ID("CodeId", "코드ID"),
    CODE_NM("CodeNm", "코드명");

    private final String header;
    private final String colName;

    SampleEnum(String header, String colName) {
        this.header = header;
        this.colName = colName;
    }

    public String getHeader() {
        return header;
    }

    public String getColName(){
        return colName;
    }
}
