package com.tuli.pharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuli.pharma.interfaces.InitComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static com.tuli.pharma.utils.ExtraUtils.BACK_FROM_ACTIVITY;

public class SettingsActivity extends AppCompatActivity implements InitComponent {

    @BindView(R.id.ln_profile)
    LinearLayout lnSettings;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void init() {
        tvToolbarTitle.setVisibility(View.GONE);
    }

    @OnClick({ R.id.tv_back})
    public void onClickListener(View view)
    {
        switch (view.getId())
        {


            case R.id.tv_back:
                BACK_FROM_ACTIVITY(this);
                break;
        }
    }


    /**
     * Override Method to active calligraphy font in this activity
     * @param newBase - activity base
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}