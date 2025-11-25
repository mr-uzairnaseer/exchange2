package org.me.gcu.syed_faiq_ali_s2238968.ui;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.me.gcu.syed_faiq_ali_s2238968.data.ExchangeRepository;
import org.me.gcu.syed_faiq_ali_s2238968.model.CurrencyRate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExchangeViewModel extends ViewModel {
    private static final long AUTO_REFRESH_INTERVAL_MS = 60 * 60 * 1000;
    
    private final ExchangeRepository repository = new ExchangeRepository();
    private final MutableLiveData<List<CurrencyRate>> rates = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> lastUpdated = new MutableLiveData<>("Never");
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final Handler refreshHandler = new Handler(Looper.getMainLooper());
    
    private final Runnable autoRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            refreshRates();
            refreshHandler.postDelayed(this, AUTO_REFRESH_INTERVAL_MS);
        }
    };

    public ExchangeViewModel() {
        startAutoRefresh();
    }

    public LiveData<List<CurrencyRate>> getRates() { return rates; }
    public LiveData<String> getLastUpdated() { return lastUpdated; }
    public LiveData<String> getErrorMessage() { return errorMessage; }

    public void refreshRates() {
        repository.fetchRates(new ExchangeRepository.FetchCallback() {
            @Override
            public void onSuccess(List<CurrencyRate> fetchedRates) {
                Collections.sort(fetchedRates, new Comparator<CurrencyRate>() {
                    @Override
                    public int compare(CurrencyRate o1, CurrencyRate o2) {
                        if (o1.getCode() == null) return 1;
                        if (o2.getCode() == null) return -1;
                        return o1.getCode().compareToIgnoreCase(o2.getCode());
                    }
                });
                
                refreshHandler.post(() -> {
                    rates.setValue(fetchedRates);
                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.UK).format(new Date());
                    lastUpdated.setValue("Last updated: " + time);
                    errorMessage.setValue(null);
                });
            }

            @Override
            public void onError(String message) {
                refreshHandler.post(() -> {
                    errorMessage.setValue(message);
                    rates.setValue(new ArrayList<>());
                });
            }
        });
    }

    public void clearRatesOnNoInternet() {
        rates.setValue(new ArrayList<>());
        errorMessage.setValue(null);  // Don't show error message, Snackbar handles it
    }

    public List<CurrencyRate> filterRates(String query) {
        List<CurrencyRate> all = rates.getValue();
        if (all == null || query == null || query.trim().isEmpty()) {
            return all != null ? all : new ArrayList<>();
        }
        
        String lowerQuery = query.toLowerCase(Locale.ROOT);
        List<CurrencyRate> filtered = new ArrayList<>();
        for (CurrencyRate rate : all) {
            if ((rate.getCode() != null && rate.getCode().toLowerCase(Locale.ROOT).contains(lowerQuery)) ||
                (rate.getTitle() != null && rate.getTitle().toLowerCase(Locale.ROOT).contains(lowerQuery)) ||
                (rate.getDescription() != null && rate.getDescription().toLowerCase(Locale.ROOT).contains(lowerQuery))) {
                filtered.add(rate);
            }
        }
        
        Collections.sort(filtered, new Comparator<CurrencyRate>() {
            @Override
            public int compare(CurrencyRate o1, CurrencyRate o2) {
                if (o1.getCode() == null) return 1;
                if (o2.getCode() == null) return -1;
                return o1.getCode().compareToIgnoreCase(o2.getCode());
            }
        });
        
        return filtered;
    }

    public CurrencyRate findRateByCode(String code) {
        List<CurrencyRate> all = rates.getValue();
        if (all == null || code == null) return null;
        for (CurrencyRate rate : all) {
            if (code.equalsIgnoreCase(rate.getCode())) {
                return rate;
            }
        }
        return null;
    }

    private void startAutoRefresh() {
        refreshRates();
        refreshHandler.postDelayed(autoRefreshRunnable, AUTO_REFRESH_INTERVAL_MS);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        refreshHandler.removeCallbacks(autoRefreshRunnable);
        repository.shutdown();
    }
}
