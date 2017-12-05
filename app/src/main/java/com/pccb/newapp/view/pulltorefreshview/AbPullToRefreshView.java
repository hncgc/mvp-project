/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pccb.newapp.view.pulltorefreshview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * 名称：AbPullToRefreshView.java
 * 描述：下拉刷新和加载更多的View.
 */
public class AbPullToRefreshView extends LinearLayout {

    /**
     * 上下文.
     */
    private Context mContext = null;

    /**
     * 下拉刷新的开关.
     */
    private boolean mEnablePullRefresh = true;

    /**
     * 加载更多的开关.
     */
    private boolean mEnableLoadMore = true;

    /**
     * y上一次保存的.
     */
    private float mLastMotionY;

    /**
     * x上一次保存的.
     */
    private float mLastMotionX;

    /**
     * header view.
     */
    private PullRefreshHeaderView mHeaderView;

    /**
     * footer view.
     */
    private LoadMoreFooterView mFooterView;

    /**
     * list or grid.
     */
    private AdapterView<?> mAdapterView;
    private AbsListView mAbsListView;

    /**
     * 是否允许在list或grid模式下没有子项时刷新和加载
     */
    private boolean mEnablePullAndLoadWithNoChild = true;

    /**
     * Scrollview.
     */
    ScrollView mScrollView;

    /**
     * NestedScrollView
     */
    private NestedScrollView mNestedScrollView;

    /**
     * RecyclerView
     */
    RecyclerView mRecyclerView;

    /**
     * header view 高度.
     */
    private int mHeaderViewHeight;

    /**
     * footer view 高度.
     */
    private int mFooterViewHeight;

    /**
     * 滑动状态.
     */
    private int mPullState;

    /**
     * 上滑动作.
     */
    private static final int PULL_UP_STATE = 0;

    /**
     * 下拉动作.
     */
    private static final int PULL_DOWN_STATE = 1;

    /**
     * 正在下拉刷新.
     */
    private boolean mPullRefreshing = false;

    /**
     * 正在加载更多.
     */
    private boolean mPullLoading = false;

    /**
     * Footer加载更多监听器.
     */
    private OnFooterLoadListener mOnFooterLoadListener;

    /**
     * Header下拉刷新监听器.
     */
    private OnHeaderRefreshListener mOnHeaderRefreshListener;

    /**
     * adapter count
     */
    private int mCount;

    /**
     * 动画显示时间
     */
    private static int animtime = 200;

    /**
     * 构造.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public AbPullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 构造.
     *
     * @param context the context
     */
    public AbPullToRefreshView(Context context) {
        super(context);
        init(context);
    }

    /**
     * 初始化View.
     *
     * @param context the context
     */
    private void init(Context context) {
        mContext = context;
        setOrientation(VERTICAL);
        // 增加HeaderView
        addHeaderView();
    }

    /**
     * add HeaderView.
     */
    private void addHeaderView() {
        mHeaderView = new PullRefreshHeaderView(mContext);
        mHeaderViewHeight = mHeaderView.getHeaderHeight();
        mHeaderView.setGravity(Gravity.BOTTOM);
        addView(mHeaderView);
    }

