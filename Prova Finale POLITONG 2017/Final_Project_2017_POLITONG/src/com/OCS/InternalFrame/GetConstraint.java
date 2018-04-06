package com.OCS.InternalFrame;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Write the part as adding the elements on the content pane
 * Since all this is designed on the GridBagConstraints
 * This could be initialized in the very same way
 * 
 * 
 * */


import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GetConstraint extends GridBagConstraints{
	
	private static final long serialVersionUID=1L;
	
	public GetConstraint(int gridx, int gridy,int gridwidth, int ipadx, boolean fill) {
		super();
		this.gridx = gridx;
		this.gridy = gridy;
		if (gridwidth > 1)
			this.gridwidth = gridwidth;
		if (ipadx > 0)
			this.ipadx = ipadx;
		this.insets = new Insets(5, 1, 3, 1);
		if (fill)
			this.fill = GridBagConstraints.HORIZONTAL;
	}
	
}
