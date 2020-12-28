package le.minh.hau.ck01;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.ViewHolder> {
private List<Food> foodList;
private Context context;
private ConnectDB connectDB;

    public AdapterFood(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclview,parent,false);
        return new ViewHolder(view);
    }

    public void setData(List<Food> foodList){
        this.foodList = foodList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Food food =foodList.get(position);

        connectDB = ConnectDB.getInstance(context);
        //holder.imghinh.setImageResource(Integer.valueOf(2131165280));
        holder.imghinh.setImageResource(food.getHinh());
        Log.d("Hinh","R.drawable.baoloai +"+R.drawable.baoloai);
        holder.lbten.setText(food.getTen());
        holder.lbgia.setText(String.valueOf(food.getGia()));
        holder.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food food1 = foodList.get(holder.getAdapterPosition());
                int sid = food1.getId();

                String sten = food1.getTen();
                int shinh = food1.getHinh();
                double sgia= food1.getGia();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_add_update);
                int rong = WindowManager.LayoutParams.MATCH_PARENT;
                int dai = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(rong,dai);
                dialog.show();
                EditText ten = dialog.findViewById(R.id.editTen);
                EditText hinh = dialog.findViewById(R.id.editHinh);

                Button btnsua2 = dialog.findViewById(R.id.btnThem_Sua);
                ten.setText(sten);
                hinh.setText(String.valueOf(shinh));

                Spinner spinnergia = dialog.findViewById(R.id.spinnerGia);
                String item[] = new String[]{"1000","2000"};

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(dialog.getContext(),
                        android.R.layout.simple_list_item_1,
                        item);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnergia.setAdapter(arrayAdapter);
                btnsua2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String uten = ten.getText().toString().trim();
                        int uhinh = Integer.valueOf(hinh.getText().toString().trim());
                        double ugia = Double.valueOf(spinnergia.getSelectedItem().toString().trim());
                        connectDB.foodDAO().update(sid,uten,uhinh,ugia);
                        foodList.clear();
                        foodList.addAll(connectDB.foodDAO().getall());
                        notifyDataSetChanged();
                    }
                });
            }
        });
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food food1 = foodList.get(holder.getAdapterPosition());
                connectDB.foodDAO().delete(food1);
                int pos =holder.getAdapterPosition();
                foodList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos,foodList.size());
            }
        });
        holder.btndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainActivity_Detail.class);
                        intent.putExtra("food",food);
                        context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imghinh,btnsua,btnxoa,btndetail;
        TextView lbten,lbgia;

        public ViewHolder(@NonNull View v) {
            super(v);
            imghinh = v.findViewById(R.id.imgHinh);
            btnsua = v.findViewById(R.id.btnSua);
            btnxoa = v.findViewById(R.id.btnXoa);
            lbten = v.findViewById(R.id.lbTen);
            lbgia = v.findViewById(R.id.lbGia);
            btndetail = v.findViewById(R.id.btnDetail);
        }
    }
}
