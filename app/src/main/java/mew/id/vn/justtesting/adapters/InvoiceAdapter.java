package mew.id.vn.justtesting.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import mew.id.vn.justtesting.Invoice;
import mew.id.vn.justtesting.R;

public class InvoiceAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Invoice> invoices;

    public InvoiceAdapter(Context context, ArrayList<Invoice> invoices) {
        this.context = context;
        this.invoices = invoices;
    }

    @Override
    public int getCount() {
        return invoices.size();
    }

    @Override
    public Invoice getItem(int position) {
        return invoices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.invoice_item, parent, false);
        }

        Invoice invoice = getItem(position);
        TextView carNumber = (TextView) convertView.findViewById(R.id.id_car_number);
        TextView distance = (TextView) convertView.findViewById(R.id.id_distance);
        TextView price = (TextView) convertView.findViewById(R.id.id_price);

        carNumber.setText(invoice.getCarNumber());
        distance.setText("Quãng đường: " + String.valueOf(invoice.getDistance()) + " km");
        java.util.Currency vnd = java.util.Currency.getInstance("VND");
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(new Locale("vi", "vn"));
        format.setCurrency(vnd);
        price.setText(format.format(invoice.getTotalPrice()));

        return convertView;
    }
}
