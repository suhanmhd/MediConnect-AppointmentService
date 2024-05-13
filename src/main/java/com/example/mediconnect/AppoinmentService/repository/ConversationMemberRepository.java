package com.example.mediconnect.AppoinmentService.repository;

import com.example.mediconnect.AppoinmentService.entity.ConversationMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConversationMemberRepository extends JpaRepository<ConversationMember, UUID> {
    // You can add custom query methods here if needed
}