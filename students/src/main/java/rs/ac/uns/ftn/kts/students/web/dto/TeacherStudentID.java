package rs.ac.uns.ftn.kts.students.web.dto;

public class TeacherStudentID {
    private Long StudentID;
    private Long TeacherID;

    public TeacherStudentID() {
    }

    public TeacherStudentID(Long studentID, Long teacherID) {
        StudentID = studentID;
        TeacherID = teacherID;
    }

    public Long getStudentID() {
        return StudentID;
    }

    public void setStudentID(Long studentID) {
        StudentID = studentID;
    }

    public Long getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(Long teacherID) {
        TeacherID = teacherID;
    }
}
