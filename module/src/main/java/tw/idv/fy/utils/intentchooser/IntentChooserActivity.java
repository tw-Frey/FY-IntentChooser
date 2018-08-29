package tw.idv.fy.utils.intentchooser;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Optional;

import java.util.Objects;
import java.util.UUID;

import static android.content.pm.PackageManager.MATCH_DEFAULT_ONLY;

@SuppressWarnings("Guava")
public class IntentChooserActivity extends Activity {

    private static ParcelUuid KEY_ID = new ParcelUuid(UUID.randomUUID());
    private static String EXTRA_INTENT = "EXTRA_INTENT";
    private static String EXTRA_KEY_ID = "EXTRA_KEY_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mainIntent = Optional.fromNullable(getIntent()).or(new Intent());
        Intent moreIntent = mainIntent.getParcelableExtra(EXTRA_INTENT);
        Object key_id = Optional.fromNullable(mainIntent.getParcelableExtra(EXTRA_KEY_ID)).orNull();
        if (Objects.equals(KEY_ID, key_id) && moreIntent != null) {
            startActivity(Intent.createChooser(moreIntent, "更多"));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    private static Intent MoreIntent(@NonNull Context context, @NonNull Intent moreIntent) {
        return new Intent(context, IntentChooserActivity.class)
                .putExtra(EXTRA_INTENT, moreIntent)
                .putExtra(EXTRA_KEY_ID, KEY_ID);
    }

    public static void OpenDefault(@NonNull Context context, @NonNull final String url) {
        final PackageManager packageManager = context.getPackageManager();
        final Uri uri = Uri.parse(url);
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, uri);
        Intent sendIntent = new Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, url)
                .setType("text/plain");
        Intent[] initIntents = com.google.common.collect.FluentIterable
                .from(packageManager.queryIntentActivities(viewIntent, MATCH_DEFAULT_ONLY))
                .transform(resolveInfo -> new LabeledIntent(
                        new Intent(viewIntent).setComponent(new ComponentName(
                                resolveInfo.activityInfo.packageName,
                                resolveInfo.activityInfo.name
                        )),
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.loadLabel(packageManager),
                        resolveInfo.icon
                ))
                .toArray(LabeledIntent.class);
        context.startActivity(
                Intent.createChooser(
                        MoreIntent(context, sendIntent),
                        "開啟"
                ).putExtra(Intent.EXTRA_INITIAL_INTENTS, initIntents)
        );
    }
}
