package com.mentora.backend.chat.repository;

import com.mentora.backend.chat.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Chat entity operations.
 * Provides CRUD operations and custom query methods for chat management.
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    List<Chat> findByUserIdOrderByUpdatedAtDesc(String userId);

    List<Chat> findByUserIdAndTitleContainingIgnoreCaseOrderByUpdatedAtDesc(String userId, String title);

    void deleteAllByUserId(String userId);

}