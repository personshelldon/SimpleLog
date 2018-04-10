/*
 * Modified by Vladyslav Lozytskyi on 11.04.18 1:39
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log.example;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.don11995.log.Group;
import com.don11995.log.LogProcessor;
import com.don11995.log.MapClass;
import com.don11995.log.MapField;
import com.don11995.log.SimpleLog;
import com.don11995.log.ValueMapper;

/*  @MapClass will generate new class com.don11995.log.ValueMapper
    with method "test" that mapping all variables that starts with "TEST" to their names.
    You need to add dependency
    annotationProcessor 'com.don11995.log:simple-log-processor:x.x.x'
*/
@MapClass(methods = {"test"}, prefixes = {"TEST"})
public class MainActivity
        extends AppCompatActivity
        implements
        View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    /*  @MapField will generate new class com.don11995.log.ValueMapper
        with method "setup" that mapping values to names.
        You need to add dependency
        annotationProcessor 'com.don11995.log:simple-log-processor:x.x.x'
     */
    @MapField("setup")
    private static final int SETUP_0 = 0;
    @MapField("setup")
    private static final int SETUP_1 = 1;
    @MapField("setup")
    private static final int SETUP_2 = 2;
    @MapField("setup")
    private static final int SETUP_3 = 3;
    @MapField("setup")
    private static final int SETUP_4 = 4;

    private static final String TEST_1 = "test1";
    private static final String TEST_2 = "test2";
    private static final String TEST_3 = "test3";

    private static final String TAG = "TEST_TAG";
    private static final String LOG = "TEST_LOG";

    /*
    * SimpleLog will print
    * --------TITLE--------
    * Line 1
    * Line 2
    * ---------------------
    *
    * and with custom TAG = "TEST_TAG"
    */
    private static final Group GROUP = new Group("TITLE")
            .append("Line 1")
            .append("Line 2")
            .tag("TEST_TAG");

    private CheckBox mDebugCheckbox;
    private CheckBox mErrorCheckbox;
    private CheckBox mInfoCheckbox;
    private CheckBox mVerboseCheckbox;
    private CheckBox mWarningCheckbox;
    private CheckBox mAssertCheckbox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleLog.setPrintReferences(true);
        SimpleLog.addLogProcessor(new LogProcessor() {
            @Override
            public void processLog(String tag,
                                   String message,
                                   int priority,
                                   @Nullable Throwable e) {
                Log.d("LogProcessor", "This code called on every log!");
            }
        });
        setContentView(R.layout.main_activity);
        findViewById(R.id.print_d).setOnClickListener(this);
        findViewById(R.id.print_e).setOnClickListener(this);
        findViewById(R.id.print_i).setOnClickListener(this);
        findViewById(R.id.print_w).setOnClickListener(this);
        findViewById(R.id.print_v).setOnClickListener(this);
        findViewById(R.id.print_wtf).setOnClickListener(this);
        findViewById(R.id.print_fd_empty).setOnClickListener(this);
        findViewById(R.id.print_fe_empty).setOnClickListener(this);
        findViewById(R.id.print_fi_empty).setOnClickListener(this);
        findViewById(R.id.print_fw_empty).setOnClickListener(this);
        findViewById(R.id.print_fv_empty).setOnClickListener(this);
        findViewById(R.id.print_fwtf_empty).setOnClickListener(this);
        findViewById(R.id.print_fd).setOnClickListener(this);
        findViewById(R.id.print_fe).setOnClickListener(this);
        findViewById(R.id.print_fi).setOnClickListener(this);
        findViewById(R.id.print_fw).setOnClickListener(this);
        findViewById(R.id.print_fv).setOnClickListener(this);
        findViewById(R.id.print_fwtf).setOnClickListener(this);
        findViewById(R.id.print_td).setOnClickListener(this);
        findViewById(R.id.print_te).setOnClickListener(this);
        findViewById(R.id.print_ti).setOnClickListener(this);
        findViewById(R.id.print_tw).setOnClickListener(this);
        findViewById(R.id.print_tv).setOnClickListener(this);
        findViewById(R.id.print_twtf).setOnClickListener(this);
        findViewById(R.id.print_group).setOnClickListener(this);
        findViewById(R.id.print_gen_test).setOnClickListener(this);
        findViewById(R.id.print_gen_setup).setOnClickListener(this);
        findViewById(R.id.print_gen_keycode).setOnClickListener(this);
        findViewById(R.id.print_gen_audiofocus).setOnClickListener(this);
        findViewById(R.id.print_throwable).setOnClickListener(this);

        mDebugCheckbox = findViewById(R.id.log_debug_checkbox);
        mErrorCheckbox = findViewById(R.id.log_error_checkbox);
        mInfoCheckbox = findViewById(R.id.log_info_checkbox);
        mVerboseCheckbox = findViewById(R.id.log_verbose_checkbox);
        mWarningCheckbox = findViewById(R.id.log_warning_checkbox);
        mAssertCheckbox = findViewById(R.id.log_assert_checkbox);

        init();
    }

    private void init() {
        if (SimpleLog.isLogLevelEnabled(SimpleLog.LOG_LEVEL_DEBUG)) {
            mDebugCheckbox.setChecked(true);
        }
        if (SimpleLog.isLogLevelEnabled(SimpleLog.LOG_LEVEL_ERROR)) {
            mErrorCheckbox.setChecked(true);
        }
        if (SimpleLog.isLogLevelEnabled(SimpleLog.LOG_LEVEL_INFO)) {
            mInfoCheckbox.setChecked(true);
        }
        if (SimpleLog.isLogLevelEnabled(SimpleLog.LOG_LEVEL_VERBOSE)) {
            mVerboseCheckbox.setChecked(true);
        }
        if (SimpleLog.isLogLevelEnabled(SimpleLog.LOG_LEVEL_WARNING)) {
            mWarningCheckbox.setChecked(true);
        }
        if (SimpleLog.isLogLevelEnabled(SimpleLog.LOG_LEVEL_ASSERT)) {
            mAssertCheckbox.setChecked(true);
        }

        mDebugCheckbox.setOnCheckedChangeListener(this);
        mErrorCheckbox.setOnCheckedChangeListener(this);
        mInfoCheckbox.setOnCheckedChangeListener(this);
        mVerboseCheckbox.setOnCheckedChangeListener(this);
        mWarningCheckbox.setOnCheckedChangeListener(this);
        mAssertCheckbox.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.print_d:
                SimpleLog.d(LOG);
                break;
            case R.id.print_i:
                SimpleLog.i(LOG);
                break;
            case R.id.print_e:
                SimpleLog.e(LOG);
                break;
            case R.id.print_v:
                SimpleLog.v(LOG);
                break;
            case R.id.print_w:
                SimpleLog.w(LOG);
                break;
            case R.id.print_wtf:
                SimpleLog.wtf(LOG);
                break;
            case R.id.print_fd_empty:
                SimpleLog.fd();
                break;
            case R.id.print_fi_empty:
                SimpleLog.fi();
                break;
            case R.id.print_fe_empty:
                SimpleLog.fe();
                break;
            case R.id.print_fv_empty:
                SimpleLog.fv();
                break;
            case R.id.print_fw_empty:
                SimpleLog.fw();
                break;
            case R.id.print_fwtf_empty:
                SimpleLog.fwtf();
                break;
            case R.id.print_fd:
                SimpleLog.fd(LOG);
                break;
            case R.id.print_fi:
                SimpleLog.fi(LOG);
                break;
            case R.id.print_fe:
                SimpleLog.fe(LOG);
                break;
            case R.id.print_fv:
                SimpleLog.fv(LOG);
                break;
            case R.id.print_fw:
                SimpleLog.fw(LOG);
                break;
            case R.id.print_fwtf:
                SimpleLog.fwtf(LOG);
                break;
            case R.id.print_td:
                SimpleLog.td(TAG, LOG);
                break;
            case R.id.print_ti:
                SimpleLog.ti(TAG, LOG);
                break;
            case R.id.print_te:
                SimpleLog.te(TAG, LOG);
                break;
            case R.id.print_tv:
                SimpleLog.tv(TAG, LOG);
                break;
            case R.id.print_tw:
                SimpleLog.tw(TAG, LOG);
                break;
            case R.id.print_twtf:
                SimpleLog.twtf(TAG, LOG);
                break;
            case R.id.print_group:
                SimpleLog.d(GROUP);
                break;
            case R.id.print_gen_test:
                SimpleLog.d(ValueMapper.test(TEST_2));
                break;
            case R.id.print_gen_setup:
                SimpleLog.i(ValueMapper.setup(SETUP_3));
                break;
            case R.id.print_gen_keycode:
                SimpleLog.e(ValueMapper.keyCode(KeyEvent.KEYCODE_DPAD_CENTER));
                break;
            case R.id.print_gen_audiofocus:
                SimpleLog.e(ValueMapper.audioFocus(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT));
                break;
            case R.id.print_throwable:
                SimpleLog.e(new TestThrowable());
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.log_debug_checkbox:
                SimpleLog.setLogLevelEnabled(SimpleLog.LOG_LEVEL_DEBUG, isChecked);
                break;
            case R.id.log_error_checkbox:
                SimpleLog.setLogLevelEnabled(SimpleLog.LOG_LEVEL_ERROR, isChecked);
                break;
            case R.id.log_info_checkbox:
                SimpleLog.setLogLevelEnabled(SimpleLog.LOG_LEVEL_INFO, isChecked);
                break;
            case R.id.log_verbose_checkbox:
                SimpleLog.setLogLevelEnabled(SimpleLog.LOG_LEVEL_VERBOSE, isChecked);
                break;
            case R.id.log_warning_checkbox:
                SimpleLog.setLogLevelEnabled(SimpleLog.LOG_LEVEL_WARNING, isChecked);
                break;
            case R.id.log_assert_checkbox:
                SimpleLog.setLogLevelEnabled(SimpleLog.LOG_LEVEL_ASSERT, isChecked);
                break;
        }
    }
}
