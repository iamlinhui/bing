package cn.promptness.bing.service;


import cn.promptness.bing.pojo.ImageDO;
import com.github.pagehelper.PageInfo;

import java.util.Date;


public interface ImageService {

    ImageDO getImageById(Integer id);

    PageInfo<ImageDO> getImagesForPage(Integer pageNo, Integer pageSize, Integer pageNoSize);

    int saveImage(ImageDO image);

    int deleteImageById(Integer id);

    boolean isExist(Date time);

}
