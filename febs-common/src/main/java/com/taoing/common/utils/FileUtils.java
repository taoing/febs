package com.taoing.common.utils;

import com.taoing.common.domain.EnumOss;
import com.taoing.common.domain.ResponseBo;
import com.taoing.common.utils.poi.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class FileUtils {

    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {

    }

    /**
     * 文件名加UUID
     *
     * @param filename 文件名
     * @return UUID_文件名
     */
    private static String makeFileName(String filename) {
        return UUID.randomUUID().toString() + "_" + filename;
    }

    /**
     * 文件名特殊字符过滤
     * @param filename 文件名
     * @return 过滤后的文件名
     * @throws PatternSyntaxException 正则异常
     */
    public static String stringFilter(String filename) {
        String regex = "[`~!@#$%^&*+=|{}':; ,/\\[\\]<>?！￥…（）【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(filename);
        return m.replaceAll("").trim();
    }

    /**
     * 生成Excel文件
     *
     * @param filename 文件名称
     * @param list 文件内容List
     * @param clazz List中对象类型
     * @return ResponseBo
     */
    public static ResponseBo createExcelByPOIKit(String filename, List<?> list,
                                                 Class<?> clazz) {
        if (list.isEmpty()) {
            return ResponseBo.warn("导出数据为空");
        } else {
            boolean operateSign = false;
            String fileName = filename + ".xlsx";
            fileName = makeFileName(fileName);
            try {
                File fileDir = new File("file");
                if (!fileDir.exists()) {
                    fileDir.mkdir();
                }
                String path = "file/" + fileName;
                FileOutputStream fos;
                fos = new FileOutputStream(path);
                // 构建工具类对象, 导出Excel文件
                operateSign = ExcelUtils.builder(clazz)
                        // 设置每个sheet的最大记录数, 默认10000, 可不设置
                        // .setMaxSheetRecoreds(10000)
                        .toExcel(list, "查询结果", fos);
            } catch (FileNotFoundException e) {
                log.error("文件未找到", e);
            }
            if (operateSign) {
                return ResponseBo.ok(fileName);
            } else {
                return ResponseBo.error("导出Excel失败, 请联系网站管理员!");
            }
        }
    }

    /**
     * 生成csv文件
     *
     * @param filename 文件名称
     * @param list 文件内容list
     * @param clazz list中的对象类型
     * @return ResponseBo
     */
    public static ResponseBo createCsv(String filename, List<?> list, Class<?> clazz) {
        if (list.isEmpty()) {
            return ResponseBo.warn("导出数据为空!");
        } else {
            boolean operateSign;
            String fileName = filename + ".csv";
            fileName = makeFileName(fileName);

            File fileDir = new File("file");
            if (!fileDir.exists())
                fileDir.mkdir();
            String path = "file/" + fileName;
            operateSign = ExcelUtils.builder(clazz)
                    .toCsv(list, path);
            if (operateSign) {
                return ResponseBo.ok(fileName);
            } else {
                return ResponseBo.error("导出Csv失败, 请联系网站管理员!");
            }
        }
    }

    /**
     * 文件上传
     *
     * @param file 文件流
     * @param randomFileName 是否使用随机文件名
     * @param oss 对象存储
     * @param savePath 文件本地保存路径
     * @return ResponseBo
     * @throws IOException
     */
    public static ResponseBo uploadFile(MultipartFile file, Boolean randomFileName,
                                        Enum oss, String savePath) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        String fileName = file.getOriginalFilename();

        // String localPath = savePath;

        if (randomFileName && StringUtils.isNotBlank(fileName)) {
            fileName = UUID.randomUUID().toString() +"." +
                    fileName.substring(fileName.lastIndexOf(".") + 1);
        }

        if (oss == EnumOss.LOCAL) {
            try {
                String defaultPath = ResourceUtils.getURL("classpath:static").getPath() + "/upload/";
                if (!new File(defaultPath).exists())
                    new File(defaultPath).mkdir();

                File dest = new File(StringUtils.isNotBlank(savePath) ? savePath : defaultPath + fileName);
                file.transferTo(dest); // 保存到指定文件
                resultMap.put("path", dest.getAbsolutePath());
            } catch (IOException e) {
                log.error("文件上传到{}失败:{}", oss, e);
                throw new IOException("文件上传失败");
            }
        } else if (oss == EnumOss.QINIU) {
            try {
                String path = QiniuOssUtil.upload((FileInputStream) file.getInputStream(), fileName);
                resultMap.put("path", path);
            } catch (IOException e) {
                log.error("文件上传到{}失败:{}", oss, e);
                throw new IOException("文件上传失败");
            }
        } else if (oss == EnumOss.ALIYUN) {
            // TODO 根据需求拓展
        } else {
            // TODO 根据需求拓展
        }

        return ResponseBo.ok(resultMap);
    }
}
