package mew.id.vn.justtesting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import mew.id.vn.justtesting.helpers.DatabaseHelper;

public class CreateUpdateActivity extends AppCompatActivity {
    private TextInputEditText carNumberInput, distanceInput, priceInput, discountInput;
    private Button magicButton, returnButton;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private Invoice invoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_update);
        mapping();
        events();
        if (getIntent().getExtras() != null) {
            setEnable(false);
            magicButton.setText("Sửa");
            invoice = (Invoice) getIntent().getSerializableExtra("Invoice");
            carNumberInput.setText(invoice.getCarNumber());
            distanceInput.setText(String.valueOf(invoice.getDistance()));
            priceInput.setText(String.valueOf((long) invoice.getPrice()));
            discountInput.setText(String.valueOf((int) invoice.getDiscount()));
            magicButton.setEnabled(true);
        } else {
            setEnable(true);
            magicButton.setText("Thêm");
        }
    }

    private void mapping() {
        carNumberInput = (TextInputEditText) findViewById(R.id.cu_car_number);
        distanceInput = (TextInputEditText) findViewById(R.id.cu_distance);
        priceInput = (TextInputEditText) findViewById(R.id.cu_price);
        discountInput = (TextInputEditText) findViewById(R.id.cu_discount);
        magicButton = (Button) findViewById(R.id.magic_button);
        returnButton = (Button) findViewById(R.id.return_button);
    }

    private void events() {
        magicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carNumber = carNumberInput.getText().toString();
                String distance = distanceInput.getText().toString();
                String price = priceInput.getText().toString();
                String discount = discountInput.getText().toString();

                switch (magicButton.getText().toString()) {
                    case "Thêm": {
                        databaseHelper.addInvoice(new Invoice(carNumber, Double.parseDouble(distance), Double.parseDouble(price), Integer.parseInt(discount)));
                        Toast.makeText(CreateUpdateActivity.this, "Thêm mới thành công", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case "Lưu": {
                        databaseHelper.updateInvoice(new Invoice(invoice.getId(), carNumber, Double.parseDouble(distance), Double.parseDouble(price), Integer.parseInt(discount)));
                        Toast.makeText(CreateUpdateActivity.this, "Sửa thành công", Toast.LENGTH_LONG).show();
                        setEnable(false);
                        break;
                    }
                    case "Sửa": {
                        setEnable(true);
                        magicButton.setText("Lưu");
                        break;
                    }
                }
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void setEnable(boolean enable) {
        carNumberInput.setEnabled(enable);
        discountInput.setEnabled(enable);
        distanceInput.setEnabled(enable);
        priceInput.setEnabled(enable);
    }
}