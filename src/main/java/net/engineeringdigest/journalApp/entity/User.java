package net.engineeringdigest.journalApp.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Document(collection = "users")
public class User {
    private ObjectId id;
    @Indexed(unique = true) //first enable this in properties file
    @NonNull    //
    private String username;
    @NonNull
    private String password;
    @DBRef      //it means it save the ref id of journal
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> role;
}
