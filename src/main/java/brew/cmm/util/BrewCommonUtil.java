package brew.cmm.util;

import brew.cmm.module.file.service.FileService;
import brew.cmm.module.file.service.FileVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class BrewCommonUtil {

    private static FileService fileService;

    public static boolean isEmpty(Object str) {
        return str == null || (str instanceof String) == false || str.toString().length() == 0;
    }

    public static String emptyConvert(Object src, String alt) {
        return isEmpty(src) ? alt : src.toString();
    }

    /**
     * String fileName = "한글,영문,특수문자 ";
     * String[] header = {"가", "나", "다", "!", "#", "%", "$", "_", "A", "B", "C", "D", "d", "e", "f", "g", "h", "i"};
     * @param response
     * @param dataList
     * @param fileName
     * @param header
     * @throws Exception
     */
    public static void downloadCSV(HttpServletResponse response, List<?> dataList, String fileName, String[] header) throws Exception {
        try {
            response.setContentType("text/csv");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName+".csv", StandardCharsets.UTF_8.toString()) + "\"");

            Cookie cookie = new Cookie("fileDownloadToken", "TRUE");
            cookie.setHttpOnly(false);
            cookie.setPath("/");
            cookie.setSecure(false);
            response.addCookie(cookie);

            PrintWriter writer = response.getWriter();

            if (!dataList.isEmpty()) {
                writer.write('\ufeff');

                Field[] fields = dataList.get(0).getClass().getDeclaredFields();

                for (int i = 0; i < header.length; i++) {
                    writer.print(header[i]);
                    if (i < header.length - 1) {
                        writer.print(",");
                    }
                }
                writer.println();

                // 데이터 행 처리
                for (Object item : dataList) {
                    for (int i = 0; i < fields.length; i++) {
                        fields[i].setAccessible(true);
                        Object value = fields[i].get(item);
                        writer.print(value);
                        if (i < fields.length - 1) {
                            writer.print(",");
                        }
                    }
                    writer.println();
                }
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("CSV 파일 생성 중 에러 발생", e);
        }
    }

    public static void downloadExcel(HttpServletResponse response, List<?> dataList, String fileName, String[] header) throws Exception {

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName + ".xlsx", StandardCharsets.UTF_8.toString()) + "\"");

        Cookie cookie = new Cookie("fileDownloadToken", "TRUE");
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(fileName);
        XSSFRow row = null;
        int rowNum = 0;

        row = sheet.createRow(rowNum++);

        for(int i =0; i < header.length; i++) {
            row.createCell(i).setCellValue(header[i]);
        }

        Object firstObj = dataList.get(0);
        Field[] fields = firstObj.getClass().getDeclaredFields();

        for (Object obj : dataList) {
            row = sheet.createRow(rowNum++);
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object value = fields[i].get(obj);
                if (value != null) {
                    row.createCell(i).setCellValue(value.toString());
                } else {
                    row.createCell(i).setCellValue("");
                }
            }
        }

        try {
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            System.out.println("엑셀 다운로드 중 오류 발생: " + e.getMessage());
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                System.out.println("엑셀 다운로드 이후 오류 발생: " + e.getMessage());
            }
        }
    }

    public static void downloadAtchFile(String atchFileId, String fileSn, HttpServletRequest request, HttpServletResponse response) throws Exception {

        FileVO fileVO = new FileVO();
        fileVO.setAtchFileId(atchFileId);
        fileVO.setFileSn(fileSn);
        FileVO fvo = fileService.selectFileInf(fileVO);

        File uFile = new File(fvo.getFileStrePath(), fvo.getStreFileNm());
        long fSize = uFile.length();

        if (fSize > 0) {
            String mimetype = "application/x-msdownload";

            String userAgent = request.getHeader("User-Agent");
            HashMap<String, String> result = BrewBrowerUtil.getBrowser(userAgent);
            if (!BrewBrowerUtil.MSIE.equals(result.get(BrewBrowerUtil.TYPEKEY))) {
                mimetype = "application/x-stuff";
            }

            String contentDisposition = BrewBrowerUtil.getDisposition(fvo.getOrignlFileNm(), userAgent, "UTF-8");
            response.setContentType(mimetype);
            response.setHeader("Content-Disposition", contentDisposition.replaceAll("[\\r\\n]", ""));
            response.setContentLengthLong(fSize);

            BufferedInputStream in = null;
            BufferedOutputStream out = null;
            FileInputStream fis = null;

            try {
                fis = new FileInputStream(uFile);
                in = new BufferedInputStream(fis);
                out = new BufferedOutputStream(response.getOutputStream());

                FileCopyUtils.copy(in, out);
                out.flush();

            } catch (IOException ex) {
                System.out.println("IO Exception");
            } finally {
                try {
                    out.close();
                    in.close();
                    fis.close();
                } catch (IOException e) {
                    System.out.println("IOException Occured");
                }
            }

        } else {
            response.setContentType("text/html;charset=UTF-8");

            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.println("<script type='text/javascript'>");
                out.println("alert('No file.');");
                out.println("window.history.back();");
                out.println("</script>");
                out.flush();
            } catch(IOException e) {
                System.out.println("IOException Occured");
            } finally {
                out.close();
            }

        }
    }

    public static void downloadZip(List<String> atchFileId, String zipName, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<FileVO> files = new ArrayList<>();

        for(int i = 0; i < atchFileId.size(); i++) {
            FileVO fvo = new FileVO();
            fvo.setAtchFileId(atchFileId.get(i));
            List<FileVO> fileList = fileService.selectFileInfs(fvo);
            for (int j = 0; j < fileList.size(); j++) {
                files.add(fileList.get(j));
            }
        }

        if (!files.isEmpty()) {

            try {
                String mimetype = "application/zip";
                String userAgent = request.getHeader("User-Agent");
                HashMap<String, String> result = BrewBrowerUtil.getBrowser(userAgent);
                if (!BrewBrowerUtil.MSIE.equals(result.get(BrewBrowerUtil.TYPEKEY))) {
                    mimetype = "application/x-stuff";
                }
                String contentDisposition = BrewBrowerUtil.getDisposition(URLEncoder.encode(zipName, StandardCharsets.UTF_8.toString()) + ".zip", userAgent, "UTF-8");
                response.setContentType(mimetype);
                response.setHeader("Content-Disposition", contentDisposition.replaceAll("[\\r\\n]", ""));

                OutputStream os = response.getOutputStream();

                try (ZipOutputStream zos = new ZipOutputStream(os)) {
                    for (FileVO fileVO : files) {
                        File file = new File(fileVO.getFileStrePath(), fileVO.getStreFileNm());
                        try (FileInputStream fis = new FileInputStream(file)) {
                            ZipEntry zipEntry = new ZipEntry(fileVO.getOrignlFileNm());
                            zos.putNextEntry(zipEntry);

                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = fis.read(buffer)) > 0) {
                                zos.write(buffer, 0, length);
                            }
                        }
                    }
                    zos.closeEntry();
                    zos.flush();
                }
            } catch (IOException e) {
                System.out.println("IOException 발생: " + e.getMessage());
            }
        } else {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<script type='text/javascript'>");
                out.println("alert('다운로드할 파일이 없습니다.');");
                out.println("window.history.back();");
                out.println("</script>");
                out.flush();
            }
        }
    }
}
