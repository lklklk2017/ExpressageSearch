package com.cdxxgc.expressagesearchdemo.constract;

import com.cdxxgc.expressagesearchdemo.entity.PostDataBean;
import com.cdxxgc.expressagesearchdemo.entity.Recorde;
import com.cdxxgc.expressagesearchdemo.entity.TrackInfo;

/**
 * Created by Administrator on 2018/4/4.
 */

public class RecordeConstruct {

    public interface M {
        void getRecordeInfo();

        void search(PostDataBean postDataBean, RecordeConstruct.M.TrackHttpResult trackHttpResult);

        /*获取追踪路线*/
        interface TrackHttpResult {
            void success(TrackInfo i);

            void error(String e);
        }
    }


    public interface V {

        void toast(String info, boolean sure);

        void toPageWithData(Class clzz, TrackInfo info);

        Recorde getRecords();

        void setBlank(boolean isBlank);

        void showDialog(boolean start);
    }

    public interface P {

        //查询并跳转
        void Search(Recorde recorde);

        //存入本地
        void saveLocal(Recorde recorde, TrackInfo info);
    }

}
