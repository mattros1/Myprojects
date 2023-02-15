
import java.awt.GraphicsEnvironment;
import java.util.Random;
import java.awt.Font;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.*;
import java.awt.FlowLayout;

public class GWackClientGUI extends JFrame{
	public GWackClientGUI() {
		setBackground(new Color(255, 7, 0));
		
		JPanel Npanel = new JPanel();
		Npanel.setBackground(new Color(255, 7, 0));
		getContentPane().add(Npanel, BorderLayout.NORTH);
		Npanel.setLayout(new BorderLayout(0, 0));
		
		JPanel nwPanel = new JPanel();
		nwPanel.setBackground(new Color(255, 140, 68));
		Npanel.add(nwPanel, BorderLayout.WEST);
		
		JPanel ncPanel = new JPanel();
		ncPanel.setBackground(new Color(255, 140, 68));
		Npanel.add(ncPanel, BorderLayout.CENTER);
		
		JLabel nLabel = new JLabel("Name\n");
		ncPanel.add(nLabel);
		
		nField = new JTextField();
		ncPanel.add(nField);
		nField.setColumns(5);
		
		JLabel ipLabel = new JLabel("Ip Address");
		ncPanel.add(ipLabel);
		
		ipField = new JTextField();
		ncPanel.add(ipField);
		ipField.setColumns(10);
		
		JLabel pLabel = new JLabel("Port");
		ncPanel.add(pLabel);
		
		textField_3 = new JTextField();
		ncPanel.add(textField_3);
		textField_3.setColumns(10);
		
		JButton dButton = new JButton("Disconnect");
		ncPanel.add(dButton);
		dButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                // get the current jframe
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(dButton);
                // terminate the frame
                currentFrame.dispose();
            } 
        });
		JPanel cePanel = new JPanel();
		cePanel.setBackground(new Color(255, 140, 68));
		Npanel.add(cePanel, BorderLayout.EAST);
		
		JPanel wPanel = new JPanel();
		wPanel.setBackground(new Color(255, 140, 68));
		getContentPane().add(wPanel, BorderLayout.WEST);
		
		JPanel sPanel = new JPanel();
		getContentPane().add(sPanel, BorderLayout.SOUTH);
		sPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel swPanel = new JPanel();
		swPanel.setBackground(new Color(255, 140, 68));
		sPanel.add(swPanel, BorderLayout.WEST);
		
		JPanel scPanel = new JPanel();
		scPanel.setBackground(new Color(255, 140, 68));
		sPanel.add(scPanel, BorderLayout.CENTER);
		scPanel.setLayout(new BorderLayout(0, 0));
	
		
		
		
		
		JLabel omLabel = new JLabel("Online Members");
	
		
		JPanel scePanel = new JPanel();
		scePanel.setBackground(new Color(255, 140, 68));
		scPanel.add(scePanel);
		scePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel cLabel = new JLabel("Compose");
		scePanel.add(cLabel, BorderLayout.NORTH);
		
		cField = new JTextField();
		scePanel.add(cField);
		cField.setColumns(10);
		
		JButton sButton = new JButton("Send");
		sButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage(cField,mField,nField);
			}
		});
		scePanel.add(sButton, BorderLayout.SOUTH);
		
		JPanel sePanel = new JPanel();
		sePanel.setBackground(new Color(255, 140, 68));
		sPanel.add(sePanel, BorderLayout.EAST);
		
		JPanel ePanel = new JPanel();
		ePanel.setBackground(new Color(255, 140, 68));
		getContentPane().add(ePanel, BorderLayout.EAST);
		
		JPanel cPanel = new JPanel();
		cPanel.setBackground(new Color(255, 140, 68));
		getContentPane().add(cPanel, BorderLayout.CENTER);
		cPanel.setLayout(new BorderLayout(0, 0));
		
		
		JPanel scwPanel = new JPanel();
		scwPanel.setBackground(new Color(255, 140, 68));
		cPanel.add(scwPanel, BorderLayout.SOUTH);
		
		scwPanel.setLayout(new BorderLayout(0, 0));
		scwPanel.add(omLabel,BorderLayout.NORTH );

		
		textField = new JTextField();
		textField.setEditable(false);
	
		scwPanel.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		
		JLabel mLabel = new JLabel("Messages");
		mLabel.setBackground(new Color(255, 140, 68));
		mLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cPanel.add(mLabel, BorderLayout.NORTH);
		
		mField = new JTextArea();
		mField.setEditable(false);
		cPanel.add(mField, BorderLayout.CENTER);
		mField.setColumns(10);
		
    
		JButton fbutton = new JButton("Change Font");
		fbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				String[] fontNames = ge.getAvailableFontFamilyNames();
				Random rand = new Random();
				int randomNum = rand.nextInt(fontNames.length);
				mField.setFont(new Font(fontNames[randomNum], Font.PLAIN, 12));
			}
		});
		
		cPanel.add(fbutton, BorderLayout.SOUTH);
		
		
	
	
	}

	
	private JTextField nField;
	private JTextField ipField;
	private JTextField textField_3;
	private JTextField textField;
	private JTextField cField;
	private JTextArea mField;

	/**
	 * Launch the application.
	 * @param args
	 */



public static void main(String[] args) {
    
    // Create a JFrame with a title 
    JFrame frame = new GWackClientGUI();
    
    // Set size of the frame
    frame.setSize(700, 700);
    
    // Set the default close operation (exit when it gets closed)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // Add an actionListener
    frame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    });

    // Make the frame visible
    frame.setVisible(true);

}




	public void sendMessage(JTextField source, JTextArea target, JTextField name) {

        String sourceContents = source.getText();
        String targetContents = target.getText();
		target.setText(targetContents + "\n" + "[ "+name.getText()+ "] :"+sourceContents);
		}
}

