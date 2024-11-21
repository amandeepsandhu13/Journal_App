package org.aman.journalapp.Repository;

import org.aman.journalapp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId> {
}
