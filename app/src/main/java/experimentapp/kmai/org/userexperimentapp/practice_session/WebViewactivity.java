package experimentapp.kmai.org.userexperimentapp.practice_session;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import experimentapp.kmai.org.userexperimentapp.R;

/**
 * Created by saila on 3/9/18.
 */

public class WebViewactivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview_layout);


        WebView browser = (WebView) findViewById(R.id.webview);
        browser.loadUrl("https://docs.google.com/forms/d/1cIbZR_8AV0g7e8S-B8Ld2GyAfuKD3WxcbJOOl8gFhE0/viewform?edit_requested=true");

    }
}
