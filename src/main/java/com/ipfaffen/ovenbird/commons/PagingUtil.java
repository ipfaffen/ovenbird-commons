package com.ipfaffen.ovenbird.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Isaias Pfaffenseller
 */
public class PagingUtil {

	/**
	 * @param paging
	 * @param maxVisible
	 * @return
	 */
	public static List<Integer> buildLimitedPageList(PagingHelper paging, int maxVisible) {
		int first = 1;
		int last = paging.getPagesAvailable();

		List<Integer> pages = new ArrayList<Integer>();

		// If there is less pages then the size then return a list with all pages.
		if(last <= maxVisible) {
			for(int i = 1; i <= last; i++) {
				pages.add(i);
			}

			return pages;
		}

		pages.add(first);
		pages.add(last);

		int page = paging.getPageNumber();
		int left = (page - 1);
		int right = (page + 1);
		int group = maxVisible - 2;

		// If the selected page it is not the first nor the last then add the page.
		if(page != first && page != last) {
			pages.add(page);
			group = group - 1;
		}

		boolean addLeft = true;

		// Add pages with left and right alternation.
		while(group > 0) {
			if(addLeft && (left > first)) {
				pages.add(left);

				group--;
				left--;
			}
			else if(right < last) {
				pages.add(right);

				group--;
				right++;
			}

			addLeft = !addLeft;
		}

		// If left is not the first it means a separator will be necessary.
		if(left > first) {
			// Negative values will be considered as a separator.
			pages.add(left * -1);

			// Remove one to maintain the correct size.
			pages.remove((Object) (left + 1));
		}

		// If right is not the last it means a separator will be necessary.
		if(right < last) {
			// Negative values will be considered as a separator.
			pages.add(right * -1);

			// Remove one to maintain the correct size.
			pages.remove((Object) (right - 1));
		}

		// Sort pages.
		Collections.sort(pages, new Comparator<Integer>() {
			@Override
			public int compare(Integer pageA, Integer pageB) {
				// If the page is negative then positive it just for sort.
				pageA = ((pageA < 0) ? (pageA * -1) : pageA);
				pageB = ((pageB < 0) ? (pageB * -1) : pageB);

				return pageA.compareTo(pageB);
			}
		});

		return pages;
	}
}