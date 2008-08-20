//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.08.19 at 11:36:25 PM BST 
//


package org.drools.task;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jvnet.hyperjaxb3.item.Item;
import org.jvnet.hyperjaxb3.item.ItemUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.builder.JAXBEqualsBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBHashCodeBuilder;


/**
 * <p>Java class for tUserlist complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tUserlist">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.drools.org/WS-HT}user" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tUserlist", propOrder = {
    "user"
})
@Entity(name = "org.drools.task.TUserlist")
@Table(name = "TUSERLIST")
@Inheritance(strategy = InheritanceType.JOINED)
public class TUserlist
    implements Equals, HashCode
{

    protected List<String> user;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;
    @XmlTransient
    protected List<TUserlist.UserItem> userItems;

    /**
     * Gets the value of the user property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the user property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUser().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    @Transient
    public List<String> getUser() {
        if (user == null) {
            user = new ArrayList<String>();
        }
        return this.user;
    }

    /**
     * 
     * 
     */
    public void setUser(List<String> user) {
        this.user = user;
    }

    /**
     * Gets the value of the hjid property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    @Id
    @Column(name = "HJID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getHjid() {
        return hjid;
    }

    /**
     * Sets the value of the hjid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHjid(Long value) {
        this.hjid = value;
    }

    @OneToMany(targetEntity = org.drools.task.TUserlist.UserItem.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "USERITEMS_TUSERLIST_ID")
    public List<TUserlist.UserItem> getUserItems() {
        if (this.userItems == null) {
            this.userItems = new ArrayList<TUserlist.UserItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.user)) {
            this.user = ItemUtils.wrap(this.user, this.userItems, TUserlist.UserItem.class);
        }
        return this.userItems;
    }

    public void setUserItems(List<TUserlist.UserItem> value) {
        this.user = null;
        this.userItems = null;
        this.userItems = value;
        if (this.userItems == null) {
            this.userItems = new ArrayList<TUserlist.UserItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.user)) {
            this.user = ItemUtils.wrap(this.user, this.userItems, TUserlist.UserItem.class);
        }
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof TUserlist)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        final TUserlist that = ((TUserlist) object);
        equalsBuilder.append(this.getUser(), that.getUser());
    }

    public boolean equals(Object object) {
        if (!(object instanceof TUserlist)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
        equals(object, equalsBuilder);
        return equalsBuilder.isEquals();
    }

    public void hashCode(HashCodeBuilder hashCodeBuilder) {
        hashCodeBuilder.append(this.getUser());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Entity(name = "org.drools.task.TUserlist$UserItem")
    @Table(name = "TUSERLISTUSERITEM")
    @Inheritance(strategy = InheritanceType.JOINED)
    public static class UserItem
        implements Item<String> , Equals, HashCode
    {

        @XmlElement(name = "user", namespace = "http://www.drools.org/WS-HT")
        protected String item;
        @XmlAttribute(name = "Hjid")
        protected Long hjid;

        /**
         * Gets the value of the item property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        @Basic
        @Column(name = "ITEM")
        public String getItem() {
            return item;
        }

        /**
         * Sets the value of the item property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setItem(String value) {
            this.item = value;
        }

        /**
         * Gets the value of the hjid property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        @Id
        @Column(name = "HJID")
        @GeneratedValue(strategy = GenerationType.AUTO)
        public Long getHjid() {
            return hjid;
        }

        /**
         * Sets the value of the hjid property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setHjid(Long value) {
            this.hjid = value;
        }

        public void equals(Object object, EqualsBuilder equalsBuilder) {
            if (!(object instanceof TUserlist.UserItem)) {
                equalsBuilder.appendSuper(false);
                return ;
            }
            if (this == object) {
                return ;
            }
            final TUserlist.UserItem that = ((TUserlist.UserItem) object);
            equalsBuilder.append(this.getItem(), that.getItem());
        }

        public boolean equals(Object object) {
            if (!(object instanceof TUserlist.UserItem)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
            equals(object, equalsBuilder);
            return equalsBuilder.isEquals();
        }

        public void hashCode(HashCodeBuilder hashCodeBuilder) {
            hashCodeBuilder.append(this.getItem());
        }

        public int hashCode() {
            final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
            hashCode(hashCodeBuilder);
            return hashCodeBuilder.toHashCode();
        }

    }

}
