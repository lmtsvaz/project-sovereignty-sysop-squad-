package com.sovereignty.sysop.domain.ports;

import com.sovereignty.sysop.domain.model.AssetInfo;

public interface ILegacyInventoryService {
    AssetInfo consultarAtivoPorPatrimonio(String codigoPatrimonio);
}
