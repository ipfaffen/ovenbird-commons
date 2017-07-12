package com.ipfaffen.ovenbird.commons;

import java.util.List;

/**
 * @author Isaias Pfaffenseller
 */
public class Pagination {

	private List<Integer> pages;
	private PagingHelper paging;
	private Integer pageFirstIndex;
	private Integer pageLastIndex;

	/**
	 * @param paging
	 */
	public Pagination(PagingHelper paging) {
		this.paging = paging;
		pages = PagingUtil.buildLimitedPageList(paging, 9);
		if(paging.getPageNumber() > 1) {
			pageFirstIndex = ((paging.getPageNumber() - 1) * paging.getPageSize());
			pageLastIndex = (pageFirstIndex + paging.getPageSize());
			pageFirstIndex += 1;
		}
		else {
			pageFirstIndex = 1;
			pageLastIndex = paging.getPageSize();
		}
		if(pageLastIndex > paging.getTotalRows()) {
			pageLastIndex = paging.getTotalRows();
		}
	}

	/**
	 * @return
	 */
	public PagingHelper getPaging() {
		return paging;
	}

	/**
	 * @return
	 */
	public List<Integer> getPages() {
		return pages;
	}

	/**
	 * @return
	 */
	public Integer getPageFirstIndex() {
		return pageFirstIndex;
	}

	/**
	 * @return
	 */
	public Integer getPageLastIndex() {
		return pageLastIndex;
	}
}