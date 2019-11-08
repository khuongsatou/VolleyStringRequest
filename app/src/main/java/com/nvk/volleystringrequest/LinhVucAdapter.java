package com.nvk.volleystringrequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LinhVucAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LOADING = 0;
    private static final int TYPE_LINH_VUC = 1;
    private Context context;
    private List<LinhVuc> linhVucs;

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

    class LinhVucHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
        }

        @Override
        public void onClick(View v) {

        }
    }

    class LoadingHolder extends RecyclerView.ViewHolder{

        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
