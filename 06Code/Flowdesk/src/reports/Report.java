package reports;

import enums.ReportType;
import utils.Date;

public class Report {
    private String reportId;
    private Date startDate;
    private Date endDate;
    private ReportType reportType;

    public Report(String reportId, Date startDate, Date endDate, ReportType reportType) {
        this.reportId = reportId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reportType = reportType;
    }

    public void generateDailyReport() {
    }

    public void generateWeeklyReport() {
    }

    public void generateMonthlyReport() {
    }

    /**
     * @return the reportId
     */
    public String getReportId() {
        return reportId;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the reportType
     */
    public ReportType getReportType() {
        return reportType;
    }

    /**
     * @param reportType the reportType to set
     */
    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }
}
