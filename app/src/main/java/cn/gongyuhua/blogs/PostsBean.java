package cn.gongyuhua.blogs;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class PostsBean {
    /**
     * id : 442
     * date : 2017-04-16T15:45:49
     * slug : %e5%ae%89%e5%8d%93%e6%ac%a2%e8%bf%8e%e7%95%8c%e9%9d%a2%e3%80%81%e5%90%af%e5%8a%a8%e7%94%bb%e9%9d%a2%e5%ae%9e%e7%8e%b0
     * type : post
     * link : http://www.gongyuhua.cn/2017/04/16/442.html
     * title : {"rendered":"安卓欢迎界面、启动画面实现"}
     * excerpt : {"rendered":"<p>&nbsp; &nbsp; 一般安卓APP启动时，都会有一个启动画面（Splash Screen）或者说欢迎画 [&hellip;]<\/p>\n","protected":false}
     * author : 1
     */

    private String id;
    private String date;
    private String slug;
    private String type;
    private String link;
    private TitleBean title;
    private ExcerptBean excerpt;
    private int author;

    public static PostsBean objectFromData(String str) {

        return new Gson().fromJson(str, PostsBean.class);
    }

    public String getId() {
        return id;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public TitleBean getTitle() {
        return title;
    }

    public void setTitle(TitleBean title) {
        this.title = title;
    }

    public ExcerptBean getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(ExcerptBean excerpt) {
        this.excerpt = excerpt;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public static class TitleBean {
        /**
         * rendered : 安卓欢迎界面、启动画面实现
         */

        private String rendered;

        public static TitleBean objectFromData(String str) {

            return new Gson().fromJson(str, TitleBean.class);
        }

        public String getRendered() {
            return rendered;
        }

        public void setRendered(String rendered) {
            this.rendered = rendered;
        }
    }

    public static class ExcerptBean {
        /**
         * rendered : <p>&nbsp; &nbsp; 一般安卓APP启动时，都会有一个启动画面（Splash Screen）或者说欢迎画 [&hellip;]</p>

         * protected : false
         */

        private String rendered;
        @SerializedName("protected")
        private boolean protectedX;

        public static ExcerptBean objectFromData(String str) {

            return new Gson().fromJson(str, ExcerptBean.class);
        }

        public String getRendered() {
            return rendered;
        }

        public void setRendered(String rendered) {
            this.rendered = rendered;
        }

        public boolean isProtectedX() {
            return protectedX;
        }

        public void setProtectedX(boolean protectedX) {
            this.protectedX = protectedX;
        }
    }
}
