package cn.qs.action;

import cn.qs.bean.*;
import cn.qs.dao.InfluxdbDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by wqs on 16/11/3.
 */
@Controller
public class UsedStatisticsAction {

    @RequestMapping("/home/usedStatistics")
    public ModelAndView  index(){

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        ModelAndView mv = new ModelAndView("usedStatistics");
        List<TotalCore> totalCores = InfluxdbDao.queryTotalCore();
        List<Memory> memories = InfluxdbDao.queryMemory();
        List<SubmitCount> submitCounts =   InfluxdbDao.querySubmitCount();
        List<SubmitMemory> submitMemories = InfluxdbDao.querySubmitMomery();
        List<SubmitCore> submitcores = InfluxdbDao.querySubmitCore();


        int size = totalCores.size();
        for(int i=0;i<size;i++){
            if(i==size-1)
                buffer.append(totalCores.get(i));
            else
                buffer.append(totalCores.get(i)).append(",");
        }
        buffer.append("]");
        mv.addObject("totalCores",buffer.toString());

        StringBuffer buffer1 = new StringBuffer();
        buffer1.append("[");
        size = memories.size();
        for(int i=0;i<size;i++){
            if(i==size-1)
                buffer1.append(memories.get(i));
            else
                buffer1.append(memories.get(i)).append(",");
        }
        buffer1.append("]");
        mv.addObject("memory",buffer1.toString());



        StringBuffer buffer3 = new StringBuffer();
        buffer3.append("[");
        int size3 = submitCounts.size();
        for(int i=0;i<size3;i++){
            if(i==size3-1)
                buffer3.append(submitCounts.get(i));
                else
                buffer3.append(submitCounts.get(i)).append(",");
        }


        buffer3.append("]");
        mv.addObject("submitCounts",buffer3.toString());



        StringBuffer buffer4 = new StringBuffer();
        buffer4.append("[");

        int size4 = submitCounts.size();
        for(int i=0;i<size3;i++){
            if(i==size4-1)
                buffer4.append(submitMemories.get(i));
            else
                buffer4.append(submitMemories.get(i)).append(",");
        }
        buffer4.append("]");
        mv.addObject("submitMemory",buffer4.toString());


        StringBuffer buffer5 = new StringBuffer();
        buffer5.append("[");
        int size5 = submitcores.size();
        for(int i=0;i<size5;i++){
            if(i==size5 -1)
                buffer5.append(submitcores.get(i));
            else
                buffer5.append(submitcores.get(i)).append(",");
        }
        buffer5.append("]");
        mv.addObject("submitCore",buffer5.toString());
        return mv;
    }


    @RequestMapping("/home/submitCount")
    public ModelAndView submitCount(){
        ModelAndView mv = new ModelAndView("");
        return mv;
    }

}
