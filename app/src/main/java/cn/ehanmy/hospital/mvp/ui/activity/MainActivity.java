package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerMainComponent;
import cn.ehanmy.hospital.di.module.MainModule;
import cn.ehanmy.hospital.mvp.contract.MainContract;
import cn.ehanmy.hospital.mvp.model.entity.UpdateResponse;
import cn.ehanmy.hospital.mvp.model.entity.store.StoreBean;
import cn.ehanmy.hospital.mvp.presenter.MainPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.MainAdapter;
import cn.ehanmy.hospital.mvp.ui.widget.CustomDialog;
import cn.ehanmy.hospital.mvp.ui.widget.SpacesItemDecoration;
import cn.ehanmy.hospital.util.CacheUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, View.OnClickListener, DefaultAdapter.OnRecyclerViewItemClickListener, AMapLocationListener {

    private static final int REQUEST_CODE_APP_INSTALL = 1000;
    private static final String FORMAT_FOR_ACTIVITY_TITLE = "欢迎%s回到%s";
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.setting)
    View settingV;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    MainAdapter mAdapter;

    long startPosition = 0;

    CustomDialog updateDialog;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if (null != mLocationClient) {
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationClient.setLocationOption(mLocationOption);
            mLocationOption.setOnceLocation(true);
            mLocationOption.setOnceLocationLatest(true);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        String loginUserName = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER_LOGIN_NAME);
        StoreBean storeBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_STORE_INFO);
        titleTV.setText(String.format(FORMAT_FOR_ACTIVITY_TITLE, loginUserName, storeBean.getName()));
        settingV.setVisibility(View.VISIBLE);
        settingV.setOnClickListener(this);
        backV.setVisibility(View.GONE);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, ArmsUtils.getDimens(ArmsUtils.getContext(), R.dimen.main_item_space)));
        ArmsUtils.configRecyclerView(recyclerView, mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showUpdateInfo(UpdateResponse updateResponse) {
        if (updateResponse.isNeedUpgrade()) {
            showUpdateDailog(updateResponse);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting:
                ArmsUtils.startActivity(SafeSettingActivity.class);
                break;
            case R.id.back:
                backV.setSelected(false);
                mPresenter.changeToMain(true);
                backV.setVisibility(View.GONE);
                break;
        }
    }

    private void showUpdateDailog(UpdateResponse updateResponse) {
        updateDialog = CustomDialog.create(getSupportFragmentManager())
                .setViewListener(new CustomDialog.ViewListener() {
                    @Override
                    public void bindView(View view) {
                        ((TextView) view.findViewById(R.id.content)).setText(updateResponse.getDescription());
                        View close = view.findViewById(R.id.close);
                        close.setVisibility(updateResponse.isForceUpgrade() ? View.GONE : View.VISIBLE);
                        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                updateDialog.dismiss();
                            }
                        });
                        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startDownload(updateResponse.getUrl(), updateResponse.getUpgradeVersion());
                                updateDialog.dismiss();
                            }
                        });
                    }
                })
                .setLayoutRes(R.layout.dialog_update)
                .setDimAmount(0.5f)
                .isCenter(true)
                .setCancelOutside(updateResponse.isForceUpgrade() ? false : true)
                .setWidth(ArmsUtils.getDimens(this, R.dimen.update_dialog_width))
                .setHeight(ArmsUtils.getDimens(this, R.dimen.update_dialog_height))
                .show();
    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        switch (position) {
            case 0:
                if (backV.isSelected()) {
                    ArmsUtils.startActivity(HospitalInfoActivity.class);
                } else {
                    // 注册会员
                    ArmsUtils.startActivity(RegisterActivity.class);
                }
                break;
            case 1:
                if (backV.isSelected()) {
                    ArmsUtils.startActivity(ActivityInfoActivity.class);
                } else {
                    // 下单中心
                    Intent intent = new Intent(this, BuyCenterActivity.class);
                    intent.putExtra("isBuy", true);
                    ArmsUtils.startActivity(intent);
                }
                break;
            case 2:
                if (backV.isSelected()) {
                    // 本店会员
                    ArmsUtils.startActivity(MemberListActivity.class);
                } else {
                    // 订单中心
                    ArmsUtils.startActivity(OrderFormCenterActivity.class);
                }
                break;
            case 3:
                if (backV.isSelected()) {
                    // 项目设置
                    ArmsUtils.startActivity(ProjectSettingActivity.class);
                } else {
                    // 用户预约
                    ArmsUtils.startActivity(UserAppointmentActivity.class);
                }
                break;
            case 4:
                // 医美预约
                Intent intent = new Intent(this, BuyCenterActivity.class);
                intent.putExtra("isBuy", false);
                ArmsUtils.startActivity(intent);
                break;
            case 5:
                // 我的店铺
                mPresenter.changeToMain(false);
                backV.setSelected(true);
                backV.setVisibility(View.VISIBLE);
                backV.setOnClickListener(this);
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Cache<String, Object> cache = ArmsUtils.obtainAppComponentFromContext(getActivity()).extras();
        cache.put("lon", String.valueOf(aMapLocation.getLongitude()));
        cache.put("lat", String.valueOf(aMapLocation.getLatitude()));
    }

    public void startDownload(String url, String name) {
        if (ArmsUtils.isEmpty(url) || ArmsUtils.isEmpty(name)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                File downloadFile = new File(Environment.getExternalStorageDirectory(), name + ".apk");// 设置路径
                int length;
                HttpURLConnection conn = null;
                try {
                    URL Url = new URL(url);
                    conn = (HttpURLConnection) Url.openConnection();
                    conn.setRequestProperty("Accept-Encoding", "identity");
                    conn.connect();
                    if (conn.getResponseCode() == 200) {
                        conn.getContentLength();
                        length = conn.getContentLength();
                        if (downloadFile.exists()) {
                            if (downloadFile.length() < length) {
                                startPosition = downloadFile.length();
                            } else if (downloadFile.length() == length) {
                                installApk(downloadFile);
                                return;
                            } else {
                                downloadFile.delete();
                                startPosition = 0;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final Request request = new Request.Builder()
                        .addHeader("RANGE", "bytes=" + startPosition + "-")
                        .url(url)
                        .build();
                OkHttpClient client = new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody body = response.body();
                        InputStream is = body.byteStream();
                        byte[] bytes = new byte[2048];
                        int len = 0;
                        long totalNum = startPosition;
                        RandomAccessFile raf = new RandomAccessFile(downloadFile, "rw");
                        while ((len = is.read(bytes, 0, bytes.length)) != -1) {
                            raf.seek(totalNum);
                            raf.write(bytes, 0, len);
                            totalNum += len;
                        }
                        body.close();
                        installApk(downloadFile);
                    }
                });
            }
        }).start();
    }

    private void installApk(File file) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(getApplication(), "cn.ehanmy.fileprovider", file);//在AndroidManifest中的android:authorities值
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");

            //兼容8.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = getActivity().getPackageManager().canRequestPackageInstalls();
                if (!hasInstallPermission) {
                    showMessage("安装应用需要打开未知来源权限，请去设置中开启权限");
                    startInstallPermissionSettingActivity(getActivity());
                    return;
                }
            }
        } else {
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        ArmsUtils.startActivity(install);
    }

    /**
     * 开启设置安装未知来源应用权限界面
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_APP_INSTALL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_APP_INSTALL) {
            File file = new File(Environment.getExternalStorageDirectory(), "hi.apk");// 设置路径
            installApk(file);
        }
    }

}
