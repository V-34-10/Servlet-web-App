package edu.fpm.pz.web.service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.*;

public class UtilLocale {
    private Set<String> AcceptLocales;
    private Locale defaultLocale;
    private DateFormat df;
    private DateFormat tf;
    private DateFormat dtf;
    private DateFormat defaultDateFormat;
    private DateFormat defaultTimeFormat;

    private UtilLocale() {
        AcceptLocales = new HashSet<>();
        AcceptLocales.add("en");
        AcceptLocales.add("ru");
        defaultLocale = new Locale("en");
        df = defaultDateFormat = DateFormat.getDateInstance(DateFormat.SHORT, defaultLocale);
        tf = defaultTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, defaultLocale);
    }

    private static UtilLocale instance;

    public static UtilLocale getInstance() {
        if (instance == null) instance = new UtilLocale();
        return instance;
    }

    public Set<String> getAcceptLocales() {
        return AcceptLocales;
    }

    private List<Locale> getRequestLocales(HttpServletRequest request) {
        final List<Locale> acceptedLocales = new ArrayList<>();
        final String userLocale = request.getHeader("Accept-Language");
        if (userLocale != null) {
            final List<Locale.LanguageRange> ranges = Locale.LanguageRange.parse(userLocale);

            if (ranges != null) {
                Iterator<Locale.LanguageRange> iter = ranges.iterator();
                while (iter.hasNext()) {
                    Locale.LanguageRange languageRange = iter.next();
                    final String localeString = languageRange.getRange();
                    final Locale locale = Locale.forLanguageTag(localeString);
                    acceptedLocales.add(locale);
                }
            }
        }
        return acceptedLocales;
    }

    public Locale getRequestLocale(HttpServletRequest request) {
        Locale result = null;
        List<Locale> localeList = getRequestLocales(request);
        Iterator<Locale> iter = localeList.iterator();
        while (iter.hasNext()) {
            Locale locale = iter.next();
            if (AcceptLocales.contains(locale.getLanguage())) {
                result = locale;
                break;
            }
        }
        if (result == null) result = defaultLocale;
        dtf = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, result);
        df = DateFormat.getDateInstance(DateFormat.MEDIUM, result);
        tf = DateFormat.getTimeInstance(DateFormat.SHORT, result);
        return result;
    }

    public DateFormat getDf() {
        return df;
    }

    public DateFormat getTf() {
        return tf;
    }

}
