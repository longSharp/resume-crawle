package com.npp.resumec.resumepro.service.impl;

import com.aliyun.oss.OSSClient;
import com.npp.resumec.resumepro.dao.ResumeConDao;
import com.npp.resumec.resumepro.dao.ResumeDao;
import com.npp.resumec.resumepro.dao.ResumeTypeDao;
import com.npp.resumec.resumepro.pojo.Resume;
import com.npp.resumec.resumepro.pojo.ResumeCon;
import com.npp.resumec.resumepro.pojo.ResumeType;
import com.npp.resumec.resumepro.service.ResumeCrawlerService;
import com.npp.resumec.resumepro.utils.HttpClientUtil;
import com.npp.resumec.resumepro.utils.OSSUtil;
import com.npp.resumec.resumepro.utils.ThreadPoolUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-09
 */
@Service
public class ResumeCrawlerServiceImpl implements ResumeCrawlerService {

    private Logger logger = Logger.getLogger(ResumeCrawlerServiceImpl.class);

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private ResumeConDao resumeConDao;

    @Autowired
    private ResumeTypeDao resumeTypeDao;

    @Override
    public int start() {
        logger.info("------------------开始爬取------------------");
        ExecutorService cachedThreadPool = ThreadPoolUtil.getCachedThreadPool();
        //发送初始请求
        String typeHtml = HttpClientUtil.getHtml("https://sc.chinaz.com/jianli/");
        //解析请求返回的页面
        Document parse = Jsoup.parse(typeHtml);
        //获取页面中的分类盒子
        Element right = parse.getElementsByClass("jl_navbar_right").first();
        //获取分类标签
        Elements li = right.getElementsByTag("li");

        //遍历分类集合标签
        for (Element element : li) {
            //创建类型对象
            ResumeType resumeType = new ResumeType();
            //生成一个类型数据id
            String typeId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            //获取类型名称
            String title = element.select("a").text();
            //设置对象属性
            resumeType.setId(typeId);
            resumeType.setTypeName(title);
            System.out.println("========主题--" + title + "--开始下载========");
            //保存到数据库
            resumeTypeDao.save(resumeType);
            //获取该分类的跳转页面链接
            String href = element.select("a").attr("href");
            //对链接进行拼接补全
            String typeUrl = "https://sc.chinaz.com" + href;
            //发送请求
            String html = HttpClientUtil.getHtml(typeUrl);
            Document parseHtml = Jsoup.parse(html);
            //解析得到的页面
            parseContext(parseHtml, typeId,cachedThreadPool);
            //如果获取页标为空, 则当前类没有数据
            Element nextpage = parseHtml.getElementsByClass("nextpage").first();
            if (nextpage != null) {
                //获取分页栏的下一页链接
                String attr = nextpage.attr("href");
                //进行翻页, 并且解析页面
                parsePage(attr, typeId, cachedThreadPool, title);
            }

        }

        logger.info("------------------爬取结束------------------");
        return 1;
    }

    @Override
    public int ossStart() {
        logger.info("------------------开始爬取------------------");
        ExecutorService cachedThreadPool = ThreadPoolUtil.getCachedThreadPool();
        //发送初始请求
        String typeHtml = HttpClientUtil.getHtml("https://sc.chinaz.com/jianli/");
        //解析请求返回的页面
        Document parse = Jsoup.parse(typeHtml);
        //获取页面中的分类盒子
        Element right = parse.getElementsByClass("jl_navbar_right").first();
        //获取分类标签
        Elements li = right.getElementsByTag("li");

        //遍历分类集合标签
        for (Element element : li) {
                //创建类型对象
                ResumeType resumeType = new ResumeType();
                //生成一个类型数据id
                String typeId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                //获取类型名称
                String title = element.select("a").text();
                //设置对象属性
                resumeType.setId(typeId);
                resumeType.setTypeName(title);
                //保存到数据库
                resumeTypeDao.save(resumeType);
                //获取该分类的跳转页面链接
                String href = element.select("a").attr("href");
                //对链接进行拼接补全
                String typeUrl = "https://sc.chinaz.com" + href;
                //发送请求
                String html = HttpClientUtil.getHtml(typeUrl);
                Document parseHtml = Jsoup.parse(html);
                //解析得到的页面
                parseContext(parseHtml, typeId,cachedThreadPool);
                //如果获取页标为空, 则当前类没有数据
                Element nextpage = parseHtml.getElementsByClass("nextpage").first();
                if (nextpage != null) {
                    //获取分页栏的下一页链接
                    String attr = nextpage.attr("href");
                    //进行翻页, 并且解析页面
                    parsePage(attr, typeId, cachedThreadPool, title);
                }

        }

        logger.info("------------------爬取结束------------------");
        return 1;
    }

