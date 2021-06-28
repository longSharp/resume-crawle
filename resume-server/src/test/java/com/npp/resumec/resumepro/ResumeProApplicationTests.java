package com.npp.resumec.resumepro;

import com.aliyun.oss.OSSClient;
import com.npp.resumec.resumepro.utils.ElasticUtil;
import com.npp.resumec.resumepro.utils.OSSUtil;
import com.npp.resumec.resumepro.utils.ThreadPoolUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ResumeProApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void threadPoolTest(){
        ExecutorService cachedThreadPool = ThreadPoolUtil.getCachedThreadPool();
        List<String> list = new ArrayList<>();
        list.add("主题1");
        list.add("主题2");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        list.add("主题3");
        int count = 0;
        for (String s : list) {
            count++;
            String title = "主题"+count;
            cachedThreadPool.execute(()->{
                System.out.println(title+"开始执行");
                for (int i = 0; i < 10; i++) {
                    System.out.println("携程"+i+"主题是:"+title);
                }
            });
        }
    }

    @Test
    void ossTest() throws MalformedURLException {
        OSSClient ossClient = OSSUtil.getOSSClient();
        URL url = new URL("https://downsc.chinaz.net/Files/DownLoad/jianli/202105/jianli15264.rar");
        String fileUrl = OSSUtil.uploadByNetworkStream(ossClient, url, "resume-pc", "resume1/jianli15264.rar");
        System.out.println(fileUrl);
    }

    @Test
    void esTest(){

        RestHighLevelClient client = ElasticUtil.getConnect();
        System.out.println(client);
        SearchRequest resumes = new SearchRequest("resumes");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);

        //精准匹配
        MatchQueryBuilder title1 = QueryBuilders.matchQuery("title", "数据");
        searchSourceBuilder.query(title1);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(false);//是否多个高亮
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);


        //执行搜索
        SearchRequest source = resumes.source(searchSourceBuilder);
        SearchResponse search = null;
        try {
            search = client.search(source, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SearchHits hits = search.getHits();
        ArrayList<Map<String, Object>> maps = new ArrayList<>();

        for (SearchHit hit : hits.getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField n_title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();//原来的结果
            sourceAsMap.put("id",hit.getId());
            //解析高亮的字段,将原来的字段换为高亮的字段即可
            if (n_title!=null){
                Text[] fragments = n_title.fragments();
                String newTitle="";
                for (Text text : fragments) {
                    newTitle += text;
                }
                sourceAsMap.put("title",newTitle);
            }
            maps.add(sourceAsMap);
        }

        System.out.println(maps);
    }

}
