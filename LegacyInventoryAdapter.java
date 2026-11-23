package com.sovereignty.sysop.infrastructure.acl;

import com.sovereignty.sysop.domain.model.AssetInfo;
import com.sovereignty.sysop.domain.ports.ILegacyInventoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;

@Component
public class LegacyInventoryAdapter implements ILegacyInventoryService {

    @Override
    @CircuitBreaker(name = "legacyInventoryCB", fallbackMethod = "fallbackInventario")
    @Retry(name = "legacyInventoryRetry")
    public AssetInfo consultarAtivoPorPatrimonio(String codigoPatrimonio) {
        String xmlLegado = chamarSoapLegado(codigoPatrimonio);
        return traduzirXmlParaDominio(codigoPatrimonio, xmlLegado);
    }

    private String chamarSoapLegado(String patrimonio) {
        return "<ATIVO><COD_PAT>" + patrimonio + "</COD_PAT><MOD>PDV-TERMINAL-X9</MOD><GAR>S</GAR></ATIVO>";
    }

    private AssetInfo traduzirXmlParaDominio(String patrimonio, String xml) {
        String modelo = xml.contains("PDV-TERMINAL-X9") ? "Terminal PDV X9" : "Equipamento Padrão";
        boolean garantia = xml.contains("<GAR>S</GAR>");
        return new AssetInfo(patrimonio, modelo, garantia);
    }

    public AssetInfo fallbackInventario(String codigoPatrimonio, Throwable ex) {
        return AssetInfo.indisponivel(codigoPatrimonio);
    }
}
