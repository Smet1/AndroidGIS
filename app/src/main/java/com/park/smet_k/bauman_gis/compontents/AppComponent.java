package com.park.smet_k.bauman_gis.compontents;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.park.smet_k.bauman_gis.api.BgisApi;
import com.park.smet_k.bauman_gis.database.DBWorker;
import com.park.smet_k.bauman_gis.model.NewsModel;
import com.park.smet_k.bauman_gis.model.RoutePoint;
import com.park.smet_k.bauman_gis.model.Stairs;
import com.park.smet_k.bauman_gis.model.StairsLink;
import com.park.smet_k.bauman_gis.searchMap.GridWithWeights;
import com.park.smet_k.bauman_gis.searchMap.WeightedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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
    public Integer StairsCount;
    // количество связей в графе лестниц
    public Integer StairsLinksCount;
    // граф лестниц
    public WeightedGraph StairsGraph;
    // массив графов этажей (грид)
    public List<GridWithWeights> LevelsGraph;
    // мапа точек на карте
    public Map<String, RoutePoint> PointsMap;

    private final String LOG_TAG = "INIT";

    private static AppComponent instance = null;
    public final DBWorker dbWorker;
    public final BgisApi bgisApi;

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

    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new AppComponent(context);
        }
    }

    public void LevelsInit() {
        LevelsGraph = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            LevelsGraph.add(new GridWithWeights(1280, 1080));
//            LevelsGraph.get(i).add_rect(2, 0, 3, 9);
            LevelsGraph.get(i).add_rect(670, 455, 749, 500);
            Log.d("level", "new level");
            LevelsGraph.get(i).add_spline(830, 630, 88, true);
        }
    }

    public void MapPointsInit() {
        PointsMap = new TreeMap<>();

        PointsMap.put("ТП", new RoutePoint(730, 475, 3, "ТП"));
        PointsMap.put("буфет", new RoutePoint(775, 527, 3, "буфет"));
        PointsMap.put("384", new RoutePoint(953, 599, 3, "384"));
    }

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

                    GetAllStairsLinksInit();
                } else {
                    Log.d(LOG_TAG, "--- GetAllStairsInit OK body == null ---");

                    StairsArray = dbWorker.GetAllStairs();
                    StairsCount = StairsArray.size();

                    GetAllStairsLinksInit();
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
                StairsCount = StairsArray.size();

                t.printStackTrace();

                GetAllStairsLinksInit();
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

                    InitStairsGraph();
                } else {
                    Log.d(LOG_TAG, "--- GetAllStairsLinksInit OK body == null ---");

                    StairsLinksArray = dbWorker.GetAllStairsLinks();

                    // вычисляем количество связей в графе лестниц
                    StairsLinksCount = StairsLinksArray.size();

                    InitStairsGraph();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<StairsLink>> call, Throwable t) {
                Log.d(LOG_TAG, "--- GetAllStairsLinksInit ERROR onFailure ---");

                // TODO(): если не будет инета, все упадет)))00)
                try {
                    StairsLinksArray = dbWorker.GetAllStairsLinks();
                } catch (Throwable ex) {
                    if (StairsLinksArray.size() == 0) {
                        Log.d(LOG_TAG, "--- StairsLinksArray.size == 0 ---");
                    }
                    ex.printStackTrace();
                }
                // вычисляем количество связей в графе лестниц
                StairsLinksCount = StairsLinksArray.size();

                InitStairsGraph();

                t.printStackTrace();
            }
        };

        // получаем связи лестниц (выполняется после загрузки лестниц)
        this.bgisApi.getLinks().enqueue(callback);
    }

    public void InitStairsGraph() {
        // создаем граф лестниц
        Log.d(LOG_TAG, "--- CREATE StairsGraph ---" + " " + StairsCount.toString() + " " + StairsLinksCount.toString());
        StairsGraph = new WeightedGraph(StairsCount, StairsLinksCount);

        for (StairsLink val : StairsLinksArray) {
            // id в бд начинаются с 1 (поэтому минус 1)
            // проверка на валидность связи (открыто или нет)
            if (val.getOpen()) {
                StairsGraph.addEdge(val.getIdFrom() - 1, val.getIdTo() - 1, val.getWeight());
            }
        }
    }

    public void GetNewsInit() {
        Callback<List<NewsModel>> callback = new Callback<List<NewsModel>>() {

            @Override
            public void onResponse(@NonNull Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                List<NewsModel> body = response.body();
                if (body != null) {
                    Log.d(LOG_TAG, "--- GetNewsInit OK body != null ---");
                    for (NewsModel i : body) {
                        Log.d(LOG_TAG, i.getTitle() + " " + i.getPayload());
                        dbWorker.TruncateNews();
                        dbWorker.InsertNews(body);
                    }
                } else {
                    Log.d(LOG_TAG, "--- GetNewsInit OK body == null ---");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<NewsModel>> call, Throwable t) {
                Log.d(LOG_TAG, "--- GetNewsInit ERROR onFailure ---");

                t.printStackTrace();
            }
        };

        this.bgisApi.getNews().enqueue(callback);
    }

    public void InitMap() {
        GetAllStairsInit();
        while (StairsArray == null) {
            Log.d(LOG_TAG, "null StairsArray");
        }

        GetAllStairsLinksInit();
        while (StairsLinksArray == null) {
            Log.d(LOG_TAG, "null StairsLinksArray");
        }

        LevelsInit();
    }
}
