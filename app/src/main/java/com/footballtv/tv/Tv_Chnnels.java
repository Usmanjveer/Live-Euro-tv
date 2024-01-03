package com.footballtv.tv;

        import android.app.AlertDialog;
        import android.content.ContentValues;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.net.ConnectivityManager;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

        import com.footballtv.tv.adapter.ParseItemAdapter4;
        import com.footballtv.tv.model.ParseItemModel4;
        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.huawei.hms.ads.AdListener;
        import com.huawei.hms.ads.AdParam;
        import com.huawei.hms.ads.BannerAdSize;
        import com.huawei.hms.ads.HwAds;
        import com.huawei.hms.ads.InterstitialAd;
        import com.huawei.hms.ads.banner.BannerView;

        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.nodes.Element;
        import org.jsoup.select.Elements;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class Tv_Chnnels extends AppCompatActivity {
    private static final String TAG = "";
    private BottomNavigationView mBottomNav;
    private RecyclerView recyclerView;
    private TextView tvNoItems;
    private LinearLayout loadingLl;
    private ParseItemAdapter4 ParseItemAdapter4;
    private final List<ParseItemModel4> parseItemModelList = new ArrayList<>();
    private BannerView bannerView;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        loadingLl = findViewById(R.id.llLoading1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        tvNoItems = findViewById(R.id.tvNoItems);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Channels");
        toolbarTitle.setTextColor(Color.WHITE);

        overridePendingTransition(0, 0);

        HwAds.init(this);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(() -> recreate());

        bannerView = findViewById(R.id.hw_banner_viewp);
        bannerView.setAdId("a4vqxxv2w2");
        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
        AdParam adParam = new AdParam.Builder().build();
        bannerView.loadAd(adParam);

        recyclerView = findViewById(R.id.recyclerView_vid);
        tvNoItems = findViewById(R.id.tvNoItems);

        Content content = new Content();
        content.execute();

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdId("d3y2v13ofu");
        loadInterstitialAd();
        // Find the Rate and Share ImageViews
        ImageView rateImageView = findViewById(R.id.rate);
        ImageView shareImageView = findViewById(R.id.share);

        // Set click listener for the Rate button
        rateImageView.setOnClickListener(v -> {
            String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://appgallery.huawei.com/app/C107414743")));
            } catch (android.content.ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://appgallery.huawei.com/app/C107414743")));
            }
        });

        // Set click listener for the Share button
        shareImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Live Football TV Euro Hd");
            intent.putExtra(Intent.EXTRA_TEXT,
                    "Watch All football mactches, Live Streaming and highlights only on Live Live Football TV Euro Hd Euro HdLive Live Football TV Euro Hd Click here to Download https://appgallery.huawei.com/app/C107414743");
            startActivity(Intent.createChooser(intent, "Share via"));
        });

        mBottomNav = findViewById(R.id.bottom_nav);

        mBottomNav.setSelectedItemId(R.id.nav_tv);

        mBottomNav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_video:
                    startActivity(new Intent(getApplicationContext(), Videos.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_news:
                    startActivity(new Intent(getApplicationContext(), News.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_tv:

                    return true;
                default:
                    return false;
            }
        });

    }

    private void loadInterstitialAd() {
        AdParam adParam = new AdParam.Builder().build();
        interstitialAd.loadAd(adParam);
    }

    private void showInterstitialAd() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show(this);
        }
    }

    private final AdListener adListener = new AdListener() {
        @Override
        public void onAdLoaded() {
            showInterstitialAd();
        }

        @Override
        public void onAdFailed(int errorCode) {
            loadInterstitialAd();
            showInterstitialAd();
        }

        @Override
        public void onAdClosed() {
            // Called when an ad is closed.
        }

        @Override
        public void onAdClicked() {
            // Called when an ad is clicked.
        }

        @Override
        public void onAdLeave() {
            // Called when an ad leaves an app.
        }

        @Override
        public void onAdOpened() {
            // Called when an ad is opened.
        }

        @Override
        public void onAdImpression() {
            // Called when an ad impression occurs.
        }
    };


    private class Content extends AsyncTask<Void, Void, List<ParseItemModel4>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<ParseItemModel4> parseItemModelList) {
            super.onPostExecute(parseItemModelList);

            recyclerView.setVisibility(View.VISIBLE);

            if (parseItemModelList.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                tvNoItems.setVisibility(View.VISIBLE);
            } else {
                ParseItemAdapter4 = new ParseItemAdapter4(parseItemModelList, Tv_Chnnels.this);
                recyclerView.setAdapter(ParseItemAdapter4);
                ParseItemAdapter4.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
            }

            showInterstitialAd();
            loadingLl.setVisibility(View.GONE);
        }

        @Override
        protected List<ParseItemModel4> doInBackground(Void... p0) {
            List<ParseItemModel4> parseItemModelList = new ArrayList<>();
            try {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager != null) {
                    if (connectivityManager.getActiveNetworkInfo() == null ||
                            !connectivityManager.getActiveNetworkInfo().isConnected()) {
                        runOnUiThread(() -> new AlertDialog.Builder(Tv_Chnnels.this)
                                .setMessage("No Internet Connection")
                                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show());
                        return parseItemModelList;
                    }
                }
                String url = "https://cricfree.live/live/sky-sports-main-event";
                Document document = Jsoup.connect(url).get();
                Elements articleHTML = document.select("ul.nav-sidebar li:not(:first-child)");

                // Create a sublist starting from the 10th element
                int startingIndex = 10;
                List<Element> subList = startingIndex < articleHTML.size() ? articleHTML.subList(startingIndex, articleHTML.size()) : Collections.emptyList();

                // Create a map to store the title and corresponding image link
                Map<String, String> titleToImageLinkMap = new HashMap<>();

    /*titleToImageLinkMap.put("Sky Sports Main Event", "https://crichd.tv/assets/uploads/channels/1.jpg");
    titleToImageLinkMap.put("Sky Sports Premier League", "https://crichd.tv/assets/uploads/channels/6.jpg");
    titleToImageLinkMap.put("Sky Sports Football", "https://crichd.tv/assets/uploads/channels/47.png");
    titleToImageLinkMap.put("Sky Sports Cricket", "https://crichd.tv/assets/uploads/channels/3.png");
    titleToImageLinkMap.put("Sky Sports News", "https://crichd.tv/assets/uploads/channels/9.png");
    titleToImageLinkMap.put("Sky Sports Action", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a2/Sky_Sport_Action.svg/2560px-Sky_Sport_Action.svg.png");
    titleToImageLinkMap.put("Sky Sports F1", "https://crichd.tv/assets/uploads/channels/7.png");
    titleToImageLinkMap.put("Sky Sports Arena", "https://crichd.tv/assets/uploads/channels/48.png");
    titleToImageLinkMap.put("Sky Sports Golf", "https://crichd.tv/assets/uploads/channels/5.png");
    titleToImageLinkMap.put("Sky Sports MIX", "https://b5.crichd.tv/assets/uploads/channels/8.png");*/
                titleToImageLinkMap.put("BT Sport 1", "https://img2.sport-tv-guide.live/images/stations/a97.png");
                titleToImageLinkMap.put("BT Sport 2", "https://img2.sport-tv-guide.live/images/stations/a100.png");
                titleToImageLinkMap.put("BT Sport 3", "https://img2.sport-tv-guide.live/images/stations/a108.png");
                titleToImageLinkMap.put("BT Sport 4", "https://img2.sport-tv-guide.live/images/stations/a98.png");
                titleToImageLinkMap.put("SuperSports Grandstand", "https://b5.crichd.tv/assets/uploads/channels/97.png");
                titleToImageLinkMap.put("SuperSports Premiere League", "https://b5.crichd.tv/assets/uploads/channels/94.png");
                titleToImageLinkMap.put("SuperSports Football", "https://b5.crichd.tv/assets/uploads/channels/93.png");
                titleToImageLinkMap.put("SuperSports Cricket", "https://www.rofootball.com/wp-content/uploads/2023/01/cricket-8.png");
                titleToImageLinkMap.put("SuperSports Laliga", "https://b5.crichd.tv/assets/uploads/channels/95.png");
                titleToImageLinkMap.put("SuperSports Rugby", "https://b5.crichd.tv/assets/uploads/channels/96.png");
                titleToImageLinkMap.put("SuperSports Action", "https://b5.crichd.tv/assets/uploads/channels/99.png");
                titleToImageLinkMap.put("Euro Sport", "https://b5.crichd.tv/assets/uploads/channels/14.png");
                titleToImageLinkMap.put("Euro Sport 2", "https://b5.crichd.tv/assets/uploads/channels/15.png");
                titleToImageLinkMap.put("FOX HD", "https://b5.crichd.tv/assets/uploads/channels/105.png");
                titleToImageLinkMap.put("FOX 2 HD", "https://b5.crichd.tv/assets/uploads/channels/106.png");
                titleToImageLinkMap.put("ESPN usa", "https://b5.crichd.tv/assets/uploads/channels/147.png");
                titleToImageLinkMap.put("ESPN 2", "https://b5.crichd.tv/assets/uploads/channels/148.png");
                titleToImageLinkMap.put("Premier Sport 1", "https://s3.eu-west-1.amazonaws.com/entertainmentie/storage/lmstelevision/tv-logos/9ec570fbdc82b613baeaddf1de974395.png");
                titleToImageLinkMap.put("Premier Sport 2", "https://s3.eu-west-1.amazonaws.com/entertainmentie/storage/lmstelevision/tv-logos/be1053602b884b605b72eaf9eb6fa7e5.png");
                titleToImageLinkMap.put("Racing UK", "https://www.catterickbridge.co.uk/portals/0/Graphics/Racing/RacingUK/v01_Racing-TV-Logo-RTV-Blue_RGB_NOBG-min.png");
                titleToImageLinkMap.put("BBC One", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/BBC_One_logo_%28box_variant%29.svg/2560px-BBC_One_logo_%28box_variant%29.svg.png");
                titleToImageLinkMap.put("bein1en", "https://assets.bein.com/mena/sites/3/2015/06/beIN_SPORTS1_ENGLISH_Digital_Mono.png");
                titleToImageLinkMap.put("bein2en", "https://assets.bein.com/mena/sites/3/2015/06/beIN_SPORTS2_ENGLISH_Digital_Mono.png");
                titleToImageLinkMap.put("bein3en", "https://i.goalzz.com/?i=o%2Fh%2F1%2F215%2Fbein-sports-english-1.png");
                titleToImageLinkMap.put("bein1premium", "https://assets.bein.com/mena/sites/3/2015/06/beIN_SPORTS1_PREMIUM_Digital_Mono.png");
                titleToImageLinkMap.put("bein2premium", "https://i.goalzz.com/?i=o%2Fh%2F1%2F893%2Fbein-sports-premium-1.png");
                titleToImageLinkMap.put("bein3premium", "https://i.goalzz.com/?i=o%2Fh%2F1%2F894%2Fbein-sports-premium-1.png");
                titleToImageLinkMap.put("Bein 1 FR", "https://i.goalzz.com/?i=o%2Fh%2F1%2F216%2Fbein-sports-french-1.png");
                titleToImageLinkMap.put("Bein 2 FR", "https://i.goalzz.com/?i=o%2Fh%2F1%2F211%2Fbein-sports-french-1.png");
                titleToImageLinkMap.put("Bein 3 FR", "https://i.goalzz.com/?i=o%2Fh%2F1%2F362%2Fbein-sports-french-1.png");
                titleToImageLinkMap.put("Bein 1 TR", "https://yt3.googleusercontent.com/ZdYiLQMeSIj3vQ2Y0R8alIZ9UYFRMYv-hCxVki9hQSEobfw0dqd46Wy_mNlVctB7hyj1KzsS=s900-c-k-c0x00ffffff-no-rj");
                titleToImageLinkMap.put("Fox 501 AU", "https://s3-eu-west-1.amazonaws.com/static.media.info/l/o/6/6550.1489223922.png");
                titleToImageLinkMap.put("Fox 502 AU", "https://b5.crichd.tv/assets/uploads/channels/102.png");
                titleToImageLinkMap.put("Fox 503 AU", "https://b5.crichd.tv/assets/uploads/channels/103.png");
                titleToImageLinkMap.put("Fox 504 AU", "https://b5.crichd.tv/assets/uploads/channels/104.png");
                titleToImageLinkMap.put("Fox 505 AU", "https://b5.crichd.tv/assets/uploads/channels/105.png");
                titleToImageLinkMap.put("Fox 506 AU", "https://b5.crichd.tv/assets/uploads/channels/106.png");
                titleToImageLinkMap.put("Sport tv 1", "https://img2.sport-tv-guide.live/images/tv-station-pt-sport-tv-1-450.png");
                titleToImageLinkMap.put("Sport tv 2", "https://i.goalzz.com/?i=ana%2Fsport_tv2_pt.jpg");
                titleToImageLinkMap.put("Sport tv 3", "https://img2.sport-tv-guide.live/images/tv-station-pt-sport-tv-3-452.png");
                titleToImageLinkMap.put("Sport tv 4", "https://img2.sport-tv-guide.live/images/tv-station-sport-tv-4-453.png");
                titleToImageLinkMap.put("Arstro Cricket", "https://www.astro-com.my/wp-content/uploads/2016/01/Astro-Cricket-Ch838.png");
                titleToImageLinkMap.put("Willow TV", "https://www.cricexec.com/wp-content/uploads/2021/09/Willow-tv-logo.png");
                for (Element item : subList) {
                    String title = item.select("a").attr("title");
                    String postLink = item.select("a").attr("href");

                    // Check if the title exists in the map, if yes, get the corresponding image link
                    String imageLink = titleToImageLinkMap.getOrDefault(title, "https://img.freepik.com/free-icon/play-button_318-250509.jpg");

                    Log.e(ContentValues.TAG, "Image Data: " + imageLink);
                    Log.e(ContentValues.TAG, "Get Data: " + title);
                    Log.e(ContentValues.TAG, "Get Data: " + postLink);

                    parseItemModelList.add(new ParseItemModel4(title, imageLink, postLink));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return parseItemModelList;
        }
        }





    @Override
    public void onBackPressed() {
        if (interstitialAd.isLoaded()) {
            showInterstitialAd();
            interstitialAd.show();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            overridePendingTransition(0, 0);
            startActivity(intent);
        }
    }
}