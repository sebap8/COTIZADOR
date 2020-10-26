package vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import controlador.Controlador;

public class BuscarProducto extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JTextField textField;
	private JButton btnBuscar;
	private JButton btnSeleccionar;
	private JLabel lblNombre;
	private JScrollPane jScrollPane1;
	private TableModel jTable1Model;
	private JTable jTable1;
	

	/**
	 * Create the frame.
	 */
	public BuscarProducto(String ventana) {
		setTitle("BUSCAR PRODUCTO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 814, 435);
		contentPane.add(panel);
		panel.setLayout(null);
		panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 14), null));
		
		
		jScrollPane1 = new JScrollPane();
		panel.add(jScrollPane1);
		jScrollPane1.setBounds(10, 59, 794, 365);
		jTable1Model = 
				new DefaultTableModel(
						new String[][] {  },
						new String[] {"Descripción"}){public boolean isCellEditable(int row, int column)
					    {
						      return false;//This causes all cells to be not editable
						    }};
		jTable1 = new JTable();
		jScrollPane1.setViewportView(jTable1);
		jTable1.setModel(jTable1Model);
		jTable1.setBounds(12, 207, 555, 194);        
        
		lblNombre = new JLabel(" NOMBRE");
		lblNombre.setBounds(10, 15, 71, 14);
		panel.add(lblNombre);
		
		textField = new JTextField();
		textField.setBounds(110, 12, 289, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		btnBuscar = new JButton("BUSCAR");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel temp1 = (DefaultTableModel) jTable1.getModel();
				while(jTable1.getRowCount()>0){
					temp1.removeRow(jTable1.getRowCount()-1);
				}
				Vector<String> productos = Controlador.getInstancia().obtenerProductosBusqueda(textField.getText());
				Collections.sort(productos);
				for(int i=0;i<productos.size();i++){
					Object nuevo[]= {productos.get(i)};
					DefaultTableModel temp = (DefaultTableModel) jTable1.getModel();			
					temp.addRow(nuevo);
				}
			}
		});
		btnBuscar.setBounds(430, 11, 89, 23);
		panel.add(btnBuscar);
		this.rootPane.setDefaultButton(btnBuscar);
		
		btnSeleccionar = new JButton("SELECCIONAR");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ventana=="1") {
					EliminarProducto.getInstancia().setProducto(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
				}
				if(ventana=="2") {
					ModificarProducto.getInstancia().setProducto(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
				}
				if(ventana=="3") {
					GenerarPresupuesto.getInstancia().setProducto(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
				}
				if(ventana=="4") {
					AgregarImagenProducto.getInstancia().setProducto(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
				}
				dispose();
				
			}
		});
		btnSeleccionar.setBounds(545, 11, 123, 23);
		panel.add(btnSeleccionar);
	}
}
