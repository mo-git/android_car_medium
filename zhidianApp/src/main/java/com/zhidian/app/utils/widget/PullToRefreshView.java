package com.zhidian.app.utils.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;
import com.zhidian.app.R;
import com.zhidian.app.utils.PreferenceUtils;
import com.zhidian.app.utils.SysConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PullToRefreshView extends LinearLayout {
    // refresh states
    private static final int PULL_TO_REFRESH = 2;
    private static final int RELEASE_TO_REFRESH = 3;
    public static final int REFRESHING = 4;

    // pull state
    private static final int PULL_UP_STATE = 0;
    private static final int PULL_DOWN_STATE = 1;
    /**
     * last y
     */
    private int mLastMotionY;
    /**
     * lock
     */
    private boolean mLock;
    /**
     * header view
     */
    private View mHeaderView;
    /**
     * footer view
     */
    private View mFooterView;
    /**
     * list or grid
     */
    private AdapterView<?> mAdapterView;
    /**
     * scrollview
     */
    private ScrollView mScrollView;
    /**
     * header view height
     */
    private int mHeaderViewHeight;
    /**
     * footer view height
     */
    private int mFooterViewHeight;

    /**
     * 文字部分布局
     */
    private LinearLayout xlistview_header_text;

    /**
     * header tip text
     */
    private TextView mHeaderTextView;
    /**
     * footer tip text
     */
    private TextView mFooterTextView;
    /**
     * header refresh time
     */
    private TextView mHeaderUpdateTextView;

    /**
     * footer progress bar
     */
    private ProgressBar mFooterProgressBar;
    /**
     * layout inflater
     */
    private LayoutInflater mInflater;
    /**
     * header view current state
     */
    public int mHeaderState;
    /**
     * footer view current state
     */
    public int mFooterState;
    /**
     * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
     */
    private int mPullState;
    private Context mContext;

    /**
     * footer Or head refresh listener
     */
    OnPullToRefreshListener onPullToRefreshListener;

    private int mTouchSlop;

    private boolean footerRefreshState = true;//是否可以加载更多 默认可以

    private boolean headerRefreshState = true;//是否可以下拉刷新 默认可以
    private int   screenWidth;
    private int ANIMDIST=80;
    private int width=0;
//    private int maxTVWidth;


    /**
     * last update time
     */
    // private String mLastUpdateTime;
    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public PullToRefreshView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    /**
     * init
     *
     * @description
     */
    private void init() {

        final ViewConfiguration configuration = ViewConfiguration
                .get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();

        mInflater = LayoutInflater.from(getContext());
        addHeaderView();

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void addHeaderView() {
        // header view
        mHeaderView = mInflater.inflate(R.layout.xlistview_header, this, false);

        xlistview_header_text= (LinearLayout) mHeaderView.findViewById(R.id.xlistview_header_text);

        mHeaderTextView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_hint_textview);
        mHeaderUpdateTextView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
        // header layout
        measureView(mHeaderView);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                mHeaderViewHeight);
        params.topMargin = -(mHeaderViewHeight);
        addView(mHeaderView, params);

    }

    private void addFooterView() {
        // footer view
        mFooterView = mInflater.inflate(R.layout.xlistview_footer, this, false);
        mFooterTextView = (TextView) mFooterView.findViewById(R.id.xlistview_footer_hint_textview);
        mFooterProgressBar = (ProgressBar) mFooterView.findViewById(R.id.xlistview_footer_progressbar);
        // footer layout
        measureView(mFooterView);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mFooterViewHeight);
        addView(mFooterView, params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addFooterView();
        initContentAdapterView();
    }

    /**
     * init AdapterView like ListView,GridView and so on;or init ScrollView
     */
    private void initContentAdapterView() {
        int count = getChildCount();
        if (count < 3) {
            throw new IllegalArgumentException(
                    "this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
        }
        View view = null;
        for (int i = 0; i < count - 1; ++i) {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>) {
                mAdapterView = (AdapterView<?>) view;
            }
            if (view instanceof ScrollView) {
                // finish later
                mScrollView = (ScrollView) view;
            }
        }
        if (mAdapterView == null && mScrollView == null) {
            throw new IllegalArgumentException(
                    "must contain a AdapterView or ScrollView in this layout!");
        }
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int y = (int) e.getRawY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                final int distance = Math.abs(deltaY);
                final boolean overscroll = getScrollY() != 0;
                if (overscroll || distance > mTouchSlop) {
                    if (isRefreshViewScroll(deltaY)) {
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }



    //指定动画结束位置
    public  void setAnimStopLocation(){
        setHeaderTopMargin(0);
        if(mHeaderUpdateTextView != null && TextUtils.isEmpty(mHeaderUpdateTextView.getText().toString().trim())){
            mHeaderUpdateTextView.setText(PreferenceUtils.getPrefString(mContext,SysConstants.REFRESHLASTTIME,getCurrentTime()));
        }
        xlistview_header_text.setVisibility(View.VISIBLE);
        this.invalidate();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mLock) {
            return true;
        }
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                if (mPullState == PULL_DOWN_STATE&&headerRefreshState) {
                    headerPrepareToRefresh(deltaY);

                } else if (mPullState == PULL_UP_STATE && footerRefreshState) {
                    footerPrepareToRefresh(deltaY);
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int topMargin = getHeaderTopMargin();
                if (mPullState == PULL_DOWN_STATE&&headerRefreshState) {
                    if (topMargin >= 0) {
                        headerRefreshing();
                    } else {
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                } else if (mPullState == PULL_UP_STATE && footerRefreshState) {
                    if (Math.abs(topMargin) >= mHeaderViewHeight/2
                            + mFooterViewHeight) {
                        footerRefreshing();
                    } else {
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean isRefreshViewScroll(int deltaY) {
        if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
            return false;
        }
        if (mAdapterView != null) {
            if (deltaY > 0) {
                View child = mAdapterView.getChildAt(0);
                if (child == null) {
                    return false;
                }
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                int top = child.getTop();
                int padding = mAdapterView.getPaddingTop();
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && Math.abs(top - padding) <= 8) {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }

            } else if (deltaY < 0) { //往上拉
                View lastChild = mAdapterView.getChildAt(mAdapterView
                        .getChildCount() - 1);
                if (lastChild == null) {
                    return false;
                }
                if (lastChild.getBottom() <= getHeight()
                        && mAdapterView.getLastVisiblePosition() == mAdapterView
                        .getCount() - 1) {
                    mPullState = PULL_UP_STATE;
                    return true;
                }
            }
        }
        if (mScrollView != null) {
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0) {
                mPullState = PULL_DOWN_STATE;
                return true;
            } else if (deltaY < 0
                    && child.getMeasuredHeight() <= getHeight()
                    + mScrollView.getScrollY()) {
                mPullState = PULL_UP_STATE;
                return true;
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void headerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        xlistview_header_text.setVisibility(View.VISIBLE);
        if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
            mHeaderState = RELEASE_TO_REFRESH;
        } else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {
            mHeaderState = PULL_TO_REFRESH;
        }
    }

    private void footerPrepareToRefresh(int deltaY) {
        setFooterViewVisibility(View.VISIBLE);
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        if (Math.abs(newTopMargin) >= (mHeaderViewHeight/2 + mFooterViewHeight)
                && mFooterState != RELEASE_TO_REFRESH) {
            mFooterTextView.setText(R.string.pull_to_refresh_footer_release_label);
            mFooterState = RELEASE_TO_REFRESH;
        } else if (Math.abs(newTopMargin) < (mHeaderViewHeight/2 + mFooterViewHeight)) {
            mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
            mFooterState = PULL_TO_REFRESH;
        }
    }

    private int changingHeaderViewTopMargin(int deltaY) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * 0.3f;
        if (deltaY > 0 && mPullState == PULL_UP_STATE
                && Math.abs(params.topMargin) <= mHeaderViewHeight) {
            return params.topMargin;
        }
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE
                && Math.abs(params.topMargin) >= mHeaderViewHeight) {
            return params.topMargin;
        }
        params.topMargin = (int) newTopMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
        return params.topMargin;
    }

    public void headerRefreshing() {
        mHeaderState = REFRESHING;
        if(width==0){//证明没有滑动,是直接调用的刷新方法
            setAnimStopLocation();
        }else{
            setHeaderTopMargin(0);
        }
        width=0;
        if (onPullToRefreshListener != null) {
            onPullToRefreshListener.onRefresh(this);
        }
    }

    private void footerRefreshing() {
        mFooterState = REFRESHING;
        int top = mHeaderViewHeight + mFooterViewHeight;
        setHeaderTopMargin(-top);
        mFooterProgressBar.setVisibility(View.VISIBLE);
        mFooterTextView.setText(R.string.pull_to_refresh_footer_refreshing_label);
        if (onPullToRefreshListener != null) {
            onPullToRefreshListener.onLoad(this);
        }
    }

    public void footerRefreshingV2() {
        mFooterState = REFRESHING;
        if (onPullToRefreshListener != null) {
            onPullToRefreshListener.onLoad(this);
        }
    }

    public void setFooterViewVisibility(int visibility){
        if(mFooterView!=null&&visibility!=mFooterView.getVisibility())
            mFooterView.setVisibility(visibility);
    }

    private void setHeaderTopMargin(int topMargin) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        params.topMargin = topMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
    }
    

    /**
     * 获取当前系统时间
     * @return
     */
    private String getCurrentTime(){
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date());
    }

    /**
     * Resets the list to a normal state after a refresh.
     *
     */
    public void stopRefresh() {
        setLastUpdated();
        setHeaderTopMargin(-mHeaderViewHeight);
        xlistview_header_text.setVisibility(View.GONE);
        mHeaderState = PULL_TO_REFRESH;
    }

    public void stopLoadMore() {
        setHeaderTopMargin(-mHeaderViewHeight);
        mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
        mFooterProgressBar.setVisibility(View.GONE);
        mFooterState = PULL_TO_REFRESH;
    }

    /**
     * Set a text to represent when the list was last updated.
     *            Last updated at.
     */
    public void setLastUpdated() {
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderUpdateTextView.setText(getCurrentTime());
        PreferenceUtils.setPrefString(mContext, SysConstants.REFRESHLASTTIME,getCurrentTime());
    }

    private int getHeaderTopMargin() {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        return params.topMargin;
    }

    public void setOnPullToRefreshListener(
            OnPullToRefreshListener onPullToRefreshListener) {
        this.onPullToRefreshListener = onPullToRefreshListener;
    }


    /**
     * Interface definition for a callback to be invoked when list/grid footer
     * view should be refreshed.
     */
    public interface OnPullToRefreshListener {
        public void onLoad(PullToRefreshView view);
        public void onRefresh(PullToRefreshView view);
    }


    public void setRefreshFooterState(boolean state) {
        this.footerRefreshState = state;
    }
    public void setRefreshHeaderState(boolean state) {
        this.headerRefreshState = state;
    }


    /**
     * 下拉距离监听
     */
    private OnPulldownListener pulldownListener;
    public void setPulldownListener(OnPulldownListener pulldownListener) {
        this.pulldownListener = pulldownListener;
    }
    public interface OnPulldownListener{
        void onPulldownDp(float distance);
    }

}
