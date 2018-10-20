package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerBuyCenterComponent;
import cn.ehanmy.hospital.di.module.BuyCenterModule;
import cn.ehanmy.hospital.mvp.contract.BuyCenterContract;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.presenter.BuyCenterPresenter;
import cn.ehanmy.hospital.util.CacheUtil;

import static com.google.zxing.BarcodeFormat.QR_CODE;
import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 订单中心，查询会员编号页面
 */
public class BuyCenterActivity extends BaseActivity<BuyCenterPresenter> implements BuyCenterContract.View, View.OnClickListener {

    @BindView(R.id.title_Layout)
    View title;
    @BindView(R.id.clear_btn)
    View clear_btn;
    @BindView(R.id.search_btn)
    View search_btn;
    @BindView(R.id.search_key)
    EditText search_key;
    @BindView(R.id.image)
    View image;
    @BindView(R.id.hide)
    TextView hide;
    @BindView(R.id.buy)
    View buy;
    @BindView(R.id.no_date)
    View noDate;
    @BindView(R.id.qr_code)
    View qr_code;  // 二维码

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBuyCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .buyCenterModule(new BuyCenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_buy_center; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    // 启动扫描二维码界面
    public void customScan() {
        ArrayList<String> desiredBarcodeFormats = new ArrayList<String>();
        desiredBarcodeFormats.add(QR_CODE.name());
        new IntentIntegrator(this)
                .setDesiredBarcodeFormats(desiredBarcodeFormats)
                .setOrientationLocked(false)
                .setCaptureActivity(SustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                showMessage("扫描失败，请重试");
            } else {
                // 扫描二维码成功
                // ScanResult 为 获取到的字符串
                String ScanResult = intentResult.getContents();
                mPresenter.requestMemberInfoById(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title, this, "下单中心");
        clear_btn.setVisibility(View.GONE);
        buy.setOnClickListener(this);
        search_btn.setOnClickListener(this);
        qr_code.setOnClickListener(this);
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
    public void updateCodeisRight(boolean codeIsRight) {
        noDate.setVisibility(View.GONE);
        image.setVisibility(View.VISIBLE);
        image.setBackground(getResources().getDrawable(codeIsRight ? R.mipmap.member_code_right : R.mipmap.member_code_wrong));
        String defaultStr = "会员编号错误，请重新查询！";
        if (codeIsRight) {
            MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
            defaultStr = "会员编号正确，请继续下单\n" + memberBean.getNickName();
            if (!TextUtils.isEmpty(memberBean.getRealName())) {
                defaultStr += ("(" + memberBean.getRealName() + ")");
            }
        }
        hide.setText(defaultStr);
        buy.setVisibility(codeIsRight ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                hideImm();
                String s = search_key.getText().toString();
                if (ArmsUtils.isEmpty(s)) {
                    showMessage("请输入会员编号后查询");
                } else {
                    mPresenter.requestHospitalInfo(s);
                }
                break;
            case R.id.buy:
                ArmsUtils.startActivity(GoodsListActivity.class);
                break;
            case R.id.qr_code:
                customScan();
                break;

        }
    }
}
