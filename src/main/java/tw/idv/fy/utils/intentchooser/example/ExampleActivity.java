package tw.idv.fy.utils.intentchooser.example;

import android.app.Activity;
import android.os.Bundle;

import tw.idv.fy.utils.intentchooser.IntentChooserActivity;

public class ExampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentChooserActivity.OpenDefault(this, getString(R.string.MDN_pdf));
        finish();
    }
}
