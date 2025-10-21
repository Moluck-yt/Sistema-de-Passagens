package br.com.cairu.bean;

import br.com.cairu.dao.UsuarioDAO;
import br.com.cairu.model.Usuario;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private Usuario usuarioLogado;
    private UsuarioDAO usuarioDAO;

    public LoginBean() {
        usuarioDAO = new UsuarioDAO();
    }

    public String autenticar() {
        try {
            if (login == null || login.trim().isEmpty()) {
                addMessage("Informe o login!", FacesMessage.SEVERITY_WARN);
                return null;
            }

            if (senha == null || senha.trim().isEmpty()) {
                addMessage("Informe a senha!", FacesMessage.SEVERITY_WARN);
                return null;
            }

            usuarioLogado = usuarioDAO.autenticar(login, senha);

            if (usuarioLogado != null) {
                // Login bem-sucedido
                FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("usuarioLogado", usuarioLogado);
                return "index?faces-redirect=true";
            } else {
                addMessage("Login ou senha inválidos!", FacesMessage.SEVERITY_ERROR);
                return null;
            }
        } catch (Exception e) {
            addMessage("Erro ao autenticar: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
            return null;
        }
    }

    public String logout() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "login?faces-redirect=true";
        } catch (Exception e) {
            addMessage("Erro ao fazer logout: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
            return null;
        }
    }

    public boolean isLogado() {
        return usuarioLogado != null;
    }

    public void verificarSessao() {
        if (!isLogado()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("login.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addMessage(String mensagem, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(severity, mensagem, null));
    }

    // Getters e Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
