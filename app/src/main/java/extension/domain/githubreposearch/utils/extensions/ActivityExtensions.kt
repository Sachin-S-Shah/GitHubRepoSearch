package extension.domain.githubreposearch.utils.extensions

import android.app.Activity
import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.getActivitysRootView(): ViewGroup {
    return window.decorView.findViewById(android.R.id.content)
}

fun Activity.showSnackbar(message: String) {
    this.runOnUiThread {
        val snackbar = Snackbar.make(
            (this as AppCompatActivity).getActivitysRootView(),
            message,
            Snackbar.LENGTH_LONG
        )
        snackbar.setActionTextColor(Color.RED)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.BLACK)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.textSize = 14f
        snackbar.show()
    }
}