package org.me.gcu.syed_faiq_ali_s2238968;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import org.me.gcu.syed_faiq_ali_s2238968.model.CurrencyRate;
import org.me.gcu.syed_faiq_ali_s2238968.ui.ConversionDialogFragment;
import org.me.gcu.syed_faiq_ali_s2238968.ui.ExchangeViewModel;
import org.me.gcu.syed_faiq_ali_s2238968.util.CurrencyFlagMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView lastUpdatedText, summaryUsd, summaryEur, summaryJpy;
    private EditText searchEdit;
    private RecyclerView ratesRecycler;
    private Button refreshButton;
    private ExchangeViewModel viewModel;
    private RatesAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lastUpdatedText = findViewById(R.id.lastUpdatedText);
        summaryUsd = findViewById(R.id.summaryUsd);
        summaryEur = findViewById(R.id.summaryEur);
        summaryJpy = findViewById(R.id.summaryJpy);
        searchEdit = findViewById(R.id.searchEdit);
        ratesRecycler = findViewById(R.id.ratesRecycler);
        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(this);
        ratesRecycler.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new RatesAdapter(new ArrayList<>(), rate -> openConversionDialog(rate));
        ratesRecycler.setAdapter(listAdapter);
        findViewById(R.id.searchButton).setOnClickListener(v -> applyFilter());
        viewModel = new ViewModelProvider(this).get(ExchangeViewModel.class);
        viewModel.getRates().observe(this, rates -> { listAdapter.submit(rates); updateSummary(rates); });
        viewModel.getLastUpdated().observe(this, time -> lastUpdatedText.setText(time));
        viewModel.getErrorMessage().observe(this, error -> { if (error != null) Toast.makeText(this, error, Toast.LENGTH_SHORT).show(); });
    }

    @Override
    public void onClick(View aview) {
        if (aview.getId() == R.id.refreshButton) {
            if (isNetworkAvailable()) {
                viewModel.refreshRates();
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
                viewModel.clearRatesOnNoInternet();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void openConversionDialog(CurrencyRate rate) {
        ConversionDialogFragment.newInstance(rate).show(getSupportFragmentManager(), "conversion");
    }

    private void updateSummary(List<CurrencyRate> rates) {
        summaryUsd.setText("USD: " + findRate(rates, "USD"));
        summaryEur.setText("EUR: " + findRate(rates, "EUR"));
        summaryJpy.setText("JPY: " + findRate(rates, "JPY"));
    }

    private String findRate(List<CurrencyRate> rates, String code) {
        for (CurrencyRate rate : rates) if (code.equalsIgnoreCase(rate.getCode())) return String.format(Locale.UK, "%.4f", rate.getRate());
        return "--";
    }

    private void applyFilter() {
        listAdapter.submit(viewModel.filterRates(searchEdit.getText().toString().trim()));
    }

    interface OnRateClickListener { void onClick(CurrencyRate item); }

    static class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.VH> {
        private final List<CurrencyRate> data;
        private final OnRateClickListener listener;
        RatesAdapter(List<CurrencyRate> d, OnRateClickListener l) { this.data = d; this.listener = l; }
        
        static class VH extends RecyclerView.ViewHolder {
            TextView codeText, rateText, titleTextItem;
            ImageView flagImage;
            View root;
            VH(View v) { super(v); root = v; codeText = v.findViewById(R.id.codeText); rateText = v.findViewById(R.id.rateText); titleTextItem = v.findViewById(R.id.titleTextItem); flagImage = v.findViewById(R.id.flagImage); }
        }
        
        @Override public VH onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            return new VH(android.view.LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate, parent, false));
        }
        
        @Override public void onBindViewHolder(VH h, int pos) {
            CurrencyRate item = data.get(pos);
            h.codeText.setText(item.getCode());
            h.rateText.setText(String.format(Locale.UK, "%.4f", item.getRate()));
            h.titleTextItem.setText(item.getTitle());
            h.root.setBackgroundColor(colorFor(item.getRate()));
            h.root.setOnClickListener(v -> listener.onClick(item));
            
            String code = item.getCode();
            int flagResId = CurrencyFlagMapper.getFlagResourceId(h.root.getContext(), code);
            
            if (flagResId != 0) {
                h.flagImage.setImageResource(flagResId);
                h.flagImage.setVisibility(View.VISIBLE);
            } else {
                h.flagImage.setVisibility(View.GONE);
            }
        }
        
        @Override public int getItemCount() { return data.size(); }
        void submit(List<CurrencyRate> items) { data.clear(); data.addAll(items); notifyDataSetChanged(); }
        private static int colorFor(double rate) {
            if (rate < 1.0) return 0xFFE3F2FD;
            if (rate < 5.0) return 0xFFE8F5E9;
            if (rate < 10.0) return 0xFFFFF8E1;
            return 0xFFFFEBEE;
        }
    }
}
