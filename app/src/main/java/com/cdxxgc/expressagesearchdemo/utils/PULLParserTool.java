package com.cdxxgc.expressagesearchdemo.utils;

import android.util.Xml;

import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.entity.Company;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/3.
 */

public class PULLParserTool {


    public static ArrayList<Company> getComList(InputStream in) throws XmlPullParserException, IOException {

        ArrayList<Company> list = null;
        Company company = null;

        //创建解析器
        XmlPullParser parser = Xml.newPullParser();
        //初始化
        parser.setInput(in, "UTF-8");
        //读取文件类型
        int type = parser.getEventType();

        //开始读取流直到有文件末尾标识为止
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_DOCUMENT:

                    break;
                case XmlPullParser.START_TAG:
                    if ("Companys".equals(parser.getName())) {
                        list = new ArrayList<Company>();
                    } else if ("Company".equals(parser.getName())) {
                        company = new Company();
                    } else if ("name".equals(parser.getName())) {
                        //查到该标签的名字获取其属性必须解析器移动到下一个区域（文本）
                        parser.next();
                        String name = parser.getText();
                        company.setName(name);
                    } else if ("res_imgId".equals(parser.getName())) {
                        parser.next();
                        String id_str = parser.getText();

//                        company.setRes_imgId(parser.getText());
                    } else if ("tel".equals(parser.getName())) {
                        parser.next();
                        company.setTel(parser.getText());
                    } else if ("companyCode".equals(parser.getName())) {
                        parser.next();
                        company.setCompanyCode(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("Company".equals(parser.getName())) {
                        list.add(company);
                    }
                    break;
            }

            //继续下一次的标签类型
            type = parser.next();
        }
        return list;
    }

    public static ArrayList<Company> getCompList() {

        ArrayList<Company> mCompanyList = new ArrayList<>();
        mCompanyList.add(new Company("顺丰速运", R.drawable.shunfeng, "95338", "SF"));
        mCompanyList.add(new Company("百世快递", R.drawable.baishi, "95320", "HTKY"));
        mCompanyList.add(new Company("中通快递", R.drawable.zhongtong, "95311", "ZTO"));
        mCompanyList.add(new Company("申通快递", R.drawable.shentong, "95543", "STO"));
        mCompanyList.add(new Company("圆通速递", R.drawable.yuantong, "95554", "YTO"));
        mCompanyList.add(new Company("韵达速递", R.drawable.yunda, "95546", "YD"));
        mCompanyList.add(new Company("邮政快递包裹", R.drawable.youzheng, "11183", "YZPY"));
        mCompanyList.add(new Company("EMS", R.drawable.ems, "11183", "ems"));
        mCompanyList.add(new Company("天天快递", R.drawable.tiantian, "400-188-8888", "HHTT"));
        mCompanyList.add(new Company("京东物流", R.drawable.jd, "950616", "JD"));
        mCompanyList.add(new Company("全峰快递", R.drawable.quanfeng, "400-100-0001", "QFKD"));
        mCompanyList.add(new Company("国通快递", R.drawable.guotong, "95327", "GTO"));
        mCompanyList.add(new Company("优速快递", R.drawable.uc, "95349", "UC"));
        mCompanyList.add(new Company("德邦", R.drawable.debang, "95353", "DBL"));
        mCompanyList.add(new Company("快捷快递", R.drawable.kuaijie, "4008-333-666", "FAST"));
        mCompanyList.add(new Company("宅急送", R.drawable.zhaijisong, "400-6789-000", "ZJS"));

        return mCompanyList;
    }
}
