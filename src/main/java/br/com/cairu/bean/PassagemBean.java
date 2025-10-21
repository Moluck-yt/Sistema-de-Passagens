package br.com.cairu.bean;

import br.com.cairu.dao.CidadeDAO;
import br.com.cairu.dao.PassagemDAO;
import br.com.cairu.dao.VeiculoDAO;
import br.com.cairu.model.Cidade;
import br.com.cairu.model.Passagem;
import br.com.cairu.model.Veiculo;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "passagemBean")
@ViewScoped
public class PassagemBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Passagem passagem;
    private List<Passagem> passagens;
    private List<Veiculo> veiculos;
    private List<Cidade> cidades;

    private PassagemDAO passagemDAO;
    private VeiculoDAO veiculoDAO;
    private CidadeDAO cidadeDAO;

    @PostConstruct
    public void init() {
        passagemDAO = new PassagemDAO();
        veiculoDAO = new VeiculoDAO();
        cidadeDAO = new CidadeDAO();

        passagem = new Passagem();
        carregarPassagens();
        carregarVeiculos();
        carregarCidades();
    }

    public void carregarPassagens() {
        try {
            passagens = passagemDAO.listarTodos();
        } catch (Exception e) {
            addMessage("Erro ao carregar passagens: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void carregarVeiculos() {
        try {
            veiculos = veiculoDAO.listarTodos();
        } catch (Exception e) {
            addMessage("Erro ao carregar veículos: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
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
            // Validações de regras de negócio
            if (!validarPassagem()) {
                return;
            }

            if (passagem.getIdPassagem() == null) {
                // Nova passagem - marcar como vendida com data atual
                passagem.setVendida(true);
                passagem.setDataVenda(new Date());
                passagemDAO.salvar(passagem);
                addMessage("Passagem vendida com sucesso!", FacesMessage.SEVERITY_INFO);
            } else {
                passagemDAO.atualizar(passagem);
                addMessage("Passagem atualizada com sucesso!", FacesMessage.SEVERITY_INFO);
            }

            limpar();
            carregarPassagens();
        } catch (Exception e) {
            addMessage("Erro ao salvar passagem: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    private boolean validarPassagem() {
        // Validar se veículo foi selecionado
        if (passagem.getVeiculo() == null || passagem.getVeiculo().getId() == null) {
            addMessage("Selecione um veículo!", FacesMessage.SEVERITY_WARN);
            return false;
        }

        // Validar se cidades foram selecionadas
        if (passagem.getCidadeOrigem() == null || passagem.getCidadeOrigem().getId() == null) {
            addMessage("Selecione a cidade de origem!", FacesMessage.SEVERITY_WARN);
            return false;
        }

        if (passagem.getCidadeDestino() == null || passagem.getCidadeDestino().getId() == null) {
            addMessage("Selecione a cidade de destino!", FacesMessage.SEVERITY_WARN);
            return false;
        }

        // Validar se origem e destino são diferentes
        if (passagem.getCidadeOrigem().getId().equals(passagem.getCidadeDestino().getId())) {
            addMessage("Cidade de origem e destino devem ser diferentes!", FacesMessage.SEVERITY_WARN);
            return false;
        }

        // Validar número da poltrona
        if (passagem.getPoltrona() == null || passagem.getPoltrona() < 1) {
            addMessage("Número da poltrona deve ser maior que zero!", FacesMessage.SEVERITY_WARN);
            return false;
        }

        // Validar se poltrona não excede o limite do veículo
        if (passagem.getPoltrona() > passagem.getVeiculo().getQtdPoltronas()) {
            addMessage("Número da poltrona excede o limite do veículo (" +
                      passagem.getVeiculo().getQtdPoltronas() + " poltronas)!",
                      FacesMessage.SEVERITY_WARN);
            return false;
        }

        // Validar se a poltrona já foi vendida
        if (passagemDAO.isPoltronaJaVendida(
                passagem.getVeiculo().getId(),
                passagem.getPoltrona(),
                passagem.getDataSaida(),
                passagem.getHoraSaida())) {
            addMessage("Esta poltrona já foi vendida para este veículo, data e horário!",
                      FacesMessage.SEVERITY_WARN);
            return false;
        }

        return true;
    }

    public void excluir() {
        try {
            passagemDAO.excluir(passagem.getIdPassagem());
            addMessage("Passagem excluída com sucesso!", FacesMessage.SEVERITY_INFO);
            limpar();
            carregarPassagens();
        } catch (Exception e) {
            addMessage("Erro ao excluir passagem: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Passagem passagem) {
        this.passagem = passagem;
    }

    public void limpar() {
        passagem = new Passagem();
    }

    private void addMessage(String mensagem, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(severity, mensagem, null));
    }

    // Getters e Setters
    public Passagem getPassagem() {
        return passagem;
    }

    public void setPassagem(Passagem passagem) {
        this.passagem = passagem;
    }

    public List<Passagem> getPassagens() {
        return passagens;
    }

    public void setPassagens(List<Passagem> passagens) {
        this.passagens = passagens;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
}
