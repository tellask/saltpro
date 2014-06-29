package com.dbcom.dataset;

import java.io.Serializable;

public class Row implements Serializable, Cloneable {
	
    public Row(int numCols) {
        currentVals = new Object[numCols];
        this.numCols = numCols;
    }
    
    public Row(int numCols, Object[] vals) {
    	this(numCols);
    	
        for (int i=0; i < numCols; i++) {
            currentVals[i] = vals[i];
        }
    }

    public int getColumnCount() {
    	return numCols;
    }
		
    public void setColumnObject(int idx, Object val) {
            currentVals[idx] = val;
    }

    public Object getColumnObject(int columnIndex) {
    	return currentVals[columnIndex]; 
    }
    
    private Object[] currentVals;
    private int numCols;
    private static final long serialVersionUID = -3535931973449595963L;
}