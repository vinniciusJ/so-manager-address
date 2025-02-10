package br.unioeste.esi.so_manager_address.specifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Search <T extends Comparable<T>>{
    private String key;
    private String operation;
    private T value;
}

