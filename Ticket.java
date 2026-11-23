package com.sovereignty.sysop.domain.model;

import com.sovereignty.sysop.domain.states.AbertoState;
import com.sovereignty.sysop.domain.states.TicketState;
import java.util.UUID;

public class Ticket {
    private final UUID id;
    private final String lojaId;
    private final String descricao;
    private final AssetInfo ativo;
    private String categoria;
    private String severidade;
    private String consultorId;
    private String solucaoAplicada;
    private TicketState estadoAtual;

    public Ticket(String lojaId, String descricao, AssetInfo ativo) {
        this.id = UUID.randomUUID();
        this.lojaId = lojaId;
        this.descricao = descricao;
        this.ativo = ativo;
        this.estadoAtual = new AbertoState();
    }

    public void concluirTriagemIa(String categoria, String severidade) {
        this.estadoAtual.concluirTriagemIa(this, categoria, severidade);
    }

    public void ativarContingencia() {
        this.estadoAtual.ativarContingencia(this);
    }

    public void atribuirConsultor(String consultorId) {
        this.estadoAtual.atribuirConsultor(this, consultorId);
    }

    public void iniciarAtendimento() {
        this.estadoAtual.iniciarAtendimento(this);
    }

    public void resolver(String solucaoAplicada) {
        this.estadoAtual.resolver(this, solucaoAplicada);
    }

    public void alterarEstado(TicketState novoEstado) {
        this.estadoAtual = novoEstado;
    }

    public void definirClassificacao(String categoria, String severidade) {
        this.categoria = categoria;
        this.severidade = severidade;
    }

    public void vincularConsultor(String consultorId) {
        this.consultorId = consultorId;
    }

    public void registrarSolucao(String solucaoAplicada) {
        this.solucaoAplicada = solucaoAplicada;
    }

    public UUID getId() { return id; }
    public String getLojaId() { return lojaId; }
    public String getDescricao() { return descricao; }
    public AssetInfo getAtivo() { return ativo; }
    public String getCategoria() { return categoria; }
    public String getSeveridade() { return severidade; }
    public String getConsultorId() { return consultorId; }
    public String getSolucaoAplicada() { return solucaoAplicada; }
    public String getEstadoNome() { return estadoAtual.getNomeEstado(); }
}
