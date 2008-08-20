//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.08.19 at 11:36:25 PM BST 
//


package org.drools.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jvnet.hyperjaxb3.item.MixedItem;
import org.jvnet.hyperjaxb3.item.MixedItemUtils;
import org.jvnet.hyperjaxb3.xml.bind.JAXBContextUtils;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.ElementAsString;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.builder.JAXBEqualsBuilder;
import org.jvnet.jaxb2_commons.lang.builder.JAXBHashCodeBuilder;
import org.w3c.dom.Element;


/**
 * <p>Java class for tExtensibleMixedContentElements complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tExtensibleMixedContentElements">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentation" type="{http://www.drools.org/WS-HT}tDocumentation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tExtensibleMixedContentElements", propOrder = {
    "content"
})
@XmlSeeAlso({
    TFrom.class,
    TArgument.class,
    TDescription.class,
    TText.class,
    TToPart.class,
    TParameter.class
})
@Entity(name = "org.drools.task.TExtensibleMixedContentElements")
@Table(name = "TEXTENSIBLEMIXEDCONTENTELEME_0")
@Inheritance(strategy = InheritanceType.JOINED)
public class TExtensibleMixedContentElements
    implements Equals, HashCode
{

    @XmlElementRef(name = "documentation", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class)
    @XmlMixed
    @XmlAnyElement
    protected List<Object> content;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();
    @XmlAttribute(name = "Hjid")
    protected Long hjid;
    @XmlTransient
    protected List<TExtensibleMixedContentElements.ContentItem> contentItems;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * {@link Element }
     * {@link JAXBElement }{@code <}{@link org.drools.task.TDocumentation }{@code >}
     * 
     * 
     */
    @Transient
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

    /**
     * 
     * 
     */
    public void setContent(List<Object> content) {
        this.content = content;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    @Transient
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
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

    @OneToMany(targetEntity = org.drools.task.TExtensibleMixedContentElements.ContentItem.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CONTENTITEMS_TEXTENSIBLEMIXE_0")
    public List<TExtensibleMixedContentElements.ContentItem> getContentItems() {
        if (this.contentItems == null) {
            this.contentItems = new ArrayList<TExtensibleMixedContentElements.ContentItem>();
        }
        if (MixedItemUtils.shouldBeWrapped(this.content)) {
            this.content = MixedItemUtils.wrap(this.content, this.contentItems, TExtensibleMixedContentElements.ContentItem.class);
        }
        return this.contentItems;
    }

    public void setContentItems(List<TExtensibleMixedContentElements.ContentItem> value) {
        this.content = null;
        this.contentItems = null;
        this.contentItems = value;
        if (this.contentItems == null) {
            this.contentItems = new ArrayList<TExtensibleMixedContentElements.ContentItem>();
        }
        if (MixedItemUtils.shouldBeWrapped(this.content)) {
            this.content = MixedItemUtils.wrap(this.content, this.contentItems, TExtensibleMixedContentElements.ContentItem.class);
        }
    }

    public void equals(Object object, EqualsBuilder equalsBuilder) {
        if (!(object instanceof TExtensibleMixedContentElements)) {
            equalsBuilder.appendSuper(false);
            return ;
        }
        if (this == object) {
            return ;
        }
        final TExtensibleMixedContentElements that = ((TExtensibleMixedContentElements) object);
        equalsBuilder.append(this.getContent(), that.getContent());
    }

    public boolean equals(Object object) {
        if (!(object instanceof TExtensibleMixedContentElements)) {
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
        hashCodeBuilder.append(this.getContent());
    }

    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new JAXBHashCodeBuilder();
        hashCode(hashCodeBuilder);
        return hashCodeBuilder.toHashCode();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @Entity(name = "org.drools.task.TExtensibleMixedContentElements$ContentItem")
    @Table(name = "TEXTENSIBLEMIXEDCONTENTELEME_1")
    @Inheritance(strategy = InheritanceType.JOINED)
    public static class ContentItem
        implements MixedItem<Object> , Equals, HashCode
    {

        @XmlElementRef(name = "documentation", namespace = "http://www.drools.org/WS-HT", type = JAXBElement.class)
        @XmlAnyElement(lax = true)
        protected Object item;
        @XmlAttribute(name = "Text")
        protected String text;
        @XmlAttribute(name = "Hjid")
        protected Long hjid;

        /**
         * 
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     {@link Element }
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TDocumentation }{@code >}
         *     
         */
        @Transient
        public Object getItem() {
            return item;
        }

        /**
         * 
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     {@link Element }
         *     {@link JAXBElement }{@code <}{@link org.drools.task.TDocumentation }{@code >}
         *     
         */
        public void setItem(Object value) {
            this.item = value;
        }

        /**
         * Gets the value of the text property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        @Basic
        @Column(name = "TEXT")
        @Lob
        public String getText() {
            return text;
        }

        /**
         * Sets the value of the text property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setText(String value) {
            this.text = value;
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

        @Basic
        @Column(name = "ITEMELEMENT")
        @Lob
        public String getItemElement() {
            if (this.getItem() instanceof Element) {
                return XmlAdapterUtils.unmarshall(ElementAsString.class, ((Element) this.getItem()));
            } else {
                return null;
            }
        }

        public void setItemElement(String target) {
            if (target!= null) {
                setItem(XmlAdapterUtils.marshall(ElementAsString.class, target));
            }
        }

        @Basic
        @Column(name = "ITEMOBJECT")
        @Lob
        public String getItemObject() {
            if ((!XmlAdapterUtils.isJAXBElement(org.drools.task.TDocumentation.class, new QName("http://www.drools.org/WS-HT", "documentation"), TExtensibleMixedContentElements.class, this.getItem()))&&JAXBContextUtils.isElement("org.drools.task", this.getItem())) {
                return JAXBContextUtils.unmarshall("org.drools.task", this.getItem());
            } else {
                return null;
            }
        }

        public void setItemObject(String target) {
            if (target!= null) {
                setItem(JAXBContextUtils.marshall("org.drools.task", target));
            }
        }

        @ManyToOne(targetEntity = org.drools.task.TDocumentation.class, cascade = {
            CascadeType.ALL
        })
        @JoinColumn(name = "ITEMDOCUMENTATION_TEXTENSIBL_0")
        public org.drools.task.TDocumentation getItemDocumentation() {
            if (XmlAdapterUtils.isJAXBElement(org.drools.task.TDocumentation.class, new QName("http://www.drools.org/WS-HT", "documentation"), TExtensibleMixedContentElements.class, this.getItem())) {
                return XmlAdapterUtils.unmarshallJAXBElement(((JAXBElement<? extends org.drools.task.TDocumentation> ) this.getItem()));
            } else {
                return null;
            }
        }

        public void setItemDocumentation(org.drools.task.TDocumentation target) {
            if (target!= null) {
                setItem(XmlAdapterUtils.marshallJAXBElement(org.drools.task.TDocumentation.class, new QName("http://www.drools.org/WS-HT", "documentation"), TExtensibleMixedContentElements.class, target));
            }
        }

        public void equals(Object object, EqualsBuilder equalsBuilder) {
            if (!(object instanceof TExtensibleMixedContentElements.ContentItem)) {
                equalsBuilder.appendSuper(false);
                return ;
            }
            if (this == object) {
                return ;
            }
            final TExtensibleMixedContentElements.ContentItem that = ((TExtensibleMixedContentElements.ContentItem) object);
            equalsBuilder.append(this.getItem(), that.getItem());
        }

        public boolean equals(Object object) {
            if (!(object instanceof TExtensibleMixedContentElements.ContentItem)) {
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
