package dev.llombardi.comparator.dto;

import dev.llombardi.comparator.annotation.DisplayName;
import dev.llombardi.comparator.annotation.DtoObserver;

@DtoObserver
public class ProposalDTO {
    private CustomerDTO customer;
    
    @DisplayName("Store")
    private Integer store;

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }
}