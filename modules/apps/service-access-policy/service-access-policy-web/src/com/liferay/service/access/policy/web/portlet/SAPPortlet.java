/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.service.access.policy.web.portlet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.service.access.policy.service.SAPEntryService;
import com.liferay.service.access.policy.web.constants.SAPPortletKeys;
import com.liferay.service.access.policy.web.constants.SAPWebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	property = {
		"com.liferay.portlet.css-class-wrapper=service-access-policy-portlet",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=Service Access Policy",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.info.keywords=Service Access Policy",
		"javax.portlet.info.short-title=Service Access Policy",
		"javax.portlet.info.title=Service Access Policy",
		"javax.portlet.init-param.clear-request-parameters=true",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SAPPortletKeys.SERVICE_ACCESS_POLICY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class SAPPortlet extends MVCPortlet {

	public void deleteSAPEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long sapEntryId = ParamUtil.getLong(actionRequest, "sapEntryId");

		_sapEntryService.deleteSAPEntry(sapEntryId);
	}

	public void getMethodNames(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String contextName = ParamUtil.getString(
			resourceRequest, "contextName");

		Map<String, Set<JSONWebServiceActionMapping>>
			jsonWebServiceActionMappingsMap =
				getServiceJSONWebServiceActionMappingsMap(contextName);

		String serviceClassName = ParamUtil.getString(
			resourceRequest, "serviceClassName");

		Set<JSONWebServiceActionMapping> jsonWebServiceActionMappingsSet =
			jsonWebServiceActionMappingsMap.get(serviceClassName);

		for (JSONWebServiceActionMapping jsonWebServiceActionMapping :
				jsonWebServiceActionMappingsSet) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			Method method = jsonWebServiceActionMapping.getActionMethod();

			String methodName = method.getName();

			jsonObject.put("methodName", methodName);

			jsonArray.put(jsonObject);
		}

		PrintWriter writer = resourceResponse.getWriter();

		writer.write(jsonArray.toString());
	}

	public Set<Map<String, String>> getRemoteServiceClassNames() {
		Set<Map<String, String>> remoteServiceClassNames = new LinkedHashSet<>();

		Set<String> contextNames =
			_jsonWebServiceActionsManager.getContextNames();

		for (String contextName : contextNames) {
			Map<String, Set<JSONWebServiceActionMapping>>
				jsonWebServiceActionMappingsSet =
					getServiceJSONWebServiceActionMappingsMap(contextName);

			for (Map.Entry<String, Set<JSONWebServiceActionMapping>> entry :
					jsonWebServiceActionMappingsSet.entrySet()) {

				Map<String, String> serviceDescription = new HashMap<>();

				serviceDescription.put(
					"serviceClassName", entry.getKey());

				Set<JSONWebServiceActionMapping> actionMappings =
					entry.getValue();

				JSONWebServiceActionMapping firstActionMapping =
					actionMappings.iterator().next();

				serviceDescription.put(
					"contextName", firstActionMapping.getContextName());

				remoteServiceClassNames.add(serviceDescription);
			}
		}

		return remoteServiceClassNames;
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		String mvcPath = ParamUtil.getString(renderRequest, "mvcPath");

		if (mvcPath.equals("/edit_entry.jsp")) {
			Set<Map<String, String>> remoteServiceClassNames =
				getRemoteServiceClassNames();

			renderRequest.setAttribute(
				SAPWebKeys.REMOTE_SERVICES_CLASS_NAMES,
				remoteServiceClassNames);
		}

		super.render(renderRequest, renderResponse);
	}

	public void updateSAPEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long sapEntryId = ParamUtil.getLong(actionRequest, "sapEntryId");

		String allowedServiceSignatures = ParamUtil.getString(
			actionRequest, "allowedServiceSignatures");
		boolean defaultSAPEntry = ParamUtil.getBoolean(
			actionRequest, "defaultSAPEntry");
		boolean enabled = ParamUtil.getBoolean(actionRequest, "enabled");
		String name = ParamUtil.getString(actionRequest, "name");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (sapEntryId > 0) {
			_sapEntryService.updateSAPEntry(
				sapEntryId, allowedServiceSignatures, defaultSAPEntry, enabled,
				name, titleMap, serviceContext);
		}
		else {
			_sapEntryService.addSAPEntry(
				allowedServiceSignatures, defaultSAPEntry, enabled, name,
				titleMap, serviceContext);
		}
	}

	protected Map<String, Set<JSONWebServiceActionMapping>>
		getServiceJSONWebServiceActionMappingsMap(String contextName) {

		Map<String, Set<JSONWebServiceActionMapping>>
			jsonWebServiceActionMappingsMap = new LinkedHashMap<>();

		List<JSONWebServiceActionMapping> jsonWebServiceActionMappings =
			_jsonWebServiceActionsManager.getJSONWebServiceActionMappings(
				contextName);

		for (JSONWebServiceActionMapping jsonWebServiceActionMapping :
				jsonWebServiceActionMappings) {

			Object actionObject = jsonWebServiceActionMapping.getActionObject();

			Class<?> serviceClass = actionObject.getClass();

			Class[] serviceInterfaces = serviceClass.getInterfaces();

			for (Class<?> serviceInterface : serviceInterfaces) {
				Annotation[] declaredAnnotations =
					serviceInterface.getDeclaredAnnotations();

				for (Annotation declaredAnnotation : declaredAnnotations) {
					if (!(declaredAnnotation instanceof AccessControlled)) {
						continue;
					}

					String serviceClassName = serviceInterface.getName();

					Set<JSONWebServiceActionMapping>
						jsonWebServiceActionMappingsSet =
							jsonWebServiceActionMappingsMap.get(
								serviceClassName);

					if (jsonWebServiceActionMappingsSet == null) {
						jsonWebServiceActionMappingsSet = new LinkedHashSet<>();

						jsonWebServiceActionMappingsMap.put(
							serviceClassName, jsonWebServiceActionMappingsSet);
					}

					jsonWebServiceActionMappingsSet.add(
						jsonWebServiceActionMapping);
				}
			}
		}

		return jsonWebServiceActionMappingsMap;
	}

	@Reference(unbind = "-")
	protected void setJSONWebServiceActionsManager(
		JSONWebServiceActionsManager jsonWebServiceActionsManager) {

		_jsonWebServiceActionsManager = jsonWebServiceActionsManager;
	}

	@Reference(unbind = "-")
	protected void setSAPEntryService(SAPEntryService sapEntryService) {
		_sapEntryService = sapEntryService;
	}

	private JSONWebServiceActionsManager _jsonWebServiceActionsManager;
	private SAPEntryService _sapEntryService;

}