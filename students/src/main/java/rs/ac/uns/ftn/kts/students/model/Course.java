package rs.ac.uns.ftn.kts.students.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Set<Enrollment> enrollments = new HashSet<Enrollment>();

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Set<Exam> exams = new HashSet<Exam>();

    @ManyToMany
    @JoinTable(name = "teaching",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id"))
    private Set<Teacher> teachers = new HashSet<Teacher>();

    private boolean active;

    public void remove(Enrollment enrollment) {
        enrollment.setCourse(null);
    }
    public void remove(Teacher teacher) {
        this.teachers.remove(teacher);
        teacher.getCourses().remove(this);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<Exam> getExams() {
        return exams;
    }

    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }
}
