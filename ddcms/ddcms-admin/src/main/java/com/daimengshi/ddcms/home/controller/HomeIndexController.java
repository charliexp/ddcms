package com.daimengshi.ddcms.home.controller;

import com.daimengshi.ddcms.service.api.DmsArticleService;
import com.daimengshi.ddcms.service.entity.model.DmsArticle;
import com.daimengshi.ddcms.service.provider.DmsArticleServiceImpl;
import com.daimengshi.ddcms.utils.ServiceUtils;
import com.jfinal.plugin.activerecord.Page;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import io.jboot.web.controller.JbootController;
import io.jboot.web.controller.annotation.RequestMapping;

import java.util.List;

/**
 * Created by zhoufeng on 2017/12/21.
 * 前台首页
 */
@RequestMapping("/")
public class HomeIndexController extends JbootController {
    private static final Log log = LogFactory.get();

    private DmsArticleService articleService;

    public HomeIndexController() {
        this.articleService = ServiceUtils.getService(DmsArticleServiceImpl.class);
    }


    public void index() {

        List<DmsArticle> topArticleList = articleService.getTopArticleList();//获取所有置顶文章

        //获得所有不置顶文章分页
        Page<DmsArticle> articlePage = articleService.getDao().paginate(
                1,
                20,
                "select *",
                "FROM  dms_article WHERE dms_article.is_top = 'off' GROUP BY dms_article.create_time desc");
        setAttr("topArticleList", topArticleList);//所有置顶文章
        setAttr("articlePage", articlePage);//文章分页

        setAttr("leftTP", "/htmls/home/layui/index/left.html"); //左边内容模板
        setAttr("rightTP", "/htmls/home/layui/index/right.html"); //右边内容模板

        render("/htmls/home/layui/lr_global.html");
    }

}
