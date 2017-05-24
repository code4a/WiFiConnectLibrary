package com.code4a.wificonnectlibrary.bean;

import java.util.List;

/**
 * Created by code4a on 2017/5/24.
 */

public class GankIoBean {

    /**
     * error : false
     * results : [{"_id":"591685fd421aa91c92f77668","createdAt":"2017-05-13T12:05:17.418Z","desc":"不要再新建 View 来画分隔符了，用 Drawable 吧","images":["http://img.gank.io/be9e2e9b-65ec-41ce-9b23-43b0c904997e"],"publishedAt":"2017-05-23T11:14:05.141Z","source":"web","type":"Android","url":"https://github.com/nekocode/DividerDrawable","used":true,"who":"nekocode"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 591685fd421aa91c92f77668
         * createdAt : 2017-05-13T12:05:17.418Z
         * desc : 不要再新建 View 来画分隔符了，用 Drawable 吧
         * images : ["http://img.gank.io/be9e2e9b-65ec-41ce-9b23-43b0c904997e"]
         * publishedAt : 2017-05-23T11:14:05.141Z
         * source : web
         * type : Android
         * url : https://github.com/nekocode/DividerDrawable
         * used : true
         * who : nekocode
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
