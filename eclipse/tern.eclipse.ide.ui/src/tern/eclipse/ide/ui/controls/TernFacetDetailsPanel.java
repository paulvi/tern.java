/**
 *  Copyright (c) 2013-2014 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package tern.eclipse.ide.ui.controls;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import tern.eclipse.ide.internal.ui.TernUIMessages;
import tern.eclipse.ide.ui.viewers.TernFacetLabelProvider;
import tern.metadata.TernFacetMetadata;
import tern.metadata.TernFacetMetadataManager;
import tern.server.ITernFacet;
import tern.utils.StringUtils;

/**
 * Display information of tern facet.
 *
 */
public class TernFacetDetailsPanel extends Composite {

	public TernFacetDetailsPanel(Composite parent, ITernFacet facet) {
		super(parent, SWT.NONE);
		createUI(facet);
	}

	private void createUI(ITernFacet facet) {

		GridLayout layout = new GridLayout(1, false);
		super.setLayout(layout);

		final Composite parent = new Composite(this, SWT.NONE);
		layout = new GridLayout(2, false);
		parent.setLayout(layout);

		// Create title header of the facet with icon.
		createHeader(parent, facet);

		// Create separator
		createSeparator();

		// Create body of the facet.
		createBody(facet);

	}

	public void createSeparator() {
		final Label separator = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	public void createBody(ITernFacet facet) {
		TernFacetMetadata metadata = facet == null ? null
				: TernFacetMetadataManager.getInstance().getMetadata(
						facet.getName());

		GridLayout layout;
		final ScrolledComposite details = new ScrolledComposite(this,
				SWT.V_SCROLL | SWT.H_SCROLL);
		details.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		details.setMinWidth(300);
		details.setExpandHorizontal(true);
		details.setExpandVertical(true);

		final Composite nestedDetailsComposite = new Composite(details,
				SWT.NONE);
		layout = new GridLayout(2, false);
		layout.marginLeft = 0;
		layout.marginTop = 0;
		layout.marginHeight = 0;
		nestedDetailsComposite.setLayout(layout);
		details.setContent(nestedDetailsComposite);

		if (metadata != null && !StringUtils.isEmpty(metadata.getDescription())) {
			final Text descTextField = new Text(nestedDetailsComposite,
					SWT.WRAP | SWT.READ_ONLY);
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 2;
			descTextField.setLayoutData(data);
			descTextField.setText(metadata.getDescription());
		}

		addInfo(nestedDetailsComposite,
				TernUIMessages.TernFacetDetailsPanel_homepage, null,
				metadata != null ? metadata.getHomePage() : "", null);
		addInfo(nestedDetailsComposite,
				TernUIMessages.TernFacetDetailsPanel_author, null,
				metadata != null ? metadata.getAuthor() : "", null);
		addInfo(nestedDetailsComposite,
				TernUIMessages.TernFacetDetailsPanel_repositoryURL, null,
				metadata != null ? metadata.getRepositoryURL() : "", null);
		addInfo(nestedDetailsComposite,
				TernUIMessages.TernFacetDetailsPanel_bugsURL, null,
				metadata != null ? metadata.getBugsURL() : "", null);
	}

	public void createHeader(final Composite parent, ITernFacet facet) {
		addInfo(parent, null, TernFacetLabelProvider.getImageFacet(facet),
				facet.getName(),
				JFaceResources.getFontRegistry().get(DetailsPanel.HEADER_FONT));
	}

	public void addInfo(final Composite parent, String valueLabel, Image image,
			String valueInfo, Font font) {

		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		label.setText(valueLabel != null ? valueLabel : "");
		if (image != null) {
			label.setImage(image);
		}

		Text textField = new Text(parent, SWT.WRAP | SWT.READ_ONLY);
		textField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textField.setText(valueInfo != null ? valueInfo : "");
		if (textField != null) {
			textField.setFont(font);
		}
	}

}