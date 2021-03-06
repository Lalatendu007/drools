package org.drools.task;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Comment implements Externalizable  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    private String text;
    
    @ManyToOne()
    private User addedBy;
    
    private Date addedDate;    
    
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong( id );
        out.writeUTF( text );
        addedBy.writeExternal( out );        
        out.writeLong( addedDate.getTime() );        
    }    
    
    public void readExternal(ObjectInput in) throws IOException,
                                            ClassNotFoundException {
        id = in.readLong();
        text = in.readUTF();
        addedBy = new User();
        addedBy.readExternal( in );
        addedDate = new Date( in.readLong() );
    }
    
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((addedBy == null) ? 0 : addedBy.hashCode());
        result = prime * result + ((addedDate == null) ? 0 : addedDate.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) return true;
        if ( obj == null ) return false;
        if ( !(obj instanceof Comment) ) return false;
        Comment other = (Comment) obj;
        if ( addedBy == null ) {
            if ( other.addedBy != null ) return false;
        } else if ( !addedBy.equals( other.addedBy ) ) return false;
        if ( addedDate == null ) {
            if ( other.addedDate != null ) return false;
        } else if ( addedDate.getTime() != other.addedDate.getTime() ) return false;
        if ( text == null ) {
            if ( other.text != null ) return false;
        } else if ( !text.equals( other.text ) ) return false;
        return true;
    }    
    
    
}