    /**
     * add FooterView.
     */
    private void addFooterView() {
        mFooterView = new LoadMoreFooterView(mContext);
        mFooterViewHeight = mFooterView.getFooterHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mFooterViewHeight);
        addView(mFooterView, params);
        mFooterView.setVisibility(View.GONE);
    }

    /**
     * 在此添加footer view保证添加到linearlayout中的最后.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addFooterView();
        initContentAdapterView();
        initRefreshView();
    }

    private void initRefreshView() {
        mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
    }

    /**
     * initView AdapterView like ListView,
     * GridView and so on;
     * or initView ScrollView.
     */
    private void initContentAdapterView() {
        int count = getChildCount();
        if (count < 3) {
            throw new IllegalArgumentException("this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
        }
        View view = null;
        for (int i = 0; i < count - 1; ++i) {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>) {
                mAdapterView = (AdapterView<?>) view;
            }
            if (view instanceof AbsListView) {
                mAbsListView = (AbsListView) view;
            }
            if (view instanceof ScrollView) {
                // finish later
                mScrollView = (ScrollView) view;
            }

            if (view instanceof NestedScrollView) {
                mNestedScrollView = (NestedScrollView) view;
            }
            if (view instanceof RecyclerView) {
                mRecyclerView = (RecyclerView) view;
            }
        }
        if (mAdapterView == null && mScrollView == null && mRecyclerView == null && mNestedScrollView == null) {
            throw new IllegalArgumentException("must contain a AdapterView or ScrollView or mRecyclerView in this layout!");
        }
    }

    /* (non-Javadoc)
     * @see android.view.ViewGroup#onInterceptTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        float y = e.getRawY();
        float x = e.getRawX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 首先拦截down事件,记录y坐标
                mLastMotionY = y;
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                // deltaY > 0 是向下运动,< 0是向上运动
                float deltaY = y - mLastMotionY;
                float deltaX = x - mLastMotionX;

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    return false;
                }
                if (isRefreshViewScroll(deltaY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }

    /*
     * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
     * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
     */
    /* (non-Javadoc)
     * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // onInterceptTouchEvent已经记录 mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = y - mLastMotionY;
                if (mPullState == PULL_DOWN_STATE) {
                    // 执行下拉
                    headerPrepareToRefresh(deltaY);
                } else if (mPullState == PULL_UP_STATE) {
                    // 执行上拉
                    mFooterView.setVisibility(View.VISIBLE);
                    footerPrepareToRefresh(deltaY);
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mPullState == PULL_DOWN_STATE) {
                    int topPadding = mHeaderView.getPaddingTop();
                    if (topPadding >= 0) {
                        // 开始刷新
                        headerRefreshing();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        hiddenRefreshHeaderView(topPadding);
                    }
                } else if (mPullState == PULL_UP_STATE) {
                    LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
                    int topmagin = params.topMargin;
                    if (Math.abs(topmagin) >= mFooterViewHeight) {
                        // 开始执行footer 刷新
                        footerLoading();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        hiddenRefreshFooterView();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 判断滑动方向，和是否响应事件.
     *
     * @param deltaY deltaY > 0 是向下运动,< 0是向上运动
     * @return true, if is refresh view scroll
     */
    private boolean isRefreshViewScroll(float deltaY) {

        if (mPullRefreshing || mPullLoading) {
            return false;
        }
        // 对于ListView和GridView
        if (mAdapterView != null) {
            // 子view(ListView or GridView)滑动到最顶端
            if (deltaY > 0) {
                // 判断是否禁用下拉刷新操作
                if (!mEnablePullRefresh) {
                    return false;
                }
                View child = mAdapterView.getChildAt(0);
                if (!mEnablePullAndLoadWithNoChild && child == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                if (child == null)
                    child = mAdapterView;
                if (mAdapterView.getFirstVisiblePosition() == 0 && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                int top = child.getTop();
                int padding = mAdapterView.getPaddingTop();
                if (mAdapterView.getFirstVisiblePosition() == 0 && Math.abs(top - padding) <= 11) {// 这里之前用3可以判断,但现在不行,还没找到原因
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }

            } else if (deltaY < 0) {
                // 判断是否禁用上拉加载更多操作
                if (!mEnableLoadMore) {
                    return false;
                }
                View lastChild = mAdapterView.getChildAt(mAdapterView.getChildCount() - 1);
                if (lastChild == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                // 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
                // 等于父View的高度说明mAdapterView已经滑动到最后
                if (lastChild.getBottom() <= getHeight() && mAdapterView.getLastVisiblePosition() == mAdapterView.getCount() - 1) {
                    mPullState = PULL_UP_STATE;
                    return true;
                }
            }
        }
        // 对于ScrollView
        if (mScrollView != null) {
            // 子scroll view滑动到最顶端
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0) {
                // 判断是否禁用下拉刷新操作
                if (!mEnablePullRefresh) {
                    return false;
                }
                mPullState = PULL_DOWN_STATE;
                return true;
            } else if (deltaY < 0 && child.getMeasuredHeight() <= getHeight() + mScrollView.getScrollY()) {
                // 判断是否禁用上拉加载更多操作
                if (!mEnableLoadMore) {
                    return false;
                }
                mPullState = PULL_UP_STATE;
                return true;
            }
        }
        // 对于mRecyclerView
        if (mRecyclerView != null) {
            // 子view滑动到最顶端
            if (deltaY > 0) {
                // 判断是否禁用下拉刷新操作
                if (!mEnablePullRefresh) {
                    return false;
                }
                View child = mRecyclerView.getLayoutManager().getChildAt(0);
                if (!mEnablePullAndLoadWithNoChild && child == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                if (child == null)
                    child = mRecyclerView;
                if (mRecyclerView.getTop() == 0
                        && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                int top = child.getTop();
                int padding = mRecyclerView.getPaddingTop();
                if (mRecyclerView.getTop() == 0
                        && Math.abs(top - padding) <= 11) {// 这里之前用3可以判断,但现在不行,还没找到原因
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
            } else if (deltaY < 0) {
                // 判断是否禁用上拉加载更多操作
                if (!mEnableLoadMore) {
                    return false;
                }
                LinearLayoutManager lManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                //View lastChild = mRecyclerView.getChildAt(mRecyclerView.getAdapter().getItemCount()-1);
                View lastChild = lManager.getChildAt(lManager.getChildCount() - 1);
                if (lastChild == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                // 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
                // 等于父View的高度说明mAdapterView已经滑动到最后
                if (lastChild.getBottom() <= getHeight() && lManager.findLastCompletelyVisibleItemPosition() == mRecyclerView.getAdapter().getItemCount() - 1) {
                    mPullState = PULL_UP_STATE;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * header 准备刷新,手指移动过程,还没有释放.
     *
     * @param deltaY 手指滑动的距离
     */
    private void headerPrepareToRefresh(float deltaY) {
        if (mPullRefreshing || mPullLoading) {
            return;
        }
        float newTopPadding = updateHeaderViewTopPadding(deltaY);
        // 当header view的topMargin>=0时，说明header view完全显示出来了 ,修改header view 的提示状态
        if (newTopPadding >= 0 && mHeaderView.getState() != PullRefreshHeaderView.STATE_REFRESHING) {
            //提示松开刷新
            mHeaderView.setState(PullRefreshHeaderView.STATE_READY);
        } else if (newTopPadding < 0 && newTopPadding > -mHeaderViewHeight) {
            //提示下拉刷新
            mHeaderView.setState(PullRefreshHeaderView.STATE_NORMAL);
        }
    }

    /**
     * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header view
     * 高度是一样，都是通过修改header view的topmargin的值来达到.
     *
     * @param deltaY 手指滑动的距离
     */
    private void footerPrepareToRefresh(float deltaY) {
        if (mPullRefreshing || mPullLoading) {
            return;
        }
        float newBottomPadding = updateFooterViewBottomPadding(deltaY);
        // 如果header view topMargin 的绝对值大于或等于header + footer 的高度
        // 说明footer view 完全显示出来了，修改footer view 的提示状态
        if (Math.abs(newBottomPadding) >= mFooterViewHeight && mFooterView.getState() != LoadMoreFooterView.STATE_LOADING) {
            mFooterView.setState(LoadMoreFooterView.STATE_NO);
        } else if (Math.abs(newBottomPadding) < mFooterViewHeight) {
            mFooterView.setState(LoadMoreFooterView.STATE_READY);
        }
    }

    /**
     * 修改footer view top padding的值.
     *
     * @param deltaY the delta y
     * @return the int
     */
    private float updateFooterViewBottomPadding(float deltaY) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        float newBottomMargin = params.topMargin + deltaY * 0.3f;
        // 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了
        // 表示如果是在上拉后一段距离,然后直接下拉
        if (deltaY > 0 && mPullState == PULL_UP_STATE && Math.abs(params.topMargin) <= mHeaderViewHeight) {
            return params.topMargin;
        }
        params.topMargin = (int) newBottomMargin;
        mHeaderView.setLayoutParams(params);
        if (mScrollView != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
        if (mRecyclerView != null) {
            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (mRecyclerView.getAdapter() != null && mRecyclerView.getAdapter().getItemCount() > 0) {
                layoutManager.scrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
            }
        }
        if (mAbsListView != null) {
            if (mAbsListView.getAdapter() != null && mAbsListView.getAdapter().getCount() > 0) {
                mAbsListView.smoothScrollToPosition(mAbsListView.getAdapter().getCount() - 1);
            }
        }
        invalidate();
        return params.topMargin;
    }

    /**
     * 修改Header view top padding的值.
     *
     * @param deltaY the delta y
     * @return the int
     */
    private float updateHeaderViewTopPadding(float deltaY) {
        float newTopPadding = mHeaderView.getPaddingTop() + deltaY * 0.3f;
        // 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了
        // 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE && Math.abs(newTopPadding) >= mHeaderViewHeight) {
            return mHeaderView.getPaddingTop();
        }
        mHeaderView.setPadding(0, (int) newTopPadding, 0, 0);
        invalidate();
        return mHeaderView.getPaddingTop();
    }

    /**
     * 刷新开始状态
     */
    public void headerRefreshing() {
        mPullRefreshing = true;
        mHeaderView.setState(PullRefreshHeaderView.STATE_REFRESHING);
        changeRefreshHeaderViewToZero();
        if (mOnHeaderRefreshListener != null) {
            mOnHeaderRefreshListener.onHeaderRefresh(this);
        }
    }

    /**
     * 加载更多.
     */
    private void footerLoading() {
        changeRefreshFooterViewToZero();
        mPullLoading = true;
        if (mOnFooterLoadListener != null) {
            mOnFooterLoadListener.onFooterLoad(this);
        }
    }

    /**
     * 隐藏下拉刷新控件，带动画
     */
    private void hiddenRefreshHeaderView() {
        ValueAnimator animator = ValueAnimator.ofInt(0, -mHeaderViewHeight);
        animator.setDuration(animtime);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingTop = (Integer) animation.getAnimatedValue();
                mHeaderView.setPadding(0, paddingTop, 0, 0);
            }
        });
        animator.start();
        animator.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                RefreshHeaderStatus();
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
            }
        });
    }

    /**
     * 隐藏下拉刷新控件，带动画
     */
    private void hiddenRefreshHeaderView(int x) {
        ValueAnimator animator = ValueAnimator.ofInt(x, -mHeaderViewHeight);
        animator.setDuration(animtime);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingTop = (Integer) animation.getAnimatedValue();
                mHeaderView.setPadding(0, paddingTop, 0, 0);
            }
        });
        animator.start();
        animator.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                RefreshHeaderStatus();
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
            }
        });
    }

    /**
     * 隐藏上拉刷新控件，带动画
     */
    private void hiddenRefreshFooterView() {
        final LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, 0);
        animator.setDuration(animtime);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingTop = (Integer) animation.getAnimatedValue();
                params.topMargin = paddingTop;
                mHeaderView.setLayoutParams(params);
            }
        });
        animator.start();
        animator.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                RefreshFooterStatus();
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
            }
        });
    }

    /**
     * 设置下拉刷新控件的paddingTop到0，带动画
     */
    private void changeRefreshHeaderViewToZero() {
        ValueAnimator animator = ValueAnimator.ofInt(mHeaderView.getPaddingTop(), 0);
        animator.setDuration(animtime);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingTop = (Integer) animation.getAnimatedValue();
                mHeaderView.setPadding(0, paddingTop, 0, 0);
            }
        });
        animator.start();
    }

    /**
     * 设置上拉刷新控件的paddingTop到0，带动画
     */
    private void changeRefreshFooterViewToZero() {
        final LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, -mFooterViewHeight);
        animator.setDuration(animtime);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int paddingTop = (Integer) animation.getAnimatedValue();
                params.topMargin = paddingTop;
                mHeaderView.setLayoutParams(params);
            }
        });
        animator.start();
    }

    /**
     * header view 完成更新后恢复初始状态.
     */
    public void onHeaderRefreshFinish() {
        hiddenRefreshHeaderView();
        mHeaderView.setState(PullRefreshHeaderView.STATE_FINISH);
    }

    public void RefreshHeaderStatus() {
        if (mPullRefreshing) {
            mHeaderView.setState(PullRefreshHeaderView.STATE_READY);
            if (mAdapterView != null) {
                mCount = mAdapterView.getCount();
                //判断有没有数据
                if (mCount > 0) {
                    mFooterView.setState(LoadMoreFooterView.STATE_READY);
                } else {
                    mFooterView.setState(LoadMoreFooterView.STATE_EMPTY);
                }
            } else {
                mFooterView.setState(LoadMoreFooterView.STATE_READY);
            }
            mPullRefreshing = false;
        } else {
            mHeaderView.setState(PullRefreshHeaderView.STATE_READY);
            if (mAdapterView != null) {
                mCount = mAdapterView.getCount();
                //判断有没有数据
                if (mCount > 0) {
                    mFooterView.setState(LoadMoreFooterView.STATE_READY);
                } else {
                    mFooterView.setState(LoadMoreFooterView.STATE_EMPTY);
                }
            } else {
                mFooterView.setState(LoadMoreFooterView.STATE_READY);
            }
            mPullRefreshing = false;
        }
    }

    /**
     * footer view 完成更新后恢复初始状态.
     */
    public void onFooterLoadFinish() {
        hiddenRefreshFooterView();
        mHeaderView.setState(PullRefreshHeaderView.STATE_NORMAL);

    }

    public void RefreshFooterStatus() {
        if (mAdapterView != null) {
            int countNew = mAdapterView.getCount();
            //判断有没有更多数据了
            if (countNew > mCount) {
                mFooterView.setState(LoadMoreFooterView.STATE_READY);
                mPullLoading = false;
                mCount = countNew;
            } else {
                mFooterView.setState(LoadMoreFooterView.STATE_NO);
                if (mPullLoading) {
                    mPullLoading = false;
                }
            }
        } else {
            mFooterView.setState(LoadMoreFooterView.STATE_READY);
            mPullLoading = false;
        }
    }

    /**
     * 设置下拉刷新的监听器.
     *
     * @param headerRefreshListener the new on header refresh listener
     */
    public void setOnHeaderRefreshListener(OnHeaderRefreshListener headerRefreshListener) {
        mOnHeaderRefreshListener = headerRefreshListener;
    }

    /**
     * 设置加载更多的监听器.
     *
     * @param footerLoadListener the new on footer load listener
     */
    public void setOnFooterLoadListener(OnFooterLoadListener footerLoadListener) {
        mOnFooterLoadListener = footerLoadListener;
    }


    /**
     * 打开或者关闭下拉刷新功能.
     *
     * @param enable 开关标记
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
    }

    /**
     * 打开或者关闭加载更多功能.
     *
     * @param enable 开关标记
     */
    public void setLoadMoreEnable(boolean enable) {
        mEnableLoadMore = enable;
    }

    /**
     * 下拉刷新是打开的吗.
     *
     * @return true, if is enable pull refresh
     */
    public boolean isEnablePullRefresh() {
        return mEnablePullRefresh;
    }

    /**
     * 加载更多是打开的吗.
     *
     * @return true, if is enable load more
     */
    public boolean isEnableLoadMore() {
        return mEnableLoadMore;
    }

    /**
     * 设置是否允许没有子控件时刷新/加载
     *
     * @param mEnablePullAndLoadWithNoChild
     */
    public void setmEnablePullAndLoadWithNoChild(boolean mEnablePullAndLoadWithNoChild) {
        this.mEnablePullAndLoadWithNoChild = mEnablePullAndLoadWithNoChild;
    }

    /**
     * 描述：获取Header View.
     *
     * @return the header view
     */
    public PullRefreshHeaderView getHeaderView() {
        return mHeaderView;
    }

    /**
     * 描述：获取Footer View.
     *
     * @return the footer view
     */
    public LoadMoreFooterView getFooterView() {
        return mFooterView;
    }

    /**
     * Interface definition for a callback to be invoked when list/grid footer
     * view should be refreshed.
     *
     * see OnFooterLoadEvent
     */
    public interface OnFooterLoadListener {

        /**
         * On footer load.
         *
         * @param view the view
         */
        public void onFooterLoad(AbPullToRefreshView view);
    }

    /**
     * Interface definition for a callback to be invoked when list/grid header
     * view should be refreshed.
     *
     * see OnHeaderRefreshEvent
     */
    public interface OnHeaderRefreshListener {

        /**
         * On header refresh.
         *
         * @param view the view
         */
        public void onHeaderRefresh(AbPullToRefreshView view);
    }

}
