package org.earthsystemcurator.cupid.nuopc.fsml.wizards;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class NUOPCProjectWizardPage3 extends WizardPage {
		
	public NUOPCProjectWizardPage3() {
		super("NUOPC Creation Wizard Page 3");
		setTitle("Create NUOPC Project");
		setDescription("Select NUOPC application properties");
	}

	public void createControl(Composite parent) {
		
		final Composite container = new Composite(parent, SWT.NULL);
		GridLayoutFactory.fillDefaults().margins(10,10).applyTo(container);
		
		/* Integer VerifyListener */
		VerifyListener intVerifyListener = new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				
				Text text = (Text) e.getSource();

	            final String oldS = text.getText();
	            String newS = oldS.substring(0, e.start) + e.text + oldS.substring(e.end);

	            if (newS.length() > 0) {
		            try {
		                Integer.parseInt(newS);
		            }
		            catch(NumberFormatException ex) {
		                e.doit = false;
		            }
	            }
	            
			}
		};
		
		
		/* DRIVER PROPERTIES */
		
		Group groupDriver = new Group(container, SWT.NULL);
		groupDriver.setText("Driver properties");
		NUOPCProjectWizard.updateFont(groupDriver, SWT.BOLD);
		//GridDataFactory.fillDefaults().span(2,1).applyTo(groupArch);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(10, 10).applyTo(groupDriver);
		
		Label driverNameLabel = new Label(groupDriver, SWT.NULL);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(driverNameLabel);
		driverNameLabel.setText("Driver name:");
	
		Text driverNameText = new Text(groupDriver, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).hint(250, SWT.DEFAULT).applyTo(driverNameText);
		driverNameText.setText("driver");
	
		Label driverTimestepLabel = new Label(groupDriver, SWT.NULL);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(driverTimestepLabel);
		driverTimestepLabel.setText("Timestep length (seconds):");
	
		Text driverTimestepText = new Text(groupDriver, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).hint(250, SWT.DEFAULT).applyTo(driverTimestepText);
		driverTimestepText.setText("30");
		driverTimestepText.addVerifyListener(intVerifyListener);		
		
		Label driverRunLengthLabel = new Label(groupDriver, SWT.NULL);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(driverRunLengthLabel);
		driverRunLengthLabel.setText("Total number of timesteps:");
	
		Text driverRunLengthText = new Text(groupDriver, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).hint(250, SWT.DEFAULT).applyTo(driverRunLengthText);
		driverRunLengthText.setText("5");
		driverRunLengthText.addVerifyListener(intVerifyListener);		
		
		
		/* MODEL PROPERTIES */
		Group groupModel = new Group(container, SWT.NULL);
		groupModel.setText("Model properties");
		NUOPCProjectWizard.updateFont(groupModel, SWT.BOLD);
		GridLayoutFactory.fillDefaults().numColumns(3).margins(10, 10).applyTo(groupModel);
		
		Label modelNameLabel = new Label(groupModel, SWT.NULL);
		modelNameLabel.setText("Model name:");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(modelNameLabel);

		Text modelNameText = new Text(groupModel, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).hint(250, SWT.DEFAULT).span(2,1).applyTo(modelNameText);
		modelNameText.setText("model");
		
		/* NUMERICAL GRID */
		Label gridLabel = new Label(groupModel, SWT.NULL);
		gridLabel.setText("Numerical grid:");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(gridLabel);
		
		Label gridTypeLabel = new Label(groupModel, SWT.NULL);
		gridTypeLabel.setText("Simple 2D Cartesian");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).span(2,1).applyTo(gridTypeLabel);
		NUOPCProjectWizard.updateFont(gridTypeLabel, SWT.ITALIC);
		

		Label minXLabel = new Label(groupModel, SWT.NULL);
		minXLabel.setText("Minimum X coordinate");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(minXLabel);
		
		Text minXText = new Text(groupModel, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).hint(50, SWT.DEFAULT).span(2,1).applyTo(minXText);
		minXText.setText("0");
		
		Label maxXLabel = new Label(groupModel, SWT.NULL);
		maxXLabel.setText("Maximum X coordinate");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(maxXLabel);
		
		Text maxXText = new Text(groupModel, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).hint(50, SWT.DEFAULT).span(2,1).applyTo(maxXText);
		maxXText.setText("100");
		
		
		Label minYLabel = new Label(groupModel, SWT.NULL);
		minYLabel.setText("Minimum Y coordinate");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(minYLabel);
		
		Text minYText = new Text(groupModel, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).hint(50, SWT.DEFAULT).span(2,1).applyTo(minYText);
		minYText.setText("0");
		
		Label maxYLabel = new Label(groupModel, SWT.NULL);
		maxYLabel.setText("Maximum Y coordinate");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(maxYLabel);
		
		Text maxYText = new Text(groupModel, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).hint(50, SWT.DEFAULT).span(2,1).applyTo(maxYText);
		maxYText.setText("100");
		
		
		Label cellsXLabel = new Label(groupModel, SWT.NULL);
		cellsXLabel.setText("Cells in X direction");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(cellsXLabel);
		
		Text cellsXText = new Text(groupModel, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).hint(50, SWT.DEFAULT).span(2,1).applyTo(cellsXText);
		cellsXText.setText("100");
		
		Label cellsYLabel = new Label(groupModel, SWT.NULL);
		cellsYLabel.setText("Cells in Y direction");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).applyTo(cellsYLabel);
		
		Text cellsYText = new Text(groupModel, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).hint(50, SWT.DEFAULT).span(2,1).applyTo(cellsYText);
		cellsYText.setText("100");
		
		
		
		
		/* IMPORTED FIELDS */
		Label importedFieldsLabel = new Label(groupModel, SWT.NULL);
		importedFieldsLabel.setText("Imported fields:");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).span(1,2).applyTo(importedFieldsLabel);
		
		final List importedFieldsList = new List (groupModel, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).span(1,2).hint(250, SWT.DEFAULT).applyTo(importedFieldsList);
		
		final Button addImportedFieldButton = new Button(groupModel, SWT.PUSH);
		addImportedFieldButton.setText("Add");
				
		final Button removeImportedFieldButton = new Button(groupModel, SWT.PUSH);
		removeImportedFieldButton.setText("Remove");
		
		
		/* EXPORTED FIELDS */
		Label exportedFieldsLabel = new Label(groupModel, SWT.NULL);
		exportedFieldsLabel.setText("Exported fields:");
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).span(1,2).applyTo(exportedFieldsLabel);
		
		final List exportedFieldsList = new List (groupModel, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.FILL).span(1,2).hint(250, SWT.DEFAULT).applyTo(exportedFieldsList);
		
		final Button addExportedFieldButton = new Button(groupModel, SWT.PUSH);
		addExportedFieldButton.setText("Add");
				
		final Button removeExportedFieldButton = new Button(groupModel, SWT.PUSH);
		removeExportedFieldButton.setText("Remove");
		
		
		final IInputValidator fieldNameValidator = new IInputValidator() {
			@Override
			public String isValid(String newText) {
				if (!newText.matches("[A-Za-z][A-Za-z0-9_]*")) {
					return "Field name invalid.  The field name must start with a letter and can only contain letters, numbers, and underscores.";
				}
				return null;
			}
		};
		
		Listener buttonListener = new Listener() {
			public void handleEvent(Event event) {
				if (event.widget == addImportedFieldButton) {

					InputDialog dlg = new InputDialog(
							event.display.getActiveShell(),
							"Imported field name", 
							"Enter the name of the imported field.", 
							"imported_field_name", 
							fieldNameValidator);
					
					if (dlg.open() == Window.OK) {
						java.util.List<String> items = Arrays.asList(importedFieldsList.getItems());
						if (!items.contains(dlg.getValue())) {
							importedFieldsList.add(dlg.getValue());
						}
					}

					
				}
				else if (event.widget == addExportedFieldButton) {

					InputDialog dlg = new InputDialog(
							event.display.getActiveShell(),
							"Exported field name", 
							"Enter the name of the exported field.", 
							"exported_field_name", 
							fieldNameValidator);
					
					if (dlg.open() == Window.OK) {
						java.util.List<String> items = Arrays.asList(exportedFieldsList.getItems());
						if (!items.contains(dlg.getValue())) {
							exportedFieldsList.add(dlg.getValue());
						}
					}

					
				}
				else if (event.widget == removeImportedFieldButton) {
					importedFieldsList.remove(importedFieldsList.getSelectionIndices());
				}
				else if (event.widget == removeExportedFieldButton) {
					exportedFieldsList.remove(exportedFieldsList.getSelectionIndices());
				}
				
			}
		};

		addImportedFieldButton.addListener(SWT.Selection, buttonListener);
		removeImportedFieldButton.addListener(SWT.Selection, buttonListener);
		addExportedFieldButton.addListener(SWT.Selection, buttonListener);
		removeExportedFieldButton.addListener(SWT.Selection, buttonListener);
		
		
		setControl(container);
		
	}
		
}