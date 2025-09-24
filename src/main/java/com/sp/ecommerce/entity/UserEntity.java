package com.sp.ecommerce.entity;

import com.sp.ecommerce.model.*;
import com.sp.ecommerce.model.enumType.*;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "users", schema = "commerce") // check script in migration
/**
 * why validations in entity?
 * use same as in requestDTO, remove @JsonProperty
 * for data type e.g. enum, json and blob, check script
 */
public class UserEntity {
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Size(max = 256, message = "Business name can have at max 256 characters.")
    private String businessName;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class) // enum
    @Column(columnDefinition = "commerce.user_type_enum") // check script
    private UserType type;

    @Valid
    @JdbcTypeCode(SqlTypes.JSON) // json
    private Address address;

    @NotNull
    @Email
    @Size(max = 256, message = "Email can have at max 256 characters.")
    private String businessEmail;

    @NotNull
    @Size(min = 10, max = 16, message = "Business phone number must be between 10 and 16 characters")
    private String businessPhone;

    @NotNull
    @Size(min = 8, max = 25, message = "Business registration number must be between 8" +
            " and 25 characters.")
    private String businessRegNo;

    @NotNull
    @Size(min = 8, max = 25, message = "tax ID  must be between 8 and 25 characters.")
    private String taxId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<BankAccountDetails> bankAccountDetails;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<WareHouseTag> wareHouseTags;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(columnDefinition = "commerce.user_status_enum")
    private UserStatus status;

    @Column
    private Boolean isDeleted = false; // default val

    @Column(updatable = false)
    @CreationTimestamp // timestamp
    private Instant createdAt;

    @Column
    @UpdateTimestamp // timestamp
    private Instant updatedAt;
}
