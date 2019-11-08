package com.nvk.volleystringrequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static final int PAGE_SIZE = 25;
    private static final String KEY_PAGE = "page";
    private static final String KEY_LIMIT = "limit";
    //private static final String BASE = "http://localhost:8000/public/api/linh_vuc";
    //private static final String BASE = "http://192.168.1.32:8000/public/api/linh_vuc/phan_trang/";
    private static final String BASE = "http://192.168.1.32:81/web/phan_trang.php";

    private RecyclerView rcvLinhVuc;
    private LinhVucAdapter adapter;
    private List<LinhVuc> linhVucs = new ArrayList<>();
    private int currentPage = 1;
    private boolean checkLoading = false;
    private boolean checkLastPage = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radiation();
        createAdapter();
        try {
            loadData(null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        checkScroll();

    }

    private void checkScroll() {
        rcvLinhVuc.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!checkLoading && !checkLastPage){
                    LinearLayoutManager layoutManager = (LinearLayoutManager) rcvLinhVuc.getLayoutManager();
                    int countItem = layoutManager.getItemCount();
                    int countChild = layoutManager.getChildCount();
                    int findChildNext = layoutManager.findFirstVisibleItemPosition();
                    if ((countChild+findChildNext) >= countItem && countItem > 0 && countItem >= PAGE_SIZE){
                        checkLoading= true;
                        currentPage++;

                        linhVucs.add(null);
                        adapter.notifyItemInserted(linhVucs.size()-1);

                        Bundle data = new Bundle();
                        data.putInt(KEY_PAGE,currentPage);
                        data.putInt(KEY_LIMIT,PAGE_SIZE);

                        try {
                            loadData(data);
                        } catch (MalformedURLException e) {e.printStackTrace();

                        }
                    }
                }


            }
        });
    }

    private void loadData(Bundle data) throws MalformedURLException {
         boolean result = Utilitis.checkNetWork(this,data);
         if (result){
             startVolley(data,BASE);
         }else{
             Utilitis.ShowDialogNotification("Vui Lòng Kiểm tra INTERNET",this).show();
         }
    }

    private void startVolley(final Bundle data,String url) throws MalformedURLException {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("AAAAA",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AAAAA",error.toString());
            }
        }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("page",String.valueOf(2));
                params.put("limit",String.valueOf(2));
                Log.d("AAAA",params.toString());
                return params;
             }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void createAdapter() {
        adapter = new LinhVucAdapter(this,linhVucs);
        rcvLinhVuc.setLayoutManager(new LinearLayoutManager(this));
        rcvLinhVuc.setAdapter(adapter);

    }

    private void radiation() {
        rcvLinhVuc = findViewById(R.id.rcvLinhVuc);
    }
}
