package cn.qs.action;

import cn.qs.dao.InfluxdbDao;
import cn.qs.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqs on 16/11/2.
 */
@Controller
public class AppAction {

    private String grafanaUrl = Config.getGrafanaUrl();

    @RequestMapping("/home/applist")
    public ModelAndView getApplist(){
        ModelAndView mv = new ModelAndView("metrics");
        List<String> applist = InfluxdbDao.queryAppId();
        mv.addObject("applist",applist);
        mv.addObject("grafana_url",grafanaUrl);
        return mv;
    }


}
