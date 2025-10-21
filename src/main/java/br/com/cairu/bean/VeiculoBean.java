package br.com.cairu.bean;

import br.com.cairu.dao.VeiculoDAO;
import br.com.cairu.model.Veiculo;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "veiculoBean")
@ViewScoped
public class VeiculoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Veiculo veiculo;
    private List<Veiculo> veiculos;
    private VeiculoDAO veiculoDAO;

    @PostConstruct
    public void init() {
        veiculoDAO = new VeiculoDAO();
        veiculo = new Veiculo();
        carregarVeiculos();
    }

    public void carregarVeiculos() {
        try {
            veiculos = veiculoDAO.listarTodos();
        } catch (Exception e) {
            addMessage("Erro ao carregar veículos: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void salvar() {
        try {
            if (veiculo.getId() == null) {
                // Verificar se já existe veículo com a mesma placa
                Veiculo veiculoExistentePlaca = veiculoDAO.buscarPorPlaca(veiculo.getPlaca());
                if (veiculoExistentePlaca != null) {
                    addMessage("Já existe um veículo com esta placa!", FacesMessage.SEVERITY_WARN);
                    return;
                }

                // Verificar se já existe veículo com o mesmo número
                Veiculo veiculoExistenteNumero = veiculoDAO.buscarPorNumero(veiculo.getNumero());
                if (veiculoExistenteNumero != null) {
                    addMessage("Já existe um veículo com este número!", FacesMessage.SEVERITY_WARN);
                    return;
                }

                veiculoDAO.salvar(veiculo);
                addMessage("Veículo cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
            } else {
                veiculoDAO.atualizar(veiculo);
                addMessage("Veículo atualizado com sucesso!", FacesMessage.SEVERITY_INFO);
            }
            limpar();
            carregarVeiculos();
        } catch (Exception e) {
            addMessage("Erro ao salvar veículo: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void excluir() {
        try {
            veiculoDAO.excluir(veiculo.getId());
            addMessage("Veículo excluído com sucesso!", FacesMessage.SEVERITY_INFO);
            limpar();
            carregarVeiculos();
        } catch (Exception e) {
            addMessage("Erro ao excluir veículo: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editar(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public void limpar() {
        veiculo = new Veiculo();
    }

    private void addMessage(String mensagem, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(severity, mensagem, null));
    }

    // Getters e Setters
    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }
}
