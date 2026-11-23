package com.sovereignty.sysop.domain.model;

public record AssetInfo(String codigoPatrimonio, String modeloEquipamento, boolean emGarantia) {
    public static AssetInfo indisponivel(String codigoPatrimonio) {
        return new AssetInfo(codigoPatrimonio, "DESCONHECIDO_FALLBACK", false);
    }
}
