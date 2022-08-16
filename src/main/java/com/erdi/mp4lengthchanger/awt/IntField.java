package com.erdi.mp4lengthchanger.awt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

public class IntField extends JTextField {
	private static final long serialVersionUID = 5144777204356799387L;
	
	private String templateText = "";
	private Color templateColor = Color.GRAY;
	
	{
		((PlainDocument) getDocument()).setDocumentFilter(new IntFilter());
	}
	
	public IntField() {
		this(16);
	}
	
	public IntField(int columns) {
		super(columns);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(getText().isEmpty()) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(templateColor);
			g.drawString(templateText, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
		}
	}
	
	public void setTemplateText(String text) {
		templateText = text;
	}
	
	public String getTemplateText() {
		return templateText;
	}
	
	public Color getTemplateColor() {
		return templateColor;
	}
	
	public void setTemplateColor(Color color) {
		this.templateColor = color;
	}
	
	public int getValue() {
		return Integer.parseInt("0".concat(getText()));
	}
}
