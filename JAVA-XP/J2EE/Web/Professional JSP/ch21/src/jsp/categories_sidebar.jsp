<font size='5'><bean:message key="showcategories.header"/></font><p>
<table width='145'>
<logic:iterate id="category" type="com.wrox.pjsp2.common.Category" 
                             name="<%= Constants.CATEGORIES_ARRAY_KEY %>">
  <tr>
    <td>
    <html:link page="/showCDs.do" name="category" property="mapping">
      <bean:write name="category" property="categoryName" filter="true"/>
    </html:link>
   </td>
  </tr>
</logic:iterate>
</table>
</p>
