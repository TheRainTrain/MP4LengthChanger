package com.erdi.mp4lengthchanger.mp4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MP4 {
	/*
	private File tempFile;
	{
		try {
			tempFile = File.createTempFile("mp4", "mp4");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	*/
	
	private byte[] data;
	
	private int durationLocation = -1;
	private int timescaleLocation = -1;
	
	private int duration;
	private int timescale;
	
	public MP4(byte[] data) {
		this.data = data;
		
		for(int i = 0; i < data.length - 4; i++) {
			if(data[i] == (char) 'm' &&
				data[i + 1] == (char) 'v' &&
				data[i + 2] == (char) 'h' &&
				data[i + 3] == (char) 'd') {
				timescaleLocation = i + 0x10;
				durationLocation = timescaleLocation + 4;
				
				break;
			}
		}

		if(durationLocation == -1)
			throw new MP4ReadException("Couldn't find duration location");
		
		if(timescaleLocation == -1)
			throw new MP4ReadException("Couldn't find timescale location");

		duration = readInt(durationLocation);
		timescale = readInt(timescaleLocation);
	}
	
	private int readInt(int location) {
		int result = (data[location + 3] & 0xff);
		result |= (data[location + 2] & 0xff) << 8;
		result |= (data[location + 1] & 0xff) << 16;
		result |= (data[location] & 0xff) << 24;
		
		return result;
	}
	
	private void writeInt(int location, int value) {
		data[location + 3] = (byte)(value);
		data[location + 2] = (byte)(value >> 8);
		data[location + 1] = (byte)(value >> 16);
		data[location] = (byte)(value >> 24);
	}
	
	public float getLength() {
		return ((float) duration) / timescale;
	}
	
	public int getRawDuration() {
		return duration;
	}
	
	public int getTimescale() {
		return timescale;
	}
	
	public void setDuration(int duration) {
		if(this.duration == duration)
			return;
		
		this.duration = duration;
		writeInt(durationLocation, duration);
	}
	
	public void setTimescale(int timescale) {
		if(this.timescale == timescale)
			return;
		
		this.timescale = timescale;
		writeInt(timescaleLocation, timescale);
	}
	
	public int getDataSize() {
		return data.length;
	}
	
	public void saveToFile(File file) throws MP4SaveException {
		try(FileOutputStream stream = new FileOutputStream(file)) {
			stream.write(data);
			stream.flush();
		} catch (IOException e) {
			throw new MP4SaveException("Exception writing file", e);
		}
	}

	public static MP4 readMP4(File file) throws MP4ReadException {
		if(!file.canRead())
			throw new MP4ReadException("File is read only");
		
		try(FileInputStream stream = new FileInputStream(file)) {
			byte[] data = new byte[(int)file.length()];
			stream.read(data);
			
			return new MP4(data);
		} catch (IOException e) {
			throw new MP4ReadException("IO Exception whilst reading file", e);
		}
	}

//	public static MP4 readMP4(File file) throws MP4ReadException {
//		if(!file.canRead())
//			throw new MP4ReadException("File is read only");
//		
//		try {
//			return readMP4(new FileReader(file));
//		} catch (FileNotFoundException e) {
//			throw new MP4ReadException("File not found", e);
//		}
//	}
	
//	public static MP4 readMP4(Reader reader) throws MP4ReadException {
//		int size = 1024;
//		
//		byte[] data = new byte[size];
//		
//		try {
//			int read;
//			int position = 0;
//			
//			while((read = reader.read()) != -1) {
//				if(position >= size) {
//					int newSize = size + 1024;
//					byte[] newData = new byte[newSize];
//					
//					System.arraycopy(data, 0, newData, 0, size);
//					
//					size = newSize;
//					data = newData;
//				}
//				
//				data[position++] = (byte)(read & 0xff);
//			}
//			
//			byte[] newData = new byte[position];
//			System.arraycopy(data, 0, newData, 0, position);
//			
//			data = newData;
//			
//			System.out.println(data.length);
//		} catch (IOException e) {
//			throw new MP4ReadException("IO Exception", e);
//		}
//		
//		return new MP4(data);
//	}

	public static class MP4ReadException extends RuntimeException {
		private static final long serialVersionUID = -1686010489728054719L;
		
		public MP4ReadException() {
			super();
		}
		
		public MP4ReadException(String message) {
			super(message);
		}

		public MP4ReadException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class MP4SaveException extends RuntimeException {
		private static final long serialVersionUID = -1686010489728054719L;
		
		public MP4SaveException() {
			super();
		}
		
		public MP4SaveException(String message) {
			super(message);
		}

		public MP4SaveException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public static class MP4ValidateException extends RuntimeException {
		private static final long serialVersionUID = -1686010489728054719L;
		
		public MP4ValidateException() {
			super();
		}
		
		public MP4ValidateException(String message) {
			super(message);
		}

		public MP4ValidateException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
