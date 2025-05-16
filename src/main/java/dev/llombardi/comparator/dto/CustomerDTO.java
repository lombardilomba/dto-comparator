package dev.llombardi.comparator.dto;

import java.util.List;

import dev.llombardi.comparator.annotation.DisplayName;
import dev.llombardi.comparator.annotation.DtoObserver;

@DtoObserver
public class CustomerDTO {
    @DisplayName("Client Name")
    private String clientName;
    
    private DocumentDTO document;
    
    private List<AddressDTO> addressList;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }

    public List<AddressDTO> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressDTO> addressList) {
        this.addressList = addressList;
    }
}
