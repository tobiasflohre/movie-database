package de.codecentric.moviedatabase.movies.actors;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import yevgeniy.melnichuk.jmdns.JMDNSServiceDiscovery;
import yevgeniy.melnichuk.jmdns.JMDNSServiceDiscovery.Service;
import yevgeniy.melnichuk.jmdns.JMDNSServiceDiscovery.ServiceCallback;
import yevgeniy.melnichuk.jmdns.ServiceTypeBuilder;

public class ActorsInformationFactory implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActorsInformationFactory.class);

    private Set<String> actorsBaseUrls = new HashSet<String>();

    @Override
    public void afterPropertiesSet() throws Exception {
        String type = new ServiceTypeBuilder().withTcp().withApp("movie-database-actors").getType();
        new JMDNSServiceDiscovery().discover(type, new ServiceCallback() {

            @Override
            public void serviceAvailable(Service service) {
                String actorsBaseUrl = service.getProperties().get("base.url");

                LOGGER.info("actors application available:" + actorsBaseUrl);
                actorsBaseUrls.add(actorsBaseUrl);
            }

            @Override
            public void serviceDisappeared(Service service) {
                String actorsBaseUrl = service.getProperties().get("base.url");

                LOGGER.info("actors application disappeared:" + actorsBaseUrl);
                actorsBaseUrls.remove(actorsBaseUrl);
            }
        });
    }

    public ActorsInformation getActorsInformation() {
        if (actorsBaseUrls.isEmpty()) {
            throw new IllegalStateException("there is no known instance of actors application.");
        }

        return new ActorsInformation(actorsBaseUrls.iterator().next());
    }

}
