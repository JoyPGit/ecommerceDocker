package com.sp.ecommerce.modules.users.repository;

import com.sp.ecommerce.modules.users.entity.DocumentEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    @Query(
            value = "Select * from commerce.documents d " +
                    "WHERE d.document_id = :documentId", nativeQuery = true)
    Optional<DocumentEntity> findByDocumentId(Long documentId);
}
