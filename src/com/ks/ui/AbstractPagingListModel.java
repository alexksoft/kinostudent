package com.ks.ui;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.AbstractListModel;

public abstract class AbstractPagingListModel<T> extends AbstractListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6613208067174831719L;
	
	private int _startPageNumber;
	private int _pageSize;
	private int _itemStartNumber;
	
//	protected ArrayList<Param> params;
	//internal use only
	private List<T> _items = new ArrayList<T>();
	
//	public AbstractPagingListModel(int startPageNumber, int pageSize, ArrayList<Param> params) {
	public AbstractPagingListModel(int startPageNumber, int pageSize) {
		super();
//		this.params = params;
		this._startPageNumber = startPageNumber;
		this._pageSize = pageSize;
		this._itemStartNumber = startPageNumber * pageSize;
		
	}
	
	public void retrieve(){
		_items = getPageData(_itemStartNumber, _pageSize);		
	}
	
	public abstract int getTotalSize();
	protected abstract List<T> getPageData(int itemStartNumber, int pageSize);
	
	@Override
	public Object getElementAt(int index) {
		return _items.get(index);
	}

	@Override
	public int getSize() {
		return _items.size();
	}
	
	public int getStartPageNumber() {
		return this._startPageNumber;
	}
	
	public int getPageSize() {
		return this._pageSize;
	}
	
	public int getItemStartNumber() {
		return _itemStartNumber;
	}

	public List<T> getItems() {
		return _items;
	}
	
	
}
