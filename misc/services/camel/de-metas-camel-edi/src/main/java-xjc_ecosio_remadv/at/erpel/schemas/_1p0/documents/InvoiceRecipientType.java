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
import at.erpel.schemas._1p0.documents.ext.InvoiceRecipientExtensionType;


/**
 * <p>Java class for InvoiceRecipientType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceRecipientType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://erpel.at/schemas/1p0/documents}BusinessEntityType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents}SuppliersInvoiceRecipientID" minOccurs="0"/&gt;
 *         &lt;element ref="{http://erpel.at/schemas/1p0/documents/ext}InvoiceRecipientExtension" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceRecipientType", propOrder = {
    "suppliersInvoiceRecipientID",
    "invoiceRecipientExtension"
})
public class InvoiceRecipientType
    extends BusinessEntityType
{

    @XmlElement(name = "SuppliersInvoiceRecipientID")
    protected String suppliersInvoiceRecipientID;
    @XmlElement(name = "InvoiceRecipientExtension", namespace = "http://erpel.at/schemas/1p0/documents/ext")
    protected InvoiceRecipientExtensionType invoiceRecipientExtension;

    /**
     * ID of the invoice recipient issued by the supplier.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuppliersInvoiceRecipientID() {
        return suppliersInvoiceRecipientID;
    }

    /**
     * Sets the value of the suppliersInvoiceRecipientID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuppliersInvoiceRecipientID(String value) {
        this.suppliersInvoiceRecipientID = value;
    }

    /**
     * Gets the value of the invoiceRecipientExtension property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceRecipientExtensionType }
     *     
     */
    public InvoiceRecipientExtensionType getInvoiceRecipientExtension() {
        return invoiceRecipientExtension;
    }

    /**
     * Sets the value of the invoiceRecipientExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceRecipientExtensionType }
     *     
     */
    public void setInvoiceRecipientExtension(InvoiceRecipientExtensionType value) {
        this.invoiceRecipientExtension = value;
    }

}
