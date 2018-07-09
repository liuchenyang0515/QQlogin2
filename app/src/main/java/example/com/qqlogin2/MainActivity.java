package example.com.qqlogin2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * 定义一个共享参数(存放数据方便的api)
     */
    private SharedPreferences sp;
    private String TAG = "MainActivity";
    private EditText et_qqnumber;
    private EditText et_passwd;
    private CheckBox cb_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_qqnumber = (EditText) findViewById(R.id.et_qqnumber);
        et_passwd = (EditText) findViewById(R.id.et_passwd);
        cb_remember = (CheckBox) findViewById(R.id.cb_remember);
        sp = this.getSharedPreferences("config", MODE_PRIVATE);
        restoreInfo();
    }

    /**
     * 根据原来保存的文件信息，把QQ号码和密码信息显示到界面
     */
    private void restoreInfo() {
        String qq = sp.getString("qq", "");
        String passwd = sp.getString("passwd", "");
        boolean isRemember = sp.getBoolean("remember", false);
        Log.d(TAG, "qq:" + qq);
        Log.d(TAG, "passwd:" + passwd);
        et_qqnumber.setText(qq);
        et_passwd.setText(passwd);
        cb_remember.setChecked(isRemember); // 恢复时也把记住密码的勾选上
    }

    /**
     * 登录按钮的点击事件
     *
     * @param view
     */
    public void login(View view) {
        String qq = et_qqnumber.getText().toString().trim();
        String passwd = et_passwd.getText().toString().trim();
        if (TextUtils.isEmpty(qq) || TextUtils.isEmpty(passwd)) {
            Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            // 登录操作，模拟登陆，数据应该提交给服务器比较是否正确
            if ("10000".equals(qq) && "123456".equals(passwd)) {
                // 是否需要记住密码
                // 将数据保存到sp文件中
                SharedPreferences.Editor edit = sp.edit();
                if (cb_remember.isChecked()) {
                    edit.putString("qq", qq);
                    edit.putString("passwd", passwd);
                    edit.putBoolean("remember", true);
                } else {
                    edit.clear(); // 没有勾选记住密码就清空SharedPreferences文件数据
                }
                edit.apply(); // 提交数据，类似于关闭流，事务
                Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
