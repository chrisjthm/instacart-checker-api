package instacartchecker;

import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import instacartchecker.api.InstacartCheckerResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class InstacartCheckerApplication extends Application<InstacartCheckerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new InstacartCheckerApplication().run(args);
    }

    @Override
    public String getName() {
        return "InstacartChecker";
    }

    @Override
    public void initialize(final Bootstrap<InstacartCheckerConfiguration> bootstrap) {
        super.initialize(bootstrap);
        GuiceBundle.Builder<InstacartCheckerConfiguration> builder =
                GuiceBundle.<InstacartCheckerConfiguration>newBuilder()
                        .setConfigClass(InstacartCheckerConfiguration.class)
                        .enableAutoConfig(this.getClass().getPackage().toString())
                        .addModule(new InstacartCheckerModule());
        bootstrap.addBundle(builder.build(Stage.DEVELOPMENT));
    }

    @Override
    public void run(final InstacartCheckerConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(InstacartCheckerResource.class);
    }

}
