package de.codecentric.moviedatabase.contentdelivery.config;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Import(CustomMimeMapper.class)
@Configuration 
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

    final static Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("file:/tmp/movie-database/content/");
    }
    
    private static final String REMOTE_URL = "https://github.com/tobiasflohre/movie-database-content-repository.git";

    @PostConstruct
    public void initGitRepo() throws IOException, InvalidRemoteException, TransportException, GitAPIException {
        // prepare a new folder for the cloned repository
        File localPath = new File("/tmp/movie-database/");
        FileSystemUtils.deleteRecursively(localPath);

        // then clone
        LOGGER.info("Cloning from " + REMOTE_URL + " to " + localPath);
        Git result = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(localPath)
                .call();

        try {
	        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
	        LOGGER.info("Having repository: " + result.getRepository().getDirectory());
        } finally {
        	result.close();
        }
    }
    
}
