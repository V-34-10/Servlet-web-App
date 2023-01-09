package edu.fpm.pz.CustomTag;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LocaleTag {
    private static String locale;
    private static String basename;
    private static String var;
    private static String key;
    private static String bundle;
    private static boolean quoted = false;

    private static Map<String, ResourceBundle> bundles = new HashMap<>();

    public static class TagSetLocale extends TagSupport {
        public void setValue(String value) {
            LocaleTag.locale = value;
        }

        public int doStartTag() {
            return SKIP_BODY;
        }
    }

    public static class TagSetBundle extends TagSupport {
        public void setBasename(String basename) {
            LocaleTag.basename = basename;
        }

        public void setVar(String var) {
            LocaleTag.var = var;
        }

        public int doStartTag() {

            ResourceBundle bundle = create();
            if (bundle != null) bundles.put(LocaleTag.var, bundle);
            return SKIP_BODY;
        }
    }

    public static class TagMessage extends TagSupport {
        public void setKey(String key) {
            LocaleTag.key = key;
        }

        public void setBundle(String bundle) {
            LocaleTag.bundle = bundle;
        }

        public void setQuoted(String quoted) {
            LocaleTag.quoted = Boolean.parseBoolean(quoted);
        }

        public int doStartTag() {

            try {
                ResourceBundle bundle = bundles.get(LocaleTag.bundle);
                if (bundle != null) {
                    if (LocaleTag.quoted)
                        pageContext.getOut().write("\"" + bundle.getString(LocaleTag.key) + "\"");
                    else
                        pageContext.getOut().write(bundle.getString(LocaleTag.key));
                }
                LocaleTag.quoted = false;
            } catch (IOException e) {
                LocaleTag.quoted = false;
                e.printStackTrace();
            }

            return SKIP_BODY;
        }
    }

    protected static ResourceBundle create() {
        String bundleName = basename;
        if (locale != null && !"en".equals(locale)) bundleName += "_" + locale;
        String resourceName = "/" + bundleName + ".properties";
        ResourceBundle bundle = null;

        try {
            InputStream stream = LocaleTag.class.getResourceAsStream(resourceName);
            if (stream != null) {
                try {
                    bundle = new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
                } finally {
                    stream.close();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bundle;
    }
}
