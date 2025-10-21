package br.com.cairu.bean;

import br.com.cairu.dao.CidadeDAO;
import br.com.cairu.dao.PassagemDAO;
import br.com.cairu.model.Cidade;
import br.com.cairu.model.Passagem;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "relatorioBean")
@ViewScoped
public class RelatorioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // Para relatório de faturamento
    private Date dataInicio;
    private Date dataFim;
    private List<Passagem> passagensVendidas;
    private BigDecimal faturamentoTotal;

    // Para relatório de passagens por roteiro
    private Cidade cidadeOrigem;
    private Cidade cidadeDestino;
    private List<Passagem> passagensPorRoteiro;
    private List<Cidade> cidades;

    private PassagemDAO passagemDAO;
    private CidadeDAO cidadeDAO;

    @PostConstruct
    public void init() {
        passagemDAO = new PassagemDAO();
        cidadeDAO = new CidadeDAO();

        passagensVendidas = new ArrayList<>();
        passagensPorRoteiro = new ArrayList<>();
        faturamentoTotal = BigDecimal.ZERO;

        carregarCidades();
    }

    public void carregarCidades() {
        try {
            cidades = cidadeDAO.listarTodos();
        } catch (Exception e) {
            addMessage("Erro ao carregar cidades: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * Consulta de faturamento por período
     */
    public void consultarFaturamento() {
        try {
            if (dataInicio == null || dataFim == null) {
                addMessage("Informe o período (data início e data fim)!", FacesMessage.SEVERITY_WARN);
                return;
            }

            if (dataInicio.after(dataFim)) {
                addMessage("Data início não pode ser maior que data fim!", FacesMessage.SEVERITY_WARN);
                return;
            }

            passagensVendidas = passagemDAO.buscarPassagensVendidasPorPeriodo(dataInicio, dataFim);
            faturamentoTotal = passagemDAO.calcularFaturamentoPorPeriodo(dataInicio, dataFim);

            if (passagensVendidas.isEmpty()) {
                addMessage("Nenhuma passagem vendida no período informado.", FacesMessage.SEVERITY_INFO);
            } else {
                addMessage("Consulta realizada com sucesso! Total de passagens: " +
                          passagensVendidas.size(), FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            addMessage("Erro ao consultar faturamento: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * Consulta de passagens vendidas por roteiro
     */
    public void consultarPassagensPorRoteiro() {
        try {
            if (cidadeOrigem == null || cidadeOrigem.getId() == null) {
                addMessage("Selecione a cidade de origem!", FacesMessage.SEVERITY_WARN);
                return;
            }

            if (cidadeDestino == null || cidadeDestino.getId() == null) {
                addMessage("Selecione a cidade de destino!", FacesMessage.SEVERITY_WARN);
                return;
            }

            if (cidadeOrigem.getId().equals(cidadeDestino.getId())) {
                addMessage("Cidade de origem e destino devem ser diferentes!", FacesMessage.SEVERITY_WARN);
                return;
            }

            passagensPorRoteiro = passagemDAO.buscarPassagensPorRoteiro(
                cidadeOrigem.getId(),
                cidadeDestino.getId()
            );

            if (passagensPorRoteiro.isEmpty()) {
                addMessage("Nenhuma passagem vendida para este roteiro.", FacesMessage.SEVERITY_INFO);
            } else {
                addMessage("Consulta realizada com sucesso! Total de passagens: " +
                          passagensPorRoteiro.size(), FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            addMessage("Erro ao consultar passagens por roteiro: " + e.getMessage(),
                      FacesMessage.SEVERITY_ERROR);
        }
    }

    public void limparFaturamento() {
        dataInicio = null;
        dataFim = null;
        passagensVendidas = new ArrayList<>();
        faturamentoTotal = BigDecimal.ZERO;
    }

    public void limparRoteiro() {
        cidadeOrigem = null;
        cidadeDestino = null;
        passagensPorRoteiro = new ArrayList<>();
    }

    private void addMessage(String mensagem, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(severity, mensagem, null));
    }

    // Getters e Setters
    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public List<Passagem> getPassagensVendidas() {
        return passagensVendidas;
    }

    public void setPassagensVendidas(List<Passagem> passagensVendidas) {
        this.passagensVendidas = passagensVendidas;
    }

    public BigDecimal getFaturamentoTotal() {
        return faturamentoTotal;
    }

    public void setFaturamentoTotal(BigDecimal faturamentoTotal) {
        this.faturamentoTotal = faturamentoTotal;
    }

    public Cidade getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(Cidade cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

    public Cidade getCidadeDestino() {
        return cidadeDestino;
    }

    public void setCidadeDestino(Cidade cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
    }

    public List<Passagem> getPassagensPorRoteiro() {
        return passagensPorRoteiro;
    }

    public void setPassagensPorRoteiro(List<Passagem> passagensPorRoteiro) {
        this.passagensPorRoteiro = passagensPorRoteiro;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
}
