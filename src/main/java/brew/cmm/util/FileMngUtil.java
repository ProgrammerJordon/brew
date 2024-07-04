package brew.cmm.util;

import brew.cmm.global.Globals;
import brew.cmm.service.fms.service.FileVO;
import brew.cmm.service.igs.service.IdGnrService;
import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.service.rch.BrewResourceCloseHelper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static brew.cmm.util.BrewStringUtil.getTimeStamp;

@Component("FileMngUtil")
@RequiredArgsConstructor
public class FileMngUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMngUtil.class);
    private static final String FILE_STORE_PATH = BrewProperties.getProperty("fileStorePath");

    public static final int BUFF_SIZE = 2048;

    private final IdGnrService idgenService;

    public List<FileVO> parseFileInf(Map<String, MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId, String storePath) throws Exception {
        int fileKey = fileKeyParam;

        String storePathString = "";
        String atchFileIdString = "";

        if (storePath == null || "".equals(storePath)) {
            storePathString = BrewProperties.getProperty("fileStorePath");
        } else {
            storePathString = BrewProperties.getProperty("fileStorePath") + BrewProperties.getProperty(storePath);
        }

        if (atchFileId == null || "".equals(atchFileId)) {
            atchFileIdString = idgenService.getNextStringId();
        } else {
            atchFileIdString = atchFileId;
        }

        File saveFolder = new File(BrewWebUtil.filePathBlackList(storePathString));

        if (!saveFolder.exists() || saveFolder.isFile()) {
            //2017.03.03 	조성원 	시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
            if (saveFolder.mkdirs()) {
                LOGGER.debug("[file.mkdirs] saveFolder : Creation Success ");
            } else {
                LOGGER.error("[file.mkdirs] saveFolder : Creation Fail ");
            }
        }

        Iterator<Map.Entry<String, MultipartFile>> itr = files.entrySet().iterator();
        MultipartFile file;
        List<FileVO> result = new ArrayList<FileVO>();
        FileVO fvo;

        while (itr.hasNext()) {
            Map.Entry<String, MultipartFile> entry = itr.next();
            file = entry.getValue();
            String orginFileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(orginFileName)) {
                continue;
            }

            // 2022.11.11 시큐어코딩 처리
            String fileExt = FilenameUtils.getExtension(orginFileName).toUpperCase();
            String newName = KeyStr + getTimeStamp() + fileKey;
            long size = file.getSize();
            String filePath = storePathString + File.separator + newName;
            file.transferTo(new File(BrewWebUtil.filePathBlackList(filePath)));

            fvo = new FileVO();
            fvo.setFileExtsn(fileExt);
            fvo.setFileStrePath(storePathString);
            fvo.setFileMg(Long.toString(size));
            fvo.setOrignlFileNm(orginFileName);
            fvo.setStreFileNm(newName);
            fvo.setAtchFileId(atchFileIdString);
            fvo.setFileSn(String.valueOf(fileKey));
            result.add(fvo);

            fileKey++;
        }

        return result;
    }

    public List<FileVO> parseFileInf(List<MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId, String storePath) throws Exception {
        int fileKey = fileKeyParam;

        String storePathString = "";
        String atchFileIdString = "";

        if (storePath == null || "".equals(storePath)) {
            storePathString = BrewProperties.getProperty("fileStorePath");
        } else {
            storePathString = BrewProperties.getProperty("fileStorePath") + storePath;
        }

        if (atchFileId == null || "".equals(atchFileId)) {
            atchFileIdString = idgenService.getNextStringId();
        } else {
            atchFileIdString = atchFileId;
        }

        File saveFolder = new File(BrewWebUtil.filePathBlackList(storePathString));

        if (!saveFolder.exists() || saveFolder.isFile()) {

            if (saveFolder.mkdirs()) {
                LOGGER.debug("[file.mkdirs] saveFolder : Creation Success ");
            } else {
                LOGGER.error("[file.mkdirs] saveFolder : Creation Fail ");
            }
        }

        List<FileVO> result = new ArrayList<FileVO>();
        FileVO fvo;

        for (MultipartFile file : files) {

            String orginFileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(orginFileName)) {
                continue;
            }

            // 2022.11.11 시큐어코딩 처리
            String fileExt = FilenameUtils.getExtension(orginFileName).toUpperCase();
            String newName = KeyStr + getTimeStamp() + fileKey;
            long size = file.getSize();
            String filePath = storePathString + File.separator + newName;
            file.transferTo(new File(BrewWebUtil.filePathBlackList(filePath)));

            fvo = new FileVO();
            fvo.setFileExtsn(fileExt);
            fvo.setFileStrePath(storePathString);
            fvo.setFileMg(Long.toString(size));
            fvo.setOrignlFileNm(orginFileName);
            fvo.setStreFileNm(newName);
            fvo.setAtchFileId(atchFileIdString);
            fvo.setFileSn(String.valueOf(fileKey));

            result.add(fvo);

            fileKey++;
        }

        return result;
    }

    protected void writeUploadedFile(MultipartFile file, String newName) throws Exception {
        InputStream stream = null;
        OutputStream bos = null;

        try {
            stream = file.getInputStream();
            File cFile = new File(FILE_STORE_PATH);

            if (!cFile.isDirectory()) {
                boolean _flag = cFile.mkdir();
                if (!_flag) {
                    throw new IOException("Directory creation Failed ");
                }
            }

            String writeFilePath = BrewWebUtil.filePathBlackList(FILE_STORE_PATH + File.separator + FilenameUtils.getName(newName));
            bos = new FileOutputStream(writeFilePath);

            int bytesRead = 0;
            byte[] buffer = new byte[BUFF_SIZE];

            while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        } finally {
            BrewResourceCloseHelper.close(bos, stream);
        }
    }

    public static void downFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String downFileName = "";
        String orgFileName = "";

        if ((String) request.getAttribute("downFile") == null) {
            downFileName = "";
        } else {
            downFileName = (String) request.getAttribute("downFile");
        }

        if ((String) request.getAttribute("orgFileName") == null) {
            orgFileName = "";
        } else {
            orgFileName = (String) request.getAttribute("orginFile");
        }

        orgFileName = orgFileName.replaceAll("\r", "").replaceAll("\n", "");

        File file = new File(BrewWebUtil.filePathBlackList(downFileName));

        if (!file.exists()) {
            throw new FileNotFoundException(downFileName);
        }

        if (!file.isFile()) {
            throw new FileNotFoundException(downFileName);
        }

        byte[] buffer = new byte[BUFF_SIZE]; //buffer size 2K.

        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition:", "attachment; filename=" + new String(orgFileName.getBytes(), "UTF-8"));
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        BufferedInputStream fin = null;
        BufferedOutputStream outs = null;

        try {
            fin = new BufferedInputStream(new FileInputStream(file));
            outs = new BufferedOutputStream(response.getOutputStream());
            int read = 0;

            while ((read = fin.read(buffer)) != -1) {
                outs.write(buffer, 0, read);
            }
        } finally {
            BrewResourceCloseHelper.close(outs, fin);
        }
    }

    public static HashMap<String, String> uploadFile(MultipartFile file) throws Exception {

        HashMap<String, String> map = new HashMap<String, String>();
        long size = file.getSize();
        String orginFileName = file.getOriginalFilename();
        String fileExt = "";
        String newName = "";

        if (StringUtils.isNotEmpty(orginFileName)) {
            fileExt = FilenameUtils.getExtension(orginFileName);
        }

        newName = getTimeStamp();
        writeFile(file, newName);
        map.put(Globals.ORIGIN_FILE_NM, orginFileName);
        map.put(Globals.UPLOAD_FILE_NM, newName);
        map.put(Globals.FILE_EXT, fileExt);
        map.put(Globals.FILE_PATH, FILE_STORE_PATH );
        map.put(Globals.FILE_SIZE, String.valueOf(size));

        return map;
    }

    protected static void writeFile(MultipartFile file, String newName) throws Exception {
        InputStream stream = null;
        OutputStream bos = null;

        try {
            stream = file.getInputStream();
            File cFile = new File(BrewWebUtil.filePathBlackList(FILE_STORE_PATH ));

            if (!cFile.isDirectory()) {
                //2017.03.03 	조성원 	시큐어코딩(ES)-부적절한 예외 처리[CWE-253, CWE-440, CWE-754]
                if (cFile.mkdirs()){
                    LOGGER.debug("[file.mkdirs] saveFolder : Creation Success ");
                } else {
                    LOGGER.error("[file.mkdirs] saveFolder : Creation Fail ");
                }
            }

            bos = new FileOutputStream(BrewWebUtil.filePathBlackList(FILE_STORE_PATH  + File.separator + FilenameUtils.getName(newName)));

            int bytesRead = 0;
            byte[] buffer = new byte[BUFF_SIZE];

            while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        } finally {
            BrewResourceCloseHelper.close(bos, stream);
        }
    }

    public void downFile(HttpServletResponse response, String streFileNm, String orignFileNm) throws Exception {
        String downFileName = BrewWebUtil.filePathBlackList(streFileNm);
        String orgFileName = orignFileNm;

        File file = new File(downFileName);

        if (!file.exists()) {
            throw new FileNotFoundException(downFileName);
        }

        if (!file.isFile()) {
            throw new FileNotFoundException(downFileName);
        }

        int fSize = (int) file.length();
        if (fSize > 0) {
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));

                String mimetype = "application/x-msdownload";

                //response.setBufferSize(fSize);
                response.setContentType(mimetype);
                response.setHeader("Content-Disposition:", "attachment; filename=" + orgFileName);
                response.setContentLength(fSize);
                //response.setHeader("Content-Transfer-Encoding","binary");
                //response.setHeader("Pragma","no-cache");
                //response.setHeader("Expires","0");
                FileCopyUtils.copy(in, response.getOutputStream());
            } finally {
                BrewResourceCloseHelper.close(in);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }

    private static String getTimeStamp() {

        String rtnStr = null;

        String pattern = "yyyyMMddhhmmssSSS";

        SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        rtnStr = sdfCurrent.format(ts.getTime());

        return rtnStr;
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            boolean result = file.delete();
        }

    }

}
