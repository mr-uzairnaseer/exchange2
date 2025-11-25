package org.me.gcu.syed_faiq_ali_s2238968.util;

import java.util.HashMap;
import java.util.Map;

public class CurrencyFlagMapper {
    
    private static final Map<String, String> CURRENCY_TO_COUNTRY = new HashMap<>();
    
    static {
        CURRENCY_TO_COUNTRY.put("USD", "us");  // United States Dollar
        CURRENCY_TO_COUNTRY.put("EUR", "fr");  // Euro (use France flag)
        CURRENCY_TO_COUNTRY.put("JPY", "jp");  // Japanese Yen
        CURRENCY_TO_COUNTRY.put("GBP", "gb");  // British Pound
        CURRENCY_TO_COUNTRY.put("CHF", "ch");  // Swiss Franc
        CURRENCY_TO_COUNTRY.put("CAD", "ca");  // Canadian Dollar
        CURRENCY_TO_COUNTRY.put("AUD", "au");  // Australian Dollar
        CURRENCY_TO_COUNTRY.put("NZD", "nz");  // New Zealand Dollar
        
        // Asian currencies
        CURRENCY_TO_COUNTRY.put("CNY", "cn");  // Chinese Yuan
        CURRENCY_TO_COUNTRY.put("HKD", "hk");  // Hong Kong Dollar
        CURRENCY_TO_COUNTRY.put("SGD", "sg");  // Singapore Dollar
        CURRENCY_TO_COUNTRY.put("INR", "in");  // Indian Rupee
        CURRENCY_TO_COUNTRY.put("PKR", "pk");  // Pakistani Rupee
        CURRENCY_TO_COUNTRY.put("KRW", "kr");  // South Korean Won
        CURRENCY_TO_COUNTRY.put("THB", "th");  // Thai Baht
        CURRENCY_TO_COUNTRY.put("MYR", "my");  // Malaysian Ringgit
        CURRENCY_TO_COUNTRY.put("IDR", "id");  // Indonesian Rupiah
        CURRENCY_TO_COUNTRY.put("PHP", "ph");  // Philippine Peso
        CURRENCY_TO_COUNTRY.put("TWD", "tw");  // Taiwan Dollar
        CURRENCY_TO_COUNTRY.put("KPW", "kp");  // North Korean Won
        CURRENCY_TO_COUNTRY.put("MMK", "mm");  // Myanmar Kyat
        CURRENCY_TO_COUNTRY.put("MNT", "mn");  // Mongolian Tugrik
        CURRENCY_TO_COUNTRY.put("MOP", "mo");  // Macanese Pataca
        CURRENCY_TO_COUNTRY.put("KGS", "kg");  // Kyrgyzstani Som
        
        // Middle Eastern currencies
        CURRENCY_TO_COUNTRY.put("AED", "ae");  // UAE Dirham
        CURRENCY_TO_COUNTRY.put("SAR", "sa");  // Saudi Riyal
        CURRENCY_TO_COUNTRY.put("QAR", "qa");  // Qatari Riyal
        CURRENCY_TO_COUNTRY.put("KWD", "kw");  // Kuwaiti Dinar
        CURRENCY_TO_COUNTRY.put("BHD", "bh");  // Bahraini Dinar
        CURRENCY_TO_COUNTRY.put("OMR", "om");  // Omani Rial
        CURRENCY_TO_COUNTRY.put("ILS", "il");  // Israeli Shekel
        CURRENCY_TO_COUNTRY.put("TRY", "tr");  // Turkish Lira
        CURRENCY_TO_COUNTRY.put("SYP", "sy");  // Syrian Pound
        
        // European currencies
        CURRENCY_TO_COUNTRY.put("NOK", "no");  // Norwegian Krone
        CURRENCY_TO_COUNTRY.put("SEK", "se");  // Swedish Krona
        CURRENCY_TO_COUNTRY.put("DKK", "dk");  // Danish Krone
        CURRENCY_TO_COUNTRY.put("PLN", "pl");  // Polish Zloty
        CURRENCY_TO_COUNTRY.put("CZK", "cz");  // Czech Koruna
        CURRENCY_TO_COUNTRY.put("HUF", "hu");  // Hungarian Forint
        CURRENCY_TO_COUNTRY.put("RON", "ro");  // Romanian Leu
        CURRENCY_TO_COUNTRY.put("BGN", "bg");  // Bulgarian Lev
        CURRENCY_TO_COUNTRY.put("HRK", "hr");  // Croatian Kuna
        CURRENCY_TO_COUNTRY.put("RUB", "ru");  // Russian Ruble
        CURRENCY_TO_COUNTRY.put("ISK", "is");  // Icelandic Króna
        CURRENCY_TO_COUNTRY.put("BYN", "by");  // Belarusian Ruble
        CURRENCY_TO_COUNTRY.put("BYR", "by");  // Belarusian Ruble (old)
        CURRENCY_TO_COUNTRY.put("BAM", "ba");  // Bosnia-Herzegovina Convertible Mark
        
        // American currencies
        CURRENCY_TO_COUNTRY.put("MXN", "mx");  // Mexican Peso
        CURRENCY_TO_COUNTRY.put("BRL", "br");  // Brazilian Real
        CURRENCY_TO_COUNTRY.put("ARS", "ar");  // Argentine Peso
        CURRENCY_TO_COUNTRY.put("CLP", "cl");  // Chilean Peso
        CURRENCY_TO_COUNTRY.put("COP", "co");  // Colombian Peso
        CURRENCY_TO_COUNTRY.put("PEN", "pe");  // Peruvian Sol
        CURRENCY_TO_COUNTRY.put("AWG", "aw");  // Aruban Florin
        CURRENCY_TO_COUNTRY.put("BMD", "bm");  // Bermudian Dollar
        CURRENCY_TO_COUNTRY.put("PAB", "pa");  // Panamanian Balboa
        
        // African currencies
        CURRENCY_TO_COUNTRY.put("ZAR", "za");  // South African Rand
        CURRENCY_TO_COUNTRY.put("EGP", "eg");  // Egyptian Pound
        CURRENCY_TO_COUNTRY.put("NGN", "ng");  // Nigerian Naira
        CURRENCY_TO_COUNTRY.put("MAD", "ma");  // Moroccan Dirham
        CURRENCY_TO_COUNTRY.put("TND", "tn");  // Tunisian Dinar
        CURRENCY_TO_COUNTRY.put("KES", "ke");  // Kenyan Shilling
        CURRENCY_TO_COUNTRY.put("DZD", "dz");  // Algerian Dinar
        CURRENCY_TO_COUNTRY.put("LSL", "ls");  // Lesotho Loti
        CURRENCY_TO_COUNTRY.put("LRD", "lr");  // Liberian Dollar
        CURRENCY_TO_COUNTRY.put("LYD", "ly");  // Libyan Dinar
        CURRENCY_TO_COUNTRY.put("MWK", "mw");  // Malawian Kwacha
        CURRENCY_TO_COUNTRY.put("MRO", "mr");  // Mauritanian Ouguiya
        CURRENCY_TO_COUNTRY.put("RWF", "rw");  // Rwandan Franc
        CURRENCY_TO_COUNTRY.put("SOS", "so");  // Somali Shilling
        CURRENCY_TO_COUNTRY.put("SDG", "sd");  // Sudanese Pound
        CURRENCY_TO_COUNTRY.put("SZL", "sz");
        CURRENCY_TO_COUNTRY.put("GHS", "gh");
        CURRENCY_TO_COUNTRY.put("AOA", "ao");
        CURRENCY_TO_COUNTRY.put("CDF", "cd");
        CURRENCY_TO_COUNTRY.put("ERN", "er");
        CURRENCY_TO_COUNTRY.put("MGA", "mg");
        CURRENCY_TO_COUNTRY.put("MZN", "mz");
        CURRENCY_TO_COUNTRY.put("ZMW", "zm");
        CURRENCY_TO_COUNTRY.put("SHP", "sh");
        CURRENCY_TO_COUNTRY.put("STD", "st");
        CURRENCY_TO_COUNTRY.put("ZWD", "zw");
        
        CURRENCY_TO_COUNTRY.put("WST", "ws");
        CURRENCY_TO_COUNTRY.put("SBD", "sb");
        CURRENCY_TO_COUNTRY.put("TOP", "to");
        CURRENCY_TO_COUNTRY.put("VUV", "vu");
        CURRENCY_TO_COUNTRY.put("XPF", "pf");
        CURRENCY_TO_COUNTRY.put("FJD", "fj");
        CURRENCY_TO_COUNTRY.put("FKP", "fk");
        
        CURRENCY_TO_COUNTRY.put("AFN", "af");
        CURRENCY_TO_COUNTRY.put("AMD", "am");
        CURRENCY_TO_COUNTRY.put("AZN", "az");
        CURRENCY_TO_COUNTRY.put("GEL", "ge");
        CURRENCY_TO_COUNTRY.put("TJS", "tj");
        CURRENCY_TO_COUNTRY.put("TMT", "tm");
        
        CURRENCY_TO_COUNTRY.put("SRD", "sr");
        
        CURRENCY_TO_COUNTRY.put("EEK", "ee");
        CURRENCY_TO_COUNTRY.put("HNL", "hn");
        CURRENCY_TO_COUNTRY.put("DOP", "do");
        CURRENCY_TO_COUNTRY.put("ANG", "cw");
        CURRENCY_TO_COUNTRY.put("BDT", "bd");
        
        CURRENCY_TO_COUNTRY.put("BTC", "btc");
        
        CURRENCY_TO_COUNTRY.put("XOF", "sn");
        CURRENCY_TO_COUNTRY.put("XAF", "cm");
    }
    
    public static String getFlagResourceName(String currencyCode) {
        if (currencyCode == null || currencyCode.isEmpty()) {
            return null;
        }
        
        String countryCode = CURRENCY_TO_COUNTRY.get(currencyCode.toUpperCase());
        if (countryCode != null) {
            return "flag_" + countryCode;
        }
        
        return null;
    }
    
    public static int getFlagResourceId(android.content.Context context, String currencyCode) {
        String resourceName = getFlagResourceName(currencyCode);
        if (resourceName != null) {
            return context.getResources().getIdentifier(
                resourceName, 
                "drawable", 
                context.getPackageName()
            );
        }
        return 0;
    }
    
    public static boolean hasFlagMapping(String currencyCode) {
        return currencyCode != null && 
               CURRENCY_TO_COUNTRY.containsKey(currencyCode.toUpperCase());
    }
    
    public static String getCountryCode(String currencyCode) {
        if (currencyCode == null) return null;
        return CURRENCY_TO_COUNTRY.get(currencyCode.toUpperCase());
    }
}
