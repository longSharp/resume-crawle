package com.npp.resumec.resumepro.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-25
 */
public class ElasticUtil {

    public static RestHighLevelClient getConnect(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.2.120", 9200, "http"))
        );
        return client;
    }

    public static void close(RestHighLevelClient client){
        if (client!=null){
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
