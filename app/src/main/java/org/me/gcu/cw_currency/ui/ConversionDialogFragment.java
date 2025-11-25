package org.me.gcu.syed_faiq_ali_s2238968.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.me.gcu.syed_faiq_ali_s2238968.R;
import org.me.gcu.syed_faiq_ali_s2238968.model.CurrencyRate;

import java.util.Locale;

public class ConversionDialogFragment extends DialogFragment {
    private static final String ARG_CODE = "code";
    private static final String ARG_RATE = "rate";

    private String currencyCode;
    private double exchangeRate;
    private boolean gbpToCurrency = true;

    private EditText amountInput;
    private TextView resultText;
    private Button directionButton;
    private TextView directionLabel;

    public static ConversionDialogFragment newInstance(CurrencyRate rate) {
        ConversionDialogFragment fragment = new ConversionDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CODE, rate.getCode());
        args.putDouble(ARG_RATE, rate.getRate());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currencyCode = getArguments().getString(ARG_CODE);
            exchangeRate = getArguments().getDouble(ARG_RATE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_conversion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView titleText = view.findViewById(R.id.conversionTitle);
        amountInput = view.findViewById(R.id.amountInput);
        resultText = view.findViewById(R.id.resultText);
        directionButton = view.findViewById(R.id.directionButton);
        directionLabel = view.findViewById(R.id.directionLabel);
        Button convertButton = view.findViewById(R.id.convertButton);
        Button closeButton = view.findViewById(R.id.closeButton);

        titleText.setText("Convert GBP ↔ " + currencyCode);
        updateDirectionUI();

        directionButton.setOnClickListener(v -> {
            gbpToCurrency = !gbpToCurrency;
            updateDirectionUI();
            resultText.setText("");
        });

        convertButton.setOnClickListener(v -> performConversion());
        closeButton.setOnClickListener(v -> dismiss());
    }

    private void updateDirectionUI() {
        if (gbpToCurrency) {
            directionLabel.setText("GBP → " + currencyCode);
            directionButton.setText(" " + currencyCode + " → GBP");
        } else {
            directionLabel.setText(currencyCode + " → GBP");
            directionButton.setText("  GBP → " + currencyCode);
        }
    }

    private void performConversion() {
        String input = amountInput.getText().toString().trim();
        
        if (TextUtils.isEmpty(input)) {
            Toast.makeText(getContext(), "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double amount = Double.parseDouble(input);
            if (amount < 0) {
                Toast.makeText(getContext(), "Amount must be positive", Toast.LENGTH_SHORT).show();
                return;
            }

            double result;
            String resultString;
            
            if (gbpToCurrency) {
                result = amount * exchangeRate;
                resultString = String.format(Locale.UK, "%.2f GBP = %.2f %s", amount, result, currencyCode);
            } else {
                result = amount / exchangeRate;
                resultString = String.format(Locale.UK, "%.2f %s = %.2f GBP", amount, currencyCode, result);
            }
            
            resultText.setText(resultString);
            
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid number format", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
