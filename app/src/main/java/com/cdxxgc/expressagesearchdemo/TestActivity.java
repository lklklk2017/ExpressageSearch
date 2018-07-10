package com.cdxxgc.expressagesearchdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cdxxgc.expressagesearchdemo.entity.User;
import com.cdxxgc.expressagesearchdemo.utils.DBManager;
import com.cdxxgc.expressagesearchdemo.utils.RetrofitManager;

import org.greenrobot.greendao.database.Database;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "test";
    private EditText mEd1;
    private EditText mEd2;
    private EditText mEd3;
    private EditText mEd4;
    private TextView mTvColumns;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

    }

    private void init() {
        mEd1 = ((EditText) findViewById(R.id.test_ed1));
        mEd2 = ((EditText) findViewById(R.id.test_ed2));
        mEd3 = ((EditText) findViewById(R.id.test_ed3));
        mEd4 = ((EditText) findViewById(R.id.test_ed4));
        mTvColumns = ((TextView) findViewById(R.id.test_colums));
        mTvResult = ((TextView) findViewById(R.id.test_result));
    }

    public void onCreatDB(View view) {
        DBManager.getInstance();
        Log.i(TAG, "数据库初始化完成");
        DBManager.getInstance().creatTable(true);
    }

    public void onInsert(View view) {
        User user = new User();
        if (!TextUtils.isEmpty(mEd1.getText())) {
            user.setId(Long.parseLong(mEd1.getText().toString().trim()));
        }
        user.setName(mEd2.getText().toString().trim());
        user.setAge(Integer.parseInt(mEd3.getText().toString().trim()));
        long insert = DBManager.getInstance().getSessionInstance().getUserDao().insertOrReplace(user);
        Log.i(TAG, "已经插入" + insert + "条数据");

    }

    public void onDelet(View view) {

        User user = new User();
        if (!TextUtils.isEmpty(mEd1.getText())) {
            user.setId(Long.parseLong(mEd1.getText().toString().trim()));
        }
        if (!TextUtils.isEmpty(mEd2.getText())) {
            user.setName(mEd2.getText().toString().trim());
        }

        if (!TextUtils.isEmpty(mEd3.getText())) {
            user.setAge(Integer.parseInt(mEd3.getText().toString().trim()));
        }

        DBManager.getInstance().getSessionInstance().getUserDao().delete(user);
        Log.i(TAG, "已经删除一条数据");
    }

    public void onQuery(View view) {

        StringBuilder columns = new StringBuilder();
        StringBuilder results = new StringBuilder();

        String[] allColumns = DBManager.getInstance().getSessionInstance().getUserDao().getAllColumns();
        for (String column : allColumns) {
            columns.append("    " + column);
        }
        mTvColumns.setText(columns.toString());

        List<User> users = DBManager.getInstance().getSessionInstance().getUserDao().loadAll();
        for (User user : users) {
            results.append(user.toString() + "\n");
        }
        mTvResult.setText(results.toString());
        Log.i(TAG, "已执行完毕查询,已经查询" + users.size() + "条");
    }

    public void onDrop(View view) {
        Database dataBase = DBManager.getInstance().getDataBase();
        DBManager.getInstance().getSessionInstance().getUserDao().dropTable(dataBase,true);
    }
}
