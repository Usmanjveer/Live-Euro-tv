package com.footballtv.tv;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.footballtv.tv.model.mdel1;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Middle22 extends AppCompatActivity {
    private mdel1 link = new mdel1();
    private LinearLayout loadingLl;
    private List<String> iframeLinks = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        String title2 = getIntent().getStringExtra("title");
        Log.e(ContentValues.TAG, "Get Data: " + title2);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(title2);
        toolbarTitle.setTextColor(Color.WHITE);

        loadingLl = findViewById(R.id.llLoading);
        String sec = getIntent().getStringExtra("postLink");
        Log.e(ContentValues.TAG, "Get sec: " + sec);
        new Thread(() -> {
            try {
                Connection.Response response = Jsoup.connect(sec)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.102 Safari/537.36")
                        .header("Referer", "https://www.crichd.live/")
                        .header("Accept-Language", "en-US,en;q=0.9")
                        .execute();

                org.jsoup.nodes.Document document = response.parse();

                org.jsoup.select.Elements scriptElement = document.select("body").select("script").eq(5);
                String scriptContent = scriptElement.html();

// Define the regex pattern
                Pattern pattern = Pattern.compile("embeds\\[(\\d+)\\] = '<iframe src=\"(.*?)\" width=.*?</iframe>'; titles\\[\\1\\] = '(.*?)';");
                Matcher matcher = pattern.matcher(scriptContent);

                while (matcher.find()) {
                    String iframeSrc = matcher.group(2);
                    String title = matcher.group(3);
                    Log.e("iframeSrc", iframeSrc);
                    Log.e("title", title);
                    iframeLinks.add(iframeSrc);
                    titles.add(title);
                }

               Element data = document.getElementById("criciframe");
                Log.e("Player", data.toString());
                String url2 = data.attr("src");

                link.setUrl(url2);
                Log.e("Player", link.getUrl());
            } catch (Exception ex) {
                // Handle exceptions
            }

            runOnUiThread(() -> {
                if (!link.getUrl().isEmpty() && link.getUrl().startsWith("http")) {
                    Intent intent = new Intent(Middle22.this, Second22.class);
                    intent.putExtra("postlink", link.getUrl());
                    intent.putExtra("label", title2);
                    intent.putStringArrayListExtra("iframeLinks", new ArrayList<>(iframeLinks));
                    intent.putStringArrayListExtra("iframeTitles", new ArrayList<>(titles));
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                }/* else {
                    Intent intent = new Intent(Middle.this, Video.class);
                    Toast.makeText(Middle.this, "Link Will Be Live On Match Time'", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                }*/
            });
        }).start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        /*if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }*/
    }
}
