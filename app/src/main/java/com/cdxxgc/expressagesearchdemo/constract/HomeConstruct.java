package com.cdxxgc.expressagesearchdemo.constract;

import com.cdxxgc.expressagesearchdemo.entity.Company;
import com.cdxxgc.expressagesearchdemo.entity.PostDataBean;
import com.cdxxgc.expressagesearchdemo.entity.TrackInfo;

/**
 * Created by Administrator on 2018/3/22.
 * 采用mvp模式去架构整个项目
 * HomePage
 *
 */

public interface HomeConstruct {

    /**
     * model
     * 抽象数据
     */
    interface M {

        /*获取追踪路线*/
        void getTrack(PostDataBean dataBean, HttpResult.TrackHttpResult result);

        interface HttpResult {

            /*获取追踪路线*/
            interface TrackHttpResult {
                void success(TrackInfo i);

                void error(String e);
            }
        }
    }

    /**
     * view
     * 抽象视图
     */
    interface V {

        /*获取运单编号*/
        String getExpressId();

        /*获取公司编码*/
        String getCompCode();

        /*界面提示*/
        void toast(String content,boolean sure);

        /*跳转*/
        void toPage(Class clzz);

        /*携带数据跳转*/
        void toPageWithData(Class clzz, TrackInfo info);

        /*获取company*/
        Company getCompanyInfo();

        void showDialog(boolean start);
    }

    /**
     * presenter
     * 抽象逻辑
     */
    interface P {

        /*验证*/
        boolean check(String id, String compCode);

        /*查看线路*/
        void track();

        /*生成本地记录*/
        void saveLocal(Company company, TrackInfo track);
    }
}
