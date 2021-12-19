<?xml version="1.0" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:template match="/">
    <HTML>
      <BODY>
        <TABLE WIDTH="600">
         <TR><TD><H1>Wrox Vacations Center</H1></TD></TR>
         <TR><TD><H2>Server-side XSLT Filtering - JSP Generated XML Data</H2></TD></TR>
        </TABLE>
        <TABLE BORDER="1">
          <TR>
            <TD><b>Trip Number</b></TD>
            <TD><b>Region</b></TD>
            <TD><b>Location</b></TD>
            <TD><b>Start</b></TD>
            <TD><b>Duration</b></TD>
            <TD><b>Price</b></TD>
          </TR>
          <xsl:for-each select="selloff/trip">
            <TR>
              <TD><xsl:value-of select="@number"/></TD>
              <TD><xsl:value-of select="@region"/></TD>
              <TD><xsl:value-of select="location"/></TD>
              <TD><xsl:value-of select="startdate"/></TD>
              <TD><xsl:value-of select="duration"/></TD>
              <TD><xsl:value-of select="price"/></TD>
            </TR>
          </xsl:for-each>
        </TABLE>
      </BODY>
    </HTML>
  </xsl:template>
</xsl:stylesheet>
