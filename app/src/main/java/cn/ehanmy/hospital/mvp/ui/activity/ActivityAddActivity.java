package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.guoqi.actionsheet.ActionSheet;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerActivityAddComponent;
import cn.ehanmy.hospital.di.module.ActivityAddModule;
import cn.ehanmy.hospital.mvp.contract.ActivityAddContract;
import cn.ehanmy.hospital.mvp.model.entity.activity.ActivityInfoBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.presenter.ActivityAddPresenter;
import cn.ehanmy.hospital.mvp.ui.widget.SpacesItemDecoration;
import cn.ehanmy.hospital.util.EdittextUtil;
import cn.ehanmy.hospital.util.ImageUploadUtils;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 添加活动和编辑活动共用
 * */
public class ActivityAddActivity extends BaseActivity<ActivityAddPresenter> implements ActivityAddContract.View, View.OnClickListener, ActionSheet.OnActionSheetSelected {

    /**如果是编辑活动，通过这个标记将活动信息传递过来，否则认为是添加活动*/
    public static final String KEY_FOR_APPOINTENT = "key_for_appointent";

    private static final int GALLERY_OPEN_REQUEST_CODE = 1;
    private static final int CROP_IMAGE_REQUEST_CODE = 2;
    private static final int CAMERA_OPEN_REQUEST_CODE = 3;
    @BindView(R.id.title_Layout)
    View title;
    @BindView(R.id.images)
    RecyclerView imagesRV;
    @BindView(R.id.add)
    View addV;
    @BindDimen(R.dimen.image_upload_width)
    int imageWdith;
    @BindDimen(R.dimen.image_upload_height)
    int imageHeight;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    RxPermissions mRxPermissions;
    @Inject
    List<String> images;

    @BindView(R.id.commit)
    View commit;
    private String mCameraFilePath = "";
    private String mCropImgFilePath = "";

    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_content)
    EditText et_content;

    /**如果是编辑活动，通过这个对象保存原始的活动。如果是新建活动，这个对象没有用*/
    private ActivityInfoBean activityInfoBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerActivityAddComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .activityAddModule(new ActivityAddModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        activityInfoBean = (ActivityInfoBean) getIntent().getSerializableExtra(KEY_FOR_APPOINTENT);
        String titleStr = activityInfoBean == null ? "添加活动":"编辑活动";
        new TitleUtil(title, this, titleStr);

        if(activityInfoBean != null){
            et_title.setText(activityInfoBean.getTitle());
            et_content.setText(activityInfoBean.getContent());
            images.add(activityInfoBean.getImage());
        }

        addV.setOnClickListener(this);
        ArmsUtils.configRecyclerView(imagesRV, mLayoutManager);
        imagesRV.addItemDecoration(new SpacesItemDecoration(ArmsUtils.dip2px(ArmsUtils.getContext(), 10), 0));
        imagesRV.setAdapter(mAdapter);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EdittextUtil.isEmpty(et_title)){
                    showMessage("请输入活动标题");
                    return;
                }

                if(EdittextUtil.isEmpty(et_content)){
                    showMessage("请输入活动内容");
                    return;
                }

                if(activityInfoBean == null){
                    mPresenter.addActivity(et_title.getText()+"",et_content.getText()+"");
                }else{
                    mPresenter.changeActivityInfo(activityInfoBean.getActivityId(),et_title.getText()+"",et_content.getText()+"");
                }
            }
        });
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                ActionSheet.showSheet(this, this, null);
                break;
        }
    }

    @Override
    public void onClick(int whichButton) {
        switch (whichButton) {
            case ActionSheet.CHOOSE_PICTURE:
                //相册
                openAlbum();
                break;
            case ActionSheet.TAKE_PICTURE:
                //拍照
                openCamera();
                break;
            case ActionSheet.CANCEL:
                //取消
                break;
        }
    }

    private void openAlbum() {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
            }
        }, mRxPermissions, mErrorHandler);

        ImageUploadUtils.startGallery(this, GALLERY_OPEN_REQUEST_CODE);

    }

    private void openCamera() {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
            }
        }, mRxPermissions, mErrorHandler);
        ImageUploadUtils.startCamera(this, CAMERA_OPEN_REQUEST_CODE, generateCameraFilePath());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_OPEN_REQUEST_CODE:
                    if (data != null) {
                        String mGalleryPath = ImageUploadUtils.parseGalleryPath(this, data.getData());

                        BitmapFactory.Options mOptions = getBitampOptions(mGalleryPath);
                        generateCropImgFilePath();
                        ImageUploadUtils.startCropImage(
                                this,
                                mGalleryPath,
                                mCropImgFilePath,
                                mOptions.outWidth,
                                mOptions.outHeight,
                                imageWdith,
                                imageHeight,
                                CROP_IMAGE_REQUEST_CODE);
                    }
                    break;
                case CROP_IMAGE_REQUEST_CODE:
//                    provideCache().put("imagePath", mCropImgFilePath);
                    mPresenter.uploadImage(new File(mCropImgFilePath));
                    images.clear();  // 只保留一张图片
                    images.add(mCropImgFilePath);
                    mAdapter.notifyDataSetChanged();
                    break;
                case CAMERA_OPEN_REQUEST_CODE:
                    if (data == null || data.getExtras() == null) {
                        BitmapFactory.Options mOptions = getBitampOptions(mCameraFilePath);
                        generateCropImgFilePath();
                        ImageUploadUtils.startCropImage(
                                this,
                                mCameraFilePath,
                                mCropImgFilePath,
                                mOptions.outWidth,
                                mOptions.outHeight,
                                imageWdith,
                                imageHeight,
                                CROP_IMAGE_REQUEST_CODE);
                    }
                    break;
            }
        }
    }

    private BitmapFactory.Options getBitampOptions(String path) {
        BitmapFactory.Options mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, mOptions);
        return mOptions;
    }

    private String generateCameraFilePath() {
        String mCameraFileDirPath = Environment.getExternalStorageDirectory() + File.separator + "camera";
        File mCameraFileDir = new File(mCameraFileDirPath);
        if (!mCameraFileDir.exists()) {
            mCameraFileDir.mkdirs();
        }
        mCameraFilePath = mCameraFileDirPath + File.separator + System.currentTimeMillis() + ".jgp";
        return mCameraFilePath;
    }

    private String generateCropImgFilePath() {
        String mCameraFileDirPath = Environment.getExternalStorageDirectory() + File.separator + "camera";
        File mCameraFileDir = new File(mCameraFileDirPath);
        if (!mCameraFileDir.exists()) {
            mCameraFileDir.mkdirs();
        }
        mCropImgFilePath = mCameraFileDirPath + File.separator + System.currentTimeMillis() + ".jgp";
        return mCropImgFilePath;
    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(imagesRV);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
    }


    @Override
    public Activity getActivity() {
        return this;
    }
}
