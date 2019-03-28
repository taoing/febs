package com.taoing.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.taoing.common.properties.FebsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

/**
 * 七牛云Oss 工具类
 */
@Component
public class QiniuOssUtil {

    private static Logger logger = LoggerFactory.getLogger(QiniuOssUtil.class);

    // 注入静态field ?
    @Autowired
    private static FebsProperties properties;

    public static String upload(FileInputStream file, String fileName) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);

        try {
            Auth auth = Auth.create(properties.getOss().getQiniu().getAccessKey(),
                    properties.getOss().getQiniu().getSecretKey());
            String upToken = auth.uploadToken(properties.getOss().getQiniu().getBucket());
            try {
                Response response = uploadManager.put(file, fileName, upToken, null, null);
                // 解析上传成功的结果, 使用Gson反序列化
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                return properties.getOss().getQiniu().getPath() + "/" + putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                logger.error(r.toString());
                try {
                    logger.error(r.bodyString());
                } catch (QiniuException ignore) {

                }
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
        return null;
    }
}