    /**
     * 对分页进行循环跳转,并且解析
     *
     * @param src    跳转的链接
     * @param typeId 类型id
     */
    public void parsePage(String src, String typeId, ExecutorService thread, String t) {
//        thread.execute(() -> {
            //如果传进来的链接为空, 则表示到了最后一页
            if (src == "") {
                System.out.println("==============主题"+t+"下载完毕==============");
                System.out.println();
                return;

            }
            int currentPage = Integer.parseInt(src.split("_")[1].split("\\.")[0]);
            String srcTo = "https://sc.chinaz.com/tag_jianli/" + src;
            String page = HttpClientUtil.getHtml(srcTo);
            Document parseHtml = Jsoup.parse(page);
            //解析页面
            System.out.println("------主题" + t + ":正在下载第" + (currentPage - 1) + "页------");
            parseContext(parseHtml, typeId,thread);
            Element nextpage = parseHtml.getElementsByClass("nextpage").first();
            if (nextpage != null) {
                //获取下页的跳转链接
                String attr = nextpage.attr("href");

                //进行递归请求操作
                parsePage(attr, typeId, thread, t);
            }
//        });

    }

    /**
     * 对详情页进行页面解析
     *
     * @param html
     * @param typeId 类型id
     */
    public void parseContext(Document html, String typeId,ExecutorService t) {
        //先得到该页面上所有的简历标签
        Elements nodes = html.getElementsByClass("ws_block");
        int size = nodes.size();
        int count = 0;
        //进行遍历解析
        for (Element node : nodes) {
            count++;
            double bf =count/size;
            int shuff = (int) (Math.ceil(bf)*100);
            if (shuff==100){
                System.out.println("正在下载:"+shuff+"%");
            }
            //获取该简历跳转到详情页的链接
            String conUrl = node.select("a").attr("href");
            String content = HttpClientUtil.getHtml("https://sc.chinaz.com" + conUrl);
            Document contentHtml = Jsoup.parse(content);
            //获取详情页中的下载按钮
            Element select = contentHtml.getElementsByClass("ppt_tit").select("span").first();
            //如果获取到的下载按钮为空, 则该简历需付费, 不进行爬取
            if (select != null) {
                //如果该简历免费,则保持简历封页的信息
                Element resumeNode = node.select("a").first().select("img").first();
                String imgUrl = "https:" + resumeNode.attr("src");
                String[] sr = imgUrl.split("/");
                String fileName = sr[sr.length - 1];
                OSSClient ossClient = OSSUtil.getOSSClient();
                URL url = null;
                try {
                    url = new URL(imgUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } finally {
                    String fileUrl = OSSUtil.uploadByNetworkStream(ossClient, url, "resume-pc", "resume/img/" + fileName);
                    String title = resumeNode.attr("alt");
                    String resumeId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                    Resume resume = new Resume();
                    resume.setId(resumeId);
                    resume.setTitle(title);
                    resume.setUrl(fileUrl);
                    resume.setTypeId(typeId);
                    resumeDao.save(resume);
                    //多线程下载简历包
                    t.execute(()->{
                        //获取详情页的详情图片集合
                        Elements select1 = contentHtml.getElementsByClass("jl_warp").select("span");
                        StringBuilder stringBuilder = new StringBuilder();
                        //遍历进行链接拼接
                        for (Element element1 : select1) {
                            String attr = element1.getElementsByTag("img").attr("src");
                            String imgUrl1 = "https:" + attr;
                            String[] sr1 = attr.split("/");
                            String fileName1 = sr1[sr1.length - 1];
                            String fileDic = sr1[sr1.length - 2];
                            OSSClient ossClient1 = OSSUtil.getOSSClient();
                            URL url1 = null;
                            try {
                                url1 = new URL(imgUrl1);
                                String fileUrl1 = OSSUtil.uploadByNetworkStream(ossClient1, url1, "resume-pc", "resume/img/" + fileDic+"/"+fileName1);
                                stringBuilder.append(";");
                                stringBuilder.append(fileUrl1);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }

                        }
                        //获取详情页信息
                        String conTitle = contentHtml.getElementsByClass("ppt_tit").select("h1").first().text();
                        String down = contentHtml.getElementsByClass("downlist").select("ul").first().select("li").first().select("a").attr("href").replaceAll("\\s*", "");
                        String[] downArr = down.split("/");
                        String fileName2 = downArr[downArr.length - 1];
                        OSSClient ossClient2 = OSSUtil.getOSSClient();
                        URL url2 = null;
                        try {
                            url2 = new URL(down);
                            String fileUrl2 = OSSUtil.uploadByNetworkStream(ossClient2, url2, "resume-pc", "resume/resuemFile/" + fileName2);
                            //将获取到的详情页信息封装到对象中
                            ResumeCon resumeCon = new ResumeCon();
                            resumeCon.setDownloadUrl(fileUrl2);
                            resumeCon.setImgUrl(stringBuilder.toString());
                            resumeCon.setTitle(conTitle);
                            resumeCon.setTId(resumeId);
                            //保存到数据库中
                            resumeConDao.save(resumeCon);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    });
                }

            }
        }
    }
}
