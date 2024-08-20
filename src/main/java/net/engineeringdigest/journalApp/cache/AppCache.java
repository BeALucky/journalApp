package net.engineeringdigest.journalApp.cache;

import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.CongfigJournalAppRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }

    @Autowired
    private CongfigJournalAppRepo congfigJournalAppRepo;

    public Map<String, String> appCache;
    @PostConstruct
    public void init(){
        appCache=  new HashMap<String, String>();
        List<ConfigJournalAppEntity> all = congfigJournalAppRepo.findAll();
        for(ConfigJournalAppEntity app : all){
            appCache.put(app.getKey(),app.getValue());
        }
    }
}
