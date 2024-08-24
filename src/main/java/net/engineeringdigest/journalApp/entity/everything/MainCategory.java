package net.engineeringdigest.journalApp.entity.everything;

import lombok.*;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Document(collection = "main_categories")
@NoArgsConstructor
@AllArgsConstructor
public class MainCategory {
    private ObjectId id;
    @Indexed(unique = true) //first enable this in properties file
    @NonNull    //
    private String category;
    private String rating ;
    private Integer ratingCount;
    private String reachingTime;
    @DBRef      //it means it save the ref id of journal
    private List<ServiceProvider> serviceProviders = new ArrayList<>();

//    private List<String> role;
}
