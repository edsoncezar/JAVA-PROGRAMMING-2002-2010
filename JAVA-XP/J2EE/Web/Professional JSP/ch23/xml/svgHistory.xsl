<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:svg="http://www.w3.org/2000/svg">
<xsl:template match="StockHistory" name="makeSVG">
  <xsl:variable name="maxClose">
     <xsl:call-template name="max">
        <xsl:with-param name="list" select="Period"/>
      </xsl:call-template>
  </xsl:variable>
  <xsl:variable name="xOffset">
     <xsl:choose>
        <xsl:when test="count(Period) &lt; 2">0</xsl:when>
        <xsl:otherwise>
           <xsl:value-of select="400 div (count(Period)-1)"/>
        </xsl:otherwise>
     </xsl:choose>
  </xsl:variable>
  <xsl:variable name="yMax">
     <xsl:choose>
        <xsl:when test="$maxClose=0">50</xsl:when>
	<xsl:otherwise>
           <xsl:value-of select="round(($maxClose+25) div 50) * 50"/>
	</xsl:otherwise>
     </xsl:choose>
  </xsl:variable>
  <xsl:variable name="svgPath">
     <xsl:call-template name="makePath">
        <xsl:with-param name="closes" select="Period/Close"/>
        <xsl:with-param name="xOffset" select="$xOffset"/>
        <xsl:with-param name="yMax" select="$yMax"/>
     </xsl:call-template>
  </xsl:variable>
  <svg:svg width="460" height="380">
      <!-- draw the title -->
      <svg:text style="font-size:12pt;text-anchor:middle" x="230" y="20">
         Stock History for: <xsl:value-of select="@company"/>
      </svg:text>
      <!-- draw the x and y axis -->
      <svg:path style="fill:none;stroke:black;stroke-width:2"
            d="M40,40 L40,340 L440,340"/>
      <!-- draw the y axis scale -->
      <svg:g style="fill:none;stroke:grey;stroke-width:1">
        <svg:path d="M40,280 L440,280"/>
        <svg:path d="M40,220 L440,220"/>
        <svg:path d="M40,160 L440,160"/>
        <svg:path d="M40,100 L440,100"/>
        <svg:path d="M40,40 L440,40"/>
      </svg:g>
      <!-- draw the y axis labels -->
      <xsl:variable name="yDivisions" select="$yMax div 5"/>
      <svg:g style="font-size:10pt;text-anchor:end">
        <svg:text x="35" y="40"><xsl:value-of select="$yMax"/></svg:text>
        <svg:text x="35" y="100"><xsl:value-of select="$yDivisions * 4"/></svg:text>
        <svg:text x="35" y="160"><xsl:value-of select="$yDivisions * 3"/></svg:text>
        <svg:text x="35" y="220"><xsl:value-of select="$yDivisions * 2"/></svg:text>
        <svg:text x="35" y="280"><xsl:value-of select="$yDivisions * 1"/></svg:text>
        <svg:text x="35" y="340">0</svg:text>
      </svg:g>
      <!-- draw the x axis labels... -->
      <svg:g style="font-size:10pt;text-anchor:middle">
        <svg:text x="40" y="355"><xsl:value-of select="Period[1]/Date"/></svg:text>
        <svg:text x="440" y="355"><xsl:value-of select="Period[last()]/Date"/></svg:text>
      </svg:g>
      <svg:path style="fill:none;stroke:red;stroke-width:2">
         <xsl:attribute name="d">
            <xsl:value-of select="$svgPath"/>
         </xsl:attribute>
      </svg:path>
  </svg:svg>
</xsl:template>

<xsl:template name="makePath">
  <xsl:param name="closes"/>
  <xsl:param name="xOffset"/>
  <xsl:param name="yMax"/>
  <xsl:for-each select="$closes">
    <xsl:choose>
       <xsl:when test="position()=1">M</xsl:when>
       <xsl:otherwise>L</xsl:otherwise>
    </xsl:choose>
    <xsl:value-of select="concat(round((position()-1) * $xOffset)+40,',',
                                 round(340 - ((. div $yMax) * 300)),' ')"/>
  </xsl:for-each>
</xsl:template>

<xsl:template name="max">
<xsl:param name="list"/>
<xsl:choose>
<xsl:when test="$list">
   <xsl:variable name="first" select="$list[1]/Close"/>
   <xsl:variable name="max-of-rest">
      <xsl:call-template name="max">
         <xsl:with-param name="list" select="$list[position()!=1]"/>
      </xsl:call-template>
   </xsl:variable>
   <xsl:choose>
   <xsl:when test="$first &gt; $max-of-rest">
      <xsl:value-of select="$first"/>
   </xsl:when>
   <xsl:otherwise>
      <xsl:value-of select="$max-of-rest"/>
   </xsl:otherwise>
   </xsl:choose>
</xsl:when>
<xsl:otherwise>0</xsl:otherwise>
</xsl:choose>
</xsl:template>
</xsl:stylesheet>
