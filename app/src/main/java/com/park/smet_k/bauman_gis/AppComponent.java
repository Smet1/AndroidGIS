package com.park.smet_k.bauman_gis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.park.smet_k.bauman_gis.api.BgisApi;
import com.park.smet_k.bauman_gis.model.Stairs;
import com.park.smet_k.bauman_gis.model.StairsLink;
import com.park.smet_k.bauman_gis.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;
import static android.content.Context.MODE_PRIVATE;

public class AppComponent {
    private final static String KEY_OAUTH = "oauth";
    private final static String STORAGE_NAME = "storage";
    // массив лестниц
    public List<Stairs> StairsArray;
    // массив связей между лестницами (1 связь - в две стороны)
    public List<StairsLink> StairsLinksArray;
    // количество узлов в графе лестниц
    Integer StairsCount;
    // количество связей в графе лестниц
    Integer StairsLinksCount;
    // граф лестниц
    WeightedGraph StairsGraph;

    private final String LOG_TAG = "INIT";

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
        this.dbWorker = new DBWorker(context);
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

        // fill stairs array
//        GetAllStairsInit();
    }

    static void init(Context context) {
        if (instance == null) {
            instance = new AppComponent(context);
        }
    }

//    ListenerHandler<On>

    // загрузка через сеть всех лестниц на старте прилки
    // при неудаче, берет данные из БД map_stairs
    // в StairsArray
    public void GetAllStairsInit() {
        Callback<List<Stairs>> callback = new Callback<List<Stairs>>() {

            @Override
            public void onResponse(@NonNull Call<List<Stairs>> call, Response<List<Stairs>> response) {
                List<Stairs> body = response.body();
                if (body != null) {
                    Log.d(LOG_TAG, "--- GetAllStairsInit OK body != null ---");

                    StairsArray = body;

                    dbWorker.TruncateStairs();
                    dbWorker.InsertStairs(StairsArray);

                    // вычисляем количество узлов в графе лестниц
                    StairsCount = StairsArray.size();

                } else {
                    Log.d(LOG_TAG, "--- GetAllStairsInit OK body == null ---");

                    StairsArray = dbWorker.GetAllStairs();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Stairs>> call, Throwable t) {
                Log.d(LOG_TAG, "--- GetAllStairsInit ERROR onFailure ---");

                // TODO(): если не будет инета, все упадет)))00)
                try {
                    StairsArray = dbWorker.GetAllStairs();
                } catch (Throwable ex) {
                    if (StairsArray.size() == 0) {
                        Log.d(LOG_TAG, "--- StairsArray.size == 0 ---");
                    }
                    ex.printStackTrace();
                }


                t.printStackTrace();
            }
        };

        // avoid static error
        this.bgisApi.getStairs().enqueue(callback);
    }

    public void GetAllStairsLinksInit() {
        Callback<List<StairsLink>> callback = new Callback<List<StairsLink>>() {

            @Override
            public void onResponse(@NonNull Call<List<StairsLink>> call, Response<List<StairsLink>> response) {
                List<StairsLink> body = response.body();
                if (body != null) {
                    Log.d(LOG_TAG, "--- GetAllStairsLinksInit OK body != null ---");

                    StairsLinksArray = body;

                    dbWorker.TruncateStairsLinks();
                    dbWorker.InsertStairsLinks(StairsLinksArray);

                    // вычисляем количество связей в графе лестниц
                    StairsLinksCount = StairsLinksArray.size();

                    // создаем граф лестниц
                    StairsGraph = new WeightedGraph(StairsCount, StairsLinksCount);

                    for (StairsLink val : StairsLinksArray) {
                        // id в бд начинаются с 1 (поэтому минус 1)
                        // проверка на валидность связи (открыто или нет)
                        if (val.getOpen()) {
                            StairsGraph.addEdge(val.getIdFrom() - 1, val.getIdTo() - 1, val.getWeight());
                        }
                    }

                } else {
                    Log.d(LOG_TAG, "--- GetAllStairsLinksInit OK body == null ---");

                    StairsLinksArray = dbWorker.GetAllStairsLinks();

                    // вычисляем количество связей в графе лестниц
                    StairsLinksCount = StairsLinksArray.size();

                    // создаем граф лестниц
                    StairsGraph = new WeightedGraph(StairsCount, StairsLinksCount);

                    for (StairsLink val : StairsLinksArray) {
                        // id в бд начинаются с 1 (поэтому минус 1)
                        // проверка на валидность связи (открыто или нет)
                        if (val.getOpen()) {
                            StairsGraph.addEdge(val.getIdFrom() - 1, val.getIdTo() - 1, val.getWeight());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<StairsLink>> call, Throwable t) {
                Log.d(LOG_TAG, "--- GetAllStairsLinksInit ERROR onFailure ---");

                // TODO(): если не будет инета, все упадет)))00)
                try {
                    StairsArray = dbWorker.GetAllStairs();
                } catch (Throwable ex) {
                    if (StairsArray.size() == 0) {
                        Log.d(LOG_TAG, "--- StairsLinksArray.size == 0 ---");
                    }
                    ex.printStackTrace();
                }


                t.printStackTrace();
            }
        };

        // получаем связи лестниц (выполняется после загрузки лестниц)
        this.bgisApi.getLinks().enqueue(callback);
    }
}
