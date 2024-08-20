package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongfigJournalAppRepo extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
