package cn.wanghaomiao.crawlers;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.xpath.model.JXDocument;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * @author 汪浩淼 [et.tw@163.com]
 * @since 2015/10/21.
 */
@Crawler(name = "basic")
public class Basic extends BaseSeimiCrawler {
  private 	String  host="http://192.168.100.251:8090";
    @Override
    public String[] startUrls() {
        //两个是测试去重的 http://192.168.100.251:8090/
        //return new String[]{"http://www.cnblogs.com/","http://www.cnblogs.com/"};
        //https://www.qqtn.com/article/article_146536_1.html
    	//http://www.win4000.com/zt/lol.html
        //return new String[]{"http://192.168.100.251:8090/"};
        //return new String[]{"https://www.qqtn.com/article/article_146536_1.html"};
    	//http://1024.917rbb.com/pw/thread.php?fid=14
        return new String[]{"http://1024.917rbb.com/pw/thread.php?fid=14"};
    }

    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        try {
            //List<Object> urls = doc.sel("//a[@class='titlelnk']/@href");
        	String pre=generateInte(new URL(response.getRealUrl()));
        	//List<Object> urls = doc.sel("//ul//img/@src");
        	
        	//List<Object> html = doc.sel("//ul//a/@href");
        	List<Object> html = doc.sel("//h3//a/@href");
        	
           /* logger.info("{}", urls.size());
            for (Object s:urls){
            	String ss=(String)s;
            	if(ss.indexOf("//")==-1)
            		ss=pre+ss;
            	//System.out.println(s);
                push(new Request(ss,"getTitle"));
            }*/
            
            for (Object s:html){
            	String ss=(String)s;
            	if(ss.indexOf("//")==-1)
            		ss=pre+"/pw/"+ss;
            	//System.out.println(ss);
            	if(ss.indexOf("php")==-1)
            	{System.out.println(ss);
                 push(new Request(ss,"getHtml"));
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String generateInte(URL url) {
		// TODO Auto-generated method stub
    	String  inte=url.getProtocol()+"://"+url.getHost();
    	if(url.getPort()!=-1)
    		inte=inte+":"+url.getPort();
    	
		return inte;
	}

	public void getTitle(Response response){
    	String dir="D:/picture/1024";
    	String url=response.getUrl();
    	System.out.println();
    	String filename=url.substring(url.lastIndexOf("/")+1);
    	//System.out.println(filename);
    	File ff=new File(dir,filename);
    	
    	response.saveTo(ff);
    	if(ff.length()<1024*100)
    		ff.delete();
    	//System.out.println(ff.length()+"----"+filename);
    	//System.out.println(filename +" save done");
    	/*response.saveTo(targetFile);
        JXDocument doc = response.document();
        try {
            logger.info("url:{} {}", response.getUrl(), doc.sel("*"));
            //do something
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
	
	public void getHtml(Response response){
		 JXDocument doc = response.document();
	        try {
	            //List<Object> urls = doc.sel("//a[@class='titlelnk']/@href");
	        	String pre=generateInte(new URL(response.getRealUrl()));
	        	//List<Object> urls = doc.sel("//img/@src");
	        	List<Object> urls = doc.sel("//tbody//img/@src");
	        	//List<Object> html = doc.sel("//a/@herf");
	            logger.info("{}", urls.size());
	            for (Object s:urls){
	            	String ss=(String)s;
	            	if(ss.indexOf("//")==-1)
	            		ss=pre+ss;
	            	//System.out.println(s);
	                push(new Request(ss,"getTitle"));
	            }
	            
	           /* for (Object s:html){
	            	String ss=(String)s;
	            	if(ss.indexOf("//")==-1)
	            		ss=pre+ss;
	            	//System.out.println(s);
	                push(new Request(ss,"getHtml"));
	            }*/
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
}
