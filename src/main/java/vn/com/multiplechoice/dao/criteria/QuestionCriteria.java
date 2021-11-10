package vn.com.multiplechoice.dao.criteria;

import vn.com.multiplechoice.dao.model.enums.PageSize;
import vn.com.multiplechoice.dao.model.enums.QuestionType;

public class QuestionCriteria {
    private String searchText;
    private QuestionType type;
    private long start;
    private PageSize size;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public PageSize getSize() {
        return size;
    }

    public void setSize(PageSize size) {
        this.size = size;
    }

}
