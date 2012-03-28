package edu.illinois.ncsa.springdata.application;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import edu.illinois.ncsa.springdata.rcp.domain.Person;
import edu.illinois.ncsa.springdata.rcp.repositories.PersonRepository;

public class View extends ViewPart {
	public static final String ID = "application.view";

	private TableViewer viewer;
	
	private PersonRepository personRepo;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent instanceof CrudRepository) {
				@SuppressWarnings("rawtypes")
				CrudRepository repo = (CrudRepository)parent;
				Object[] beans = new Object[(int)repo.count()];
				int idx = 0;
				for(Object o : repo.findAll()) {
					beans[idx++] = o;
				}
				return beans;
			}
	        return new Object[0];
		}
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		
		@Override
		public String getText(Object element) {
			if (element instanceof Person) {
				Person person = (Person)element;
				return person.getFirstName() + " " + person.getLastName();
			}
			return super.getText(element);
		}

		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		// Provide the input to the ContentProvider
		
		viewer.setInput(Activator.getDefault().getBean(PersonRepository.class));
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}