package com.nextinno.underground.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by rsjung on 2016-11-30.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractDomain {
    @Id
    @GeneratedValue
    private Long idx;

    @CreatedDate
    private Date createDate;

    @LastModifiedDate
    private Date modifiedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractDomain that = (AbstractDomain) o;
        return idx.equals(that.idx);
    }

    @Override
    public int hashCode() {
        return idx.hashCode();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AbstractDomain{");
        sb.append("idx=").append(idx);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifiedDate=").append(modifiedDate);
        sb.append('}');
        return sb.toString();
    }
}
