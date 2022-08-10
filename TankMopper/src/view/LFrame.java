package view;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LFrame extends JFrame{
	
	public JLabel l1,l2;
	public JTextField tf;
	public JPasswordField pf;
	public JButton lb,cb;
	public Container c;
	public ImageIcon ic;
	public Font f;

	
	
	public void makeframe()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setBounds(500,250,420,300);
	    this.setTitle("Login to TankMopper");
	    ic = new ImageIcon(getClass().getResource("tanki.png"));
	    this.setIconImage(ic.getImage());
	     c=this.getContentPane();
	     c.setLayout(null);
	     
	     f= new Font("serif",Font.BOLD,18);
	     
	     c.setBackground(new Color(35,43,42));
	     l1= new JLabel("Username : ");
	     l1.setBounds(50,50,150,50);
	     l1.setFont(f);
	     l1.setForeground(Color.white);
	     c.add(l1);
	     
	     tf= new JTextField();
	     tf.setFont(f);
	     tf.setBounds(170,50,200,40);
	     tf.setBackground(new Color(35,43,42));
	     tf.setForeground(Color.white);
	     c.add(tf);
	     
	     
	     l2= new JLabel("Password : ");
	     l2.setBounds(50,120,150,50);
	     l2.setFont(f);
	     l2.setForeground(Color.white);
	     c.add(l2);
	     
	     pf= new JPasswordField();
	     pf.setBounds(170,120,200,40);
	     pf.setBackground(new Color(35,43,42));
	     pf.setForeground(Color.white);
	     pf.setEchoChar('*');
	     pf.setFont(f);
	     c.add(pf);
	     
	     lb = new JButton("Login");
	     lb.setBounds(170,190,90,35);
	     lb.setFont(f);
	     lb.setBackground(new Color(17,122,101));
	     lb.setForeground(Color.white);
	     c.add(lb);
	     
	     cb = new JButton("Clear");
	     cb.setBounds(280,190,90,35);
	     cb.setFont(f);
	     cb.setBackground(new Color(17,122,101));
	     cb.setForeground(Color.white);
	     c.add(cb);
	     
	     cb.addActionListener(new ActionListener(){
	    	 
	    	 public void actionPerformed(ActionEvent ae)
	    	 {
	    		 tf.setText("");
	    		 pf.setText("");
	    	 }
	    	 

	      });
	     
	     lb.addActionListener(new ActionListener() {
	    	 
	    	 public void actionPerformed(ActionEvent ae)
	    	 {
	    		 String s1= tf.getText();
	    		 String s2= pf.getText();
	    		 if(s1.equals("")||s2.equals(""))
	    		 {
	    			 JOptionPane.showMessageDialog(null,"invalid username or password");
	    		 }
	    		 else {
	    		 dispose();
	    		 TankMopper mp = new TankMopper();
	    		 mp.rt();
	    		 }
	           
	    
	    	 }
	     });
	     
	     this.setVisible(true);
	}
	
	public static void main(String args[])
			{    
		     
	             LFrame frame = new LFrame();
	             frame.makeframe();
	             
			}
	
	

}
