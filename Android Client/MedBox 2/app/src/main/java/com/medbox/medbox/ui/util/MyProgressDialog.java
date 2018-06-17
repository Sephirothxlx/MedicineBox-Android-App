package com.medbox.medbox.ui.util;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.medbox.medbox.R;

/**
 * Created by Sephiroth on 17/3/5.
 */
public class MyProgressDialog extends android.app.ProgressDialog
{
    public MyProgressDialog(Context context)
    {
        super(context);
    }

    public MyProgressDialog(Context context, int theme)
    {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context)
    {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.my_progress_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

}
