package dev.llombardi.comparator.dto;

import dev.llombardi.comparator.annotation.DisplayName;
import dev.llombardi.comparator.annotation.DtoObserver;
import dev.llombardi.comparator.enums.DocumentType;

@DtoObserver
public class DocumentDTO {
	@DisplayName("Document Number")
	private String number;

	@DisplayName("Document Date")
	private String date;

	@DisplayName("Document Type")
	private DocumentType type;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}
}