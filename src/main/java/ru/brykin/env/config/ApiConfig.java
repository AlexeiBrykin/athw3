package ru.brykin.env.config;


import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",
        "system:properties",
        "classpath:config/${env}/apiConfig.properties"
})
public interface ApiConfig extends Config{

    @Config.Key("api.student.url")
    String url();
}
