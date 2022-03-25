package Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;

    private String courseTitle;
    private String startDate;
    private String endDate;
    private String status;
    private String courseMentor;
    private String noteInfo;
    private int termNum;

    //constructor


    public Course(int courseId, String courseTitle, String startDate, String endDate, String status, String courseMentor, String noteInfo, int termNum) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.courseMentor = courseMentor;
        this.noteInfo = noteInfo;
        this.termNum = termNum;
    }

    //String to String
    @Override
    public String toString() {
        return "Courses{" +
                "courseId=" + courseId +
                ", courseTitle='" + courseTitle + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", courseMentor='" + courseMentor + '\'' +
                ", noteInfo='" + noteInfo + '\'' +
                ", termNum=" + termNum +
                '}';
    }

    //getters and setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourseMentor() {
        return courseMentor;
    }

    public void setCourseMentor(String courseMentor) {
        this.courseMentor = courseMentor;
    }

    public String getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(String noteInfo) {
        this.noteInfo = noteInfo;
    }

    public int getTermNum() {
        return termNum;
    }

    public void setTermNum(int termNum) {
        this.termNum = termNum;
    }
}
