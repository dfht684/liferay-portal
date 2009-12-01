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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchPermissionException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.impl.PermissionImpl;
import com.liferay.portal.model.impl.PermissionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <a href="PermissionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PermissionPersistence
 * @see       PermissionUtil
 * @generated
 */
public class PermissionPersistenceImpl extends BasePersistenceImpl<Permission>
	implements PermissionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = PermissionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_RESOURCEID = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByResourceId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_RESOURCEID = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByResourceId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEID = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByResourceId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_A_R = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByA_R",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_A_R = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByA_R",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Permission permission) {
		EntityCacheUtil.putResult(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionImpl.class, permission.getPrimaryKey(), permission);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A_R,
			new Object[] {
				permission.getActionId(), new Long(permission.getResourceId())
			}, permission);
	}

	public void cacheResult(List<Permission> permissions) {
		for (Permission permission : permissions) {
			if (EntityCacheUtil.getResult(
						PermissionModelImpl.ENTITY_CACHE_ENABLED,
						PermissionImpl.class, permission.getPrimaryKey(), this) == null) {
				cacheResult(permission);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(PermissionImpl.class.getName());
		EntityCacheUtil.clearCache(PermissionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public Permission create(long permissionId) {
		Permission permission = new PermissionImpl();

		permission.setNew(true);
		permission.setPrimaryKey(permissionId);

		return permission;
	}

	public Permission remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Permission remove(long permissionId)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Permission permission = (Permission)session.get(PermissionImpl.class,
					new Long(permissionId));

			if (permission == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + permissionId);
				}

				throw new NoSuchPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					permissionId);
			}

			return remove(permission);
		}
		catch (NoSuchPermissionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Permission remove(Permission permission) throws SystemException {
		for (ModelListener<Permission> listener : listeners) {
			listener.onBeforeRemove(permission);
		}

		permission = removeImpl(permission);

		for (ModelListener<Permission> listener : listeners) {
			listener.onAfterRemove(permission);
		}

		return permission;
	}

	protected Permission removeImpl(Permission permission)
		throws SystemException {
		permission = toUnwrappedModel(permission);

		try {
			clearGroups.clear(permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}

		try {
			clearRoles.clear(permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}

		try {
			clearUsers.clear(permission.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}

		Session session = null;

		try {
			session = openSession();

			if (permission.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(PermissionImpl.class,
						permission.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(permission);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		PermissionModelImpl permissionModelImpl = (PermissionModelImpl)permission;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A_R,
			new Object[] {
				permissionModelImpl.getOriginalActionId(),
				new Long(permissionModelImpl.getOriginalResourceId())
			});

		EntityCacheUtil.removeResult(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionImpl.class, permission.getPrimaryKey());

		return permission;
	}

	public Permission updateImpl(
		com.liferay.portal.model.Permission permission, boolean merge)
		throws SystemException {
		permission = toUnwrappedModel(permission);

		boolean isNew = permission.isNew();

		PermissionModelImpl permissionModelImpl = (PermissionModelImpl)permission;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, permission, merge);

			permission.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(PermissionModelImpl.ENTITY_CACHE_ENABLED,
			PermissionImpl.class, permission.getPrimaryKey(), permission);

		if (!isNew &&
				(!Validator.equals(permission.getActionId(),
					permissionModelImpl.getOriginalActionId()) ||
				(permission.getResourceId() != permissionModelImpl.getOriginalResourceId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_A_R,
				new Object[] {
					permissionModelImpl.getOriginalActionId(),
					new Long(permissionModelImpl.getOriginalResourceId())
				});
		}

		if (isNew ||
				(!Validator.equals(permission.getActionId(),
					permissionModelImpl.getOriginalActionId()) ||
				(permission.getResourceId() != permissionModelImpl.getOriginalResourceId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A_R,
				new Object[] {
					permission.getActionId(),
					new Long(permission.getResourceId())
				}, permission);
		}

		return permission;
	}

	protected Permission toUnwrappedModel(Permission permission) {
		if (permission instanceof PermissionImpl) {
			return permission;
		}

		PermissionImpl permissionImpl = new PermissionImpl();

		permissionImpl.setNew(permission.isNew());
		permissionImpl.setPrimaryKey(permission.getPrimaryKey());

		permissionImpl.setPermissionId(permission.getPermissionId());
		permissionImpl.setCompanyId(permission.getCompanyId());
		permissionImpl.setActionId(permission.getActionId());
		permissionImpl.setResourceId(permission.getResourceId());

		return permissionImpl;
	}

	public Permission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Permission findByPrimaryKey(long permissionId)
		throws NoSuchPermissionException, SystemException {
		Permission permission = fetchByPrimaryKey(permissionId);

		if (permission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + permissionId);
			}

			throw new NoSuchPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				permissionId);
		}

		return permission;
	}

	public Permission fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Permission fetchByPrimaryKey(long permissionId)
		throws SystemException {
		Permission permission = (Permission)EntityCacheUtil.getResult(PermissionModelImpl.ENTITY_CACHE_ENABLED,
				PermissionImpl.class, permissionId, this);

		if (permission == null) {
			Session session = null;

			try {
				session = openSession();

				permission = (Permission)session.get(PermissionImpl.class,
						new Long(permissionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (permission != null) {
					cacheResult(permission);
				}

				closeSession(session);
			}
		}

		return permission;
	}

	public List<Permission> findByResourceId(long resourceId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(resourceId) };

		List<Permission> list = (List<Permission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_RESOURCEID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_PERMISSION_WHERE);

				query.append(_FINDER_COLUMN_RESOURCEID_RESOURCEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Permission>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_RESOURCEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<Permission> findByResourceId(long resourceId, int start, int end)
		throws SystemException {
		return findByResourceId(resourceId, start, end, null);
	}

	public List<Permission> findByResourceId(long resourceId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(resourceId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Permission> list = (List<Permission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_RESOURCEID,
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

				query.append(_SQL_SELECT_PERMISSION_WHERE);

				query.append(_FINDER_COLUMN_RESOURCEID_RESOURCEID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceId);

				list = (List<Permission>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Permission>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_RESOURCEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Permission findByResourceId_First(long resourceId,
		OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		List<Permission> list = findByResourceId(resourceId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourceId=");
			msg.append(resourceId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Permission findByResourceId_Last(long resourceId,
		OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		int count = countByResourceId(resourceId);

		List<Permission> list = findByResourceId(resourceId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourceId=");
			msg.append(resourceId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Permission[] findByResourceId_PrevAndNext(long permissionId,
		long resourceId, OrderByComparator obc)
		throws NoSuchPermissionException, SystemException {
		Permission permission = findByPrimaryKey(permissionId);

		int count = countByResourceId(resourceId);

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

			query.append(_SQL_SELECT_PERMISSION_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEID_RESOURCEID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(resourceId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					permission);

			Permission[] array = new PermissionImpl[3];

			array[0] = (Permission)objArray[0];
			array[1] = (Permission)objArray[1];
			array[2] = (Permission)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Permission findByA_R(String actionId, long resourceId)
		throws NoSuchPermissionException, SystemException {
		Permission permission = fetchByA_R(actionId, resourceId);

		if (permission == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("actionId=");
			msg.append(actionId);

			msg.append(", resourceId=");
			msg.append(resourceId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPermissionException(msg.toString());
		}

		return permission;
	}

	public Permission fetchByA_R(String actionId, long resourceId)
		throws SystemException {
		return fetchByA_R(actionId, resourceId, true);
	}

	public Permission fetchByA_R(String actionId, long resourceId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { actionId, new Long(resourceId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_A_R,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_PERMISSION_WHERE);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_A_R_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_A_R_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_A_R_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_A_R_RESOURCEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(resourceId);

				List<Permission> list = q.list();

				result = list;

				Permission permission = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A_R,
						finderArgs, list);
				}
				else {
					permission = list.get(0);

					cacheResult(permission);

					if ((permission.getActionId() == null) ||
							!permission.getActionId().equals(actionId) ||
							(permission.getResourceId() != resourceId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A_R,
							finderArgs, permission);
					}
				}

				return permission;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_A_R,
						finderArgs, new ArrayList<Permission>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Permission)result;
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

	public List<Permission> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Permission> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<Permission> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<Permission> list = (List<Permission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_PERMISSION);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				sql = _SQL_SELECT_PERMISSION;

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<Permission>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Permission>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Permission>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByResourceId(long resourceId) throws SystemException {
		for (Permission permission : findByResourceId(resourceId)) {
			remove(permission);
		}
	}

	public void removeByA_R(String actionId, long resourceId)
		throws NoSuchPermissionException, SystemException {
		Permission permission = findByA_R(actionId, resourceId);

		remove(permission);
	}

	public void removeAll() throws SystemException {
		for (Permission permission : findAll()) {
			remove(permission);
		}
	}

	public int countByResourceId(long resourceId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(resourceId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RESOURCEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_PERMISSION_WHERE);

				query.append(_FINDER_COLUMN_RESOURCEID_RESOURCEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RESOURCEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByA_R(String actionId, long resourceId)
		throws SystemException {
		Object[] finderArgs = new Object[] { actionId, new Long(resourceId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_A_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_PERMISSION_WHERE);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_A_R_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_A_R_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_A_R_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_A_R_RESOURCEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(resourceId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_A_R, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_PERMISSION);

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

	public List<com.liferay.portal.model.Group> getGroups(long pk)
		throws SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) throws SystemException {
		return getGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_GROUPS_PERMISSIONS,
			PermissionModelImpl.TABLE_GROUPS_PERMISSIONS, "getGroups",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.Group> list = (List<com.liferay.portal.model.Group>)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(3);

					query.append(_SQL_GETGROUPS);
					query.append(ORDER_BY_CLAUSE);
					query.append(obc.getOrderBy());

					sql = query.toString();
				}

				else {
					sql = _SQL_GETGROUPS.concat(com.liferay.portal.model.impl.GroupModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Group_",
					com.liferay.portal.model.impl.GroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Group>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.Group>();
				}

				groupPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_GROUPS_PERMISSIONS,
			PermissionModelImpl.TABLE_GROUPS_PERMISSIONS, "getGroupsSize",
			new String[] { Long.class.getName() });

	public int getGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_GROUP = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_GROUPS_PERMISSIONS,
			PermissionModelImpl.TABLE_GROUPS_PERMISSIONS, "containsGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsGroup(long pk, long groupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(groupPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_GROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsGroup.contains(pk, groupPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_GROUP,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsGroups(long pk) throws SystemException {
		if (getGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addGroup(long pk, long groupPK) throws SystemException {
		try {
			addGroup.add(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}
	}

	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			addGroup.add(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}
	}

	public void addGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}
	}

	public void addGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}
	}

	public void clearGroups(long pk) throws SystemException {
		try {
			clearGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}
	}

	public void removeGroup(long pk, long groupPK) throws SystemException {
		try {
			removeGroup.remove(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}
	}

	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			removeGroup.remove(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}
	}

	public void removeGroups(long pk, long[] groupPKs)
		throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				removeGroup.remove(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}
	}

	public void removeGroups(long pk,
		List<com.liferay.portal.model.Group> groups) throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				removeGroup.remove(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}
	}

	public void setGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			Set<Long> groupPKSet = SetUtil.fromArray(groupPKs);

			List<com.liferay.portal.model.Group> groups = getGroups(pk);

			for (com.liferay.portal.model.Group group : groups) {
				if (!groupPKSet.contains(group.getPrimaryKey())) {
					removeGroup.remove(pk, group.getPrimaryKey());
				}
				else {
					groupPKSet.remove(group.getPrimaryKey());
				}
			}

			for (Long groupPK : groupPKSet) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}
	}

	public void setGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			long[] groupPKs = new long[groups.size()];

			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.model.Group group = groups.get(i);

				groupPKs[i] = group.getPrimaryKey();
			}

			setGroups(pk, groupPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_GROUPS_PERMISSIONS);
		}
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk)
		throws SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end) throws SystemException {
		return getRoles(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ROLES = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_ROLES_PERMISSIONS,
			PermissionModelImpl.TABLE_ROLES_PERMISSIONS, "getRoles",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.Role> list = (List<com.liferay.portal.model.Role>)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(3);

					query.append(_SQL_GETROLES);
					query.append(ORDER_BY_CLAUSE);
					query.append(obc.getOrderBy());

					sql = query.toString();
				}

				else {
					sql = _SQL_GETROLES.concat(com.liferay.portal.model.impl.RoleModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Role_",
					com.liferay.portal.model.impl.RoleImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.Role>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.Role>();
				}

				rolePersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ROLES_SIZE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_ROLES_PERMISSIONS,
			PermissionModelImpl.TABLE_ROLES_PERMISSIONS, "getRolesSize",
			new String[] { Long.class.getName() });

	public int getRolesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETROLESSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ROLE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_ROLES_PERMISSIONS,
			PermissionModelImpl.TABLE_ROLES_PERMISSIONS, "containsRole",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsRole(long pk, long rolePK) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(rolePK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ROLE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsRole.contains(pk, rolePK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ROLE,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsRoles(long pk) throws SystemException {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addRole(long pk, long rolePK) throws SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}
	}

	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}
	}

	public void addRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}
	}

	public void addRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}
	}

	public void clearRoles(long pk) throws SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}
	}

	public void removeRole(long pk, long rolePK) throws SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}
	}

	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}
	}

	public void removeRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				removeRole.remove(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}
	}

	public void removeRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				removeRole.remove(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}
	}

	public void setRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			Set<Long> rolePKSet = SetUtil.fromArray(rolePKs);

			List<com.liferay.portal.model.Role> roles = getRoles(pk);

			for (com.liferay.portal.model.Role role : roles) {
				if (!rolePKSet.contains(role.getPrimaryKey())) {
					removeRole.remove(pk, role.getPrimaryKey());
				}
				else {
					rolePKSet.remove(role.getPrimaryKey());
				}
			}

			for (Long rolePK : rolePKSet) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}
	}

	public void setRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			long[] rolePKs = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.model.Role role = roles.get(i);

				rolePKs[i] = role.getPrimaryKey();
			}

			setRoles(pk, rolePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_ROLES_PERMISSIONS);
		}
	}

	public List<com.liferay.portal.model.User> getUsers(long pk)
		throws SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end) throws SystemException {
		return getUsers(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERS = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS,
			PermissionModelImpl.TABLE_USERS_PERMISSIONS, "getUsers",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(obc)
			};

		List<com.liferay.portal.model.User> list = (List<com.liferay.portal.model.User>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (obc != null) {
					query = new StringBundler(3);

					query.append(_SQL_GETUSERS);
					query.append(ORDER_BY_CLAUSE);
					query.append(obc.getOrderBy());

					sql = query.toString();
				}

				sql = _SQL_GETUSERS;

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("User_",
					com.liferay.portal.model.impl.UserImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portal.model.User>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portal.model.User>();
				}

				userPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS,
			PermissionModelImpl.TABLE_USERS_PERMISSIONS, "getUsersSize",
			new String[] { Long.class.getName() });

	public int getUsersSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USER = new FinderPath(com.liferay.portal.model.impl.UserModelImpl.ENTITY_CACHE_ENABLED,
			PermissionModelImpl.FINDER_CACHE_ENABLED_USERS_PERMISSIONS,
			PermissionModelImpl.TABLE_USERS_PERMISSIONS, "containsUser",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsUser(long pk, long userPK) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk), new Long(userPK) };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USER,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUser.contains(pk, userPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USER,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsUsers(long pk) throws SystemException {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addUser(long pk, long userPK) throws SystemException {
		try {
			addUser.add(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}
	}

	public void addUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			addUser.add(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}
	}

	public void addUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}
	}

	public void addUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				addUser.add(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}
	}

	public void clearUsers(long pk) throws SystemException {
		try {
			clearUsers.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}
	}

	public void removeUser(long pk, long userPK) throws SystemException {
		try {
			removeUser.remove(pk, userPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}
	}

	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		try {
			removeUser.remove(pk, user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}
	}

	public void removeUsers(long pk, long[] userPKs) throws SystemException {
		try {
			for (long userPK : userPKs) {
				removeUser.remove(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}
	}

	public void removeUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			for (com.liferay.portal.model.User user : users) {
				removeUser.remove(pk, user.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}
	}

	public void setUsers(long pk, long[] userPKs) throws SystemException {
		try {
			Set<Long> userPKSet = SetUtil.fromArray(userPKs);

			List<com.liferay.portal.model.User> users = getUsers(pk);

			for (com.liferay.portal.model.User user : users) {
				if (!userPKSet.contains(user.getPrimaryKey())) {
					removeUser.remove(pk, user.getPrimaryKey());
				}
				else {
					userPKSet.remove(user.getPrimaryKey());
				}
			}

			for (Long userPK : userPKSet) {
				addUser.add(pk, userPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}
	}

	public void setUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			long[] userPKs = new long[users.size()];

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = users.get(i);

				userPKs[i] = user.getPrimaryKey();
			}

			setUsers(pk, userPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(PermissionModelImpl.TABLE_USERS_PERMISSIONS);
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Permission")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Permission>> listenersList = new ArrayList<ModelListener<Permission>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Permission>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsGroup = new ContainsGroup(this);

		addGroup = new AddGroup(this);
		clearGroups = new ClearGroups(this);
		removeGroup = new RemoveGroup(this);

		containsRole = new ContainsRole(this);

		addRole = new AddRole(this);
		clearRoles = new ClearRoles(this);
		removeRole = new RemoveRole(this);

		containsUser = new ContainsUser(this);

		addUser = new AddUser(this);
		clearUsers = new ClearUsers(this);
		removeUser = new RemoveUser(this);
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
	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	protected ContainsRole containsRole;
	protected AddRole addRole;
	protected ClearRoles clearRoles;
	protected RemoveRole removeRole;
	protected ContainsUser containsUser;
	protected AddUser addUser;
	protected ClearUsers clearUsers;
	protected RemoveUser removeUser;

	protected class ContainsGroup {
		protected ContainsGroup(PermissionPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSGROUP,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long permissionId, long groupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(permissionId), new Long(groupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddGroup {
		protected AddGroup(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Groups_Permissions (permissionId, groupId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long permissionId, long groupId)
			throws SystemException {
			if (!_persistenceImpl.containsGroup.contains(permissionId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeAddAssociation(permissionId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeAddAssociation(groupId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(groupId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterAddAssociation(permissionId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterAddAssociation(groupId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	protected class ClearGroups {
		protected ClearGroups(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Permissions WHERE permissionId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long permissionId) throws SystemException {
			ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

			List<com.liferay.portal.model.Group> groups = null;

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				groups = getGroups(permissionId);

				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onBeforeRemoveAssociation(permissionId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(permissionId) });

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onAfterRemoveAssociation(permissionId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onAfterRemoveAssociation(group.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveGroup {
		protected RemoveGroup(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Groups_Permissions WHERE permissionId = ? AND groupId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long permissionId, long groupId)
			throws SystemException {
			if (_persistenceImpl.containsGroup.contains(permissionId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeRemoveAssociation(permissionId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeRemoveAssociation(groupId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(groupId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterRemoveAssociation(permissionId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterRemoveAssociation(groupId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	protected class ContainsRole {
		protected ContainsRole(PermissionPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSROLE,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long permissionId, long roleId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(permissionId), new Long(roleId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddRole {
		protected AddRole(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Roles_Permissions (permissionId, roleId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long permissionId, long roleId)
			throws SystemException {
			if (!_persistenceImpl.containsRole.contains(permissionId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeAddAssociation(permissionId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeAddAssociation(roleId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(roleId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterAddAssociation(permissionId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterAddAssociation(roleId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	protected class ClearRoles {
		protected ClearRoles(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Roles_Permissions WHERE permissionId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long permissionId) throws SystemException {
			ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

			List<com.liferay.portal.model.Role> roles = null;

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				roles = getRoles(permissionId);

				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onBeforeRemoveAssociation(permissionId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onBeforeRemoveAssociation(role.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(permissionId) });

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onAfterRemoveAssociation(permissionId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onAfterRemoveAssociation(role.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveRole {
		protected RemoveRole(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Roles_Permissions WHERE permissionId = ? AND roleId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long permissionId, long roleId)
			throws SystemException {
			if (_persistenceImpl.containsRole.contains(permissionId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeRemoveAssociation(permissionId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeRemoveAssociation(roleId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(roleId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterRemoveAssociation(permissionId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterRemoveAssociation(roleId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	protected class ContainsUser {
		protected ContainsUser(PermissionPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSER,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long permissionId, long userId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(permissionId), new Long(userId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery _mappingSqlQuery;
	}

	protected class AddUser {
		protected AddUser(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Permissions (permissionId, userId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long permissionId, long userId)
			throws SystemException {
			if (!_persistenceImpl.containsUser.contains(permissionId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeAddAssociation(permissionId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeAddAssociation(userId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(userId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterAddAssociation(permissionId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterAddAssociation(userId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	protected class ClearUsers {
		protected ClearUsers(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Permissions WHERE permissionId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long permissionId) throws SystemException {
			ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

			List<com.liferay.portal.model.User> users = null;

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				users = getUsers(permissionId);

				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onBeforeRemoveAssociation(permissionId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onBeforeRemoveAssociation(user.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(permissionId) });

			if ((listeners.length > 0) || (userListeners.length > 0)) {
				for (com.liferay.portal.model.User user : users) {
					for (ModelListener<Permission> listener : listeners) {
						listener.onAfterRemoveAssociation(permissionId,
							com.liferay.portal.model.User.class.getName(),
							user.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
						listener.onAfterRemoveAssociation(user.getPrimaryKey(),
							Permission.class.getName(), permissionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUser {
		protected RemoveUser(PermissionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Permissions WHERE permissionId = ? AND userId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long permissionId, long userId)
			throws SystemException {
			if (_persistenceImpl.containsUser.contains(permissionId, userId)) {
				ModelListener<com.liferay.portal.model.User>[] userListeners = userPersistence.getListeners();

				for (ModelListener<Permission> listener : listeners) {
					listener.onBeforeRemoveAssociation(permissionId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onBeforeRemoveAssociation(userId,
						Permission.class.getName(), permissionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(permissionId), new Long(userId)
					});

				for (ModelListener<Permission> listener : listeners) {
					listener.onAfterRemoveAssociation(permissionId,
						com.liferay.portal.model.User.class.getName(), userId);
				}

				for (ModelListener<com.liferay.portal.model.User> listener : userListeners) {
					listener.onAfterRemoveAssociation(userId,
						Permission.class.getName(), permissionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private PermissionPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_PERMISSION = "SELECT permission FROM Permission permission";
	private static final String _SQL_SELECT_PERMISSION_WHERE = "SELECT permission FROM Permission permission WHERE ";
	private static final String _SQL_COUNT_PERMISSION = "SELECT COUNT(permission) FROM Permission permission";
	private static final String _SQL_COUNT_PERMISSION_WHERE = "SELECT COUNT(permission) FROM Permission permission WHERE ";
	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Groups_Permissions ON (Groups_Permissions.groupId = Group_.groupId) WHERE (Groups_Permissions.permissionId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE permissionId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Groups_Permissions WHERE permissionId = ? AND groupId = ?";
	private static final String _SQL_GETROLES = "SELECT {Role_.*} FROM Role_ INNER JOIN Roles_Permissions ON (Roles_Permissions.roleId = Role_.roleId) WHERE (Roles_Permissions.permissionId = ?)";
	private static final String _SQL_GETROLESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE permissionId = ?";
	private static final String _SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Roles_Permissions WHERE permissionId = ? AND roleId = ?";
	private static final String _SQL_GETUSERS = "SELECT {User_.*} FROM User_ INNER JOIN Users_Permissions ON (Users_Permissions.userId = User_.userId) WHERE (Users_Permissions.permissionId = ?)";
	private static final String _SQL_GETUSERSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE permissionId = ?";
	private static final String _SQL_CONTAINSUSER = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Permissions WHERE permissionId = ? AND userId = ?";
	private static final String _FINDER_COLUMN_RESOURCEID_RESOURCEID_2 = "permission.resourceId = ?";
	private static final String _FINDER_COLUMN_A_R_ACTIONID_1 = "permission.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_A_R_ACTIONID_2 = "permission.actionId = ? AND ";
	private static final String _FINDER_COLUMN_A_R_ACTIONID_3 = "(permission.actionId IS NULL OR permission.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_A_R_RESOURCEID_2 = "permission.resourceId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "permission.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Permission exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Permission exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(PermissionPersistenceImpl.class);
}