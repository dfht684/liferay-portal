<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/mail/init.jsp" %>

<c:choose>
	<c:when test="<%= Validator.isNotNull(forwardAddress) %>">
		<%= LanguageUtil.format(pageContext, "all-email-from-x-is-being-forwarded-to-x", new Object[] {"<b>" + user.getEmailAddress() + "</b>", "<b>" + StringUtil.replace(forwardAddress, StringPool.SPACE, ", ") + "</b>"}, false) %>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= user.hasCompanyMx() %>">
				<liferay-util:include page="/html/portlet/mail/mail.jsp" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/html/portlet/mail/register.jsp" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>