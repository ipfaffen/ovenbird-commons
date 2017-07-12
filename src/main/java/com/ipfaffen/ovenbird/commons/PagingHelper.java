package com.ipfaffen.ovenbird.commons;

/**
 * @author Isaias Pfaffenseller
 */
public class PagingHelper {
	
	private int pageSize;
	private int pageNumber = 1;
	private int pagesAvailable;
	private int totalRows;

	/**
	 * @param pageSize
	 */
	public PagingHelper(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @return
	 */
	public int getPagesAvailable() {
		return pagesAvailable;
	}

	/**
	 * @param pagesAvailable
	 */
	public void setPagesAvailable(int pagesAvailable) {
		this.pagesAvailable = pagesAvailable;
	}

	/**
	 * @return
	 */
	public int getTotalRows() {
		return totalRows;
	}

	/**
	 * @param totalRows
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;

		int div = new Double(totalRows / pageSize).intValue();
		if((totalRows % pageSize) > 0) {
			div++;
		}

		setPagesAvailable(div);
	}

	/**
	 * @param pageNumber
	 */
	public void setPage(int pageNumber) {
		if(pagesAvailable > 0 && pageNumber > pagesAvailable) {
			this.pageNumber = pagesAvailable;
		}
		else if(pageNumber <= 0) {
			this.pageNumber = 1;
		}
		else {
			this.pageNumber = pageNumber;
		}
	}

	public void nextPage() {
		pageNumber++;
		if(pageNumber > pagesAvailable) {
			pageNumber = pagesAvailable;
		}
	}

	public void previousPage() {
		pageNumber--;
		if(pageNumber <= 0) {
			pageNumber = 1;
		}
	}

	/**
	 * @return
	 */
	public int getStartIndex() {
		if(pageNumber == 1) {
			return 0;
		}
		return ((pageNumber - 1) * pageSize);
	}

	/**
	 * @return
	 */
	public int getEndIndex() {
		return (pageNumber * pageSize);
	}
}