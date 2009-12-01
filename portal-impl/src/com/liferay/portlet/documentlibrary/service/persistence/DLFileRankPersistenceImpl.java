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

package com.liferay.portlet.documentlibrary.service.persistence;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFileRankException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="DLFileRankPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileRankPersistence
 * @see       DLFileRankUtil
 * @generated
 */
public class DLFileRankPersistenceImpl extends BasePersistenceImpl<DLFileRank>
	implements DLFileRankPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileRankImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_F_N = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByF_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_F_N = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByF_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_F_N = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByF_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_U_F_N = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_U_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U_F_N = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_U_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(DLFileRank dlFileRank) {
		EntityCacheUtil.putResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankImpl.class, dlFileRank.getPrimaryKey(), dlFileRank);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_F_N,
			new Object[] {
				new Long(dlFileRank.getCompanyId()),
				new Long(dlFileRank.getUserId()),
				new Long(dlFileRank.getFolderId()),
				
			dlFileRank.getName()
			}, dlFileRank);
	}

	public void cacheResult(List<DLFileRank> dlFileRanks) {
		for (DLFileRank dlFileRank : dlFileRanks) {
			if (EntityCacheUtil.getResult(
						DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
						DLFileRankImpl.class, dlFileRank.getPrimaryKey(), this) == null) {
				cacheResult(dlFileRank);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(DLFileRankImpl.class.getName());
		EntityCacheUtil.clearCache(DLFileRankImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public DLFileRank create(long fileRankId) {
		DLFileRank dlFileRank = new DLFileRankImpl();

		dlFileRank.setNew(true);
		dlFileRank.setPrimaryKey(fileRankId);

		return dlFileRank;
	}

	public DLFileRank remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public DLFileRank remove(long fileRankId)
		throws NoSuchFileRankException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileRank dlFileRank = (DLFileRank)session.get(DLFileRankImpl.class,
					new Long(fileRankId));

			if (dlFileRank == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileRankId);
				}

				throw new NoSuchFileRankException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					fileRankId);
			}

			return remove(dlFileRank);
		}
		catch (NoSuchFileRankException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileRank remove(DLFileRank dlFileRank) throws SystemException {
		for (ModelListener<DLFileRank> listener : listeners) {
			listener.onBeforeRemove(dlFileRank);
		}

		dlFileRank = removeImpl(dlFileRank);

		for (ModelListener<DLFileRank> listener : listeners) {
			listener.onAfterRemove(dlFileRank);
		}

		return dlFileRank;
	}

	protected DLFileRank removeImpl(DLFileRank dlFileRank)
		throws SystemException {
		dlFileRank = toUnwrappedModel(dlFileRank);

		Session session = null;

		try {
			session = openSession();

			if (dlFileRank.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(DLFileRankImpl.class,
						dlFileRank.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(dlFileRank);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DLFileRankModelImpl dlFileRankModelImpl = (DLFileRankModelImpl)dlFileRank;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U_F_N,
			new Object[] {
				new Long(dlFileRankModelImpl.getOriginalCompanyId()),
				new Long(dlFileRankModelImpl.getOriginalUserId()),
				new Long(dlFileRankModelImpl.getOriginalFolderId()),
				
			dlFileRankModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankImpl.class, dlFileRank.getPrimaryKey());

		return dlFileRank;
	}

	public DLFileRank updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank,
		boolean merge) throws SystemException {
		dlFileRank = toUnwrappedModel(dlFileRank);

		boolean isNew = dlFileRank.isNew();

		DLFileRankModelImpl dlFileRankModelImpl = (DLFileRankModelImpl)dlFileRank;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, dlFileRank, merge);

			dlFileRank.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
			DLFileRankImpl.class, dlFileRank.getPrimaryKey(), dlFileRank);

		if (!isNew &&
				((dlFileRank.getCompanyId() != dlFileRankModelImpl.getOriginalCompanyId()) ||
				(dlFileRank.getUserId() != dlFileRankModelImpl.getOriginalUserId()) ||
				(dlFileRank.getFolderId() != dlFileRankModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileRank.getName(),
					dlFileRankModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U_F_N,
				new Object[] {
					new Long(dlFileRankModelImpl.getOriginalCompanyId()),
					new Long(dlFileRankModelImpl.getOriginalUserId()),
					new Long(dlFileRankModelImpl.getOriginalFolderId()),
					
				dlFileRankModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((dlFileRank.getCompanyId() != dlFileRankModelImpl.getOriginalCompanyId()) ||
				(dlFileRank.getUserId() != dlFileRankModelImpl.getOriginalUserId()) ||
				(dlFileRank.getFolderId() != dlFileRankModelImpl.getOriginalFolderId()) ||
				!Validator.equals(dlFileRank.getName(),
					dlFileRankModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_F_N,
				new Object[] {
					new Long(dlFileRank.getCompanyId()),
					new Long(dlFileRank.getUserId()),
					new Long(dlFileRank.getFolderId()),
					
				dlFileRank.getName()
				}, dlFileRank);
		}

		return dlFileRank;
	}

	protected DLFileRank toUnwrappedModel(DLFileRank dlFileRank) {
		if (dlFileRank instanceof DLFileRankImpl) {
			return dlFileRank;
		}

		DLFileRankImpl dlFileRankImpl = new DLFileRankImpl();

		dlFileRankImpl.setNew(dlFileRank.isNew());
		dlFileRankImpl.setPrimaryKey(dlFileRank.getPrimaryKey());

		dlFileRankImpl.setFileRankId(dlFileRank.getFileRankId());
		dlFileRankImpl.setGroupId(dlFileRank.getGroupId());
		dlFileRankImpl.setCompanyId(dlFileRank.getCompanyId());
		dlFileRankImpl.setUserId(dlFileRank.getUserId());
		dlFileRankImpl.setCreateDate(dlFileRank.getCreateDate());
		dlFileRankImpl.setFolderId(dlFileRank.getFolderId());
		dlFileRankImpl.setName(dlFileRank.getName());

		return dlFileRankImpl;
	}

	public DLFileRank findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public DLFileRank findByPrimaryKey(long fileRankId)
		throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = fetchByPrimaryKey(fileRankId);

		if (dlFileRank == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileRankId);
			}

			throw new NoSuchFileRankException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				fileRankId);
		}

		return dlFileRank;
	}

	public DLFileRank fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public DLFileRank fetchByPrimaryKey(long fileRankId)
		throws SystemException {
		DLFileRank dlFileRank = (DLFileRank)EntityCacheUtil.getResult(DLFileRankModelImpl.ENTITY_CACHE_ENABLED,
				DLFileRankImpl.class, fileRankId, this);

		if (dlFileRank == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileRank = (DLFileRank)session.get(DLFileRankImpl.class,
						new Long(fileRankId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (dlFileRank != null) {
					cacheResult(dlFileRank);
				}

				closeSession(session);
			}
		}

		return dlFileRank;
	}

	public List<DLFileRank> findByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<DLFileRank> list = (List<DLFileRank>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_DLFILERANK_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileRank>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileRank> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<DLFileRank> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileRank> list = (List<DLFileRank>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
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
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_DLFILERANK_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = (List<DLFileRank>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileRank>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileRank findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		List<DLFileRank> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileRank findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		int count = countByUserId(userId);

		List<DLFileRank> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileRank[] findByUserId_PrevAndNext(long fileRankId, long userId,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(3 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileRank);

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = (DLFileRank)objArray[0];
			array[1] = (DLFileRank)objArray[1];
			array[2] = (DLFileRank)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileRank> findByG_U(long groupId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		List<DLFileRank> list = (List<DLFileRank>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_DLFILERANK_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileRank>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileRank> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	public List<DLFileRank> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileRank> list = (List<DLFileRank>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(4 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_DLFILERANK_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<DLFileRank>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileRank>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileRank findByG_U_First(long groupId, long userId,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		List<DLFileRank> list = findByG_U(groupId, userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileRank findByG_U_Last(long groupId, long userId,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		int count = countByG_U(groupId, userId);

		List<DLFileRank> list = findByG_U(groupId, userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileRank[] findByG_U_PrevAndNext(long fileRankId, long groupId,
		long userId, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		int count = countByG_U(groupId, userId);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(4 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileRank);

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = (DLFileRank)objArray[0];
			array[1] = (DLFileRank)objArray[1];
			array[2] = (DLFileRank)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<DLFileRank> findByF_N(long folderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(folderId), name };

		List<DLFileRank> list = (List<DLFileRank>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_F_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_DLFILERANK_WHERE);

				query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_F_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_F_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_F_N_NAME_2);
					}
				}

				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileRank>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_F_N, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<DLFileRank> findByF_N(long folderId, String name, int start,
		int end) throws SystemException {
		return findByF_N(folderId, name, start, end, null);
	}

	public List<DLFileRank> findByF_N(long folderId, String name, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(folderId),
				
				name,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileRank> list = (List<DLFileRank>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_F_N,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (obc != null) {
					query = new StringBundler(4 +
							(obc.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_DLFILERANK_WHERE);

				query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_F_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_F_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_F_N_NAME_2);
					}
				}

				if (obc != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
				}

				else {
					query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				list = (List<DLFileRank>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileRank>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_F_N,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public DLFileRank findByF_N_First(long folderId, String name,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		List<DLFileRank> list = findByF_N(folderId, name, 0, 1, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileRank findByF_N_Last(long folderId, String name,
		OrderByComparator obc) throws NoSuchFileRankException, SystemException {
		int count = countByF_N(folderId, name);

		List<DLFileRank> list = findByF_N(folderId, name, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFileRankException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public DLFileRank[] findByF_N_PrevAndNext(long fileRankId, long folderId,
		String name, OrderByComparator obc)
		throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByPrimaryKey(fileRankId);

		int count = countByF_N(folderId, name);

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (obc != null) {
				query = new StringBundler(4 +
						(obc.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILERANK_WHERE);

			query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_F_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_F_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_F_N_NAME_2);
				}
			}

			if (obc != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);
			}

			else {
				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(folderId);

			if (name != null) {
				qPos.add(name);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					dlFileRank);

			DLFileRank[] array = new DLFileRankImpl[3];

			array[0] = (DLFileRank)objArray[0];
			array[1] = (DLFileRank)objArray[1];
			array[2] = (DLFileRank)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public DLFileRank findByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = fetchByC_U_F_N(companyId, userId, folderId, name);

		if (dlFileRank == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileRankException(msg.toString());
		}

		return dlFileRank;
	}

	public DLFileRank fetchByC_U_F_N(long companyId, long userId,
		long folderId, String name) throws SystemException {
		return fetchByC_U_F_N(companyId, userId, folderId, name, true);
	}

	public DLFileRank fetchByC_U_F_N(long companyId, long userId,
		long folderId, String name, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(userId), new Long(folderId),
				
				name
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_U_F_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(6);

				query.append(_SQL_SELECT_DLFILERANK_WHERE);

				query.append(_FINDER_COLUMN_C_U_F_N_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_U_F_N_USERID_2);

				query.append(_FINDER_COLUMN_C_U_F_N_FOLDERID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_C_U_F_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_U_F_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_U_F_N_NAME_2);
					}
				}

				query.append(DLFileRankModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
				}

				List<DLFileRank> list = q.list();

				result = list;

				DLFileRank dlFileRank = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_F_N,
						finderArgs, list);
				}
				else {
					dlFileRank = list.get(0);

					cacheResult(dlFileRank);

					if ((dlFileRank.getCompanyId() != companyId) ||
							(dlFileRank.getUserId() != userId) ||
							(dlFileRank.getFolderId() != folderId) ||
							(dlFileRank.getName() == null) ||
							!dlFileRank.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_F_N,
							finderArgs, dlFileRank);
					}
				}

				return dlFileRank;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U_F_N,
						finderArgs, new ArrayList<DLFileRank>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (DLFileRank)result;
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

	public List<DLFileRank> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<DLFileRank> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<DLFileRank> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<DLFileRank> list = (List<DLFileRank>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_DLFILERANK);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, obc);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_DLFILERANK.concat(DLFileRankModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (obc == null) {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileRank>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<DLFileRank>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserId(long userId) throws SystemException {
		for (DLFileRank dlFileRank : findByUserId(userId)) {
			remove(dlFileRank);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (DLFileRank dlFileRank : findByG_U(groupId, userId)) {
			remove(dlFileRank);
		}
	}

	public void removeByF_N(long folderId, String name)
		throws SystemException {
		for (DLFileRank dlFileRank : findByF_N(folderId, name)) {
			remove(dlFileRank);
		}
	}

	public void removeByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws NoSuchFileRankException, SystemException {
		DLFileRank dlFileRank = findByC_U_F_N(companyId, userId, folderId, name);

		remove(dlFileRank);
	}

	public void removeAll() throws SystemException {
		for (DLFileRank dlFileRank : findAll()) {
			remove(dlFileRank);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_DLFILERANK_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_DLFILERANK_WHERE);

				query.append(_FINDER_COLUMN_G_U_GROUPID_2);

				query.append(_FINDER_COLUMN_G_U_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByF_N(long folderId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(folderId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_F_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_DLFILERANK_WHERE);

				query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_F_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_F_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_F_N_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_F_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_U_F_N(long companyId, long userId, long folderId,
		String name) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Long(userId), new Long(folderId),
				
				name
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_U_F_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_DLFILERANK_WHERE);

				query.append(_FINDER_COLUMN_C_U_F_N_COMPANYID_2);

				query.append(_FINDER_COLUMN_C_U_F_N_USERID_2);

				query.append(_FINDER_COLUMN_C_U_F_N_FOLDERID_2);

				if (name == null) {
					query.append(_FINDER_COLUMN_C_U_F_N_NAME_1);
				}
				else {
					if (name.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_U_F_N_NAME_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_U_F_N_NAME_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				qPos.add(folderId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_U_F_N,
					finderArgs, count);

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

				Query q = session.createQuery(_SQL_COUNT_DLFILERANK);

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
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileRank")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileRank>> listenersList = new ArrayList<ModelListener<DLFileRank>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileRank>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence")
	protected com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence dlFolderPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected com.liferay.portal.service.persistence.LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static final String _SQL_SELECT_DLFILERANK = "SELECT dlFileRank FROM DLFileRank dlFileRank";
	private static final String _SQL_SELECT_DLFILERANK_WHERE = "SELECT dlFileRank FROM DLFileRank dlFileRank WHERE ";
	private static final String _SQL_COUNT_DLFILERANK = "SELECT COUNT(dlFileRank) FROM DLFileRank dlFileRank";
	private static final String _SQL_COUNT_DLFILERANK_WHERE = "SELECT COUNT(dlFileRank) FROM DLFileRank dlFileRank WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "dlFileRank.userId = ?";
	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "dlFileRank.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "dlFileRank.userId = ?";
	private static final String _FINDER_COLUMN_F_N_FOLDERID_2 = "dlFileRank.folderId = ? AND ";
	private static final String _FINDER_COLUMN_F_N_NAME_1 = "dlFileRank.name IS NULL";
	private static final String _FINDER_COLUMN_F_N_NAME_2 = "dlFileRank.name = ?";
	private static final String _FINDER_COLUMN_F_N_NAME_3 = "(dlFileRank.name IS NULL OR dlFileRank.name = ?)";
	private static final String _FINDER_COLUMN_C_U_F_N_COMPANYID_2 = "dlFileRank.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_F_N_USERID_2 = "dlFileRank.userId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_F_N_FOLDERID_2 = "dlFileRank.folderId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_F_N_NAME_1 = "dlFileRank.name IS NULL";
	private static final String _FINDER_COLUMN_C_U_F_N_NAME_2 = "dlFileRank.name = ?";
	private static final String _FINDER_COLUMN_C_U_F_N_NAME_3 = "(dlFileRank.name IS NULL OR dlFileRank.name = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileRank.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileRank exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileRank exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(DLFileRankPersistenceImpl.class);
}