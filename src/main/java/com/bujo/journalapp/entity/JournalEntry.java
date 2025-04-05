package com.bujo.journalapp.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JournalEntry {
    @Id
    @MongoId
    private String id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
