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

package com.liferay.adaptive.media.web.internal.processor;

import com.liferay.adaptive.media.processor.AdaptiveMedia;
import com.liferay.adaptive.media.processor.AdaptiveMediaURLFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class DefaultAdaptiveMediaURLFactoryTest {

	@Before
	public void setUp() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	@Test
	public void testMediaURLWhenPathDoesNotEndInSlash() {
		String pathModule = StringPool.SLASH + StringUtil.randomString();

		Mockito.when(
			_portal.getPathModule()
		).thenReturn(
			pathModule
		);

		URI relativeURI = URI.create(StringUtil.randomString());

		Mockito.when(
			_media.getRelativeURI()
		).thenReturn(
			relativeURI
		);

		URI uri = _urlFactory.createAdaptiveMediaURI(_media);

		String uriString = uri.toString();

		Assert.assertTrue(uriString.contains(pathModule));
		Assert.assertTrue(uriString.contains(relativeURI.toString()));
	}

	@Test
	public void testMediaURLWhenPathEndsInSlash() {
		String pathModule =
			StringPool.SLASH + StringUtil.randomString() + StringPool.SLASH;

		Mockito.when(
			_portal.getPathModule()
		).thenReturn(
			pathModule
		);

		URI relativeURI = URI.create(StringUtil.randomString());

		Mockito.when(
			_media.getRelativeURI()
		).thenReturn(
			relativeURI
		);

		URI uri = _urlFactory.createAdaptiveMediaURI(_media);

		String uriString = uri.toString();

		Assert.assertTrue(uriString.contains(pathModule));
		Assert.assertTrue(uriString.contains(relativeURI.toString()));
	}

	private final AdaptiveMedia<?> _media = Mockito.mock(AdaptiveMedia.class);
	private final Portal _portal = Mockito.mock(Portal.class);
	private final AdaptiveMediaURLFactory _urlFactory =
		new DefaultAdaptiveMediaURLFactory();

}