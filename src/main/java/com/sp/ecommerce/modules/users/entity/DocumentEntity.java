package com.sp.ecommerce.modules.users.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "user_documents", schema = "commerce")
public class DocumentEntity {

    /**
     * hibernate picks the sequence gen of postgres specified in script
     * add schema name
     *
     */
    @Id
    @SequenceGenerator(
            name = "user_documents_seq_gen",
            sequenceName = "commerce.user_documents_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_documents_seq_gen"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private UUID userId;

    @NotNull
    @Size(max = 256, message = "Document name can have at max 256 characters.")
    private String documentName;

    private String type;

    @JsonIgnore
    @Column(name = "document_data", columnDefinition = "bytea")
    private byte[] documentData;

    @Column(name = " document_size_original")
    private Long documentSizeOriginal;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(updatable = false)
    @UpdateTimestamp
    private Instant updatedAt;


}
