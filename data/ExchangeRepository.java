package org.me.gcu.syed_faiq_ali_s2238968.data;

import org.me.gcu.syed_faiq_ali_s2238968.model.CurrencyRate;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangeRepository {
    private static final String RSS_URL = "https://www.fx-exchange.com/gbp/rss.xml";
    
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private List<CurrencyRate> cachedRates = new ArrayList<>();

    public interface FetchCallback {
        void onSuccess(List<CurrencyRate> rates);
        void onError(String message);
    }

    public void fetchRates(FetchCallback callback) {
        executor.execute(() -> {
            try {
                String xml = downloadXml(RSS_URL);
                List<CurrencyRate> rates = parseXml(xml);
                cachedRates = rates;
                callback.onSuccess(rates);
            } catch (Exception e) {
                callback.onError("Failed to fetch rates: " + e.getMessage());
            }
        });
    }

    private String downloadXml(String urlString) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (connection instanceof java.net.HttpURLConnection) {
                ((java.net.HttpURLConnection) connection).disconnect();
            }
        }

        String xml = result.toString();
        int start = xml.indexOf("<?");
        if (start > 0) xml = xml.substring(start);
        int end = xml.indexOf("</rss>");
        if (end > 0) xml = xml.substring(0, end + 6);
        
        return xml;
    }

    private List<CurrencyRate> parseXml(String xml) {
        List<CurrencyRate> rates = new ArrayList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xml));
            
            int eventType = xpp.getEventType();
            CurrencyRate current = null;
            
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String name = xpp.getName();
                    if (name.equalsIgnoreCase("item")) {
                        current = new CurrencyRate();
                    } else if (current != null) {
                        if (name.equalsIgnoreCase("title")) {
                            current.setTitle(xpp.nextText());
                            parseTitle(current);
                        } else if (name.equalsIgnoreCase("description")) {
                            String desc = xpp.nextText();
                            current.setDescription(desc);
                            
                            if (current.getRate() == 0.0) {
                                Double r = extractRateFromDescription(desc);
                                if (r != null && r > 0) {
                                    current.setRate(r);
                                }
                            }
                        } else if (name.equalsIgnoreCase("pubDate")) {
                            current.setPubDate(xpp.nextText());
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item") && current != null && current.getCode() != null) {
                        rates.add(current);
                        current = null;
                    }
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
        }
        return rates;
    }

    private void parseTitle(CurrencyRate item) {
        String title = item.getTitle();
        if (title == null) return;
        
        try {
            String t = title.trim();
            
            int slash = t.indexOf('/');
            if (slash > 0) {
                int openParen = t.indexOf('(', slash);
                int closeParen = t.indexOf(')', openParen);
                
                if (openParen > 0 && closeParen > openParen) {
                    String code = t.substring(openParen + 1, closeParen).trim();
                    if (code.length() >= 3) {
                        item.setCode(code.toUpperCase());
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    private Double extractFirstNumber(String text) {
        if (text == null) return null;
        StringBuilder sb = new StringBuilder();
        boolean dotSeen = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if ((c >= '0' && c <= '9') || (c == '.' && !dotSeen)) {
                sb.append(c);
                if (c == '.') dotSeen = true;
            } else if (sb.length() > 0) {
                break;
            }
        }
        try {
            return sb.length() > 0 ? Double.parseDouble(sb.toString()) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private Double extractRateFromDescription(String description) {
        if (description == null) return null;
        
        try {
            int equalIndex = description.indexOf('=');
            
            if (equalIndex > 0) {
                String afterEquals = description.substring(equalIndex + 1).trim();
                StringBuilder rateStr = new StringBuilder();
                boolean dotSeen = false;
                
                for (int i = 0; i < afterEquals.length(); i++) {
                    char c = afterEquals.charAt(i);
                    if ((c >= '0' && c <= '9') || (c == '.' && !dotSeen)) {
                        rateStr.append(c);
                        if (c == '.') dotSeen = true;
                    } else if (rateStr.length() > 0) {
                        break;
                    }
                }
                
                if (rateStr.length() > 0) {
                    return Double.parseDouble(rateStr.toString());
                }
            }
        } catch (Exception e) {
        }
        
        return null;
    }

    public List<CurrencyRate> getCachedRates() {
        return new ArrayList<>(cachedRates);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
