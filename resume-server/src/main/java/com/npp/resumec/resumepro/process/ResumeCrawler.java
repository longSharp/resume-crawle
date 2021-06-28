package com.npp.resumec.resumepro.process;

import com.npp.resumec.resumepro.pojo.Resume;
import com.npp.resumec.resumepro.pojo.ResumeCon;
import com.npp.resumec.resumepro.pojo.ResumeType;
import com.npp.resumec.resumepro.utils.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.UUID;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-09
 */
public class ResumeCrawler {

//    @Autowired
//    private ResumeTypeService resumeTypeService = SpringUtilTo.getApplicationContext().getBean(ResumeTypeService.class);
//
//    @Autowired
//    private ResumeService resumeService = SpringUtilTo.getApplicationContext().getBean(ResumeService.class);
//
//    @Autowired
//    private ResumeConService resumeConService = SpringUtilTo.getApplicationContext().getBean(ResumeConService.class);

    /**
     * 开始爬取方法
     */
    public void start(){
        System.out.println("------------------开始爬取------------------");
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
            System.out.println(resumeType);
            //保存到数据库
//            System.out.println(this.resumeTypeService);
//            resumeTypeService.save(resumeType);
            //获取该分类的跳转页面链接
            String href = element.select("a").attr("href");
            //对链接进行拼接补全
            String typeUrl = "https://sc.chinaz.com" + href;
            //发送请求
            String html = HttpClientUtil.getHtml(typeUrl);
            Document parseHtml = Jsoup.parse(html);
            //解析得到的页面
            parseContext(parseHtml,typeId);
            //获取分页栏的下一页链接
            String attr = parseHtml.getElementsByClass("nextpage").first().attr("href");
            //进行翻页, 并且解析页面
            parsePage(attr,typeId);

        }

        System.out.println("------------------爬取结束------------------");
    }

    /**
     *对分页进行循环跳转,并且解析
     * @param src 跳转的链接
     * @param typeId 类型id
     */
    public void parsePage(String src,String typeId){
        //如果传进来的链接为空, 则表示到了最后一页
        if (src == ""){
            return;
        }
        String srcTo = "https://sc.chinaz.com/tag_jianli/" + src;
        String page = HttpClientUtil.getHtml(srcTo);
        Document parseHtml = Jsoup.parse(page);
        //解析页面
        parseContext(parseHtml,typeId);
        //获取下页的跳转链接
        String attr = parseHtml.getElementsByClass("nextpage").first().attr("href");
        //进行递归请求操作
        parsePage(attr,typeId);
    }

    /**
     * 对详情页进行页面解析
     * @param html
     * @param typeId 类型id
     */
    public void parseContext(Document html,String typeId){
        //先得到该页面上所有的简历标签
        Elements nodes = html.getElementsByClass("ws_block");
        //进行遍历解析
        for (Element node : nodes) {
            //获取该简历跳转到详情页的链接
            String conUrl = node.select("a").attr("href");
            String content = HttpClientUtil.getHtml("https://sc.chinaz.com"+conUrl);
            Document contentHtml = Jsoup.parse(content);
            //获取详情页中的下载按钮
            Element select = contentHtml.getElementsByClass("ppt_tit").select("span").first();
            //如果获取到的下载按钮为空, 则该简历需付费, 不进行爬取
            if (select != null){
                //如果该简历免费,则保持简历封页的信息
                Element resumeNode = node.select("a").first().select("img").first();
                String imgUrl = resumeNode.attr("src");
                String title = resumeNode.attr("alt");
                String resumeId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                Resume resume = new Resume();
                resume.setId(resumeId);
                resume.setTitle(title);
                resume.setUrl(imgUrl);
                resume.setTypeId(typeId);
//                resumeService.save(resume);

                //获取详情页的详情图片集合
                Elements select1 = contentHtml.getElementsByClass("jl_warp").select("span");
                StringBuilder imgUrls = new StringBuilder();
                //遍历进行链接拼接
                for (Element element1 : select1) {
                    String attr = element1.getElementsByTag("img").attr("src");
                    imgUrls.append(";http:");
                    imgUrls.append(attr);
                }
                //获取详情页信息
                String conTitle = contentHtml.getElementsByClass("ppt_tit").select("h1").first().text();
                String down = contentHtml.getElementsByClass("downlist").select("ul").first().select("li").first().select("a").attr("href");
                //将获取到的详情页信息封装到对象中
                ResumeCon resumeCon = new ResumeCon();
                resumeCon.setDownloadUrl(down);
                resumeCon.setImgUrl(imgUrls.toString());
                resumeCon.setTitle(conTitle);
                resumeCon.setTId(resumeId);
                //保存到数据库中
//                resumeConService.save(resumeCon);
            }
        }
    }

}
