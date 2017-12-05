package com.pccb.newapp.mvp.product;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.pccb.newapp.R;
import com.pccb.newapp.base.BaseFragment;
import com.pccb.newapp.bean.Tender;
import com.pccb.newapp.bean.TenderListResponse;
import com.pccb.newapp.util.PccbUtils;
import com.pccb.newapp.util.ToastUtil;
import com.pccb.newapp.view.pulltorefreshview.AbPullToRefreshView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cgc on 2017/12/1
 */
public class ProductItemFragment extends BaseFragment implements ProductContract.View {
    //传递参数-产品类型
    private static final String EXTRA_TENDER_TYPE = "extra_tender_type";

    //产品分类-普资你我
    public static final String TENDER_TYPE_PERSONAL = "PZ_PERSONAL";
    //产品分类-普资华企
    public static final String TENDER_TYPE_ENTERPRISE = "PZ_ENTERPRISE";
    //产品分类-转让市场
    public static final String TENDER_TYPE_CLAIM = "PZ_CLAIM";

    @BindView(R.id.ptr_pull)
    AbPullToRefreshView mPtrPull;
//    @BindView(R.id.tv_fragment_name)
//    TextView mTvFragmentName;
    @BindView(R.id.rv_product_list)
    RecyclerView mRvProductList;

    //产品类型
    private String mTenderType = "";

    ProductPresenter mProductPresent;

    //产品列表
    private List<Tender> mProductList;
    private ProductListAdapter mAdapter;

    //判断是刷新还是加载更多
    private boolean mIsRefresh = true;

    //当前页号
    private int mCurPage = 1;
    //总页数
    private int mTotalPage = 0;

    // loading
    ZLoadingDialog dialog;

    private TenderListResponse mTenderListResponse;

    public static ProductItemFragment newInstance(String categarytags) {
        ProductItemFragment fragment = new ProductItemFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TENDER_TYPE, categarytags);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTenderType = getArguments().getString(EXTRA_TENDER_TYPE);
            Logger.d("ProductItemFragment onCreate mTenderType = " + mTenderType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProductPresent = new ProductPresenter(this);
        intView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mTenderListResponse != null) {
            handleTenderList(mTenderListResponse);
        }
    }

    void intView() {
        //mTvFragmentName.setText(mTenderType);
        Logger.d("ProductItemFragment intView() mTenderType = " + mTenderType);

        // 打开关闭下拉刷新加载更多功能
        mPtrPull.setOnHeaderRefreshListener(view -> {
            mProductList = null;
            mIsRefresh = true;
            mCurPage = 1;
            mProductPresent.getTenderListData(mTenderType, mCurPage);
        });
        mPtrPull.setOnFooterLoadListener(view -> {
            mIsRefresh = false;
            if (mTotalPage > mCurPage) {
                mCurPage += 1;
                mProductPresent.getTenderListData(mTenderType, mCurPage);
            } else {
                mPtrPull.onFooterLoadFinish();
                ToastUtil.showToast(getActivity(), "没有更多数据");
            }
        });

        mProductPresent.getTenderListData(mTenderType, mCurPage);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick(R.id.tv_fragment_name)
    public void onViewClicked() {
    }


    @Override
    public void setPresenter(ProductContract.Presenter presenter) {

    }

    @Override
    public void onShowTenderList(TenderListResponse response) {
        if (response.getTotalRows() == 0) {
            //mEmptyLayout.setVisibility(View.VISIBLE);
            mRvProductList.setVisibility(View.GONE);
        } else {
            // 保存
            mTenderListResponse = response;

            //mEmptyLayout.setVisibility(View.GONE);
            mRvProductList.setVisibility(View.VISIBLE);
            handleTenderList(response);
        }

    }

    /**
     * 显示产品列表
     * @param tenderListResponse
     */
    private void handleTenderList(TenderListResponse tenderListResponse) {
        mTotalPage = tenderListResponse.getTotalPages();
        if (tenderListResponse.isSuccess()) {
            if (mIsRefresh) {
                //第一页
                mProductList = tenderListResponse.getRows();
                mAdapter = new ProductListAdapter(getActivity(), mProductList);
                mRvProductList.setAdapter(mAdapter);
            } else {
                //上拉加载页
                mProductList.addAll(tenderListResponse.getRows());
                mAdapter.notifyDataSetChanged();
            }
        } else {
            String msg = TextUtils.isEmpty(tenderListResponse.getResultDetail()) ?
                    tenderListResponse.getResultMessage() : tenderListResponse.getResultDetail();
            PccbUtils.showAlertDialog(getActivity(), msg);
        }
    }

    @Override
    public void onHeaderRefreshFinish() {
        mPtrPull.onHeaderRefreshFinish();
    }

    @Override
    public void onFooterLoadFinish() {
        mPtrPull.onFooterLoadFinish();
    }

    @Override
    public void onShowWaitDialog() {
        dialog = new ZLoadingDialog(mContext);
        Z_TYPE type = Z_TYPE.ROTATE_CIRCLE;
        dialog.setLoadingBuilder(type)
                .setLoadingColor(Color.BLACK)
                .setHintText("Loading...")
                .setHintTextSize(16) // 设置字体大小
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .show();
    }

    @Override
    public void onHideWaitDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onShowToast(String msg) {
        ToastUtil.showToast(mActivity, msg);
    }

    @Override
    public Activity returnActiviy() {
        return mActivity;
    }

    @Override
    public Context returnContext() {
        return mContext;
    }


}
