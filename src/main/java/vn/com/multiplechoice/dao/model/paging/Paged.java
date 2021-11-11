package vn.com.multiplechoice.dao.model.paging;

import org.springframework.data.domain.Page;

public class Paged<T> {
	private Page<T> page;
	private Paging paging;

	public Paged() {
	}

	public Paged(Page<T> page, Paging paging) {
		super();
		this.page = page;
		this.paging = paging;
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((page == null) ? 0 : page.hashCode());
		result = prime * result + ((paging == null) ? 0 : paging.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paged other = (Paged) obj;
		if (page == null) {
			if (other.page != null)
				return false;
		} else if (!page.equals(other.page))
			return false;
		if (paging == null) {
			if (other.paging != null)
				return false;
		} else if (!paging.equals(other.paging))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Paged [page=" + page + ", paging=" + paging + "]";
	}

}