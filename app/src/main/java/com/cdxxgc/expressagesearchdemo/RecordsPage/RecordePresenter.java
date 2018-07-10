package com.cdxxgc.expressagesearchdemo.RecordsPage;

import android.util.Log;

import com.cdxxgc.expressagesearchdemo.DetailPage.DetailView;
import com.cdxxgc.expressagesearchdemo.constract.RecordeConstruct;
import com.cdxxgc.expressagesearchdemo.entity.PostDataBean;
import com.cdxxgc.expressagesearchdemo.entity.Recorde;
import com.cdxxgc.expressagesearchdemo.entity.TrackInfo;
import com.cdxxgc.expressagesearchdemo.utils.DBManager;
import com.cdxxgc.expressagesearchdemo.utils.TransFormUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */

public class RecordePresenter implements RecordeConstruct.P {

    private RecordeConstruct.V view;
    private RecordeConstruct.M model;

    public RecordePresenter(RecordeConstruct.V view) {
        this.view = view;
        model = new RecordeModel();
    }

    @Override
    public void Search(Recorde recorde) {
        view.showDialog(true);
        //验证
        if (recorde == null) {
            view.toast("Search Failed", false);
            view.showDialog(false);
            return;
        }

        String compCode = recorde.getComCode();//公司编号
        String expressId = recorde.getLogisticCode() + "";//运单编号
        String userId = "1328204";//用户ID
        String keyValue = "eac56356-a712-4fdd-ac92-42b161b6da0d";//key值
        String dataType = "2"; //2-json
        String charset = "UTF-8";//编码格式

        String jsonStr = "{\'OrderCode\':\'\'," +
                "\'ShipperCode\':\'" + compCode + "\'," +
                "\'LogisticCode\':\'" + expressId + "\'}";

        //RequestData 经url（utf-8）编码
        String requestData_utf = null;
        try {
            requestData_utf = TransFormUtil.urlEncoder(jsonStr, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //数据签名：把(jsonStr+APIKey)进行MD5加密，然后Base64编码，最后 进行URL(utf-8)编码
        String dataSign = null;
        try {
            String base64_md5str = TransFormUtil.base64(TransFormUtil.MD5(jsonStr + keyValue, charset), charset);
//            Log.i("info", "base64_md5str:" + base64_md5str);

            dataSign = TransFormUtil.urlEncoder(base64_md5str, charset);
//            Log.i("info", "数据签名：dataSign:" + dataSign);
        } catch (Exception e) {
            Log.e("error", "数据签名解析失败！");
            e.printStackTrace();
        }
        //执行网络请求
           /*封装请求实体并进行网络请求*/
        PostDataBean postDataBean = new PostDataBean("1002", userId, requestData_utf, dataSign, dataType);
        model.search(postDataBean, new RecordeConstruct.M.TrackHttpResult() {
            @Override
            public void success(TrackInfo i) {
                view.showDialog(false);
                onsucess(i);
            }

            @Override
            public void error(String e) {
                view.showDialog(false);
                view.toast(e, true);
            }
        });
    }

    /**
     * 网络请求成功时候的回调
     * （当前主线程）
     *
     * @param info
     */
    private void onsucess(TrackInfo info) {
        String state = info.getState();
         /* 物流状态 state
            0-无轨迹
            1-已揽收
            2-在途中
            3-签收
            4-问题件
            */
        switch (state) {
            case "0":
                view.toast("查找君表示没有找到轨迹信息..(-_-||", false);
                break;
            case "4":
                view.toast("查找君表示这是个问题快件..(-_-||", false);
                break;
            default:
                //更新本地数据
                saveLocalPre(info);
                view.toPageWithData(DetailView.class, info);
                break;
        }
    }

    /**
     * 存入数据库之前的准备工作
     *
     * @param newInfo 重新获取的数据
     */
    private void saveLocalPre(TrackInfo newInfo) {
        Recorde recorde = view.getRecords();
        saveLocal(recorde, newInfo);
        if (recorde == null || newInfo == null) {
            view.toast("fail to save in local ,please try again", true);
            return;
        }
    }

    @Override
    public void saveLocal(Recorde recorde, TrackInfo info) {

        //1.封装记录bean
        Recorde newRecorde = new Recorde();
        newRecorde.setComCode(recorde.getComCode());//公司编号
        newRecorde.setCompName(recorde.getCompName());//公司名称
        newRecorde.setComTel(recorde.getComTel());//公司电话
        newRecorde.setRes_imgId(recorde.getRes_imgId());//公司图片id

        newRecorde.setLogisticCode(Long.parseLong(info.getLogisticCode().trim()));//运单编号
        List<TrackInfo.TracesBean> traces = info.getTraces();//获取物流信息
        newRecorde.setLastAcceptStation(traces.get(traces.size() - 1).getAcceptStation());//最近的地址
        newRecorde.setLastAcceptTime(traces.get(traces.size() - 1).getAcceptTime());//最近的时间
        DBManager.getInstance().getSessionInstance().getRecordeDao().insertOrReplace(newRecorde);
        Log.i("info", "已执行插入：" + recorde.toString());
    }
}
