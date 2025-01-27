//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.7 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.10.31 at 08:41:49 AM CET 
//


package at.erpel.schemas._1p0.documents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeliveryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeliveryType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}DeliveryRecipient" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}DeliveryDetails" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliveryType", propOrder = {
    "deliveryRecipient",
    "deliveryDetails"
})
public class DeliveryType {

    @XmlElement(name = "DeliveryRecipient")
    protected DeliveryRecipientType deliveryRecipient;
    @XmlElement(name = "DeliveryDetails")
    protected DeliveryDetailsType deliveryDetails;

    /**
     * Details about the recipient of the delivery of goods or services.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryRecipientType }
     *     
     */
    public DeliveryRecipientType getDeliveryRecipient() {
        return deliveryRecipient;
    }

    /**
     * Sets the value of the deliveryRecipient property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryRecipientType }
     *     
     */
    public void setDeliveryRecipient(DeliveryRecipientType value) {
        this.deliveryRecipient = value;
    }

    /**
     * Details about the delivery of good or services specified in this document.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryDetailsType }
     *     
     */
    public DeliveryDetailsType getDeliveryDetails() {
        return deliveryDetails;
    }

    /**
     * Sets the value of the deliveryDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryDetailsType }
     *     
     */
    public void setDeliveryDetails(DeliveryDetailsType value) {
        this.deliveryDetails = value;
    }

}
