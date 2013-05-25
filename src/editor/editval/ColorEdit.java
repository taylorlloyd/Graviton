package editor.editval;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import editor.EditVal;
import editor.EditorGravitable;
import editor.EditorObject;
import editor.EditorWindow;

public class ColorEdit extends EditVal implements ActionListener{
	JButton colorBtn;
	EditorGravitable model;
	public ColorEdit() {
		colorBtn = new JButton("Choose");
		colorBtn.setIcon(iconColor(Color.RED));
		colorBtn.addActionListener(this);
		setLayout(new GridLayout(1,1));
		add(colorBtn);
	}
	public void load(EditorObject eo) {
		model = (EditorGravitable)eo;
		colorBtn.setIcon(iconColor(model.getColor()));
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		new Thread(new Runnable() {
			public void run() {
				model.setColor(new ColorChooser(model.getColor()).chooseColor());
				colorBtn.setIcon(iconColor(model.getColor()));
				EditorWindow.pane.drawUpdate();
			}
		}).start();
	}
	public ImageIcon iconColor(Color c) {
		Image i = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		Graphics g = i.getGraphics();
		g.setColor(c);
		g.fillRect(0, 0, 20, 20);
		return new ImageIcon(i);
	}
}
class ColorChooser extends JFrame implements MouseListener, ActionListener{
	Color selected;
	Color orig;
	Color picked;
	JButton choose;
	JButton cancel;
	JLabel box;
	JLabel brightness;
	public ColorChooser(Color init) {
		orig = init;
		selected = init;
		choose = new JButton("Choose");
		choose.addActionListener(this);
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		box = new JLabel(new ImageIcon(selectedColorChooser(init)));
		box.addMouseListener(this);
		brightness = new JLabel(new ImageIcon(brightnessScale(init)));
		brightness.addMouseListener(this);
		setLayout(new GridBagLayout());
		add(box, gbc(0, 0, 3, 3, GridBagConstraints.NONE, 1, 1));
		add(brightness, gbc(3, 0, 1, 3, GridBagConstraints.NONE, 1, 1));
		add(choose, gbc(0,3,2,1, GridBagConstraints.HORIZONTAL, 1, 1));
		add(cancel, gbc(2,3,2,1, GridBagConstraints.HORIZONTAL, 1, 1));
		pack();
	}
	private void setColor(Color c) {
		selected = c;
		box.setIcon(new ImageIcon(selectedColorChooser(c)));
		brightness.setIcon(new ImageIcon(brightnessScale(c)));
	}
	public BufferedImage colorChooser() {
		BufferedImage i = new BufferedImage(255,255, BufferedImage.TYPE_INT_ARGB);
		Graphics g = i.getGraphics();
		for(int h=0;h<255;h++){
			for(int s=0;s<255;s++) {
				g.setColor(Color.getHSBColor(((float)h)/255, ((float)s)/255, 0.8f));
				g.drawRect(h, s, 1, 1);
			}
		}
		return i;
	}
	public Image selectedColorChooser(Color c) {
		float[] hsb = getHSB(c);
		BufferedImage i = colorChooser();
		Graphics g = i.getGraphics();
		g.setColor(new Color(0,0,0));
		g.drawOval((int)(255*hsb[0]-3), (int)(255*hsb[1]-3), 6, 6);
		return i;
	}
	public Image brightnessScale(Color c) {
		float[] hsb = getHSB(c);
		BufferedImage i = new BufferedImage(60,255,BufferedImage.TYPE_INT_ARGB);
		Graphics g = i.getGraphics();
		for(int j=0;j<255;j++) {
			g.setColor(Color.getHSBColor(hsb[0], hsb[1], (float)j/255));
			g.drawLine(0, j, 29, j);
		}
		g.setColor(c);
		g.fillRect(30, 0, 30, 255);
		g.setColor(Color.black);
		g.drawLine(0, (int)(hsb[2]*255)-1, 35, (int)(hsb[2]*255)-1);
		g.drawLine(0, (int)(hsb[2]*255)+1, 35, (int)(hsb[2]*255)+1);
		return i;
	}
	public ImageIcon iconColor(Color c) {
		Image i = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
		Graphics g = i.getGraphics();
		g.setColor(c);
		g.fillRect(0, 0, 20, 20);
		return new ImageIcon(i);
	}
	public float[] getHSB(Color c){
		float[] ar = new float[3];
		Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), ar);
		return ar;
	}
	@Override
	public void mouseClicked(MouseEvent me) {
		float[] hsb = getHSB(selected);
		if(me.getSource().equals(box)) {
			setColor(Color.getHSBColor((float)(me.getPoint().x)/255, (float)(me.getPoint().y)/255, hsb[2]));
		} else if(me.getSource().equals(brightness)) {
			setColor(Color.getHSBColor(hsb[0], hsb[1], (float)(me.getPoint().y)/255));
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(choose))
			picked = selected;
		if(ae.getSource().equals(cancel))
			picked = orig;
		setVisible(false);
		
	}
	public GridBagConstraints gbc(int gridx, int gridy, int gridwidth, int gridheight, int fill, double weightx, double weighty) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=gridx;
		gbc.gridy=gridy;
		gbc.gridwidth=gridwidth;
		gbc.gridheight=gridheight;
		gbc.fill=fill;
		gbc.weightx=weightx;
		gbc.weighty=weighty;
		return gbc;
	}
	public Color chooseColor() {
		picked = null;
		setVisible(true);
		while(picked == null)
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return picked;
	}
}