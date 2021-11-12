package com.arno.demo.life.view

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.arno.demo.life.R
import com.arno.demo.life.utils.DisplayUtils

class DesktopDialogActivity : AppCompatActivity() {
    private lateinit var layoutRoot: ConstraintLayout
    private lateinit var desktopView: DesktopPopWindow
    private lateinit var btnShowDialog: AppCompatButton
    private lateinit var btnHideDialog: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop_dialog)
        layoutRoot = findViewById(R.id.layout_root)
        desktopView = findViewById(R.id.desktop_view)
        btnShowDialog = findViewById(R.id.btn_show_dialog)
        btnHideDialog = findViewById(R.id.btn_hide_dialog)
    }

    fun show(view: View) {
        desktopView.showDialog()
    }

    fun hide(view: View) {
        desktopView.hideDialog()
    }

    private fun createPopupWindow(contentView: View): PopupWindow? {
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val popupWindow = PopupWindow(
            contentView,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        )
        popupWindow.isTouchable = true
        popupWindow.setBackgroundDrawable(BitmapDrawable())
        popupWindow.isFocusable = true
//        popupWindow.animationStyle = R.style.PopupAnimation
        popupWindow.isClippingEnabled = true
        popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED
        popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        popupWindow.showAtLocation(
            layoutRoot,
            Gravity.NO_GRAVITY,
            0,
            DisplayUtils.getScreenHeightPixels(this) - contentView.measuredHeight
        )
        popupWindow.update()
        popupWindow.setOnDismissListener(object : PopupWindow.OnDismissListener {
            override fun onDismiss() {
//                this@DesktopDialogActivity.setWindowBackgroundAlpha(1.0f) // 当PopupWindow消失的时候Window完全透明
            }
        })
        return popupWindow
    }
}
