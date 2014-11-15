package com.hyf.core.filter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Path;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;

@Component
public class HyfFilterFactory implements ResourceFilterFactory {
	@Resource
	private ApplicationContext applicationContext;

	@Override
	public List<ResourceFilter> create(AbstractMethod am) {
		List<ResourceFilter> resourceFilterList = null;
		Path path = am.getAnnotation(javax.ws.rs.Path.class);
		String uri = null;
		if(path != null){
			uri = path.value();
			if(    uri != null
					&& !uri.equals("user/login")
					&& !uri.equals("user/register")
					&& !uri.equals("activity/like")
					&& !uri.equals("activity/comment")
					&& !uri.equals("activity/arroundlist")) {
				HyfResourceFilter filter = (HyfResourceFilter) applicationContext
						.getBean("hyfFilter");
				if(resourceFilterList == null){
					resourceFilterList = new ArrayList<ResourceFilter>();
				}
				resourceFilterList.add(filter);
			}
		}
		return resourceFilterList;
	}
}
