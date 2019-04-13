package cn.promptness.bing.controller;


import cn.promptness.bing.pojo.ImageDO;
import cn.promptness.bing.service.ImageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ImageController {


    @Autowired
    private ImageService imageService;

    @GetMapping(value = {"/index.html", "/"})
    public String toIndex(Model model) {
        PageInfo<ImageDO> pageInfo = imageService.getImagesForPage(1, 12, 5);
        model.addAttribute(pageInfo);
        return "index";
    }

    @GetMapping(value = {"/images/{pageNo:[0-9]*}","/images"})
    public String pageForImage(@PathVariable(required = false) Integer pageNo, Model model) {
        PageInfo<ImageDO> pageInfo = imageService.getImagesForPage(Optional.ofNullable(pageNo).orElse(0), 12, 5);
        model.addAttribute(pageInfo);
        return "index";
    }

    @GetMapping(value = "/image/{id:[0-9]*}")
    public String infoImage(@PathVariable Integer id, Model model) {
        ImageDO image = imageService.getImageById(id);
        if (image == null) {
            return "redirect:/index.html";
        }
        model.addAttribute(image);
        return "detail";
    }

}
