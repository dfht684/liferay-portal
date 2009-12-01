/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLayoutSetException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.model.impl.LayoutSetModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="LayoutSetPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPersistence
 * @see       LayoutSetUtil
 * @generated
 */
public class LayoutSetPersistenceImpl extends BasePersistenceImpl<LayoutSet>
	implements LayoutSetPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutSetImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_VIRTUALHOST = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByVirtualHost", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_VIRTUALHOST = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByVirtualHost", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(LayoutSet layoutSet) {
		EntityCacheUtil.putResult(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetImpl.class, layoutSet.getPrimaryKey(), layoutSet);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
			new Object[] { layoutSet.getVirtualHost() }, layoutSet);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
			new Object[] {
				new Long(layoutSet.getGroupId()),
				Boolean.valueOf(layoutSet.getPrivateLayout())
			}, layoutSet);
	}

	public void cacheResult(List<LayoutSet> layoutSets) {
		for (LayoutSet layoutSet : layoutSets) {
			if (EntityCacheUtil.getResult(
						LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
						LayoutSetImpl.class, layoutSet.getPrimaryKey(), this) == null) {
				cacheResult(layoutSet);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(LayoutSetImpl.class.getName());
		EntityCacheUtil.clearCache(LayoutSetImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public LayoutSet create(long layoutSetId) {
		LayoutSet layoutSet = new LayoutSetImpl();

		layoutSet.setNew(true);
		layoutSet.setPrimaryKey(layoutSetId);

		return layoutSet;
	}

	public LayoutSet remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public LayoutSet remove(long layoutSetId)
		throws NoSuchLayoutSetException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutSet layoutSet = (LayoutSet)session.get(LayoutSetImpl.class,
					new Long(layoutSetId));

			if (layoutSet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + layoutSetId);
				}

				throw new NoSuchLayoutSetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					layoutSetId);
			}

			return remove(layoutSet);
		}
		catch (NoSuchLayoutSetException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutSet remove(LayoutSet layoutSet) throws SystemException {
		for (ModelListener<LayoutSet> listener : listeners) {
			listener.onBeforeRemove(layoutSet);
		}

		layoutSet = removeImpl(layoutSet);

		for (ModelListener<LayoutSet> listener : listeners) {
			listener.onAfterRemove(layoutSet);
		}

		return layoutSet;
	}

	protected LayoutSet removeImpl(LayoutSet layoutSet)
		throws SystemException {
		layoutSet = toUnwrappedModel(layoutSet);

		Session session = null;

		try {
			session = openSession();

			if (layoutSet.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(LayoutSetImpl.class,
						layoutSet.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(layoutSet);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		LayoutSetModelImpl layoutSetModelImpl = (LayoutSetModelImpl)layoutSet;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
			new Object[] { layoutSetModelImpl.getOriginalVirtualHost() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P,
			new Object[] {
				new Long(layoutSetModelImpl.getOriginalGroupId()),
				Boolean.valueOf(layoutSetModelImpl.getOriginalPrivateLayout())
			});

		EntityCacheUtil.removeResult(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetImpl.class, layoutSet.getPrimaryKey());

		return layoutSet;
	}

	public LayoutSet updateImpl(com.liferay.portal.model.LayoutSet layoutSet,
		boolean merge) throws SystemException {
		layoutSet = toUnwrappedModel(layoutSet);

		boolean isNew = layoutSet.isNew();

		LayoutSetModelImpl layoutSetModelImpl = (LayoutSetModelImpl)layoutSet;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, layoutSet, merge);

			layoutSet.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetImpl.class, layoutSet.getPrimaryKey(), layoutSet);

		if (!isNew &&
				(!Validator.equals(layoutSet.getVirtualHost(),
					layoutSetModelImpl.getOriginalVirtualHost()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
				new Object[] { layoutSetModelImpl.getOriginalVirtualHost() });
		}

		if (isNew ||
				(!Validator.equals(layoutSet.getVirtualHost(),
					layoutSetModelImpl.getOriginalVirtualHost()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
				new Object[] { layoutSet.getVirtualHost() }, layoutSet);
		}

		if (!isNew &&
				((layoutSet.getGroupId() != layoutSetModelImpl.getOriginalGroupId()) ||
				(layoutSet.getPrivateLayout() != layoutSetModelImpl.getOriginalPrivateLayout()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P,
				new Object[] {
					new Long(layoutSetModelImpl.getOriginalGroupId()),
					Boolean.valueOf(
						layoutSetModelImpl.getOriginalPrivateLayout())
				});
		}

		if (isNew ||
				((layoutSet.getGroupId() != layoutSetModelImpl.getOriginalGroupId()) ||
				(layoutSet.getPrivateLayout() != layoutSetModelImpl.getOriginalPrivateLayout()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
				new Object[] {
					new Long(layoutSet.getGroupId()),
					Boolean.valueOf(layoutSet.getPrivateLayout())
				}, layoutSet);
		}

		return layoutSet;
	}

	protected LayoutSet toUnwrappedModel(LayoutSet layoutSet) {
		if (layoutSet instanceof LayoutSetImpl) {
			return layoutSet;
		}

		LayoutSetImpl layoutSetImpl = new LayoutSetImpl();

		layoutSetImpl.setNew(layoutSet.isNew());
		layoutSetImpl.setPrimaryKey(layoutSet.getPrimaryKey());

		layoutSetImpl.setLayoutSetId(layoutSet.getLayoutSetId());
		layoutSetImpl.setGroupId(layoutSet.getGroupId());
		layoutSetImpl.setCompanyId(layoutSet.getCompanyId());
		layoutSetImpl.setPrivateLayout(layoutSet.isPrivateLayout());
		layoutSetImpl.setLogo(layoutSet.isLogo());
		layoutSetImpl.setLogoId(layoutSet.getLogoId());
		layoutSetImpl.setThemeId(layoutSet.getThemeId());
		layoutSetImpl.setColorSchemeId(layoutSet.getColorSchemeId());
		layoutSetImpl.setWapThemeId(layoutSet.getWapThemeId());
		layoutSetImpl.setWapColorSchemeId(layoutSet.getWapColorSchemeId());
		layoutSetImpl.setCss(layoutSet.getCss());
		layoutSetImpl.setPageCount(layoutSet.getPageCount());
		layoutSetImpl.setVirtualHost(layoutSet.getVirtualHost());
		layoutSetImpl.setLayoutSetPrototypeId(layoutSet.getLayoutSetPrototypeId());

		return layoutSetImpl;
	}

	public LayoutSet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public LayoutSet findByPrimaryKey(long layoutSetId)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = fetchByPrimaryKey(layoutSetId);

		if (layoutSet == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + layoutSetId);
			}

			throw new NoSuchLayoutSetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				layoutSetId);
		}

		return layoutSet;
	}

	public LayoutSet fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public LayoutSet fetchByPrimaryKey(long layoutSetId)
		throws SystemException {
		LayoutSet layoutSet = (LayoutSet)EntityCacheUtil.getResult(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetImpl.class, layoutSetId, this);

		if (layoutSet == null) {
			Session session = null;

			try {
				session = openSession();

				layoutSet = (LayoutSet)session.get(LayoutSetImpl.class,
						new Long(layoutSetId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (layoutSet != null) {
					cacheResult(layoutSet);
				}

				closeSession(session);
			}
		}

		return layoutSet;
	}

	public List<LayoutSet> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<LayoutSet> list = (List<LayoutSet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_LAYOUTSET_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSet>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<LayoutSet> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<LayoutSet> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<LayoutSet> list = (List<LayoutSet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(3 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_LAYOUTSET_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<LayoutSet>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSet>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public LayoutSet findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchLayoutSetException, SystemException {
		List<LayoutSet> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSet findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchLayoutSetException, SystemException {
		int count = countByGroupId(groupId);

		List<LayoutSet> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSet[] findByGroupId_PrevAndNext(long layoutSetId,
		long groupId, OrderByComparator obc)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = findByPrimaryKey(layoutSetId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_LAYOUTSET_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					layoutSet);

			LayoutSet[] array = new LayoutSetImpl[3];

			array[0] = (LayoutSet)objArray[0];
			array[1] = (LayoutSet)objArray[1];
			array[2] = (LayoutSet)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutSet findByVirtualHost(String virtualHost)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = fetchByVirtualHost(virtualHost);

		if (layoutSet == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("virtualHost=");
			msg.append(virtualHost);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutSetException(msg.toString());
		}

		return layoutSet;
	}

	public LayoutSet fetchByVirtualHost(String virtualHost)
		throws SystemException {
		return fetchByVirtualHost(virtualHost, true);
	}

	public LayoutSet fetchByVirtualHost(String virtualHost,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { virtualHost };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_LAYOUTSET_WHERE);

				if (virtualHost == null) {
					query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_1);
				}
				else {
					if (virtualHost.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_3);
					}
					else {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (virtualHost != null) {
					qPos.add(virtualHost);
				}

				List<LayoutSet> list = q.list();

				result = list;

				LayoutSet layoutSet = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
						finderArgs, list);
				}
				else {
					layoutSet = list.get(0);

					cacheResult(layoutSet);

					if ((layoutSet.getVirtualHost() == null) ||
							!layoutSet.getVirtualHost().equals(virtualHost)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
							finderArgs, layoutSet);
					}
				}

				return layoutSet;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
						finderArgs, new ArrayList<LayoutSet>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (LayoutSet)result;
			}
		}
	}

	public LayoutSet findByG_P(long groupId, boolean privateLayout)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = fetchByG_P(groupId, privateLayout);

		if (layoutSet == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutSetException(msg.toString());
		}

		return layoutSet;
	}

	public LayoutSet fetchByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		return fetchByG_P(groupId, privateLayout, true);
	}

	public LayoutSet fetchByG_P(long groupId, boolean privateLayout,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_LAYOUTSET_WHERE);

				query.append(_FINDER_COLUMN_G_P_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				List<LayoutSet> list = q.list();

				result = list;

				LayoutSet layoutSet = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
						finderArgs, list);
				}
				else {
					layoutSet = list.get(0);

					cacheResult(layoutSet);

					if ((layoutSet.getGroupId() != groupId) ||
							(layoutSet.getPrivateLayout() != privateLayout)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
							finderArgs, layoutSet);
					}
				}

				return layoutSet;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
						finderArgs, new ArrayList<LayoutSet>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (LayoutSet)result;
			}
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<LayoutSet> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<LayoutSet> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<LayoutSet> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<LayoutSet> list = (List<LayoutSet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(2 +
							(obc.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_LAYOUTSET);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_LAYOUTSET;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<LayoutSet>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LayoutSet>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSet>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (LayoutSet layoutSet : findByGroupId(groupId)) {
			remove(layoutSet);
		}
	}

	public void removeByVirtualHost(String virtualHost)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = findByVirtualHost(virtualHost);

		remove(layoutSet);
	}

	public void removeByG_P(long groupId, boolean privateLayout)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = findByG_P(groupId, privateLayout);

		remove(layoutSet);
	}

	public void removeAll() throws SystemException {
		for (LayoutSet layoutSet : findAll()) {
			remove(layoutSet);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_LAYOUTSET_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByVirtualHost(String virtualHost) throws SystemException {
		Object[] finderArgs = new Object[] { virtualHost };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_VIRTUALHOST,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_LAYOUTSET_WHERE);

				if (virtualHost == null) {
					query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_1);
				}
				else {
					if (virtualHost.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_3);
					}
					else {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (virtualHost != null) {
					qPos.add(virtualHost);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_VIRTUALHOST,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_LAYOUTSET_WHERE);

				query.append(_FINDER_COLUMN_G_P_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTSET);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.LayoutSet")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LayoutSet>> listenersList = new ArrayList<ModelListener<LayoutSet>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LayoutSet>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.persistence.AccountPersistence")
	protected com.liferay.portal.service.persistence.AccountPersistence accountPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.AddressPersistence")
	protected com.liferay.portal.service.persistence.AddressPersistence addressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.BrowserTrackerPersistence")
	protected com.liferay.portal.service.persistence.BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ClassNamePersistence")
	protected com.liferay.portal.service.persistence.ClassNamePersistence classNamePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected com.liferay.portal.service.persistence.CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ContactPersistence")
	protected com.liferay.portal.service.persistence.ContactPersistence contactPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.CountryPersistence")
	protected com.liferay.portal.service.persistence.CountryPersistence countryPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.EmailAddressPersistence")
	protected com.liferay.portal.service.persistence.EmailAddressPersistence emailAddressPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ImagePersistence")
	protected com.liferay.portal.service.persistence.ImagePersistence imagePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPersistence layoutSetPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutSetPrototypePersistence")
	protected com.liferay.portal.service.persistence.LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ListTypePersistence")
	protected com.liferay.portal.service.persistence.ListTypePersistence listTypePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LockPersistence")
	protected com.liferay.portal.service.persistence.LockPersistence lockPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.MembershipRequestPersistence")
	protected com.liferay.portal.service.persistence.MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrganizationPersistence")
	protected com.liferay.portal.service.persistence.OrganizationPersistence organizationPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupPermissionPersistence")
	protected com.liferay.portal.service.persistence.OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgGroupRolePersistence")
	protected com.liferay.portal.service.persistence.OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.OrgLaborPersistence")
	protected com.liferay.portal.service.persistence.OrgLaborPersistence orgLaborPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordPolicyRelPersistence")
	protected com.liferay.portal.service.persistence.PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PasswordTrackerPersistence")
	protected com.liferay.portal.service.persistence.PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PermissionPersistence")
	protected com.liferay.portal.service.persistence.PermissionPersistence permissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PhonePersistence")
	protected com.liferay.portal.service.persistence.PhonePersistence phonePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PluginSettingPersistence")
	protected com.liferay.portal.service.persistence.PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPersistence")
	protected com.liferay.portal.service.persistence.PortletPersistence portletPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletItemPersistence")
	protected com.liferay.portal.service.persistence.PortletItemPersistence portletItemPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.PortletPreferencesPersistence")
	protected com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RegionPersistence")
	protected com.liferay.portal.service.persistence.RegionPersistence regionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ReleasePersistence")
	protected com.liferay.portal.service.persistence.ReleasePersistence releasePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceActionPersistence")
	protected com.liferay.portal.service.persistence.ResourceActionPersistence resourceActionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceCodePersistence")
	protected com.liferay.portal.service.persistence.ResourceCodePersistence resourceCodePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePermissionPersistence")
	protected com.liferay.portal.service.persistence.ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence")
	protected com.liferay.portal.service.persistence.RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ServiceComponentPersistence")
	protected com.liferay.portal.service.persistence.ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ShardPersistence")
	protected com.liferay.portal.service.persistence.ShardPersistence shardPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence")
	protected com.liferay.portal.service.persistence.UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence")
	protected com.liferay.portal.service.persistence.UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserIdMapperPersistence")
	protected com.liferay.portal.service.persistence.UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPersistence userTrackerPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserTrackerPathPersistence")
	protected com.liferay.portal.service.persistence.UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebDAVPropsPersistence")
	protected com.liferay.portal.service.persistence.WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WebsitePersistence")
	protected com.liferay.portal.service.persistence.WebsitePersistence websitePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence")
	protected com.liferay.portal.service.persistence.WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_LAYOUTSET = "SELECT layoutSet FROM LayoutSet layoutSet";
	private static final String _SQL_SELECT_LAYOUTSET_WHERE = "SELECT layoutSet FROM LayoutSet layoutSet WHERE ";
	private static final String _SQL_COUNT_LAYOUTSET = "SELECT COUNT(layoutSet) FROM LayoutSet layoutSet";
	private static final String _SQL_COUNT_LAYOUTSET_WHERE = "SELECT COUNT(layoutSet) FROM LayoutSet layoutSet WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutSet.groupId = ?";
	private static final String _FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_1 = "layoutSet.virtualHost IS NULL";
	private static final String _FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_2 = "layoutSet.virtualHost = ?";
	private static final String _FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_3 = "(layoutSet.virtualHost IS NULL OR layoutSet.virtualHost = ?)";
	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "layoutSet.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PRIVATELAYOUT_2 = "layoutSet.privateLayout = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutSet.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutSet exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutSet exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(LayoutSetPersistenceImpl.class);
}