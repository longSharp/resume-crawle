package com.npp.resumec.resumepro.service.impl;

import com.npp.resumec.resumepro.dao.ResumeDao;
import com.npp.resumec.resumepro.pojo.Resume;
import com.npp.resumec.resumepro.service.ResumeService;
import com.npp.resumec.resumepro.utils.ElasticUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-08
 */
@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeDao resumeDao;

    @Override
    public void save(Resume resume) {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replace("-","").toLowerCase();
        resume.setId(s);
        this.resumeDao.save(resume);
    }


    @Override
    public List<Resume> findAll() {
        return this.resumeDao.findAll();
    }

    @Override
    public List<Resume> findByTypeId(String typeId) {
        return this.resumeDao.findByTypeId(typeId);
    }

    @Override
    public ArrayList<Map<String, Object>> findByTitle(String title,int page,int limit) {
        RestHighLevelClient client = ElasticUtil.getConnect();
        SearchRequest resumes = new SearchRequest("resumes");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页
        searchSourceBuilder.from(page);
        searchSourceBuilder.size(limit);

        //精准匹配
        MatchQueryBuilder title1 = QueryBuilders.matchQuery("title",title);
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
        ElasticUtil.close(client);
        return maps;
    }

    @Override
    public List<Resume> findByPage(int page, int limit) {
        return this.resumeDao.findByPage(page,limit);
    }

    @Override
    public List<Resume> findByPage(int page, int limit, String typeId) {
        return this.resumeDao.findByPageTo(page,limit,typeId);
    }

    @Override
    public int findByTitleToPage(String title) {
        int size = resumeDao.findByTitleToPage(title);
        return size;
    }
}
