package cn.promptness.bing.dao;

import cn.promptness.bing.pojo.ImageDO;

import java.util.List;

/**
 * @author : Lynn
 * @date : 2019-03-29 02:00
 */
public interface ImageDao {


    ImageDO getImageById(Integer id);

    List<ImageDO> listImage();

    int saveImage(ImageDO image);

    int deleteImageById(Integer id);

    int count(String timeStr);
}
