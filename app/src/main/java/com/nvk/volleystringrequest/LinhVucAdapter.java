package com.nvk.volleystringrequest;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinhVucAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LOADING = 0;
    private static final int TYPE_LINH_VUC = 1;
    private static final String KEY_ID = "id";
    private Context context;
    private List<LinhVuc> linhVucs;
    private static final String BASE_THEM = "http://192.168.1.32:8000/public/api/linh_vuc/them";
    private static final String BASE_CAP_NHAT = "http://192.168.1.32:8000/public/api/linh_vuc/cap_nhat";
    private static final String BASE_XOA = "http://192.168.1.32:8000/public/api/linh_vuc/xoa";
    private static final String KEY_TEN_LINH_VUC = "ten_linh_vuc";

    public LinhVucAdapter(Context context, List<LinhVuc> linhVucs) {
        this.context = context;
        this.linhVucs = linhVucs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LINH_VUC){
            View view = LayoutInflater.from(context).inflate(R.layout.custom_item_linh_vuc,parent,false);
            return new LinhVucHolder(view);
        }else if(viewType == TYPE_LOADING){
            View view = LayoutInflater.from(context).inflate(R.layout.custom_loading,parent,false);
            return new LoadingHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LinhVuc linhVuc = linhVucs.get(position);
        if (holder instanceof LinhVucHolder){
            LinhVucHolder linhVucHolder = (LinhVucHolder) holder;
            linhVucHolder.tvTenLinhVuc.setText(linhVuc.getTenLinhVuc());
        }
    }

    @Override
    public int getItemCount() {
        return linhVucs.size();
    }

    @Override
    public int getItemViewType(int position) {
        return linhVucs.get(position) == null ? TYPE_LOADING : TYPE_LINH_VUC;
    }

    class LinhVucHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTenLinhVuc;
        private CardView cvItemLinhVuc;
        private Button btnThem;
        private Button btnXoa;

        public LinhVucHolder(@NonNull View itemView) {
            super(itemView);

            tvTenLinhVuc = itemView.findViewById(R.id.tvTenLinhVuc);
            cvItemLinhVuc = itemView.findViewById(R.id.cvItemLinhVuc);
            btnThem = itemView.findViewById(R.id.btnThem);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            cvItemLinhVuc.setOnClickListener(this);

            btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowDialogInsert().show();
                }
            });

            btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinhVuc linhVuc = linhVucs.get(getLayoutPosition());
                    ShowDialogXoa(linhVuc).show();
                }
            });
        }


        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_linh_vuc, null, false);
            Button btnOK = view.findViewById(R.id.btnOK);
            Button btnCancel = view.findViewById(R.id.btnCancel);
            final EditText edtTenLinhVuc = view.findViewById(R.id.edtTenLinhVuc);
            edtTenLinhVuc.setText(linhVucs.get(getLayoutPosition()).getTenLinhVuc()+"");
            builder.setView(view);
            final AlertDialog dialog = builder.create();
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String tenLinhVuc = edtTenLinhVuc.getText().toString().trim();
                    if (tenLinhVuc.isEmpty()) {
                        Toast.makeText(context, "Bạn Chưa Nhập Gì", Toast.LENGTH_SHORT).show();
                    } else {
                        StringRequest request = new StringRequest(Request.Method.POST, BASE_CAP_NHAT, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("AAAAA", response);
                                if (response.equals("success")) {
                                    MainActivity mainActivity = (MainActivity) context;
                                    linhVucs.clear();
                                    mainActivity.loadData(null);
                                    Toast.makeText(context, "Thêm Thành Công", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("AAAAA", error.toString());
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put(KEY_ID,String.valueOf(linhVucs.get(getLayoutPosition()).getId()));
                                params.put(KEY_TEN_LINH_VUC, tenLinhVuc);
                                return params;
                            }
                        };


                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(request);


                    }
                    dialog.dismiss();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    private AlertDialog ShowDialogXoa(final LinhVuc linhVuc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác Nhận").setTitle("Bạn Có Muốn Xóa");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringRequest request = new StringRequest(Request.Method.POST, BASE_XOA, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("AAAAA",response);
                        if (response.equals("success")){
                            MainActivity mainActivity = (MainActivity) context;
                            linhVucs.clear();
                            mainActivity.loadData(null);
                            Toast.makeText(context,"Xóa Thành Công",Toast.LENGTH_SHORT).show();
                        }

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
                        params.put(KEY_ID,String.valueOf(linhVuc.getId()));
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(request);


            }
        });
        return builder.create();
    }

    class LoadingHolder extends RecyclerView.ViewHolder{

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private AlertDialog ShowDialogInsert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_linh_vuc,null,false);
        Button btnOK =view.findViewById(R.id.btnOK);
        Button btnCancel =view.findViewById(R.id.btnCancel);
        final EditText edtTenLinhVuc = view.findViewById(R.id.edtTenLinhVuc);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tenLinhVuc = edtTenLinhVuc.getText().toString().trim();
                if (tenLinhVuc.isEmpty()){
                    Toast.makeText(context,"Bạn Chưa Nhập Gì",Toast.LENGTH_SHORT).show();
                }else{
                    StringRequest request = new StringRequest(Request.Method.POST, BASE_THEM, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("AAAAA",response);
                            if (response.equals("success")){
                                MainActivity mainActivity = (MainActivity) context;
                                linhVucs.clear();
                                mainActivity.loadData(null);
                                Toast.makeText(context,"Thêm Thành Công",Toast.LENGTH_SHORT).show();

                            }

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
                            params.put(KEY_TEN_LINH_VUC,tenLinhVuc);
                            return params;
                        }
                    };


                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(request);


                }
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

}
