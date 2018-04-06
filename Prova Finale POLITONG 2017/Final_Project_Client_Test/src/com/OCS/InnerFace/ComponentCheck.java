package com.OCS.InnerFace;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.OCS.Dao.model.*;
import com.OCS.client.MainBox;

public class ComponentCheck extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField conditionContent;
	private JComboBox conditionName;
	private Socket socketClient;
	protected DataOutputStream out;
	protected ObjectInputStream inBean;
	public ComponentCheck() {
		super();
		this.socketClient = MainBox.getSocketClient();
		setIconifiable(true);
		setClosable(true);
		setTitle("Component");
		getContentPane().setLayout(new GridBagLayout());
		setBounds(100, 100, 650, 375);

		table = new JTable();
		table.setEnabled(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		final DefaultTableModel dftm = (DefaultTableModel) table.getModel();
		String[] tableHeads = new String[]{"ID", "Name", "Type", "Price"};
		dftm.setColumnIdentifiers(tableHeads);
		
		final JScrollPane scrollPane = new JScrollPane(table);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_6.insets = new Insets(0, 10, 0, 10);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth = 6;
		gridBagConstraints_6.gridy = 1;
		gridBagConstraints_6.gridx = 0;
		getContentPane().add(scrollPane, gridBagConstraints_6);
/////////////////////////////////////////////////////////////////////////////////
		setupComponet(new JLabel(" Choose锛�"), 0, 0, 1, 1, false);
		conditionName = new JComboBox();
		conditionName.setModel(new DefaultComboBoxModel(new String[]{"ID","Name", "Type"}));
		setupComponet(conditionName, 1, 0, 1, 30, true);
		conditionContent = new JTextField();
		setupComponet(conditionContent, 2, 0, 2, 140, true);

		final JButton queryButton = new JButton();
		setupComponet(queryButton, 4, 0, 1, 1, false);
		queryButton.setText("Find");
////////////////////////////////////////////////////////////////////////////////////////////
		final JButton showAllButton = new JButton();
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

					try {
						out = new DataOutputStream(socketClient.getOutputStream());
						out.writeUTF("QUERY");///????
			    	    out.flush();
			    	    String condition = conditionName.getSelectedItem().toString();
			    	    out.writeUTF(condition);
			    	    out.flush();
			    	    inBean = new ObjectInputStream(socketClient.getInputStream());
			    	    List<Component> list;
						list = (List<Component>)inBean.readObject();
						updateTable(list, dftm);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		setupComponet(showAllButton, 5, 0, 1, 1, false);
		showAllButton.setText("Show ALL");
////////////////////////////////////////////////////////////////////////////////////////////
		final JButton buynowButton = new JButton();
		setupComponet(buynowButton, 1, 2, 1, 1, false);
		buynowButton.setText("Buy Now");
		
		final JButton addtobagButton = new JButton();
		setupComponet(addtobagButton, 4, 2, 1, 1, false);
		addtobagButton.setText("Put into Cart");
	}

	private void setupComponet(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		if (gridwidth > 1)
			gridBagConstrains.gridwidth = gridwidth;
		if (ipadx > 0)
			gridBagConstrains.ipadx = ipadx;
		gridBagConstrains.insets = new Insets(5, 1, 3, 1);
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(component, gridBagConstrains);
	}

	private void updateTable(List<Component> list, final DefaultTableModel dftm) {
		int num = dftm.getRowCount();
		for (int i = 0; i < num; i++)
			dftm.removeRow(0);
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Component component = (Component) iterator.next();
			Vector rowData = new Vector();
			rowData.add(component.getId());
			rowData.add(component.getName());
			rowData.add(component.getType());
			rowData.add(component.getPrice());
			dftm.addRow(rowData);
		}
	}
	

}
