package com.erdi.mp4lengthchanger.awt;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class IntFilter extends DocumentFilter {
	private boolean isInt(String text) {
		try {
			Integer.parseInt(text);
		} catch(NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder builder = new StringBuilder();
		
		builder.append(doc.getText(0, doc.getLength()));
		builder.insert(offset, string);
		
		if(isInt(string))
			super.insertString(fb, offset, string, attr);
	}
	
	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();

		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);

		if(isInt(sb.toString()))
			super.replace(fb, offset, length, text, attrs);
	}
	
	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();

		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);

		if(isInt(sb.toString()) || sb.toString().isEmpty())
			super.remove(fb, offset, length);
	}
}
