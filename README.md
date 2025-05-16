# DTO Comparer

A lightweight Java library for comparing Data Transfer Objects (DTOs) by recursively inspecting all fields (including nested objects and lists), annotating display names, and flagging “monitored” fields for special tracking.

Exameple of output:
```text
Client Name: 'Person's Name' -> 'New Name' => (monitored=true)
Document Type: 'TYPE1' -> 'TYPE2' => (monitored=false)
Street: 'Street A' -> 'Street A2' => (monitored=false)
Store: 'null' -> '1' => (monitored=true)
```

---

## Features

- **Deep comparison** of arbitrary DTOs, including nested objects and lists  
- **Human-friendly display names** via `@DisplayName` annotation  
- **Monitored-field support**: flag a subset of fields whose changes you care about  
- **JSON‐backed historical versioning**: compare a live DTO instance against a JSON snapshot

### Requirements

- Java 11 (or higher)  
- Jackson Databind & JSR-310 module  
- A JPA provider (optional, if you persist change records)
