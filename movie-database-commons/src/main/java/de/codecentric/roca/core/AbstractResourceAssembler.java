package de.codecentric.roca.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

public abstract class AbstractResourceAssembler<T, D extends ResourceSupport> {
	
	public List<D> toResource(List<T> entities){
		Assert.notNull(entities);
		List<D> resourceList = new ArrayList<D>();
		for (T entity: entities){
			resourceList.add(toResource(entity));
		}
		return resourceList;
	}
	
	public abstract D toResource(T entity);
	
}
