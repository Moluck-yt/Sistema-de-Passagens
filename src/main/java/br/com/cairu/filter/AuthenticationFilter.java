package br.com.cairu.filter;

import br.com.cairu.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filtro de autenticacao para verificar se o usuario esta logado
 * antes de acessar paginas protegidas
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"*.xhtml"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicializacao do filtro, se necessario
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // Verificar se e uma pagina publica (nao precisa autenticacao)
        if (isPaginaPublica(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // Verificar se e um recurso estatico (CSS, JS, imagens)
        if (isRecursoEstatico(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // Verificar se o usuario esta logado
        HttpSession session = httpRequest.getSession(false);
        Usuario usuarioLogado = null;

        if (session != null) {
            usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        }

        // Se nao estiver logado, redirecionar para login
        if (usuarioLogado == null) {
            String contextPath = httpRequest.getContextPath();
            String loginURL = httpRequest.getScheme() + "://" +
                             httpRequest.getServerName() + ":" +
                             httpRequest.getServerPort() +
                             contextPath + "/login.xhtml";
            httpResponse.sendRedirect(loginURL);
            return;
        }

        // Usuario autenticado, permitir acesso
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Limpeza do filtro, se necessario
    }

    /**
     * Verifica se a pagina e publica (nao precisa autenticacao)
     */
    private boolean isPaginaPublica(String requestURI) {
        return requestURI.contains("login.xhtml") ||
               requestURI.contains("javax.faces.resource") ||
               requestURI.contains("jakarta.faces.resource");
    }

    /**
     * Verifica se e um recurso estatico
     */
    private boolean isRecursoEstatico(String requestURI) {
        return requestURI.contains("/resources/") ||
               requestURI.contains("/css/") ||
               requestURI.contains("/js/") ||
               requestURI.contains("/images/") ||
               requestURI.endsWith(".css") ||
               requestURI.endsWith(".js") ||
               requestURI.endsWith(".png") ||
               requestURI.endsWith(".jpg") ||
               requestURI.endsWith(".jpeg") ||
               requestURI.endsWith(".gif") ||
               requestURI.endsWith(".ico");
    }
}
