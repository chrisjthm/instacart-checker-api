package instacartchecker;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import instacartchecker.client.InstacartClient;
import instacartchecker.service.InstacartService;
import instacartchecker.service.InstacartServiceImpl;
import instacartchecker.service.SmsSender;
import instacartchecker.service.SmsSenderImpl;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

public class InstacartCheckerModule extends AbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(InstacartCheckerModule.class);

    public void configure() {

    }

    @Provides
    @Singleton
    @Named("instacartClient")
    public InstacartClient instacartClient(InstacartCheckerConfiguration config) {
        LOG.info("Providing instacart client with URL {}", config.getInstacartUrl());
        Client client = ClientBuilder.newClient();
        WebTarget webTarget
                = client.target(config.getInstacartUrl());
        return WebResourceFactory.newResource(InstacartClient.class, webTarget);
    }

    @Provides
    @Singleton
    @Named("vertx")
    public Vertx vertx(InstacartCheckerConfiguration config) {
        return Vertx.vertx(new VertxOptions()
                .setWorkerPoolSize(config.getWorkerPoolSize()));
    }

    @Provides
    @Singleton
    @Named("instacartService")
    public InstacartService instacartService(InstacartCheckerConfiguration config) {
        return new InstacartServiceImpl(instacartClient(config));
    }

    @Provides
    @Singleton
    @Named("smsSender")
    public SmsSender smsSender(InstacartCheckerConfiguration config) {
        return new SmsSenderImpl(config.getTwilioAccountSID(), config.getTwilioAuthToken(), config.getFromPhoneNumber());
    }

}
