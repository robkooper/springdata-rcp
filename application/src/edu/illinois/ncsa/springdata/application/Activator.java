package edu.illinois.ncsa.springdata.application;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import edu.illinois.ncsa.springdata.rcp.domain.Person;
import edu.illinois.ncsa.springdata.rcp.repositories.PersonRepository;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "application"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	// ApplicationContext
	private ApplicationContext appcontext;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		//appcontext = new GenericXmlApplicationContext(new FileSystemResource("applicationContext.xml"));
		appcontext = new GenericXmlApplicationContext(new ClassPathResource("/META-INF/applicationContext.xml"));
		
		PersonRepository repo = getBean(PersonRepository.class);
		
		Person p = new Person();
		p.setFirstName("Rob");
		p.setLastName("Kooper");
		repo.save(p);
		
		p = new Person();
		p.setFirstName("Chris");
		p.setLastName("Navarro"); 
		repo.save(p);
	}
	
	/**
	 * Find bean in context and return it.
	 * @param beanclass class of bean to find
	 * @return instance of the bean
	 */
	public <T> T getBean(Class<T> beanclass) {
		return appcontext.getBean(beanclass);
	}
	

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
