package ec.org.alone.webservice;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/alone")
public class JaxRsActivator extends Application {
	private Set<Class<?>> resources = new HashSet<Class<?>>();

	public JaxRsActivator() {
		getClasses().add(RESTService.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		return set;
	}
}
