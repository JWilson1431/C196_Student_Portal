package Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    private String title;
    private String startDate;
    private String endDate;

    //Constructor


    public Assessment(int assessmentId, String title, String startDate, String endDate) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //String to string


    @Override
    public String toString() {
        return "Assessments{" +
                "assessmentId=" + assessmentId +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    //getters and setters

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

