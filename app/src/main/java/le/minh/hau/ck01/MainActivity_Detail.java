package le.minh.hau.ck01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity_Detail extends AppCompatActivity {

    ImageView hinh,btncongsl,btntrusl;
    TextView lbtendetail,lbgiadetail,lbsldetai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__detail);
        hinh = findViewById(R.id.imgHinhDetail);
        btncongsl = findViewById(R.id.imageView8);
        btntrusl = findViewById(R.id.imageView10);
        lbtendetail = findViewById(R.id.lbTenDetail);
        lbgiadetail = findViewById(R.id.lbGiaDetail);
        lbsldetai = findViewById(R.id.textView2);


        Intent intent = getIntent();
        Food food = (Food) intent.getSerializableExtra("food");

        hinh.setImageResource(food.getHinh());
        lbtendetail.setText(food.getTen());
        lbgiadetail.setText(String.valueOf(food.getGia()));


    }
}