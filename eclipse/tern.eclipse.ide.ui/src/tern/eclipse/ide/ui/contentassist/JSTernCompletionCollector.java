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
package tern.eclipse.ide.ui.contentassist;

import java.util.List;

import org.eclipse.jface.text.contentassist.ICompletionProposal;

import tern.server.ITernServer;
import tern.server.protocol.completions.ITernCompletionCollector;

/**
 * Tern collector which creates {@link JSTernCompletionProposal}.
 * 
 */
public class JSTernCompletionCollector implements ITernCompletionCollector {

	private final List<ICompletionProposal> proposals;
	private final int startOffset;

	public JSTernCompletionCollector(List<ICompletionProposal> proposals,
			int startOffset) {
		this.proposals = proposals;
		this.startOffset = startOffset;
	}

	@Override
	public void addProposal(String name, String type, String origin,
			Object doc, int pos, Object completion, ITernServer ternServer) {
		JSTernCompletionProposal proposal = new JSTernCompletionProposal(name,
				type, origin, doc, pos, startOffset);
		proposals.add(proposal);

		// expand functions if the functiosn contains several "optionnal" parameters.
		// ex : the expansion of "fn(selector: string, context?: frameElement)" returns an array of functions
		// 
		String[] functions = proposal.expand();
		if (functions != null) {
			for (int i = 0; i < functions.length; i++) {
				proposals.add(new JSTernCompletionProposal(name, functions[i],
						origin, doc, pos, startOffset));
			}
		}

	}
}
