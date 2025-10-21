package br.com.cairu.bean;

import br.com.cairu.dao.CidadeDAO;
import br.com.cairu.model.Cidade;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "cidadeBean")
@ViewScoped
public class CidadeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Cidade cidade;
    private List<Cidade> cidades;
    private CidadeDAO cidadeDAO;

    @PostConstruct
    public void init() {
        cidadeDAO = new CidadeDAO();
        cidade = new Cidade();
        carregarCidades();
    }

    public void carregarCidades() {
        try {
            cidades = cidadeDAO.listarTodos();
        } catch (Exception e) {
            addMessage("Erro ao carregar cidades: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void salvar() {
        try {
            if (cidade.getId() == null) {
                // Verificar se já existe cidade com o mesmo ID IBGE
                Cidade cidadeExistente = cidadeDAO.buscarPorIdIBGE(cidade.getIdCidade());
                if (cidadeExistente != null) {
                    addMessage("Já existe uma cidade com este código IBGE!", FacesMessage.SEVERITY_WARN);
                    return;
                }
                cidadeDAO.salvar(cidade);
                addMessage("Cidade cadastrada com sucesso!", FacesMessage.SEVERITY_INFO);
            } else {
                cidadeDAO.atualizar(cidade);
                addMessage("Cidade atualizada com sucesso!", FacesMessage.SEVERITY_INFO);
            }
            limpar();
            carregarCidades();
        } catch (Exception e) {
            addMessage("Erro ao salvar cidade: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void excluir() {
        try {
            cidadeDAO.excluir(cidade.getId());
            addMessage("Cidade excluída com sucesso!", FacesMessage.SEVERITY_INFO);
            limpar();
            carregarCidades();
        } catch (Exception e) {
            addMessage("Erro ao excluir cidade: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Cidade cidade) {
        this.cidade = cidade;
    }

    public void limpar() {
        cidade = new Cidade();
    }

    private void addMessage(String mensagem, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(severity, mensagem, null));
    }

    // Getters e Setters
    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
}
