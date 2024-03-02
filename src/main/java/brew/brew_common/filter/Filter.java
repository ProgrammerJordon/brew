package brew.brew_common.filter;

import jakarta.servlet.*;

import java.io.IOException;

public interface Filter {
    void init(FilterConfig var1) throws ServletException;

    void doFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException;

    void destroy();
}
