package de.codecentric.moviedatabase.actors;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import yevgeniy.melnichuk.jmdns.JMDNSServicePromoter;
import yevgeniy.melnichuk.jmdns.ServiceTypeBuilder;

public class JMDNSServicePromotion implements InitializingBean, DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(JMDNSServicePromotion.class);
    private JMDNSServicePromoter promoter;

    @Value("${moviedatabase.actors.url.base}")
    private String baseUrl;

    @Value("${server.port}")
    private int serverPort;

    @Override
    public void afterPropertiesSet() throws Exception {
        String type = new ServiceTypeBuilder().withTcp().withApp("movie-database-actors").getType();

        Map<String, String> props = new HashMap<String, String>();
        props.put("base.url", baseUrl);

        // TODO replace port with port number form configuration - not important for the POC
        promoter = new JMDNSServicePromoter().promote(type, "movie-database-actors", "actors application", serverPort, props);
        LOGGER.info("promoting service of type " + type);
    }

    @Override
    public void destroy() throws Exception {
        // this will take few seconds for every network interface, but will ensure everybody gets notified.
        promoter.shutdown();
    }

}
