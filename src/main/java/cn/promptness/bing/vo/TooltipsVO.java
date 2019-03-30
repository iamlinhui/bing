package cn.promptness.bing.vo;

import com.alibaba.fastjson.JSON;

/**
 * @author : Lynn
 * @date : 2019-03-29 01:40
 */
public class TooltipsVO {

    private String loading;
    private String previous;
    private String next;
    private String walle;
    private String walls;

    public String getLoading() {
        return loading;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getWalle() {
        return walle;
    }

    public void setWalle(String walle) {
        this.walle = walle;
    }

    public String getWalls() {
        return walls;
    }

    public void setWalls(String walls) {
        this.walls = walls;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
