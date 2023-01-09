package edu.fpm.pz.web.filter;

import edu.fpm.pz.config.SecurityConfig;
import edu.fpm.pz.web.service.UtilLocale;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;


public class SecurityFilter implements Filter {

    private final SecurityConfig securityConfig = new SecurityConfig();

    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        session.setAttribute("locale", UtilLocale.getInstance().getRequestLocale(httpRequest).getLanguage());
        if (UtilLocale.getInstance().getRequestLocale(httpRequest).getLanguage() == "ru")
            session.setAttribute("moment", "L");
        else
            session.setAttribute("moment", "ll");

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String url = httpRequest.getRequestURL().toString();
        if (shouldBeProtected(url)) {
            if (session == null || session.getAttribute("user") == null) {
                httpResponse.sendRedirect("/login");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    private boolean shouldBeProtected(String url) {
        Set<String> protectedURLs = securityConfig.getProtectedURLs();
        return protectedURLs.stream().anyMatch(url::endsWith);
    }
}
