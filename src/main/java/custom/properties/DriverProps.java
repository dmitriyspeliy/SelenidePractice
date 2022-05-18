package custom.properties;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:driver.properties"
})
public interface DriverProps extends Config {
    @Key("default.timeout")
    int defaultTimeout();

    @Key("urlYandex")
    String defaultUrl();

}
