package tw.idv.fy.utils.intentchooser.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import tw.idv.fy.utils.intentchooser.IntentChooserActivity

class ExampleActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, IntentChooserActivity::class.java))
    }
}
