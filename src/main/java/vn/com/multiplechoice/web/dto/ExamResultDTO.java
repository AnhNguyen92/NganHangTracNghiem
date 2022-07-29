package vn.com.multiplechoice.web.dto;

import java.util.ArrayList;
import java.util.List;

public class ExamResultDTO {
    private List<ExamResultItemDTO> examResultItemDTOs = new ArrayList<>();
    private int totalTrueAnswer = 0;
    private double totalScore = 0.0;

    public List<ExamResultItemDTO> getExamResultItemDTOs() {
        return examResultItemDTOs;
    }

    public void setExamResultItemDTOs(List<ExamResultItemDTO> examResultItemDTOs) {
        this.examResultItemDTOs = examResultItemDTOs;
    }

    public int getTotalTrueAnswer() {
        return totalTrueAnswer;
    }

    public void setTotalTrueAnswer(int totalTrueAnswer) {
        this.totalTrueAnswer = totalTrueAnswer;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public void increaseTotalTrueAnswer() {
        this.totalTrueAnswer++;
    }
}
