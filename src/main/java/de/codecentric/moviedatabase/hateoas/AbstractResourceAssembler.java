package de.codecentric.moviedatabase.hateoas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.Assert;

public abstract class AbstractResourceAssembler<T, D extends ResourceSupport> implements ResourceAssembler<T, D> {
	
	public List<D> toResource(List<T> entities){
		Assert.notNull(entities);
		List<D> resourceList = new ArrayList<D>();
		for (T entity: entities){
			resourceList.add(toResource(entity));
		}
		return resourceList;
	}
	
}
