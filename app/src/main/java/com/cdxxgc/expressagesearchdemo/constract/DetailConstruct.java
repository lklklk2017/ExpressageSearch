package com.cdxxgc.expressagesearchdemo.constract;

/**
 * Created by Administrator on 2018/3/22.
 * 采用mvp模式去架构整个项目
 * DetailPage
 */

public class DetailConstruct {

    /**
     * model
     * 抽象数据
     */
    public interface M {

    }

    /**
     * view
     * 抽象视图
     */
    public interface V {
        //isRefresh
        void setIsrefresh(boolean isrefresh);
    }

    /**
     * presenter
     * 抽象逻辑
     */
    public interface P {

        /*获取数据*/
//        void getData();


    }

}
