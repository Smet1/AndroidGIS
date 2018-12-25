package com.park.smet_k.bauman_gis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.park.smet_k.bauman_gis.api.BgisApi;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;
import static android.content.Context.MODE_PRIVATE;

public class AppComponent {
    private final static String KEY_OAUTH = "oauth";
    private final static String STORAGE_NAME = "storage";

    private static AppComponent instance = null;
    final DBWorker dbWorker;
    final BgisApi bgisApi;

    private final SharedPreferences prefs;

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public static AppComponent getInstance() {
        return instance;
    }

    private AppComponent(Context context) {
        // DBWorker init
        dbWorker = new DBWorker(context);
        this.prefs = context.getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain ->
                        chain.proceed(
                                chain.request().newBuilder()
                                        .build()))
                .build();

        // API init
        this.bgisApi = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .baseUrl(BgisApi.BASE_URL)
                .build()
                .create(BgisApi.class);
    }

    static void init(Context context) {
        if (instance == null) {
            instance = new AppComponent(context);
        }
    }

//    ListenerHandler<On>
}
