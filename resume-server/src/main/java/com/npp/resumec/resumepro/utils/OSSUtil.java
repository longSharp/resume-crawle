package com.npp.resumec.resumepro.utils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author 龙朝敏
 */
public class OSSUtil {

    private static ClientConfiguration conf = null;

    static {
        conf = new ClientConfiguration();
        conf.setConnectionTimeout(1000*60);
        conf.setMaxConnections(10000);
        conf.setMaxErrorRetry(4);
    }

    /**
     *
     * @Title: getOSSClient
     * @Description: 获取oss客户端
     * @return OSSClient oss客户端
     * @throws
     */
    public static OSSClient getOSSClient() {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            //使用对应的endpoint地址
            String endpoint = properties.getProperty("aliyun.oss.endpoint");

            // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议创建并使用RAM账号进行API访问或日常运维，请登录https://ram.console.aliyun.com 创建RAM账号。
            String accessKeyId = properties.getProperty("aliyun.oss.accessKeyId");

            String accessKeySecret = properties.getProperty("aliyun.oss.accessKeySecret");

            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret,conf);

            return ossClient;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     *
     * @Title: uploadByNetworkStream
     * @Description: 通过网络流上传文件
     * @param ossClient 	oss客户端
     * @param url 			URL
     * @param bucketName 	bucket名称
     * @param objectName 	上传文件目录和（包括文件名）例如“test/index.html”
     * @return String 		返回存储后文件的路径
     * @throws
     */
    public static String uploadByNetworkStream(OSSClient ossClient, URL url, String bucketName, String objectName) {
        String fileUrl = null;
        try {
            InputStream inputStream = url.openStream();
            ossClient.putObject(bucketName, objectName, inputStream);
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            URL url1 = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
            fileUrl = url1.toString().split("\\?")[0];
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return fileUrl;
    }

    /**
     *
     * @Title: uploadByInputStream
     * @Description: 通过输入流上传文件
     * @param ossClient 	oss客户端
     * @param inputStream 	输入流
     * @param bucketName 	bucket名称
     * @param objectName 	上传文件目录和（包括文件名） 例如“test/a.jpg”
     * @return void 		返回类型
     * @throws
     */
    public static void uploadByInputStream(OSSClient ossClient, InputStream inputStream, String bucketName,
                                           String objectName) {
        try {
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     *
     * @Title: uploadByFile
     * @Description: 通过file上传文件
     * @param ossClient 	oss客户端
     * @param file 			上传的文件
     * @param bucketName 	bucket名称
     * @param objectName 	上传文件目录和（包括文件名） 例如“test/a.jpg”
     * @return void 		返回类型
     * @throws
     */
    public static void uploadByFile(OSSClient ossClient, File file, String bucketName, String objectName) {
        try {
            ossClient.putObject(bucketName, objectName, file);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     *
     * @Title: deleteFile
     * @Description: 根据key删除oss服务器上的文件
     * @param ossClient		oss客户端
     * @param bucketName		bucket名称
     * @param key    		文件路径/名称，例如“test/a.txt”
     * @return void    		返回类型
     * @throws
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String key) {
        ossClient.deleteObject(bucketName, key);
    }

    /**
     *
     * @Title: getInputStreamByOSS
     * @Description:根据key获取服务器上的文件的输入流
     * @param ossClient 	oss客户端
     * @param bucketName 	bucket名称
     * @param key 			文件路径和名称
     * @return InputStream 	文件输入流
     * @throws
     */
    public static InputStream getInputStreamByOSS(OSSClient ossClient, String bucketName, String key) {
        InputStream content = null;
        try {
            OSSObject ossObj = ossClient.getObject(bucketName, key);
            content = ossObj.getObjectContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     *
     * @Title: queryAllObject
     * @Description: 查询某个bucket里面的所有文件
     * @param ossClient		oss客户端
     * @param bucketName		bucket名称
     * @return List<String>  文件路径和大小集合
     * @throws
     */
    public static List<String> queryAllObject(OSSClient ossClient, String bucketName) {
        List<String> results = new ArrayList<String>();
        try {
            // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            // objectListing.getObjectSummaries获取所有文件的描述信息。
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                results.add(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return results;
    }


}
