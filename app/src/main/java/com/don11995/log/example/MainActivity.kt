package com.don11995.log.example

import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.don11995.log.*

/*  @MapClass will generate new class com.don11995.log.ValueMapper
    with method "test" that mapping all variables that starts with "TEST" to their names.
    You need to add dependency
    annotationProcessor 'com.don11995.log:simple-log-processor:x.x.x'
*/
@MapClass(methods = ["test"], prefixes = ["TEST"])
class MainActivity : AppCompatActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private var mDebugCheckbox: CheckBox? = null
    private var mErrorCheckbox: CheckBox? = null
    private var mInfoCheckbox: CheckBox? = null
    private var mVerboseCheckbox: CheckBox? = null
    private var mWarningCheckbox: CheckBox? = null
    private var mAssertCheckbox: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SimpleLog.setPrintReferences(true)
        SimpleLog.addLogProcessor(object : LogProcessor() {
            override fun processLog(tag: String,
                                    message: String,
                                    priority: LogLevel,
                                    e: Throwable?) {
                Log.d("LogProcessor", "This code called on every log!")
            }
        })
        setContentView(R.layout.main_activity)
        findViewById<View>(R.id.print_d).setOnClickListener(this)
        findViewById<View>(R.id.print_e).setOnClickListener(this)
        findViewById<View>(R.id.print_i).setOnClickListener(this)
        findViewById<View>(R.id.print_w).setOnClickListener(this)
        findViewById<View>(R.id.print_v).setOnClickListener(this)
        findViewById<View>(R.id.print_wtf).setOnClickListener(this)
        findViewById<View>(R.id.print_fd_empty).setOnClickListener(this)
        findViewById<View>(R.id.print_fe_empty).setOnClickListener(this)
        findViewById<View>(R.id.print_fi_empty).setOnClickListener(this)
        findViewById<View>(R.id.print_fw_empty).setOnClickListener(this)
        findViewById<View>(R.id.print_fv_empty).setOnClickListener(this)
        findViewById<View>(R.id.print_fwtf_empty).setOnClickListener(this)
        findViewById<View>(R.id.print_fd).setOnClickListener(this)
        findViewById<View>(R.id.print_fe).setOnClickListener(this)
        findViewById<View>(R.id.print_fi).setOnClickListener(this)
        findViewById<View>(R.id.print_fw).setOnClickListener(this)
        findViewById<View>(R.id.print_fv).setOnClickListener(this)
        findViewById<View>(R.id.print_fwtf).setOnClickListener(this)
        findViewById<View>(R.id.print_td).setOnClickListener(this)
        findViewById<View>(R.id.print_te).setOnClickListener(this)
        findViewById<View>(R.id.print_ti).setOnClickListener(this)
        findViewById<View>(R.id.print_tw).setOnClickListener(this)
        findViewById<View>(R.id.print_tv).setOnClickListener(this)
        findViewById<View>(R.id.print_twtf).setOnClickListener(this)
        findViewById<View>(R.id.print_group).setOnClickListener(this)
        findViewById<View>(R.id.print_gen_test).setOnClickListener(this)
        findViewById<View>(R.id.print_gen_setup).setOnClickListener(this)
        findViewById<View>(R.id.print_gen_keycode).setOnClickListener(this)
        findViewById<View>(R.id.print_gen_audiofocus).setOnClickListener(this)
        findViewById<View>(R.id.print_throwable).setOnClickListener(this)

        mDebugCheckbox = findViewById(R.id.log_debug_checkbox)
        mErrorCheckbox = findViewById(R.id.log_error_checkbox)
        mInfoCheckbox = findViewById(R.id.log_info_checkbox)
        mVerboseCheckbox = findViewById(R.id.log_verbose_checkbox)
        mWarningCheckbox = findViewById(R.id.log_warning_checkbox)
        mAssertCheckbox = findViewById(R.id.log_assert_checkbox)

        init()
    }

    private fun init() {
        if (SimpleLog.isLogLevelEnabled(LogLevel.DEBUG)) {
            mDebugCheckbox?.isChecked = true
        }
        if (SimpleLog.isLogLevelEnabled(LogLevel.ERROR)) {
            mErrorCheckbox?.isChecked = true
        }
        if (SimpleLog.isLogLevelEnabled(LogLevel.INFO)) {
            mInfoCheckbox?.isChecked = true
        }
        if (SimpleLog.isLogLevelEnabled(LogLevel.VERBOSE)) {
            mVerboseCheckbox?.isChecked = true
        }
        if (SimpleLog.isLogLevelEnabled(LogLevel.WARNING)) {
            mWarningCheckbox?.isChecked = true
        }
        if (SimpleLog.isLogLevelEnabled(LogLevel.ASSERT)) {
            mAssertCheckbox?.isChecked = true
        }

        mDebugCheckbox?.setOnCheckedChangeListener(this)
        mErrorCheckbox?.setOnCheckedChangeListener(this)
        mInfoCheckbox?.setOnCheckedChangeListener(this)
        mVerboseCheckbox?.setOnCheckedChangeListener(this)
        mWarningCheckbox?.setOnCheckedChangeListener(this)
        mAssertCheckbox?.setOnCheckedChangeListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.print_d -> SimpleLog.d(LOG)
            R.id.print_i -> SimpleLog.i(LOG)
            R.id.print_e -> SimpleLog.e(LOG)
            R.id.print_v -> SimpleLog.v(LOG)
            R.id.print_w -> SimpleLog.w(LOG)
            R.id.print_wtf -> SimpleLog.wtf(LOG)
            R.id.print_fd_empty -> SimpleLog.fd()
            R.id.print_fi_empty -> SimpleLog.fi()
            R.id.print_fe_empty -> SimpleLog.fe()
            R.id.print_fv_empty -> SimpleLog.fv()
            R.id.print_fw_empty -> SimpleLog.fw()
            R.id.print_fwtf_empty -> SimpleLog.fwtf()
            R.id.print_fd -> SimpleLog.fd(LOG)
            R.id.print_fi -> SimpleLog.fi(LOG)
            R.id.print_fe -> SimpleLog.fe(LOG)
            R.id.print_fv -> SimpleLog.fv(LOG)
            R.id.print_fw -> SimpleLog.fw(LOG)
            R.id.print_fwtf -> SimpleLog.fwtf(LOG)
            R.id.print_td -> SimpleLog.td(TAG, LOG)
            R.id.print_ti -> SimpleLog.ti(TAG, LOG)
            R.id.print_te -> SimpleLog.te(TAG, LOG)
            R.id.print_tv -> SimpleLog.tv(TAG, LOG)
            R.id.print_tw -> SimpleLog.tw(TAG, LOG)
            R.id.print_twtf -> SimpleLog.twtf(TAG, LOG)
            R.id.print_group -> SimpleLog.d(GROUP)
            R.id.print_gen_test -> SimpleLog.d(ValueMapper.test(TEST_2))
            R.id.print_gen_setup -> SimpleLog.i(ValueMapper.setup(SETUP_3))
            R.id.print_gen_keycode -> SimpleLog.e(ValueMapper.keyCode(KeyEvent.KEYCODE_DPAD_CENTER))
            R.id.print_gen_audiofocus -> SimpleLog.e(ValueMapper.audioFocus(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT))
            R.id.print_throwable -> SimpleLog.e(TestThrowable())
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.log_debug_checkbox -> SimpleLog.setLogLevelEnabled(LogLevel.DEBUG, isChecked)
            R.id.log_error_checkbox -> SimpleLog.setLogLevelEnabled(LogLevel.ERROR, isChecked)
            R.id.log_info_checkbox -> SimpleLog.setLogLevelEnabled(LogLevel.INFO, isChecked)
            R.id.log_verbose_checkbox -> SimpleLog.setLogLevelEnabled(LogLevel.VERBOSE, isChecked)
            R.id.log_warning_checkbox -> SimpleLog.setLogLevelEnabled(LogLevel.WARNING, isChecked)
            R.id.log_assert_checkbox -> SimpleLog.setLogLevelEnabled(LogLevel.ASSERT, isChecked)
        }
    }

    @Suppress("unused")
    companion object {

        /**
         * @MapField will generate new class com.don11995.log.ValueMapper
         * with method "setup" that mapping values to names.
         * You need to add dependency
         * annotationProcessor 'com.don11995.log:simple-log-processor:x.x.x'
         */
        @MapField("setup")
        private val SETUP_0 = 0

        @MapField("setup")
        private val SETUP_1 = 1

        @MapField("setup")
        private val SETUP_2 = 2

        @MapField("setup")
        private val SETUP_3 = 3

        @MapField("setup")
        private val SETUP_4 = 4

        private const val TEST_1 = "test1"
        private const val TEST_2 = "test2"
        private const val TEST_3 = "test3"
        private const val TEST_4 = "test4"

        private const val TAG = "TEST_TAG"
        private const val LOG = "TEST_LOG"

        /**
         * SimpleLog will print
         * --------TITLE--------
         * Line 1
         * Line 2
         * ---------------------
         *
         * and with custom TAG = "TEST_TAG"
         */
        private val GROUP = Group("TITLE")
                .append("Line 1")
                .append("Line 2")
                .tag("TEST_TAG")
    }
}
