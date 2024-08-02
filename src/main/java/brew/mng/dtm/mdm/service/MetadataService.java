package brew.mng.dtm.mdm.service;

public interface MetadataService {

    public Metadata insertMetadata(MetadataVO vo);
    public Metadata selectMetadataList(MetadataVO vo);
    public Metadata selectMetadataDtls(MetadataVO vo);
    public Metadata updateMetadata(MetadataVO vo);
    public Metadata deleteMetadata(MetadataVO vo) throws Exception;
}
