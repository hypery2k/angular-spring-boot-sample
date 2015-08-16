package ngSpring.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractBaseEntity {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "CET")
    protected Date insertDate;

    protected boolean deleted;

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
