package de.codecentric.moviedatabase.contentdelivery.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.codecentric.moviedatabase.contentdelivery.controller.SearchController;
import de.codecentric.moviedatabase.contentdelivery.domain.ContentDocument;
import de.codecentric.moviedatabase.contentdelivery.domain.ContentDocumentBuilder;
import de.codecentric.moviedatabase.contentdelivery.repository.ContentDocumentRepository;
import de.codecentric.moviedatabase.contentdelivery.repository.RepositoryPackageMarker;

@Import(CustomMimeMapper.class)
@EnableElasticsearchRepositories(basePackageClasses=RepositoryPackageMarker.class)
@Configuration 
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

    final static Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class);
    
    private static final String REMOTE_URL = "https://github.com/tobiasflohre/movie-database-content-repository.git";

    @Autowired
    private ContentDocumentRepository contentDocumentRepository;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/content/**").addResourceLocations("file:/tmp/movie-database/content/");
    }
    
    @Bean
    public SearchController searchController(){
    	return new SearchController(contentDocumentRepository);
    }
    
    @PostConstruct
    public void init() throws InvalidRemoteException, TransportException, IOException, GitAPIException{
    	initGitRepo();
    	buildElasticsearchIndex();
    }
    
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
    
    public void buildElasticsearchIndex() throws IOException{
    	final ObjectMapper objectMapper = new ObjectMapper();
    	final String pathPrefix = "/tmp/movie-database/content/";
        Path p = Paths.get(pathPrefix);
        FileVisitor<Path> fv = new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path path, BasicFileAttributes attrs)
              throws IOException {
            String urlPath = path.toString().substring(pathPrefix.length());
            System.out.println(urlPath);
            if (attrs.isRegularFile()){
            	String filename = path.getFileName().toString();
            	if (filename.toLowerCase().endsWith(".json")){
            		Map<?,?> values = objectMapper.readValue(path.toFile(), Map.class);
            		ContentDocument contentDocument = new ContentDocumentBuilder(urlPath).type((String) values.get("type")).url(urlPath).build();
            		contentDocumentRepository.save(contentDocument);
            	};
            }
            return FileVisitResult.CONTINUE;
          }
        };
        Files.walkFileTree(p, fv);
    }
    
}
