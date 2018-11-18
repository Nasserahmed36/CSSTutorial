<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:strip-space elements="ingredients"/>

    <xsl:template match="ingredients">
        <p>
            <b>Ingredients:</b> <xsl:apply-templates />
        </p>
    </xsl:template>

    <xsl:template match="item">
        (<xsl:value-of select="position()"/>)
        <xsl:apply-templates />,
    </xsl:template>

    <xsl:template match="item[position()=last()]">
        (<xsl:value-of select="position()"/>)
        <xsl:apply-templates />.
    </xsl:template>
</xsl:stylesheet>