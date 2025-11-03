package ru.brykin.env;

import org.aeonbits.owner.ConfigFactory;
import ru.brykin.env.config.ApiConfig;

public class Env {
    public static class API{
        public static final ApiConfig API_CONFIG = ConfigFactory.create(ApiConfig.class);
    }
}
