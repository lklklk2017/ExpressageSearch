package com.cdxxgc.expressagesearchdemo.HomePage;

import android.text.TextUtils;
import android.util.Log;

import com.cdxxgc.expressagesearchdemo.DetailPage.DetailView;
import com.cdxxgc.expressagesearchdemo.constract.HomeConstruct;
import com.cdxxgc.expressagesearchdemo.entity.Company;
import com.cdxxgc.expressagesearchdemo.entity.PostDataBean;
import com.cdxxgc.expressagesearchdemo.entity.Recorde;
import com.cdxxgc.expressagesearchdemo.entity.TrackInfo;
import com.cdxxgc.expressagesearchdemo.utils.DBManager;
import com.cdxxgc.expressagesearchdemo.utils.TransFormUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Administrator on 2018/3/22.
 * 首页逻辑层
 */
public class HomePresent implements HomeConstruct.P {

    private HomeConstruct.V view;
    private HomeConstruct.M mode;

    public HomePresent(HomeConstruct.V view) {
        this.view = view;
        mode = new HomeModel();
    }

    /**
     * 获取物流信息
     */
    @Override
    public void track() {

        String compCode = view.getCompCode();//公司编号
        String expressId = view.getExpressId();//运单编号
        String userId = "1328204";//用户ID
        String keyValue = "eac56356-a712-4fdd-ac92-42b161b6da0d";//key值
        String dataType = "2"; //2-json
        String charset = "UTF-8";//编码格式

        if (!check(compCode, expressId)) {
            view.toast("快递编号或公司编号不能为空哦~", false);
            return;
        }

        view.showDialog(true);
//        //测试数据
//        compCode = "SF";
//        expressId = "118461988807";
//        userId = "1237100";
//        keyValue = "56da2cf8-c8a2-44b2-b6fa-476cd7d1ba17";

        //请求内容 json格式
        String jsonStr = "{\'OrderCode\':\'\'," +
                "\'ShipperCode\':\'" + compCode + "\'," +
                "\'LogisticCode\':\'" + expressId + "\'}";

//        Log.i("info", "jsonStr:" + jsonStr);

        //RequestData 经url（utf-8）编码
        String requestData_utf = null;
        try {
            requestData_utf = TransFormUtil.urlEncoder(jsonStr, charset);
//            Log.i("info", "RequestData 经url（utf-8）编码:" + requestData_utf);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //数据签名：把(jsonStr+APIKey)进行MD5加密，然后Base64编码，最后 进行URL(utf-8)编码
//        Log.i("info", "jsonStr+APIKey:" + jsonStr + keyValue);
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

//        //请求报文参数(仅测试使用)
//        String postStr = "RequestType=1002"
//                + "&EBusinessID=" + userId
//                + "&RequestData=" + requestData_utf
//                + "&DataSign=" + dataSign
//                + "&DataType=" + dataType;
//
//        Log.i("info", "请求报文参数(最终提交字段)：postStr:" + postStr);

        /*封装请求实体并进行网络请求*/
        PostDataBean postDataBean = new PostDataBean("1002", userId, requestData_utf, dataSign, dataType);
        mode.getTrack(postDataBean, new HomeConstruct.M.HttpResult.TrackHttpResult() {
            @Override
            public void success(TrackInfo i) {
                view.showDialog(false);
                onsucess(i);
            }

            @Override
            public void error(String e) {
                view.toast(e, true);
                view.showDialog(false);
            }
        });
    }

    /**
     * 存入数据库之前的准备工作
     *
     * @param info
     */
    private void saveLocalPre(TrackInfo info) {
        Company companyInfo = view.getCompanyInfo();
        saveLocal(companyInfo, info);
        if (companyInfo == null || info == null) {
            view.toast("fail to save in local ,please try again", true);
            return;
        }
    }

    /**
     * 生产记录
     * 存入本地数据库
     */
    @Override
    public void saveLocal(Company company, TrackInfo track) {

        //1.封装记录bean
        Recorde recorde = new Recorde();
        recorde.setComCode(company.getCompanyCode());//公司编号
        recorde.setCompName(company.getName());//公司名称
        recorde.setComTel(company.getTel());//公司电话
        recorde.setRes_imgId(company.getRes_imgId());//公司图片id
        recorde.setLogisticCode(Long.parseLong(track.getLogisticCode().trim()));//运单编号
        List<TrackInfo.TracesBean> traces = track.getTraces();//获取物流信息
        recorde.setLastAcceptStation(traces.get(traces.size() - 1).getAcceptStation());//最近的地址
        recorde.setLastAcceptTime(traces.get(traces.size() - 1).getAcceptTime());//最近的时间

        //执行本地数据库
        DBManager.getInstance().getSessionInstance().getRecordeDao().insertOrReplace(recorde);
        Log.i("info", "已执行插入：" + recorde.toString());
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
                saveLocalPre(info);
                view.toPageWithData(DetailView.class, info);
                break;
        }
    }


    /**
     * 验证
     *
     * @param id
     * @param compCode
     * @return
     */
    @Override
    public boolean check(String id, String compCode) {
        return (!TextUtils.isEmpty(id)) && (!TextUtils.isEmpty(compCode));
    }
}
