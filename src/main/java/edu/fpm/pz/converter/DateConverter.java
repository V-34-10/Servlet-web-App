package edu.fpm.pz.converter;

import edu.fpm.pz.web.service.ToErrorPage;
import edu.fpm.pz.web.service.UtilLocale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateConverter {
    protected static Logger LOGGER = LoggerFactory.getLogger(DateConverter.class);

    public static Timestamp toDB(Date value) {
        Timestamp ts = new Timestamp(value.getTime());
        return ts;
    }

    public static Date fromDBToDate(Timestamp value) {
        if (value == null) return null;
        return new Date(value.getTime());
    }

    public static Date fromHTMLToDate(String value) throws ParseException {
        DateFormat df = UtilLocale.getInstance().getDf();
        return df.parse(value);
    }

    public static Date fromHTMLToTime(String value) throws ParseException {
        DateFormat tf = UtilLocale.getInstance().getTf();
        return tf.parse(value);
    }

    public static java.sql.Date toDBDate(java.util.Date value) {
        return new java.sql.Date(value.getTime());
    }

    public static java.sql.Time toDBTime(java.util.Date value) {
        return new java.sql.Time(value.getTime());
    }

    public static Date fromDBToDate(java.sql.Date value) {
        if (value == null) return null;
        return new Date(value.getTime());
    }

    public static Date fromDBToTime(java.sql.Time value) {
        if (value == null) return null;
        return new Date(value.getTime());
    }


    public static Date getDate(String value,
                                         HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Date date = null;
        try {
            date = fromHTMLToDate(value);
        } catch (ParseException e) {
            e.printStackTrace();
            ToErrorPage.forward(request, response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        return date;
    }

    public static Date getTime(String value,
                               HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Date time = null;
        try {
            time = fromHTMLToTime(value);
        } catch (ParseException e) {
            e.printStackTrace();
            ToErrorPage.forward(request, response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        return time;
    }
}
