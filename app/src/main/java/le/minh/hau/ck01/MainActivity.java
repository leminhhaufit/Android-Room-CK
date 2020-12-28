package le.minh.hau.ck01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
     ConnectDB connectDB;
    ImageView btnThem,btnTim;
    RecyclerView recyclerView;
    List<Food> foodList =new ArrayList<>();
    LinearLayoutManager linearlayout;

    AdapterFood adapterFood;
    EditText txttim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnThem = findViewById(R.id.btnThem);
        btnTim = findViewById(R.id.btnTim);
        txttim = findViewById(R.id.editTim);
         recyclerView = findViewById(R.id.recyclerview);
        connectDB = ConnectDB.getInstance(this);
        foodList = connectDB.foodDAO().getall();
        linearlayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearlayout);
        adapterFood =new AdapterFood(foodList,MainActivity.this);
        recyclerView.setAdapter(adapterFood);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_add_update);
                int rong = WindowManager.LayoutParams.MATCH_PARENT;
                int dai = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(rong,dai);
                dialog.show();
                EditText txtten = dialog.findViewById(R.id.editTen);
                EditText txthinh = dialog.findViewById(R.id.editHinh);

                Spinner spinnergia = dialog.findViewById(R.id.spinnerGia);

               String item[] = new String[]{"1000","2000"};

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(dialog.getContext(),
                        android.R.layout.simple_list_item_1,
                        item);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnergia.setAdapter(arrayAdapter);


                Button btnthem = dialog.findViewById(R.id.btnThem_Sua);

                btnthem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String uten =txtten.getText().toString().trim();
                        int uhinh =Integer.valueOf(txthinh.getText().toString().trim());
                        double ugia =Double.valueOf(spinnergia.getSelectedItem().toString().trim());
                        Food ufood = new Food(uten,uhinh,ugia);
                        connectDB.foodDAO().insert(ufood);

                        foodList = new ArrayList<>();
                        foodList = connectDB.foodDAO().getall();
                        adapterFood.setData(foodList);

                    }
                });

            }
        });
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sten = txttim.getText().toString();
                foodList = new ArrayList<>();
                foodList = connectDB.foodDAO().searchbyName(sten);
                adapterFood.setData(foodList);
                Log.d("Tim ","Food "+ connectDB.foodDAO().searchbyName(sten).toString());
            }
        });
    }
}