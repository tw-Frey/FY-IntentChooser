package tw.idv.fy.utils.intentchooser

import android.app.Activity
import android.os.Bundle
import android.widget.Toast

class IntentChooserActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this, R.string.tw_idv_fy_utils_intentchooser_open, Toast.LENGTH_LONG).show();
    }
}
