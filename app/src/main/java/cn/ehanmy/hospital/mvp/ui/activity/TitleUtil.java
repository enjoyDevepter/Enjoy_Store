package cn.ehanmy.hospital.mvp.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;

import cn.ehanmy.hospital.R;

public class TitleUtil {

    private TextView titleNameView;
    private View backBotton;

   public TitleUtil(View titleView, BaseActivity activity, String activityName){
        this.titleNameView = titleView.findViewById(R.id.title);
        if(titleNameView != null){
            titleNameView.setText(activityName);
        }
        backBotton = titleView.findViewById(R.id.back);
        if(backBotton != null){
            backBotton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
   }
}
