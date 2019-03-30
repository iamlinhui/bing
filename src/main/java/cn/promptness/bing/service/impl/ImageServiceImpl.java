package cn.promptness.bing.service.impl;


import cn.promptness.bing.dao.ImageDao;
import cn.promptness.bing.pojo.ImageDO;
import cn.promptness.bing.service.ImageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Resource
    private ImageDao imageDao;

    @Override
    public ImageDO getImageById(Integer id) {
        return imageDao.getImageById(id);
    }

    @Override
    public PageInfo<ImageDO> getImagesForPage(Integer pageNo, Integer pageSize, Integer pageNoSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<ImageDO> imageDOList = imageDao.listImage();
        return new PageInfo<>(imageDOList, pageNoSize);
    }

    @Override
    public int saveImage(ImageDO image) {
        return imageDao.saveImage(image);
    }

    @Override
    public int deleteImageById(Integer id) {
        return imageDao.deleteImageById(id);
    }

    @Override
    public boolean isExist(Date time) {
        String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(time);
        return imageDao.count(timeStr) > 0;
    }


}
