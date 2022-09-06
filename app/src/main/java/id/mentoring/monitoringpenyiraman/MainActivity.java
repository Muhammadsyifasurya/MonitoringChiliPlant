package id.mentoring.monitoringpenyiraman;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvLembab;
    private TextView mTvPupuk;
    private MaterialButton mBtnPupuk;
    private MaterialButton mBtnSiram;
    private MaterialButton mBtnOtomatis;
    private String mLembabValue;
    private String mPupukValue;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initPresenter();

        mBtnPupuk.setOnClickListener(this);
        mBtnOtomatis.setOnClickListener(this);
        mBtnSiram.setOnClickListener(this);
    }

    private void initPresenter() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mLembabValue = snapshot.child("Data/LembabValue").getValue().toString();
                mTvLembab.setText(mLembabValue);

                mPupukValue = snapshot.child("Data/PupukValue").getValue().toString();
                mTvPupuk.setText(mPupukValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initView() {
        mTvLembab = findViewById(R.id.TvLembab);
        mTvPupuk = findViewById(R.id.TvPupuk);
        mBtnPupuk = findViewById(R.id.BtnPupuk);
        mBtnSiram = findViewById(R.id.BtnSiram);
        mBtnOtomatis = findViewById(R.id.BtnOtomatis);
    }

    @Override
    public void onClick(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        switch (view.getId()){
            case R.id.BtnPupuk:
                DatabaseReference BtnPupuk = database.getReference("Relay/Pupuk");
                String BtnPupukTxt =mBtnPupuk.getText().toString();
                BtnPupuk.setValue(true);
                Toast.makeText(MainActivity.this, "Berhasil Menambahkan Pupuk", Toast.LENGTH_SHORT).show();

            break;

            case R.id.BtnSiram:
                DatabaseReference BtnSiram = database.getReference("Relay/Siram");
                String BtnSiramTxt = mBtnSiram.getText().toString();
                BtnSiram.setValue(true);
                Toast.makeText(MainActivity.this, "Berhasil Menyiram", Toast.LENGTH_SHORT).show();

            break;

            case R.id.BtnOtomatis:
                DatabaseReference BtnOtomatis = database.getReference("Relay/Otomatis");
                String BtnOtomatisTxt = mBtnOtomatis.getText().toString();
                if(BtnOtomatisTxt.equals("Otomatis")){
                    BtnOtomatis.setValue(true);
                    mBtnOtomatis.setText("Manual");
                    mBtnSiram.setEnabled(true);
                    mBtnPupuk.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Manual", Toast.LENGTH_SHORT).show();

                } else {
                    BtnOtomatis.setValue(false);
                    mBtnOtomatis.setText("Otomatis");
                    mBtnSiram.setEnabled(false);
                    mBtnPupuk.setEnabled(false);
                    Toast.makeText(MainActivity.this, "Otomatis", Toast.LENGTH_SHORT).show(); }

            break;
        }
    }
}