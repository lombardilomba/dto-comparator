package dev.llombardi.comparator;

import java.util.List;

import dev.llombardi.comparator.comparison.ChangedField;
import dev.llombardi.comparator.dto.AddressDTO;
import dev.llombardi.comparator.dto.CustomerDTO;
import dev.llombardi.comparator.dto.DocumentDTO;
import dev.llombardi.comparator.dto.ProposalDTO;
import dev.llombardi.comparator.enums.DocumentType;
import dev.llombardi.comparator.utils.DtoComparer;

public class Application {

	public static void main(String[] args) {
		ProposalDTO newDto = new ProposalDTO();
		newDto.setCustomer(new CustomerDTO());
		newDto.getCustomer().setClientName("New Name");
		newDto.getCustomer().setDocument(new DocumentDTO());
		newDto.getCustomer().getDocument().setNumber("123");
		newDto.getCustomer().getDocument().setDate("2024-01-01");
		newDto.getCustomer().getDocument().setType(DocumentType.TYPE2);
		newDto.setStore(1);

		AddressDTO address1 = new AddressDTO();
		address1.setStreet("Street A2");
		address1.setCity("Lisbon");

		AddressDTO address2 = new AddressDTO();
		address2.setStreet("Street B");
		address2.setCity("Braga");

		newDto.getCustomer().setAddressList(List.of(address1, address2));

		String storedJson = """
				{
				  "customer": {
				    "clientName": "Person's Name",
				    "document": {
				      "number": "123",
				      "date": "2024-01-01",
				      "type": "TYPE1"
				    },
				    "addressList": [
				      {
				        "street": "Street A",
				        "city": "Lisbon"
				      },
				      {
				        "street": "Street B",
				        "city": "Braga"
				      }
				    ]
				  }
				}
				""";

		List<String> monitoredFields = List.of("customer.clientName", "customer.addressList.city", "store");

		List<ChangedField> changes = DtoComparer.compare(newDto, storedJson, monitoredFields);
		changes.forEach(System.out::println);
	}

}
