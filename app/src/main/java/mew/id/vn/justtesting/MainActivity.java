package mew.id.vn.justtesting;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import mew.id.vn.justtesting.adapters.InvoiceAdapter;
import mew.id.vn.justtesting.helpers.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText seachBox;
    private ListView listView;
    private FloatingActionButton addButton;
    private ArrayList<Invoice> invoices = new ArrayList<>();
    private InvoiceAdapter adapter;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    private ActivityResultLauncher<Intent> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        loadTable();
        adapter = new InvoiceAdapter(this, invoices);
        listView.setAdapter(adapter);
        events();
    }

    private void mapping() {
        seachBox = (TextInputEditText) findViewById(R.id.id_search_box);
        listView = (ListView) findViewById(R.id.id_listview);
        addButton = (FloatingActionButton) findViewById(R.id.id_add_button);
        result = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == Activity.RESULT_OK) {
                    loadTable();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void events() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.launch(new Intent(MainActivity.this, CreateUpdateActivity.class));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc chắn muốn xoá?");
                builder.setPositiveButton("Thực hiện", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        invoices.stream().filter(i -> i.getTotalPrice() <= adapter.getItem(position).getTotalPrice()).forEach(i -> databaseHelper.deleteInvoice(i));
                        Toast.makeText(MainActivity.this, "Đã xoá thành công!", Toast.LENGTH_SHORT).show();
                        loadTable();
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, CreateUpdateActivity.class);
                intent.putExtra("Invoice", adapter.getItem(position));
                result.launch(intent);
            }
        });

        seachBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!seachBox.getText().toString().isEmpty()) {
                    ArrayList<Invoice> invoices1 = (invoices.stream()
                            .filter(i -> i.getTotalPrice() < Double.parseDouble(seachBox.getText().toString()))
                            .collect(Collectors.toCollection(ArrayList::new)));
                    InvoiceAdapter adapter1 = new InvoiceAdapter(MainActivity.this, invoices1);
                    listView.setAdapter(adapter1);
                } else {
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    private void loadTable()  {
        if (databaseHelper.getAll().size() == 0) {
            databaseHelper.addInvoice(new Invoice( "30K1-19827", 12.5, 2000, 10));
            databaseHelper.addInvoice(new Invoice( "32K1-72813", 10, 3000, 13));
            databaseHelper.addInvoice(new Invoice( "34K1-23489", 20, 4000, 15));
            databaseHelper.addInvoice(new Invoice( "80K1-83723", 16.2, 1000, 16));
            databaseHelper.addInvoice(new Invoice( "72K1-27834", 17, 5000, 18));
            databaseHelper.addInvoice(new Invoice( "98K1-89012", 50, 9000, 17));
            databaseHelper.addInvoice(new Invoice( "99K1-81290", 29.2, 1000, 20));
            databaseHelper.addInvoice(new Invoice( "23K1-38272", 19.2, 2000, 11));
            databaseHelper.addInvoice(new Invoice( "12K1-02932", 30.1, 4000, 12));
        }
        invoices.clear();
        invoices.addAll(databaseHelper.getAll());
        Collections.sort(invoices, Comparator.comparing(Invoice::getTotalPrice).reversed());
    }
}