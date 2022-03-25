package Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termNum;

    private String termName;
    private String startDate;
    private String endDate;

    //constructor
    public Term(int termNum, String termName, String startDate, String endDate) {
        this.termNum = termNum;
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //String to String
    @Override
    public String toString() {
        return this.termName;
    }

    //Getters and setters
    public int getTermNum() {
        return termNum;
    }

    public void setTermNum(int termNum) {
        this.termNum = termNum;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
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

