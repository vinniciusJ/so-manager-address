package br.unioeste.esi.so_manager_address.domains.dto.external;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ExternalAddressDTO {
    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    private String ibge;
    private String bairro;
}
