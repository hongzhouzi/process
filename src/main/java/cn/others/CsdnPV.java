/*
package cn.others;

import com.sun.deploy.net.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsdnPV {
    private String username;
    public CsdnPV( String name) {
        this.username = name;
    }

    */
/*
     * 获取当前用户文章的总页数
     *//*

    public int getPageNum() throws Exception {
        int num = 0;
        String url = "http://blog.csdn.net/" + username; // 博客主页
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            java.io.InputStream is = entity.getContent();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            String pattern = "<span>.*?条数据.*?共(.*?)页</span>"; // 获取总页数
            Pattern r = Pattern.compile(pattern);
            while ((info = br.readLine()) != null) { // 循环读取页面信息
                // System.out.println(info);
                Matcher m = r.matcher(info);
                if (m.find()) {
                    num = Integer.parseInt(m.group(1));
                    break;
                }
            }
        }
        return num;
    }

    */
/*
     * 获取第 num 页所有文章的url和标题
     *//*

    public Map<String, String> getArticleByPage(int num) throws Exception {
        Map<String, String> articles = new HashMap<String, String>();
        String url = "http://blog.csdn.net/" + username + "/article/list/"
                + num; // 第num页文章
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String content = ""; // 保存整个网页的内容
        if (entity != null) {
            java.io.InputStream is = entity.getContent();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) { // 循环读取客户端的信息
                content = content + info + "\n";
            }
            // System.out.println(content);
        }
        // 获取文章url和标题
        String pattern = "<span class=\"link_title\"><a href=\"/" + username
                + "/article/details/(.*?)\">" + "\n(.*?)\n";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        while (m.find()) {
            String id = m.group(1);
            String articleURL = "http://blog.csdn.net/" + username + "/article/details/" + id;
            String title = m.group(2);
            title = title.trim();
            articles.put(articleURL, title);
        }
        return articles;
    }

    */
/*
     * 刷所有文章num次
     *//*

    public void visitAll(int num) {
        int pageNum = 0;
        try {
            pageNum = getPageNum(); // 文章的总页数
            for (int i = 1; i <= pageNum; i++) {  //每页一个线程去刷
                Map<String, String> articles = getArticleByPage(i);
                VisitThread vt = new VisitThread(articles, num);
                vt.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CsdnPV b = new CsdnPV("cighao");
        b.visitAll(1);
        //b.visitOne("Java 输入/输出流", 1);;
        while(true);
    }
}
*/
