package cn.promptness.bing.vo;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author : Lynn
 * @date : 2019-03-29 01:41
 */
public class DataVO {

    private List<ImageVO> images;

    private TooltipsVO tooltips;

    public List<ImageVO> getImages() {
        return images;
    }

    public void setImages(List<ImageVO> images) {
        this.images = images;
    }

    public TooltipsVO getTooltips() {
        return tooltips;
    }

    public void setTooltips(TooltipsVO tooltips) {
        this.tooltips = tooltips;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
