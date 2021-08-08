package rs.ac.uns.ftn.kts.students.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String svrhaUplate;

    private Integer vrednostUplate;

    private boolean active;

    private Date date;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Student student;

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

    public String getSvrhaUplate() {
        return svrhaUplate;
    }

    public void setSvrhaUplate(String svrhaUplate) {
        this.svrhaUplate = svrhaUplate;
    }

    public Integer getVrednostUplate() {
        return vrednostUplate;
    }

    public void setVrednostUplate(Integer vrednostUplate) {
        this.vrednostUplate = vrednostUplate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}