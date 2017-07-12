package com.ipfaffen.ovenbird.commons;

import java.util.Collection;

/**
 * @author Isaias Pfaffenseller
 */
@SuppressWarnings("serial")
public class PagedList<T> extends DataList<T> {

	private PagingHelper paging;

	public PagedList() {
		super();
	}

	/**
	 * @param c
	 */
	public PagedList(Collection<T> c) {
		super(c);
	}

	/**
	 * @return
	 */
	public PagingHelper getPaging() {
		return paging;
	}

	/**
	 * @param pagingHelper
	 */
	public void setPaging(PagingHelper pagingHelper) {
		this.paging = pagingHelper;
	}
}