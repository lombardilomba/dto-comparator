package dev.llombardi.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import dev.llombardi.comparator.comparison.ChangedField;
import dev.llombardi.comparator.dto.AddressDTO;
import dev.llombardi.comparator.dto.CustomerDTO;
import dev.llombardi.comparator.dto.DocumentDTO;
import dev.llombardi.comparator.dto.ProposalDTO;
import dev.llombardi.comparator.utils.DtoComparer;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DtoComparerTest {

	@Test
	void should_detect_store_field_change() {
	    ProposalDTO current = new ProposalDTO();
	    current.setStore(100);
	
	    String oldJson = """
	    { "store": 500 }
	    """;
		
		List<String> monitor = List.of("store");
		List<ChangedField> changes = DtoComparer.compare(current, oldJson, monitor);
		
		assertEquals(1, changes.size(), "Exactly one change expected");
		ChangedField change = changes.get(0);
		
		assertEquals("store", change.getPath());
		assertTrue(change.isMonitored());
		assertEquals("Store", change.getDisplayName());
	}
	
	@Test
	void should_handle_missing_old_node_as_null() {
	    DocumentDTO current = new DocumentDTO();
	    current.setNumber("X");
	
		String oldJson = "{}"; // no 'number' field
		
		List<ChangedField> changes = DtoComparer.compare(current, oldJson, List.of());
		assertEquals(1, changes.size());
		ChangedField c = changes.get(0);
		
		assertNull(c.getPreviousValue());
		assertEquals("X", c.getNewValue());
		assertFalse(c.isMonitored());
		assertEquals("Document Number", c.getDisplayName());
	}

	@Test
	void should_compare_nested_document_number() {
	    CustomerDTO current = new CustomerDTO();
	    DocumentDTO doc = new DocumentDTO();
	    doc.setNumber("ABC");
		current.setDocument(doc);
		
		String oldJson = """
		    { "document": { "number": "XYZ" } }
		    """;
		
		List<ChangedField> changes = DtoComparer.compare(current, oldJson, List.of("document.number"));
		assertEquals(1, changes.size());
		
		ChangedField c = changes.get(0);
		assertEquals("document.number", c.getPath());
		assertEquals("XYZ", c.getPreviousValue());
		assertEquals("ABC", c.getNewValue());
		assertTrue(c.isMonitored());
		assertEquals("Document Number", c.getDisplayName());
	}

	@Test
	void should_compare_list_elements_and_indices() {
	    CustomerDTO current = new CustomerDTO();
	    AddressDTO a1 = new AddressDTO();
	    a1.setStreet("one");
		AddressDTO a2 = new AddressDTO();
		a2.setStreet("two-new");
		current.setAddressList(List.of(a1, a2));
		
		String oldJson = """
		    { "addressList": [
		        { "street": "one" },
		        { "street": "two-old" }
		      ]
		    }
		    """;
		
		List<ChangedField> changes = DtoComparer.compare(current, oldJson, List.of("addressList.street"));
		assertEquals(1, changes.size());
		
		ChangedField c = changes.get(0);
		assertEquals("addressList[1].street", c.getPath());
		assertEquals("two-old", c.getPreviousValue());
		assertEquals("two-new", c.getNewValue());
		assertTrue(c.isMonitored());
		assertEquals("Street", c.getDisplayName());
	}

	@Test
	void should_mark_unmonitored_fields_correctly() {
	    AddressDTO current = new AddressDTO();
	    current.setCity("Porto");
	
		String oldJson = """
		    { "city": "OldCity" }
		    """;
		
		List<ChangedField> changes = DtoComparer.compare(current, oldJson, List.of());
		assertEquals(1, changes.size());
		ChangedField c = changes.get(0);
		
		assertFalse(c.isMonitored(), "Field should not be marked monitored when not in list");
		assertEquals("City", c.getDisplayName());
		assertEquals("OldCity", c.getPreviousValue());
		assertEquals("Porto", c.getNewValue());
	}
	
}
