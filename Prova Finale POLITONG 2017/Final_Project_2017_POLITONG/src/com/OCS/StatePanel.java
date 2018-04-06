package com.OCS;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the state panel and decide the arrangements of the state label
 * 
 * The right side of state panel is static and would not change
 * The left side of state panel is dynamic and would change according to the window choosed
 * 
 * 
 * */

import java.awt.*;
import java.util.Date;

import javax.swing.*;

import static javax.swing.border.BevelBorder.*;

public class StatePanel extends JPanel{
	private static final long serialVersionUID=1L;
	
	private JLabel stateLabel=null;
	
	private JLabel user=null;
	private JLabel date=null;
	
	public StatePanel(){
		super();
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createBevelBorder(RAISED));
		
		date= new JLabel();
		Date now=new Date();
		date.setText(String.format("%tF", now));
		
		user=new JLabel("Caesar Technology Company   ");
		
		GridBagConstraints gridBagConstraints=setGridBagConstraints(0,0);
		gridBagConstraints.weightx=1.0;
		//set the constraint of the state label 
		
		GridBagConstraints gridBagConstraints1=setGridBagConstraints(6,0);
		gridBagConstraints1.weighty=1.0;
		//set the constraint of the JSerparator on the right side
		
		GridBagConstraints gridBagConstraints2=setGridBagConstraints(5,0);
		//set the location of the user label
		
		GridBagConstraints gridBagConstraints3=setGridBagConstraints(4,0);
		//set the location of another JSerparator
		
		GridBagConstraints gridBagConstraints4=setGridBagConstraints(3,0);
		//set the locationof the date label
		
		GridBagConstraints gridBagConstraints5=setGridBagConstraints(2,0);
		//set the location of another JSerparator
		
		this.add(getStateLabel(), gridBagConstraints);
		
		this.add(getJSeparatorVertical(), gridBagConstraints1);
		this.add(user, gridBagConstraints2);
		this.add(getJSeparatorVertical(), gridBagConstraints3);
		this.add(date, gridBagConstraints4);
		this.add(getJSeparatorVertical(), gridBagConstraints5);
	}
	
	/*Set the grid bag constraints
	 * */
	private GridBagConstraints setGridBagConstraints(int x,int y){
		GridBagConstraints constraint=new GridBagConstraints();
		constraint.gridx=x;
		constraint.gridy=y;
		constraint.fill=GridBagConstraints.BOTH;
		constraint.insets=new Insets(0, 5, 0, 5);
		return constraint;
	}
	
	/*Set the state label
	 * Show the information of the most front internal frame
	 * */
	public JLabel getStateLabel() {
		if (stateLabel==null) {
			stateLabel=new JLabel();
			stateLabel.setText("Welcome to CaesarTech.Inc! Ciao!");
		}
		return stateLabel;
	}
	
	/*Initialization of a vertical JSeparator
	 * */
	private JSeparator getJSeparatorVertical() {
		JSeparator jSeparator=new JSeparator();
		jSeparator.setOrientation(JSeparator.VERTICAL);
		return jSeparator;
	}
}
