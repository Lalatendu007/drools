//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.08.19 at 11:36:25 PM BST 
//


package org.drools.task;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jvnet.hyperjaxb3.item.Item;
import org.jvnet.hyperjaxb3.item.ItemUtils;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.builder.JAXBEqualsBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBHashCodeBuilder;


/**
 * <p>Java class for tPeopleAssignments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tPeopleAssignments">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.drools.org/WS-HT}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.drools.org/WS-HT}genericHumanRole" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tPeopleAssignments", propOrder = {
    "genericHumanRole"
})
@Entity(name = "org.drools.task.TPeopleAssignments")
@Table(name = "TPEOPLEASSIGNMENTS")
public class TPeopleAssignments
    extends TExtensibleElements
    implements Equals, HashCode
{

    @XmlElementRefs({
        @XmlElementRef(name = "businessAdministrators", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class),
        @XmlElementRef(name = "taskStakeholders", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class),
        @XmlElementRef(name = "recipients", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class),
        @XmlElementRef(name = "excludedOwners", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class),
        @XmlElementRef(name = "potentialOwners", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class),
        @XmlElementRef(name = "taskInitiator", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class)
    })
    protected List<JAXBElement<org.drools.task.TGenericHumanRole>> genericHumanRole;
    @XmlTransient
    protected List<TPeopleAssignments.GenericHumanRoleItem> genericHumanRoleItems;

    /**
     * Gets the value of the genericHumanRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genericHumanRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGenericHumanRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
     * {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
     * {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
     * {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
     * {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
     * {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
     * 
     * 
     */
    @Transient
    public List<JAXBElement<org.drools.task.TGenericHumanRole>> getGenericHumanRole() {
        if (genericHumanRole == null) {
            genericHumanRole = new ArrayList<JAXBElement<org.drools.task.TGenericHumanRole>>();
        }
        return this.genericHumanRole;
    }

    /**
     * 
     * 
     */
    public void setGenericHumanRole(List<JAXBElement<org.drools.task.TGenericHumanRole>> genericHumanRole) {
        this.genericHumanRole = genericHumanRole;
    }

    @OneToMany(targetEntity = org.drools.task.TPeopleAssignments.GenericHumanRoleItem.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "GENERICHUMANROLEITEMS_TPEOPL_0")
    public List<TPeopleAssignments.GenericHumanRoleItem> getGenericHumanRoleItems() {
        if (this.genericHumanRoleItems == null) {
            this.genericHumanRoleItems = new ArrayList<TPeopleAssignments.GenericHumanRoleItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.genericHumanRole)) {
            this.genericHumanRole = ItemUtils.wrap(this.genericHumanRole, this.genericHumanRoleItems, TPeopleAssignments.GenericHumanRoleItem.class);
        }
        return this.genericHumanRoleItems;
    }

    public void setGenericHumanRoleItems(List<TPeopleAssignments.GenericHumanRoleItem> value) {
        this.genericHumanRole = null;
        this.genericHumanRoleItems = null;
        this.genericHumanRoleItems = value;
        if (this.genericHumanRoleItems == null) {
            this.genericHumanRoleItems = new ArrayList<TPeopleAssignments.GenericHumanRoleItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.genericHumanRole)) {
            this.genericHumanRole = ItemUtils.wrap(this.genericHumanRole, this.genericHumanRoleItems, TPeopleAssignments.GenericHumanRoleItem.class);
        }
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof TPeopleAssignments)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        super.equals(object, equalsBuilder);
        final TPeopleAssignments that = ((TPeopleAssignments) object);
        equalsBuilder.append(this.getGenericHumanRole(), that.getGenericHumanRole());
    }

    public boolean equals(Object object) {
        if (!(object instanceof TPeopleAssignments)) {
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
        super.hashCode(hashCodeBuilder);
        hashCodeBuilder.append(this.getGenericHumanRole());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Entity(name = "org.drools.task.TPeopleAssignments$GenericHumanRoleItem")
    @Table(name = "TPEOPLEASSIGNMENTSGENERICHUM_0")
    @Inheritance(strategy = InheritanceType.JOINED)
    public static class GenericHumanRoleItem
        implements Item<JAXBElement<org.drools.task.TGenericHumanRole>> , Equals, HashCode
    {

        @XmlElementRefs({
            @XmlElementRef(name = "businessAdministrators", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class),
            @XmlElementRef(name = "taskStakeholders", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class),
            @XmlElementRef(name = "recipients", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class),
            @XmlElementRef(name = "excludedOwners", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class),
            @XmlElementRef(name = "potentialOwners", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class),
            @XmlElementRef(name = "taskInitiator", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class)
        })
        protected JAXBElement<org.drools.task.TGenericHumanRole> item;
        @XmlAttribute(name = "Hjid")
        protected Long hjid;

        /**
         * 
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     
         */
        @Transient
        public JAXBElement<org.drools.task.TGenericHumanRole> getItem() {
            return item;
        }

        /**
         * 
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TGenericHumanRole }{@code >}
         *     
         */
        public void setItem(JAXBElement<org.drools.task.TGenericHumanRole> value) {
            this.item = ((JAXBElement<org.drools.task.TGenericHumanRole> ) value);
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

        @ManyToOne(targetEntity = org.drools.task.TGenericHumanRole.class, cascade = {
            CascadeType.ALL
        })
        @JoinColumn(name = "ITEMBUSINESSADMINISTRATORS_T_0")
        public org.drools.task.TGenericHumanRole getItemBusinessAdministrators() {
            if (XmlAdapterUtils.isJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "businessAdministrators"), JAXBElement.GlobalScope.class, this.getItem())) {
                return XmlAdapterUtils.unmarshallJAXBElement(((JAXBElement<? extends org.drools.task.TGenericHumanRole> ) this.getItem()));
            } else {
                return null;
            }
        }

        public void setItemBusinessAdministrators(org.drools.task.TGenericHumanRole target) {
            if (target!= null) {
                setItem(XmlAdapterUtils.marshallJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "businessAdministrators"), JAXBElement.GlobalScope.class, target));
            }
        }

        @ManyToOne(targetEntity = org.drools.task.TGenericHumanRole.class, cascade = {
            CascadeType.ALL
        })
        @JoinColumn(name = "ITEMTASKSTAKEHOLDERS_TPEOPLE_0")
        public org.drools.task.TGenericHumanRole getItemTaskStakeholders() {
            if (XmlAdapterUtils.isJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "taskStakeholders"), JAXBElement.GlobalScope.class, this.getItem())) {
                return XmlAdapterUtils.unmarshallJAXBElement(((JAXBElement<? extends org.drools.task.TGenericHumanRole> ) this.getItem()));
            } else {
                return null;
            }
        }

        public void setItemTaskStakeholders(org.drools.task.TGenericHumanRole target) {
            if (target!= null) {
                setItem(XmlAdapterUtils.marshallJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "taskStakeholders"), JAXBElement.GlobalScope.class, target));
            }
        }

        @ManyToOne(targetEntity = org.drools.task.TGenericHumanRole.class, cascade = {
            CascadeType.ALL
        })
        @JoinColumn(name = "ITEMRECIPIENTS_TPEOPLEASSIGN_0")
        public org.drools.task.TGenericHumanRole getItemRecipients() {
            if (XmlAdapterUtils.isJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "recipients"), JAXBElement.GlobalScope.class, this.getItem())) {
                return XmlAdapterUtils.unmarshallJAXBElement(((JAXBElement<? extends org.drools.task.TGenericHumanRole> ) this.getItem()));
            } else {
                return null;
            }
        }

        public void setItemRecipients(org.drools.task.TGenericHumanRole target) {
            if (target!= null) {
                setItem(XmlAdapterUtils.marshallJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "recipients"), JAXBElement.GlobalScope.class, target));
            }
        }

        @ManyToOne(targetEntity = org.drools.task.TGenericHumanRole.class, cascade = {
            CascadeType.ALL
        })
        @JoinColumn(name = "ITEMEXCLUDEDOWNERS_TPEOPLEAS_0")
        public org.drools.task.TGenericHumanRole getItemExcludedOwners() {
            if (XmlAdapterUtils.isJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "excludedOwners"), JAXBElement.GlobalScope.class, this.getItem())) {
                return XmlAdapterUtils.unmarshallJAXBElement(((JAXBElement<? extends org.drools.task.TGenericHumanRole> ) this.getItem()));
            } else {
                return null;
            }
        }

        public void setItemExcludedOwners(org.drools.task.TGenericHumanRole target) {
            if (target!= null) {
                setItem(XmlAdapterUtils.marshallJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "excludedOwners"), JAXBElement.GlobalScope.class, target));
            }
        }

        @ManyToOne(targetEntity = org.drools.task.TGenericHumanRole.class, cascade = {
            CascadeType.ALL
        })
        @JoinColumn(name = "ITEMPOTENTIALOWNERS_TPEOPLEA_0")
        public org.drools.task.TGenericHumanRole getItemPotentialOwners() {
            if (XmlAdapterUtils.isJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "potentialOwners"), JAXBElement.GlobalScope.class, this.getItem())) {
                return XmlAdapterUtils.unmarshallJAXBElement(((JAXBElement<? extends org.drools.task.TGenericHumanRole> ) this.getItem()));
            } else {
                return null;
            }
        }

        public void setItemPotentialOwners(org.drools.task.TGenericHumanRole target) {
            if (target!= null) {
                setItem(XmlAdapterUtils.marshallJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "potentialOwners"), JAXBElement.GlobalScope.class, target));
            }
        }

        @ManyToOne(targetEntity = org.drools.task.TGenericHumanRole.class, cascade = {
            CascadeType.ALL
        })
        @JoinColumn(name = "ITEMTASKINITIATOR_TPEOPLEASS_0")
        public org.drools.task.TGenericHumanRole getItemTaskInitiator() {
            if (XmlAdapterUtils.isJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "taskInitiator"), JAXBElement.GlobalScope.class, this.getItem())) {
                return XmlAdapterUtils.unmarshallJAXBElement(((JAXBElement<? extends org.drools.task.TGenericHumanRole> ) this.getItem()));
            } else {
                return null;
            }
        }

        public void setItemTaskInitiator(org.drools.task.TGenericHumanRole target) {
            if (target!= null) {
                setItem(XmlAdapterUtils.marshallJAXBElement(org.drools.task.TGenericHumanRole.class, new QName("http://www.drools.org/WS-HT", "taskInitiator"), JAXBElement.GlobalScope.class, target));
            }
        }

        public void equals(Object object, EqualsBuilder equalsBuilder) {
            if (!(object instanceof TPeopleAssignments.GenericHumanRoleItem)) {
                equalsBuilder.appendSuper(false);
                return ;
            }
            if (this == object) {
                return ;
            }
            final TPeopleAssignments.GenericHumanRoleItem that = ((TPeopleAssignments.GenericHumanRoleItem) object);
            equalsBuilder.append(this.getItem(), that.getItem());
        }

        public boolean equals(Object object) {
            if (!(object instanceof TPeopleAssignments.GenericHumanRoleItem)) {
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
